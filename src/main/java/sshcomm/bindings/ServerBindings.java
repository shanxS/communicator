package sshcomm.bindings;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import sshcomm.communicator.Receiver;
import sshcomm.communicator.Transmitter;
import sshcomm.communicator.impl.slack.SlackConfig;
import sshcomm.communicator.impl.slack.SlackInitializer;
import sshcomm.communicator.impl.slack.SlackRx;
import sshcomm.communicator.impl.slack.SlackTx;
import sshcomm.ssh.server.shim.ServerShim;
import sshcomm.Utils;


public class ServerBindings extends AbstractModule {

    final private String configFilePath;
    public ServerBindings(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    @Override
    protected void configure() {
        bind(Transmitter.class).to(SlackTx.class);
        bind(Receiver.class).to(ServerShim.class);
        bind(SlackRx.class).asEagerSingleton();
    }

    @Provides
    SlackConfig provideSlackConfig() {
        SlackInitializer slackInitializer = new SlackInitializer();
        slackInitializer.init(Utils.getProp(configFilePath));
        return slackInitializer.getConfig();
    }

}
