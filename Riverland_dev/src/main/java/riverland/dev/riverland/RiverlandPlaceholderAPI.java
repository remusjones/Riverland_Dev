package riverland.dev.riverland;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.text.NumberFormat;

public class RiverlandPlaceholderAPI extends PlaceholderExpansion
{
    @Override
    public boolean canRegister(){
        return true;
    }
    /**
     * This method should always return true unless we
     * have a dependency we need to make sure is on the server
     * for our placeholders to work!
     *
     * @return always true since we do not have any dependencies.
     */
    /**
     * The name of the person who created this expansion should go here.
     *
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor(){
        return "The_Salty_Wizard";
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>This must be unique and can not contain % or _
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier(){
        return "Riverlands";
    }

    /**
     * This is the version of this expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion(){
        return "1.0.0";
    }

    /**
     * This is the method called when a placeholder with our identifier
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param  player
     *         A {@link org.bukkit.OfflinePlayer OfflinePlayer}.
     * @param  identifier
     *         A String containing the identifier/value.
     *
     * @return Possibly-null String of the requested identifier.
     */

    private String MercPurchasePrice = "Merc_Purchase_Price";
    private String MercUpkeepCost = "Merc_Upkeep_Cost";
    private String MercCurrentUpkeepCost = "Merc_Current_Upkeep_Cost";
    private String MercRent = "Merc_Rent";
    private String MercCost = "Merc_Cost";
    private String MercCurrentAmount = "Merc_Current_Amount_Total";
    private String MercCurrentStored = "Merc_Current_Stored_Total";
    private String MercCurrentUsed = "Merc_Current_Used_Total";
    private String MercUpkeepDue = "Merc_Upkeep_Due";
    @Override
    public String onRequest(OfflinePlayer player, String identifier){

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        // %Riverlands_MercPurchasePrice%
        // get next cost
        if(identifier.equalsIgnoreCase(MercPurchasePrice))
        {
            // get data
            NPCFaction npcFaction = Riverland._Instance.getNPCFaction(player.getPlayer());
            // get cost
            return formatter.format(npcFaction.getPurchaseCost()).replace("$","");
        }
        if(identifier.equalsIgnoreCase(MercCurrentAmount))
        {
            // get data
            NPCFaction npcFaction = Riverland._Instance.getNPCFaction(player.getPlayer());
            // get cost

            String cost = Short.toString(npcFaction.NPCCount);
            // return cost
            return cost;
        }
        if (identifier.equalsIgnoreCase(MercUpkeepDue))
        {
            NPCFaction npcFaction = Riverland._Instance.getNPCFaction(player.getPlayer());
            if (npcFaction.NPCCount > 0) {

                return Integer.toString(npcFaction.remainingHours);
            }else return Integer.toString(0);

        }
        if(identifier.equalsIgnoreCase(MercCurrentStored))
        {
            // get data
            NPCFaction npcFaction = Riverland._Instance.getNPCFaction(player.getPlayer());
            // get cost
            String cost = Short.toString(npcFaction.storedNPCs);
            // return cost
            return cost;
        }
        if(identifier.equalsIgnoreCase(MercCurrentUsed))
        {
            // get data
            NPCFaction npcFaction = Riverland._Instance.getNPCFaction(player.getPlayer());
            // get cost
            String cost = Integer.toString(npcFaction.NPCCount - npcFaction.storedNPCs);
            // return cost
            return cost;
        }

        // %Riverlands_MercUpkeepCost%
        if(identifier.equalsIgnoreCase(MercUpkeepCost)){
            // get data
            NPCFaction npcFaction = Riverland._Instance.getNPCFaction(player.getPlayer());
            if (!npcFaction.hasUpdatedUpkeepDataSinceLoad)
            {
                npcFaction.getUpkeepCost();
            }
            // get cost
            return npcFaction.storedUpkeepFormatted;
          // String cost = Double.toString(npcFaction.getUpkeepCost());
          // // return cost
          // return cost;
        }
        // %Riverlands_CurrentMercUpkeepCost%;
        if (identifier.equalsIgnoreCase(MercCurrentUpkeepCost))
        {
            NPCFaction npcFaction = Riverland._Instance.getNPCFaction(player.getPlayer());
            return formatter.format(npcFaction.getCurrentUpkeepCost()).replace("$","");
        }

        if (identifier.equalsIgnoreCase(MercRent))
        {
            NPCFaction npcFaction = Riverland._Instance.getNPCFaction(player.getPlayer());
            //  String cost = Double.toString(npcFaction.getCostPerNPC());
            return formatter.format(npcFaction.getCostPerNPC()).replace("$","");
           // return cost;
        }
        if (identifier.equalsIgnoreCase(MercCost))
        {
            NPCFaction npcFaction = Riverland._Instance.getNPCFaction(player.getPlayer());

            return formatter.format(npcFaction.getPurchaseCost()).replace("$","");
        }

        // We return null if an invalid placeholder (f.e. %example_placeholder3%)
        // was provided
        return null;
    }
}
