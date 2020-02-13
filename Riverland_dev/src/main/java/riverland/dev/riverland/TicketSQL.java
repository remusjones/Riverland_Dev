package riverland.dev.riverland;


// sql include
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
// calender include..
import java.util.*;
import java.util.Date;
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
    public ArrayList<Integer> removeDataBaseIssueID = new ArrayList<Integer>();
    public int highestTicketID = 0;

    public TicketSQL(String _host, int _port, String _prefix,String _dataBase, String _userName, String _password)
    {
        // load mySQL Settings..
        host = _host;
        port = _port;
        prefix = _prefix;
        database = _dataBase;
        username = _userName;
        password = _password;
    }
/*
ResultSet result = statement.executeQuery("SELECT * FROM PlayerData WHERE BALANCE = 0;");
List<String> bankruptPlayers = new ArrayList<String>();
while (result.next()) {
    String name = result.getString("PLAYERNAME");
    bankruptPlayers.add(name);
}
 */
    // User 1
    // user report, user pos, status = Status.Working
    // user report 2, user pos 2, status = Status.Unassigned
    // User 2
    // user report. user pos, status = Status.Finished


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

                    while (result.next())
                    {
                        int currID = result.getInt("TICKET_ID");
                        if (currID > highestTicketID)
                            highestTicketID = currID;

                        String issue = result.getString("PLAYER_NAME") + " : " + result.getString("MSG");
                        storedTicketIssues.put(currID, issue);
                    }

                    Set<Map.Entry<Player, String>> map = SqlToWrite.entrySet();
                    int iter = 1;
                    for (Map.Entry<Player, String> pair : map)
                    {
                        try
                        {
                            Integer id = highestTicketID + iter;
                            statement.executeUpdate(
                                    "INSERT INTO " +
                                            "TICKETS " +
                                            "(TICKET_ID, PLAYER_NAME, MSG) " +
                                            "VALUES (" + id + "," + "'" + pair.getKey().getDisplayName() + "', '" + pair.getValue() + "');"); //TODO: substring pair.getValue() to not exceed table value size..
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
                        Riverland._Instance.getLogger().log(Level.INFO, "Removed ID's.. ");
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
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
        }
    }
}
