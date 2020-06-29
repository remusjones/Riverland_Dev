# Riverland_Dev

<H3> Getting Started </H3>
<b>Download / Install Intellij</b>

```
https://www.jetbrains.com/idea/
```
<h3></h3>
<b>Download Repository</b>

```
https://github.com/remusjones/Riverland_Dev/
```

<h3></h3>
<b>Open Project inside IntelliJ</b>

```
Riverland_Dev\Riverland_dev\.idea
```

<H3> Class Information </H3>

<H4> Riverland.java </h4>
  <b>Used to reload important, or data that can be modified at runtime</b>

  ```Java
      public void ConfigReload()
      {
          config = getConfig();
          tntRadiusDefault = config.getDouble("TNT_ExplosionRadius"); 
          tntRadiusHigh = config.getDouble("TNT_ExplosionRadiusHigh");
          tntRadiusLow = config.getDouble("TNT_ExplosionRadiusLow");
          tntBunkerBusterRange = config.getDouble("TNT_BunkerBusterRange");
          tntBreakChance = config.getDouble("TNT_BreakChance");
      }
  ```
  <H4></h4>
  <b>Used to set important, or data that can be modified at runtime</b>

  ```Java
      public void UpdateConfig()
      {
          config.set("TNT_ExplosionRadiusHigh", tntRadiusHigh);
          config.set("TNT_ExplosionRadius", tntRadiusDefault);
          config.set("TNT_ExplosionRadiusLow", tntRadiusLow);
          config.set("TNT_BunkerBusterRange", tntBunkerBusterRange);
          config.set("TNT_BreakChance", tntBreakChance);
          config.set("TNT_BunkerBusterIgnoresWater", IgnoreWater);
          saveConfig();
      }
  ```
  <H4></h4>
  <b>Used to override the OnLoad on JavaPlugin to allow Riverland to register with the WorldGuard dependency.</b>

  ```Java
      @Override
      public void onLoad()
      {
          if (CustomExplosionFlag == null) {
              FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
              try {
                  // create a flag with the name "my-custom-flag", defaulting to true
                  StateFlag flag = new StateFlag("RiverlandExplosion", true);
                  registry.register(flag);
                  CustomExplosionFlag = flag; // only set our field if there was no error
              } catch (FlagConflictException e) {
                  // some other plugin registered a flag by the same name already.
                  // you can use the existing flag, but this may cause conflicts - be sure to check type
                  Flag<?> existing = registry.get("RiverlandExplosion");
                  if (existing instanceof StateFlag) {
                      CustomExplosionFlag = (StateFlag) existing;
                  } else {
                      // types don't match - this is bad news! some other plugin conflicts with you
                      // hopefully this never actually happens
                  }
              }
          }
      }
  ```
  
  <H4></h4>
  <b>Used to override OnEnable in JavaPlugin to allow Riverland to register, load and assign different variable types, and data.</b>
  
  ```Java
  @Override
    public void onEnable()
    {
    }
  ```
  
  <H4></h4>
  <b>Used as a backup to remove all serialized TNT information, in-case of exploits or non-removed data</b>
  
  ```Java
    public void ClearTNTMemory() // call this to clear all saved tnt
    {
        try
        {
            gsonObj = new Gson();
            FileWriter myWriter = new FileWriter(f);
            ArrayList<SerializableTNT> list = new ArrayList<>();
            Set<Map.Entry<Location, Integer>> map = tntPositions.entrySet();
            for(Map.Entry<Location, Integer> pair : map)
            {
                SerializableTNT tnt = new SerializableTNT();
                tnt.Set(pair.getKey(),pair.getValue());
                list.add(tnt);
            }
            getLogger().log(Level.WARNING,"String Contents: " + "[]");
            myWriter.write("[]");
            getLogger().log(Level.WARNING,"Writing Json.." );
            myWriter.close();
        }
        catch (Exception exc)
        {
            getLogger().log(Level.WARNING,"Could not load TNT Json" + exc.toString());
        }
        this.tntPositions.clear();
    }
  ```
  
  <H4></h4>
  <b>Used to Serialized TNT Information, and update config.</b>
  
  ```Java
    @Override
    public void onDisable() {

        saveConfig();
        f = new File(folder, "TNTWorldPositions.yml");
        folder = this.getDataFolder();
        try
        {
            getLogger().log(Level.WARNING,"Saving Json.. " );
            gsonObj = new Gson();
            FileWriter myWriter = new FileWriter(f);
            //
            ArrayList<SerializableTNT> list = new ArrayList<>();
            Set<Map.Entry<Location, Integer>> map = tntPositions.entrySet();
            for(Map.Entry<Location, Integer> pair : map)
            {
                SerializableTNT tnt = new SerializableTNT();
                tnt.Set(pair.getKey(),pair.getValue());
                list.add(tnt);
            }
            String str = gsonObj.toJson(list);

            if (str.length() > 3)
            {
                myWriter.write(str);
                getLogger().log(Level.WARNING,"Writing Json.." );
            }
            myWriter.close();
        }
        catch (Exception exc)
        {
            getLogger().log(Level.WARNING,"Could not save TNT Json" + exc.toString());
        }
    }
  ```
  
