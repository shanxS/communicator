package sshcomm.ssh.client;

import org.apache.commons.lang3.ArrayUtils;
import sshcomm.communicator.Transmitter;

import javax.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class SSHOutputStream extends OutputStream {

    private final int CARRIAGE_RETURN = 13;
    private final Transmitter transmitter;
    private ArrayList<Byte> stream;

    @Inject
    public SSHOutputStream(Transmitter transmitter) {
        this.transmitter = transmitter;
        stream = new ArrayList<>();
    }

    @Override
    public void write(int b) throws IOException {
        transmitter.send("[SSHOUTPUTSTREAM] implement this god damn function, kumar ");
    }

    @Override
    public void write(byte[] b, int off, int len) {
        // The general contract for write(b, off, len) is that some of the bytes in the array b are written to the output stream in order; element b[off] is the first byte written and b[off+len-1] is the last byte written by this operation.
        // https://docs.oracle.com/javase/7/docs/api/java/io/OutputStream.html#write(byte[],%20int,%20int)
        try {
            byte[] data = Arrays.copyOfRange(b, off, len+off);
            String dataString = new String(data);
            System.out.println(dataString);
            write(data);
        } catch (Exception e) {
            transmitter.send("[SSHOUTPUTSTREAM] error in write " + e.getMessage());
        }
    }

    @Override
    public void write(byte[] b) {
        transmitter.send(new String(b));
    }

    @Override
    public void flush() {
        // noop since this impl flushes everything as it receives it
    }

    @Override
    public void close() {
        transmitter.send("[SSHOUTPUTSTREAM] is closing");
    }
}
