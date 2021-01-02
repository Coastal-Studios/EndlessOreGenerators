package OreGenerator.Events;

import OreGenerator.Commands.OreGen;
import OreGenerator.Main;
import OreGenerator.Runnables.BlockSet;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceevent implements Listener {
  @EventHandler
  public void onPlace(BlockPlaceEvent e) {
    if (e.getItemInHand() == null || e.getItemInHand().getType() == Material.AIR)
      return; 
    String[] s = Main.config("ItemFromCommand.Name").split(" ");
    if (!e.getItemInHand().hasItemMeta())
      return; 
    if (e.getItemInHand().getItemMeta().getLore().contains("to mine this back up)")) {
      List<String> ints = new ArrayList<>();
      for (String s1 : e.getItemInHand().getItemMeta().getLore()) {
        for (String t : s1.split(" ")) {
          String l = ChatColor.stripColor(t);
          if (isInt(l))
            ints.add(l); 
        } 
      } 
      if (ints.size() != 2) {
        e.getPlayer().sendMessage("not find Max value or OresPerInterval value");
        return;
      } 
      Main.oresFile.getConfig().set("OresPlaced." + e.getBlockPlaced().getLocation().toString().replace(".", ",") + ".Max", Integer.valueOf(Integer.parseInt(ints.get(1))));
      Main.oresFile.getConfig().set("OresPlaced." + e.getBlockPlaced().getLocation().toString().replace(".", ",") + ".OresPerInterval", Integer.valueOf(Integer.parseInt(ints.get(0))));
      Main.oresFile.getConfig().set("OresPlaced." + e.getBlockPlaced().getLocation().toString().replace(".", ",") + ".OresLeft", Integer.valueOf(1));
      Main.oresFile.getConfig().set("OresPlaced." + e.getBlockPlaced().getLocation().toString().replace(".", ",") + ".Ore", OreGen.getOrs(e.getBlockPlaced().getType()));
      Main.oresFile.saveConfig();
      Main.oresFile.reloadConfig();
      BlockSet.start(e.getBlockPlaced());
    } 
  }
  
  public static boolean isInt(String s) {
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException nfe) {
      return false;
    } 
    return true;
  }
}
