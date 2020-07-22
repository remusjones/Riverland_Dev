package riverland.dev.riverland;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.ChannelManager;
import net.dv8tion.jda.api.managers.EmoteManager;
import net.dv8tion.jda.api.requests.restaction.GuildAction;
import net.dv8tion.jda.internal.requests.Route;

import javax.security.auth.login.LoginException;
import java.nio.channels.Channel;
import java.util.logging.Level;

public class RiverlandDiscordBot
{
    private MessageChannel targetChannel = null;
    private Guild targetGuild = null;
    public String defaultToken = "Njc5MDU3NzIwOTE3MDk4NDk2.XxUedQ.ru158e8NhL6VhQMMbwyS-nJNzds";
    public String defaultChannel = "734628142865449040";
    JDA jda = null;
    public RiverlandDiscordBot() throws LoginException, InterruptedException {
        jda = JDABuilder.createDefault(defaultToken).build().awaitReady();
        jda.addEventListener(new RiverlandDiscordReactionListener());
        jda.getPresence().setActivity(Activity.watching("the shitters ruin the server"));


    }
    public void SendMessageToChannel(String textData)
    {
        if (targetGuild == null || targetChannel == null) {
            targetGuild = jda.getGuilds().get(0);
            targetChannel = targetGuild.getTextChannelById(defaultChannel);;
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
    public void ResolveOriginalMessage(Integer ticketID)
    {
        // here we want to search back, to the ticket that we had stored..
        // resolve, and delete from SQL..
        if (targetGuild == null || targetChannel == null) {
            targetGuild = jda.getGuilds().get(0);
            targetChannel = targetGuild.getTextChannelById(defaultChannel);;
        }
    }
    public void SendMessageToChannel(EmbedBuilder msg)
    {
        if (targetGuild == null || targetChannel == null) {
            targetGuild = jda.getGuilds().get(0);
            targetChannel = targetGuild.getTextChannelById(defaultChannel);;
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
    public void SendMessageToChannel(EmbedBuilder msg, boolean placeEmote)
    {
        if (targetGuild == null || targetChannel == null) {
            targetGuild = jda.getGuilds().get(0);
            targetChannel = targetGuild.getTextChannelById(defaultChannel);;
        }

        try {

            if (placeEmote)
            {
                targetChannel.sendMessage(msg.build()).queue(message -> {
                    message.addReaction("✅").queue();
                });
                Long id = targetChannel.getLatestMessageIdLong();

              //  targetChannel.retrieveMessageById(id).complete().addReaction(Emote);
            }else
            {
                targetChannel.sendMessage(msg.build()).queue();
            }
            msg.clear();
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }


}
