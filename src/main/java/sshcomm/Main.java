package sshcomm;

import sshcomm.bindings.ClientBindings;
import sshcomm.bindings.ServerBindings;
import com.google.inject.Guice;
import com.google.inject.Injector;
import sshcomm.communicator.Receiver;
import sshcomm.ssh.client.SSHClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Main {
    public static void main(String[] args) throws Exception {

        if (args.length != 3) {
            throw new Exception("need 3 argument to start comm systems:");
        }

        String configPath = Utils.getConfigFilePathMap().get(args[0]);
        if (args[0].contains("server")) {
            Injector injector = Guice.createInjector(new ServerBindings(configPath));
            Runnable rx = (Runnable) injector.getInstance(Receiver.class);

            ExecutorService ex = new ScheduledThreadPoolExecutor(10);
            ex.submit(rx);
        } else if (args[0].contains("client")) {
            Injector injector = Guice.createInjector(new ClientBindings(configPath));
            Runnable rx = (Runnable) injector.getInstance(SSHClient.class);
            ExecutorService ex = new ScheduledThreadPoolExecutor(10);
            ex.submit(rx);
        }
    }
}
