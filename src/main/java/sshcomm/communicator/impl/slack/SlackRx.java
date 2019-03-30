package sshcomm.communicator.impl.slack;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import sshcomm.communicator.Receiver;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;

@Slf4j
public class SlackRx {
    private final String myName;

    @Inject
    public SlackRx(SlackConfig config, Receiver receiver) {

        this.myName = config.getSenderName();
        SlackSession session = SlackSessionFactory.createWebSocketSlackSession(config.getAuthToken());


        try {
            session.connect();

            SlackMessagePostedListener messagePostedListener = new SlackMessagePostedListener() {
                @Override
                public void onEvent(SlackMessagePosted event, SlackSession session) {
                    SlackChannel channelOnWhichMessageWasPosted = event.getChannel();
                    String messageContent = event.getMessageContent();
                    SlackUser messageSender = event.getSender();

                    if (!messageSender.getUserName().equals(myName)) {
                        receiver.receive(messageContent);
                    }
                    log.error("Receive on channel " + channelOnWhichMessageWasPosted + " sender: " + messageSender);

                }
            };

            session.addMessagePostedListener(messagePostedListener);

        } catch (IOException e) {
            log.error("SlackRx cant connect: " + e.getStackTrace());
        }
    }
}
