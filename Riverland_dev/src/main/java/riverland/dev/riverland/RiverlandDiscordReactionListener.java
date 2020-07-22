package riverland.dev.riverland;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class RiverlandDiscordReactionListener extends ListenerAdapter {
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        if (Riverland.discordBot.defaultChannel.equalsIgnoreCase(event.getChannel().getId()) == false)
            return;

        MessageReaction reaction = event.getReaction();
        MessageReaction.ReactionEmote emote = reaction.getReactionEmote();
        MessageChannel channel = event.getChannel();
        Message message = event.getChannel().retrieveMessageById(event.getMessageId()).complete();
        if (emote.getEmoji().equals("✅") == false)
            return;
        if (message.getEmbeds().size() == 0)
            return;



        MessageEmbed embed = message.getEmbeds().get(0);
        if (event.getUser().isBot())
            return;
        boolean success = false;
        for (MessageEmbed.Field field : embed.getFields()) {
            if (field.getName().equalsIgnoreCase("id")) {


                Integer id;
                try {
                    // cast to integer
                    id = Integer.parseInt(field.getValue());
                } catch (Exception exc) {
                    break;
                }
                if (Riverland._InstanceRiverLandTicket.storedTicketIssues.get(id) != null) {
                    if (Riverland.discordBot != null) {
                        //Riverland.discordBot.SendMessageToChannel(player.getDisplayName() + " has resolved ticket issue: \n```" + target +" | " + Riverland._InstanceRiverLandTicket.storedTicketIssues.get(target) + "```");
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setColor(Color.green);
                        eb.setTitle("Ticket Completed");
                        eb.addField("Ticket: ", Riverland._InstanceRiverLandTicket.storedTicketIssues.get(id), false);

                        eb.setFooter("Resolved by: " + event.getMember().getNickname());
                        eb.setThumbnail("https://lesspestcontrol.com.au/wp-content/uploads/green-tick.png");
                        Riverland.discordBot.SendMessageToChannel(eb);
                        Riverland._InstanceRiverLandTicket.removeDataBaseIssueID.add(id);
                        success = true;
                    }
                }
            }
        }
        if (success) {
            channel.deleteMessageById(event.getMessageId()).queue();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        boolean found = event.getMessage().getContentRaw().equalsIgnoreCase("!purge");

        if (found && Riverland.discordBot.defaultChannel.equalsIgnoreCase(event.getChannel().getId()))
        {
            event.getChannel().getIterableHistory().queue(result -> {
               event.getChannel().purgeMessages(result);
            });
        }
    }
}
