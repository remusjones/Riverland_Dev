package riverland.dev.riverland;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.bukkit.Location;

import java.io.Serializable;

public class SerializableTNT implements Serializable
{

    @Expose
    @SerializedName("world")
    public String world;
    @Expose
    @SerializedName("x")
    public float x;
    @Expose
    @SerializedName("y")
    public float y;
    @Expose
    @SerializedName("z")
    public float z;
    @Expose
    @SerializedName("tntType")
    public Integer tntType;


    public void Set(Location loc, Integer type)
    {
        x = loc.getBlockX();
        y = loc.getBlockY();
        z = loc.getBlockZ();
        world = loc.getWorld().getName();
        tntType = type;
    }

}
