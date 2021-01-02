package OreGenerator.Events;

import OreGenerator.Commands.OreGen;
import OreGenerator.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractEvent implements Listener {
  @EventHandler
  public void onCLick(PlayerInteractEvent e) {
    if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
      for (String loc : Main.oresFile.getConfig().getConfigurationSection("OresPlaced.").getKeys(false)) {
        String locup = loc.replace(",", ".");
        if (e.getClickedBlock().getLocation().toString().replace(",", ".").equalsIgnoreCase(locup)) {
          int ores = Main.oresFile.getConfig().getInt("OresPlaced." + e.getClickedBlock().getLocation().toString().replace(".", ",") + ".OresLeft");
          e.getPlayer().sendMessage(OreGen.FixColor(Main.message("Messages.OresLeft").replace("%amount%", ores + "")));
        } 
      }  
  }
}
