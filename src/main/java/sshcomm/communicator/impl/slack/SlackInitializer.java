package sshcomm.communicator.impl.slack;

import sshcomm.communicator.CommInitializer;
import lombok.Getter;

import java.util.Properties;

public class SlackInitializer implements CommInitializer {
    @Getter private SlackConfig config;

    @Override
    public void init(Properties properties) {
        this.config = SlackConfig.builder()
                   .authToken(properties.getProperty("authToken"))
                   .senderName(properties.getProperty("sender"))
                   .receiverName(properties.getProperty("receiver"))
                   .build();

    }
}
