package de.albtraum.commands;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandArguments {
    private final MessageReceivedEvent msgReceivedEvent;
    private final String rawArgs;

    public CommandArguments(MessageReceivedEvent msgReceivedEvent, String rawArgs) {
        this.msgReceivedEvent = msgReceivedEvent;
        this.rawArgs = rawArgs;
    }

    public String[] getArgs() {
        return rawArgs.replace("\n", " ").split(" ");
    }

    public String getRawArgs() {
        return rawArgs;
    }

    public String getRawMessage() {
        return msgReceivedEvent.getMessage().getContentRaw();
    }

    public MessageChannel getChannel() {
        return msgReceivedEvent.getChannel();
    }

    public String getChannelID() {
        return msgReceivedEvent.getChannel().getId();
    }

    public Member getSenderMember() {
        return msgReceivedEvent.getMember();
    }

    public User getSenderUser() {
        return msgReceivedEvent.getAuthor();
    }

    public MessageReceivedEvent getMsgReceivedEvent() {
        return msgReceivedEvent;
    }

    public Message getMessage() {
        return msgReceivedEvent.getMessage();
    }

    public Guild getGuild() {
        return msgReceivedEvent.getGuild();
    }
}

