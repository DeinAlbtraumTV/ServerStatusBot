package de.albtraum;

import de.albtraum.commands.CommandHelp;
import de.albtraum.commands.CommandMCServerStatus;
import de.albtraum.commands.CommandRegistry;
import de.albtraum.commands.CommandServerStatus;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

import static de.albtraum.config.ConfigController.cfg;
import static de.albtraum.config.ConfigController.loadConfig;

public class Main {

    public static JDA bot;

    public static void main (String[] args) {

        JDABuilder builder;

        try {
            loadConfig();
            if (cfg == null) {
                System.err.println("No configuration, exiting!");
                System.exit(2);
            }
            if (cfg.botToken.equals("INSERT HERE") || cfg.botToken.isEmpty()) {
                System.err.println("Edit the file ./bot_config.json, you need to add the bot token");
                System.exit(1);
            }
            if (cfg.game.equals("INSERT HERE") || cfg.game.isEmpty()) {
                System.err.println("Edit the file ./bot_config.json, you need to set the game");
                System.exit(1);
            }

            CommandRegistry.registerCommands(new CommandHelp(), new CommandMCServerStatus(), new CommandServerStatus());

            builder = JDABuilder.create(cfg.botToken, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES);
            builder.addEventListeners(new BotEventListener())
                    .setStatus(OnlineStatus.ONLINE)
                    .setActivity(Activity.playing(cfg.game));

            bot = builder.build();
            System.out.println("\n\n\n\nInvite URL for this bot: https://discordapp.com/api/oauth2/authorize?client_id=" + bot.getSelfUser().getId() + "&permissions=92160&scope=bot");

        } catch (LoginException e) {
            System.err.println("Failed to log in:");
            e.printStackTrace();
            System.exit(1);
        }

    }
}
