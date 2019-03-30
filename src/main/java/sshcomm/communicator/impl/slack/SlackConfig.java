package sshcomm.communicator.impl.slack;

import lombok.Builder;
import lombok.Getter;

@Builder
public class SlackConfig {
    @Getter final private String authToken;
    @Getter final private String senderName;
    @Getter final private String receiverName;
}
