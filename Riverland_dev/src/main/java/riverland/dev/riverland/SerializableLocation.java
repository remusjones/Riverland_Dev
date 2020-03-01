package riverland.dev.riverland;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.bukkit.Location;

import java.io.Serializable;

public class SerializableLocation
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
    @SerializedName("yaw")
    public float yaw;

    @Expose
    @SerializedName("pitch")
    public float pitch;

    public void Set(Location loc)
    {
        x = loc.getBlockX();
        y = loc.getBlockY();
        z = loc.getBlockZ();
        yaw = loc.getYaw();
        pitch = loc.getPitch();
        world = loc.getWorld().getName();
    }
    public Location Get()
    {
        return new Location(Riverland._Instance.getServer().getWorld(world), x,y,z,yaw,pitch);
    }

}
