package OreGenerator.Runnables;

import OreGenerator.Commands.OreGen;
import OreGenerator.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockSet {
  public static void start(final Block b) {
    int ores = Main.oresFile.getConfig().getInt("OresPlaced." + b.getLocation().toString().replace(".", ",") + ".OresPerInterval") * 20;
    final int max = Main.oresFile.getConfig().getInt("OresPlaced." + b.getLocation().toString().replace(".", ",") + ".Max") - 1;
    (new BukkitRunnable() {
        public void run() {
          int oresleft = Main.oresFile.getConfig().getInt("OresPlaced." + b.getLocation().toString().replace(".", ",") + ".OresLeft");
          if (oresleft > max) {
            cancel();
            return;
          } 
          if (oresleft >= max)
            cancel(); 
          if (b.getType() == Material.CLAY)
            b.setType(OreGen.getOre(Main.oresFile.getConfig().getString("OresPlaced." + b.getLocation().toString().replace(".", ",") + ".Ore"))); 
          oresleft++;
          Main.oresFile.getConfig().set("OresPlaced." + b.getLocation().toString().replace(".", ",") + ".OresLeft", Integer.valueOf(oresleft));
          Main.oresFile.saveConfig();
          Main.oresFile.reloadConfig();
        }
      }).runTaskTimer(Main.getPlugin(), ores, ores);
  }
}
