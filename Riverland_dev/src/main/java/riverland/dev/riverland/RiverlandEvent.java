package riverland.dev.riverland;

public class RiverlandEvent
{
    protected RiverlandEventManager riverlandEventManager = null;
    protected Boolean isEventComplete = false;

    public RiverlandEvent(RiverlandEventManager manager)
    {
        riverlandEventManager = manager;
    }
    public boolean RiverlandEventInit()
    {
        return false;
    }
    public void InvokeManagerEventFinish()
    {
        isEventComplete = true;
        riverlandEventManager.FinishEvent(this);
    }
}
