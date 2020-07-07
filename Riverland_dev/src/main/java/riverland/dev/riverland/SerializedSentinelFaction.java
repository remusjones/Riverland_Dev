package riverland.dev.riverland;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.UUID;

public class SerializedSentinelFaction
{
    @Expose
    @SerializedName("factionName")
    String factionID = "";

    @Expose
    @SerializedName("npcUUID")
    ArrayList<UUID> NpcUUID = new ArrayList<>();

    @Expose
    @SerializedName("UnusedNPCs")
    short storedNPCS;
}
