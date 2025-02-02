package riverland.dev.riverland;


// sql include
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.sql.*;
// calender include..
import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;


public class TicketSQL
{
    private Connection connection;
    private String host, database, username, password, prefix;
    private String ticket, user, message;
    private int port;
    public boolean _IsTaskRunning = false;
    Map<Player, String> SqlToWrite = new HashMap <Player, String>();
    // int ID / String info..
    public Map<Integer, String> storedTicketIssues = new HashMap<Integer, String>();
    public Map<String, Integer> playerSubmittedTicketIssues = new HashMap<String,Integer>();
    public Integer maxIssuesPerPlayer = 5;



    public ArrayList<Integer> removeDataBaseIssueID = new ArrayList<Integer>();
    public int highestTicketID = 0;

    public TicketSQL(String _host, int _port, String _prefix,String _dataBase, String _userName, String _password, Integer maxIssuesOpen)
    {
        // load mySQL Settings..
        host = _host;
        port = _port;
        prefix = _prefix;
        database = _dataBase;
        username = _userName;
        password = _password;
        maxIssuesPerPlayer = maxIssuesOpen;
    }

    public BukkitRunnable WriteSQLData = new BukkitRunnable()
    {

        @Override
        public void run()
        {
            _IsTaskRunning = true;
            try
            {
                    openConnection();
                    Statement statement = connection.createStatement();
                    java.util.Date date = new Date();
                    Object param = new java.sql.Timestamp(date.getTime());
                    storedTicketIssues.clear();
                    // dump issues..
                    ResultSet result = statement.executeQuery("SELECT * FROM TICKETS");
                    playerSubmittedTicketIssues.clear();
                    while (result.next())
                    {
                        int currID = result.getInt("TICKET_ID");
                        String playerName = result.getString("PLAYER_NAME");
                        if (currID > highestTicketID)
                            highestTicketID = currID;

                        String issue = playerName + " : " + result.getString("MSG");
                        storedTicketIssues.put(currID, issue);

                        // get player name, and compare it against our already stored issues..

                        if (playerSubmittedTicketIssues.containsKey(playerName))
                        {
                            // adds an increase to the player sum.
                            //playerSubmittedTicketIssues.merge(playerName, 1,Integer::);
                            playerSubmittedTicketIssues.merge(playerName, 1, (prev, one) -> prev + one);
                        }else // otherwise we init the list..
                            playerSubmittedTicketIssues.put(playerName, 1);

                    }

                    Set<Map.Entry<Player, String>> map = SqlToWrite.entrySet();
                    ArrayList<RiverlandDiscordTicketData> ticketDataList = new ArrayList<>();
                    int iter = 1;
                    for (Map.Entry<Player, String> pair : map)
                    {
                        try
                        {
                            Integer id = highestTicketID + iter;
                            RiverlandDiscordTicketData newData = new RiverlandDiscordTicketData();
                            newData.ID = id;
                            newData.message = pair.getValue();
                            newData.player = pair.getKey();
                            ticketDataList.add(newData);
                            statement.executeUpdate(
                                    "INSERT INTO " +
                                            "TICKETS " +
                                            "(TICKET_ID, PLAYER_NAME, MSG) " +
                                            "VALUES (" + id + "," + "'" + pair.getKey().getName() + "', '" + pair.getValue() + "');"); //TODO: substring pair.getValue() to not exceed table value size..
                            iter++;
                        }
                        catch (Exception exc)
                        {
                            // syntax issue likely to cause issues here..
                            Riverland._Instance.getLogger().log(Level.WARNING, exc.toString());
                        }

                    }
                    if (map.size() > 0)
                    {
                        Riverland._Instance.getLogger().log(Level.INFO, "Sending: " + map.size() + " pairs..");
                        for(Player player : Bukkit.getOnlinePlayers())
                        {
                            if (player.isOp() || player.hasPermission("riverland.OpAdminHelp"))
                            {
                                net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent( "New Ticket(s) in AdminHelp " );
                                message.setColor( ChatColor.GOLD );

                                net.md_5.bungee.api.chat.TextComponent click = new net.md_5.bungee.api.chat.TextComponent( " [Click to View]" );
                                click.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/opadminhelp display")); // if the user clicks the message, tp them to the coords
                                click.setColor( ChatColor.AQUA );
                                message.addExtra( click );

                                player.sendMessage(message);
                            }
                        }

                        if (Riverland.discordBot != null)
                        {
                            String messageToDiscord = "";
                            EmbedBuilder eb = new EmbedBuilder();
                            //MessageEmbed msg = new MessageEmbed(0,0,0,0,0,0,0,0,0,0,0,0,0)
                            for (RiverlandDiscordTicketData ticket : ticketDataList)
                            {
                                eb.setTitle("New Ticket Available");
                                eb.addField("ID", ticket.ID.toString(),false);
                                eb.setColor(Color.RED);
                                eb.setThumbnail("https://www.pe.com/wp-content/uploads/2018/01/rpe-bus-bestlaw-warning.jpg");
                                eb.addField(ticket.player.getDisplayName(),ticket.message,false);
                                Riverland.discordBot.SendMessageToChannel(eb,true);
                            }
                        }
                        SqlToWrite.clear(); // everything has been written, clear the list..
                    }
                    if (removeDataBaseIssueID.size() > 0) // removes index(s) specified..
                    {
                        for(Integer val : removeDataBaseIssueID)
                        {
                            try
                            {
                                statement.executeUpdate("DELETE FROM TICKETS WHERE TICKET_ID=" + val + ";");
                            }
                            catch (Exception exc)
                            {
                                // unknown index will cause issues here..
                                Riverland._Instance.getLogger().log(Level.WARNING, exc.toString());
                            }
                        }
                        removeDataBaseIssueID.clear();
                    }

            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    public void WriteTicketToServer(Player player, String userMsg)
    {
        SqlToWrite.put(player,userMsg);
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }
        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }

            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;
            Properties props = new Properties();
            props.setProperty("user",this.username);
            props.setProperty("password",this.password);
            props.setProperty("ssl","false");
            connection = DriverManager.getConnection(url, props);
            //connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);

        }
    }
}
