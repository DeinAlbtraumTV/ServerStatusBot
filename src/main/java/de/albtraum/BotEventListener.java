package de.albtraum;

import de.albtraum.commands.CommandArguments;
import de.albtraum.commands.CommandBase;
import de.albtraum.commands.CommandRegistry;
import de.albtraum.utils.ArrayUtils;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import static de.albtraum.config.ConfigController.cfg;

public class BotEventListener implements EventListener {
    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent) {
            System.out.println("API is ready!");
        }
        if (event instanceof MessageReceivedEvent) {
            final MessageReceivedEvent ev = (MessageReceivedEvent) event;
            String rawMsg = ev.getMessage().getContentRaw();

            if (rawMsg.startsWith(cfg.commandPrefix)) {
                final String command = rawMsg.replaceFirst(cfg.commandPrefix, "").replace("\n", " ").split(" ")[0];
                final String rawArgs = rawMsg.replaceFirst(cfg.commandPrefix, "").replaceFirst(command + " ", "");
                //Execute command
                for (CommandBase cmd : CommandRegistry.commands) {
                    if (command.equals(cmd.getCommand()) || ArrayUtils.containsString(cmd.getAliases(), command)) {
                        if (cmd.canExecute(ev))
                            if (cmd.run(new CommandArguments(ev, rawArgs))) return;
                            else ev.getChannel().sendMessage("You don't have permission to use this command!").queue();
                    }
                }
            }
        }
        //Delete command output when command gets deleted
        if (event instanceof MessageDeleteEvent) {
            MessageDeleteEvent e = (MessageDeleteEvent) event;
            e.getTextChannel().getHistoryAfter(e.getMessageIdLong(), 1).queue((h) -> {
                for (int i = 0; i < h.getRetrievedHistory().size(); i++) {
                    if (h.getRetrievedHistory().get(i).getAuthor().getId().equals(Main.bot.getSelfUser().getId())) {
                        h.getRetrievedHistory().get(i).delete().queue();
                    }
                }
            });
        }
    }
}

