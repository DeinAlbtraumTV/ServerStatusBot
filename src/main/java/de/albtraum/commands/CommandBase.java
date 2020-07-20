package de.albtraum.commands;

import de.albtraum.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

public abstract class CommandBase {
    final String command;
    final String[] aliases;
    final String description;

    private CommandBase() {
        this.command = description = "";
        this.aliases = new String[0];
    }

    protected CommandBase(String cmd) {
        this.command = cmd;
        this.description = "";
        this.aliases = new String[0];
    }

    protected CommandBase(String cmd, String[] aliases) {
        this.command = cmd;
        this.description = "";
        this.aliases = aliases;
    }

    protected CommandBase(String cmd, String description) {
        this.command = cmd;
        this.aliases = new String[0];
        this.description = description;
    }

    protected CommandBase(String cmd, String description, String... aliases) {
        this.command = cmd;
        this.aliases = aliases;
        this.description = description;
    }

    /**
     * Override to add code to run when using this command
     *
     * @return if this command was successful
     */
    public abstract boolean run(final CommandArguments args);

    public String getCommandSuffix() {
        return "";
    }

    /**
     * Override this to return true to only allow admins to use this command. Will be hidden in the help command
     */
    boolean isAdminCommand() {
        return false;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }

    /**
     * Sends an message string to the specified channel id
     */
    protected final void sendMessage(final String message, final String channelID) {
        Objects.requireNonNull(Main.bot.getTextChannelById(channelID)).sendMessage(message).queue();
    }

    /**
     * Sends an message to the specified channel id
     */
    protected final void sendMessage(final Message message, final String channelID) {
        Objects.requireNonNull(Main.bot.getTextChannelById(channelID)).sendMessage(message).queue();
    }

    public boolean canExecute(final MessageReceivedEvent ev) {
        return !isAdminCommand() || Objects.requireNonNull(ev.getMember()).hasPermission(Permission.ADMINISTRATOR);
    }
}
