package de.albtraum.commands;

import de.albtraum.utils.INetUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CommandMCServerStatus extends CommandBase{
    public CommandMCServerStatus() {
        super("mc-server-status", "Get information on a minecraft server running on the ip provided", "mc-status", "mcserverstatus", "mcstatus");
    }

    @Override
    public boolean run(CommandArguments args) {
        args.getChannel().sendTyping().queue();
        System.out.println((args.getRawArgs()));
        try {
            URL url = new URL("https://api.mcsrvstat.us/2/" + args.getRawArgs());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            StringBuilder result = new StringBuilder();

            Scanner sc = new Scanner(url.openStream());
            while(sc.hasNext())
            {
                result.append(sc.nextLine());
            }
            sc.close();

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(result.toString());

            if (jsonObject.get("online").equals(false)) {
                sendMessage(new MessageBuilder(new EmbedBuilder().setDescription("The server with the ip: " + args.getRawArgs() + " is either offline or unreachable.").setFooter("Created by DeinAlbtraum#6224 || [GitHub](https://github.com/DeinAlbtraumTV/ServerStatusBot)").setColor(new Color(255, 127, 0)).build()).build(), args.getChannelID());
            } else if (jsonObject.get("online").equals(true)){
                JSONObject players = (JSONObject) jsonObject.get("players");
                sendMessage(new MessageBuilder(
                        new EmbedBuilder()
                                .setTitle(args.getRawArgs())
                                .setDescription(
                                        "The server is currently online!\n " +
                                                "IP: " + jsonObject.get("ip") + "\n " +
                                                "Hostname: " + jsonObject.get("hostname") + "\n " +
                                                "Port: " + jsonObject.get("port") + "\n " +
                                                "Version: " + jsonObject.get("version") + "\n " +
                                                "Ping: " + INetUtils.ping((String) jsonObject.get("hostname")) + "\n \n " +
                                                "Players online: " + players.get("online") + "/" + players.get("max")
                                        )
                                        .setFooter("Created by DeinAlbtraum#6224 || [GitHub](https://github.com/DeinAlbtraumTV/ServerStatusBot)")
                                        .setColor(new Color(255, 127, 0))
                                        .build())
                                .build()
                                , args.getChannelID());
            } else {
                sendMessage(new MessageBuilder(new EmbedBuilder().setDescription("Failed to retrieve the information from the API.\n Please try again later.").setFooter("Created by DeinAlbtraum#6224 || [GitHub](https://github.com/DeinAlbtraumTV/ServerStatusBot)").setColor(Color.RED).build()).build(), args.getChannelID());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    boolean isAdminCommand() {
        return false;
    }
}
