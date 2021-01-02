package OreGenerator;

import OreGenerator.Commands.OreGen;
import OreGenerator.Events.BlockPlaceevent;
import OreGenerator.Events.BreakEvent;
import OreGenerator.Events.InteractEvent;
import OreGenerator.Files.configFile;
import OreGenerator.Files.messagesFile;
import OreGenerator.Files.oreFile;
import OreGenerator.Runnables.BlockSet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
  static Plugin plugin;
  
  public static messagesFile messageFile;
  
  public static configFile configFil;
  
  public static oreFile oresFile;
  
  public void onEnable() {
    saveConfig();
    getConfig().options().copyDefaults(true);
    saveConfig();
    reloadConfig();
    getCommand("oregenerator").setExecutor((CommandExecutor)new OreGen());
    Bukkit.getPluginManager().registerEvents((Listener)new BlockPlaceevent(), (Plugin)this);
    Bukkit.getPluginManager().registerEvents((Listener)new BreakEvent(), (Plugin)this);
    Bukkit.getPluginManager().registerEvents((Listener)new InteractEvent(), (Plugin)this);
    plugin = (Plugin)this;
    super.onEnable();
    messageFile = new messagesFile(this);
    messageFile.setupConfig();
    messageFile.saveConfig();
    configFil = new configFile(this);
    configFil.setupConfig();
    configFil.saveConfig();
    oresFile = new oreFile(this);
    oresFile.setupConfig();
    oresFile.saveConfig();
    startup();
  }
  
  public void startup() {
    for (String loc : oresFile.getConfig().getConfigurationSection("OresPlaced.").getKeys(false)) {
      String[] loc2 = loc.split(",");
      String world = loc2[0].replace("Location{world=CraftWorld{name=", "").replace("}", "");
      int x = Integer.parseInt(loc2[1].replace("x=", ""));
      int y = Integer.parseInt(loc2[3].replace("y=", ""));
      int z = Integer.parseInt(loc2[5].replace("z=", ""));
      Location locf = new Location(Bukkit.getWorld(world), x, y, z);
      BlockSet.start(locf.getBlock());
    } 
  }
  
  public void onDisable() {
    super.onDisable();
  }
  
  public static String message(String configString) {
    return messageFile.getConfig().getString(configString);
  }
  
  public static String config(String configString) {
    return configFil.getConfig().getString(configString);
  }
  
  public static Plugin getPlugin() {
    return plugin;
  }
}
