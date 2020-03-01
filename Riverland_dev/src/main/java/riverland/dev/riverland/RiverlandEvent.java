package riverland.dev.riverland;

import net.minecraft.server.v1_15_R1.EntityLiving;
import net.minecraft.server.v1_15_R1.EntityTypes;

import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RiverlandEvent
{
    // entity and location pairs for mobs
    private Map<Location, EntityTypes<EntityLiving>> defaultMobSpawnLocations = new HashMap<>();
    // entity and location pairs for boss's
    private Map<Location, EntityTypes<EntityLiving>> bossMobSpawnLocations = new HashMap<>();
    private Map<Integer, String> eventOpenAnnouncements = new HashMap<>();
    private Map<Integer, String> eventDuringAnnouncements  = new HashMap<>();

    private Integer eventOpenTicks = 120;
    private Integer eventStartTicks = 300;
    private Boolean isEventComplete = false;


    public void SetDefaultMobLocations(ArrayList<Location> mobLocations)
    {
      //  defaultMobSpawnLocations = mobLocations;
    }


}
