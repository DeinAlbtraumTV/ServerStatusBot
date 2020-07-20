package de.albtraum.commands;

import de.albtraum.config.ConfigController;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;

import java.util.Arrays;

public class CommandHelp extends CommandBase {
    public CommandHelp() {
        super("help", "Shows a list of all commands");
    }

    @Override
    public boolean run(final CommandArguments args) {
        final EmbedBuilder b = new EmbedBuilder();
        b.setTitle("Command List");
        for (final CommandBase cmd : CommandRegistry.commands) {
            b.addField(ConfigController.cfg.commandPrefix + cmd.getCommand() + " ", (cmd.getDescription().isEmpty() ? "No description provided." : cmd.getDescription()) + (cmd.getAliases().length > 0 ? "\nAliases: " + Arrays.toString(cmd.getAliases()).replaceAll("\\[", "").replaceAll("]", "") : ""), false);
        }
        sendMessage(new MessageBuilder(b).build(), args.getChannelID());
        return true;
    }
}

