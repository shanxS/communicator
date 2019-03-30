package sshcomm.bindings;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import sshcomm.Utils;
import sshcomm.communicator.Receiver;
import sshcomm.communicator.Transmitter;
import sshcomm.communicator.impl.slack.SlackConfig;
import sshcomm.communicator.impl.slack.SlackInitializer;
import sshcomm.communicator.impl.slack.SlackRx;
import sshcomm.communicator.impl.slack.SlackTx;
import sshcomm.ssh.client.SSHClient;
import sshcomm.ssh.client.SSHInputStream;
import sshcomm.ssh.client.SSHOutputStream;
import sshcomm.ssh.client.impl.JSchSSH;

import java.io.InputStream;
import java.io.OutputStream;

public class ClientBindings extends AbstractModule {

    final private String configFilePath;
    public ClientBindings(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    @Override
    protected void configure() {
        bind(Transmitter.class).to(SlackTx.class);
        bind(SSHInputStream.class).in(Singleton.class);
        bind(Receiver.class).to(SSHInputStream.class);
        bind(SlackRx.class).asEagerSingleton();

        bind(OutputStream.class).to(SSHOutputStream.class);


        bind(InputStream.class).to(SSHInputStream.class);
        bind(SSHClient.class).to(JSchSSH.class);
    }

    @Provides
    SlackConfig provideSlackConfig() {
        SlackInitializer slackInitializer = new SlackInitializer();
        slackInitializer.init(Utils.getProp(configFilePath));
        return slackInitializer.getConfig();
    }
}

