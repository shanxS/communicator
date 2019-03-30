package sshcomm.communicator.impl.slack;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import sshcomm.communicator.Transmitter;

import javax.inject.Inject;
import java.io.IOException;

public class SlackTx implements Transmitter {

    private final SlackSession session;
    private final String userName;
    private SlackUser user;

    @Inject
    public SlackTx(SlackConfig config) {
            this.session = SlackSessionFactory.createWebSocketSlackSession(config.getAuthToken());
            this.userName = config.getReceiverName();
    }

    @Override
    public void send(String message) {
        if (!session.isConnected()) {
            try {
                session.connect();
            } catch (IOException e) {
                System.out.println("Exception while connecting to slack: " + e.getMessage());
            }
        }

        if (user == null) {
            this.user = session.findUserByUserName(userName);
        }

        session.sendMessageToUser(user, message, null);
    }
}
