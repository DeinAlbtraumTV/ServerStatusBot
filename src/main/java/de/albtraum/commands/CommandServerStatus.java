package de.albtraum.commands;

import de.albtraum.utils.INetUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;

import java.awt.*;
import java.io.IOException;

public class CommandServerStatus extends CommandBase{
    public CommandServerStatus() {
        super("server-status", "Get the Status of a server", "serverstatus");
    }

    @Override
    public boolean run(CommandArguments args) {
        long ping;

        try {
            ping = INetUtils.ping(args.getRawArgs());
        } catch (IOException e) {
            sendMessage(new MessageBuilder(new EmbedBuilder().setDescription("Failed to connect to server. \n The server has to be without http/https, like this: 'www.google.com' not this: 'https://www.google.com' \n Please try again later.").setFooter("Created by DeinAlbtraum#6224 || [GitHub](https://github.com/DeinAlbtraumTV/ServerStatusBot)").setColor(Color.RED).build()).build(), args.getChannelID());
            return true;
        }

        if (ping != 9999L) {
            sendMessage(new MessageBuilder(
                            new EmbedBuilder()
                                    .setTitle(args.getRawArgs())
                                    .setDescription("The server is online! \n Ping: " + ping + "\n Link: https://" + args.getRawArgs())
                                    .setColor(new Color(255, 127, 0))
                                    .build())
                            .build()
                    , args.getChannelID());
        } else {
            sendMessage(new MessageBuilder(new EmbedBuilder().setDescription("Failed to connect to server.\n Please try again later.").setFooter("Created by DeinAlbtraum#6224 || [GitHub](https://github.com/DeinAlbtraumTV/ServerStatusBot)").setColor(Color.RED).build()).build(), args.getChannelID());
        }
        return true;
    }

    @Override
    boolean isAdminCommand() {
        return false;
    }
}
