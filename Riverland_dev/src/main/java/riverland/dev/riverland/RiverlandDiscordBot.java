package riverland.dev.riverland;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.ChannelManager;
import net.dv8tion.jda.api.requests.restaction.GuildAction;
import net.dv8tion.jda.internal.requests.Route;

import javax.security.auth.login.LoginException;
import java.nio.channels.Channel;
import java.util.logging.Level;

public class RiverlandDiscordBot
{
    private MessageChannel targetChannel = null;
    private Guild targetGuild = null;
    JDA jda = null;
    public RiverlandDiscordBot() throws LoginException, InterruptedException {
        jda = JDABuilder.createDefault("Njc5MDU3NzIwOTE3MDk4NDk2.XxUedQ.ru158e8NhL6VhQMMbwyS-nJNzds").build().awaitReady();
    }
    public void SendMessageToChannel(String textData)
    {
        if (targetGuild == null || targetChannel == null) {
            targetGuild = jda.getGuilds().get(0);
            targetChannel = targetGuild.getTextChannelById("734628142865449040");;
        }

        try {
            targetChannel.sendMessage(textData) /* => RestAction<Message> */
                    .queue(response /* => Message */ -> {
                    });

        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
    public void SendMessageToChannel(EmbedBuilder msg)
    {
        if (targetGuild == null || targetChannel == null) {
            targetGuild = jda.getGuilds().get(0);
            targetChannel = targetGuild.getTextChannelById("734628142865449040");;
        }

        try {
            targetChannel.sendMessage(msg.build()).queue();
            msg.clear();
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }


}
