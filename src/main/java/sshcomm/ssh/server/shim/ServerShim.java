package sshcomm.ssh.server.shim;

import com.google.inject.Inject;
import sshcomm.communicator.Receiver;
import sshcomm.communicator.Transmitter;

import java.util.Scanner;

public class ServerShim implements Receiver, Runnable {
    private Transmitter transmitter;

    @Inject
    public ServerShim(Transmitter tx) {
        this.transmitter = tx;
    }

    @Override
    public void receive(String message) {
        System.out.println(message);
    }

    @Override
    public void run() {
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String cmd = scanner.next();
            transmitter.send(cmd);
        }
    }
}
