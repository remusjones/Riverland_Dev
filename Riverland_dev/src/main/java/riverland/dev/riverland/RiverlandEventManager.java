package riverland.dev.riverland;

import java.util.ArrayList;

public class RiverlandEventManager
{
    ArrayList<RiverlandEvent> activeRiverlandEvents = new ArrayList<>();
    // commands will be start + contains(args[1])
    // commands will be stop + contains(args[1])
    public ArrayList<String> eventStartCommandList = new ArrayList<String>();
    public RiverlandEventManager()
    {
        eventStartCommandList.add(RiverlandEventType.THUMBUS.toString());   // add enum string to command list
        eventStartCommandList.add(RiverlandEventType.PVPDUELS.toString());  // add enum string to command list
        eventStartCommandList.add(RiverlandEventType.DROPPARTY.toString()); // add enum string to command list
        eventStartCommandList.add(RiverlandEventType.PVPFFA.toString());    // add enum string to command list
    }

    public boolean BeginEvent(RiverlandEventType event)
    {
        switch (event)
        {
            case THUMBUS:
            {
                ThumbusEvent newEvent = new ThumbusEvent(this);
                activeRiverlandEvents.add(newEvent);
                newEvent.RiverlandEventInit();
                return true;
            }
            case PVPFFA:
            {
                return false; // not implemented
            }
            case PVPDUELS:
            {
                // start pvpevent
            }
            case DROPPARTY:
            {
                // invoke a command, rather than a full event ??
            }
        }
        return true;
    }

    // disposes of the event.
    public void FinishEvent(RiverlandEvent event)
    {
        activeRiverlandEvents.remove(event);
        event = null;
    }
}
