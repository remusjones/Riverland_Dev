package riverland.dev.riverland;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

//Handles events and commands.
public class RiverlandEventManagerListener implements CommandExecutor
{
    private RiverlandEventManager riverlandEventManager = null;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (riverlandEventManager == null)
            riverlandEventManager = Riverland._Instance.riverlandEventManager;

        if (sender.isOp())
        {
            // compare against eventManager command list
            if (args.length > 0)
            {
                //eventmanager start thumbus
                boolean isStart = args[0].toLowerCase().contains("start"); // check if the event is starting or stopping..
                if (isStart)
                {
                    if (riverlandEventManager.eventStartCommandList.contains(args[1].toUpperCase()))
                    {

                        RiverlandEventType event = RiverlandEventType.valueOf(args[1].toUpperCase()); // cast as enum safely
                        boolean success = riverlandEventManager.BeginEvent(event); // start event.
                        if (event == null)
                        {
                            Riverland._Instance.getServer().broadcastMessage("Could not find event");
                        }else {
                            Riverland._Instance.getServer().broadcastMessage("success: " + success + " does not match: " + " " +  RiverlandEventType.THUMBUS.toString() + " | " + event.toString());
                        }

                    }

                    return true;

                }
            }
        }
        Riverland._Instance.getServer().broadcastMessage("Test false");
        // should only be executed by an event driven protocol
        return false;
    }
}
