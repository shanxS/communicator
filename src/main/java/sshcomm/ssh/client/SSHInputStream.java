package sshcomm.ssh.client;

import sshcomm.communicator.Receiver;
import sshcomm.communicator.Transmitter;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Queue;

public class SSHInputStream extends InputStream implements Receiver {

    private Queue<Byte> input;
    private Transmitter transmitter;

    @Inject
    public SSHInputStream(Transmitter transmitter) {
        input = new ArrayDeque<>();
        this.transmitter = transmitter;
    }

    @Override
    public void receive(String message) {
        message += "\n";
        byte[] bytes = message.getBytes();
        for (int i=0; i<bytes.length; ++i) {
            input.add(bytes[i]);
        }
    }

    @Override
    public int read() throws IOException {
        if (input.size() == 0) {
            return -1;
        } else {
            return input.poll();
        }
    }

    @Override
    public int read(byte[] b) {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) {
        if (len == 0) {
            return 0;
        }

        if (input.size() == 0) {
            return 0;
        }

        final int maxReadSize = off + len;
        int actualReadSize = 0;
        while (input.size() > 0 && off < maxReadSize) {
            b[off] = input.poll();
            ++off;
            ++actualReadSize;
        }

        return actualReadSize;
    }

    @Override
    public long skip(long n) {
        int bytesSkipped = 0;
        while (input.size() > 0 && n > 0) {
            input.poll();
            ++bytesSkipped;
        }

        return bytesSkipped;
    }

    @Override
    public int available() {
        return input.size();
    }

    @Override
    public void close() {
        transmitter.send("SSHInputStream is closing");
        input = new ArrayDeque<>();
    }
}
