package OreGenerator.Events;

import OreGenerator.Commands.OreGen;
import OreGenerator.Main;
import OreGenerator.Runnables.BlockSet;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class BreakEvent implements Listener {
  @EventHandler
  public void onbreak(final BlockBreakEvent e) {
    for (String loc : Main.oresFile.getConfig().getConfigurationSection("OresPlaced.").getKeys(false)) {
      String locup = loc.replace(",", ".");
      if (e.getBlock().getLocation().toString().replace(",", ".").equalsIgnoreCase(locup)) {
        if (e.getPlayer().isSneaking()) {
          ItemStack item = null;
          String s0 = Main.oresFile.getConfig().getString("OresPlaced." + e.getBlock().getLocation().toString().replace(".", ",") + ".Ore");
          int s1 = Main.oresFile.getConfig().getInt("OresPlaced." + e.getBlock().getLocation().toString().replace(".", ",") + ".OresPerInterval");
          int s2 = Main.oresFile.getConfig().getInt("OresPlaced." + e.getBlock().getLocation().toString().replace(".", ",") + ".Max");
          if (OreGen.getOre(s0) == Material.LOG) {
            item = new ItemStack(Material.LOG, 1, (short)0);
          } else {
            item = new ItemStack(OreGen.getOre(s0), 1);
          } 
          List<String> lores = new ArrayList<>();
          for (String lore : Main.configFil.getConfig().getStringList("ItemFromCommand.Lore")) {
            String lore1 = ChatColor.translateAlternateColorCodes('&', lore).replace("%ore%", OreGen.getOre(s0).name().toLowerCase()).replace("%amount%", s1 + "").replace("%max%", s2 + "");
            lores.add(lore1);
          } 
          lores.add(" ");
          lores.add("to mine this back up)");
          ItemMeta im = item.getItemMeta();
          im.setLore(lores);
          im.setDisplayName(OreGen.FixColor(Main.config("ItemFromCommand.Name").replace("%ore%", OreGen.getOrs(item.getType()))));
          item.setItemMeta(im);
          e.getPlayer().getInventory().addItem(new ItemStack[] { item });
          e.getBlock().setType(Material.AIR);
          Main.oresFile.getConfig().set("OresPlaced." + e.getBlock().getLocation().toString().replace(".", ","), null);
          Main.oresFile.saveConfig();
          Main.oresFile.reloadConfig();
          return;
        } 
        if (e.getBlock().getType() == Material.CLAY) {
          e.setCancelled(true);
          return;
        } 
        if (Main.oresFile.getConfig().getInt("OresPlaced." + loc + ".OresLeft") <= 1) {
          (new BukkitRunnable() {
              public void run() {
                e.getBlock().setType(Material.CLAY);
                cancel();
              }
            }).runTaskLater(Main.getPlugin(), 1L);
          Main.oresFile.getConfig().set("OresPlaced." + e.getBlock().getLocation().toString().replace(".", ",") + ".OresLeft", Integer.valueOf(0));
          Main.oresFile.saveConfig();
          Main.oresFile.reloadConfig();
          return;
        } 
        (new BukkitRunnable() {
            public void run() {
              e.getBlock().setType(OreGen.getOre(Main.oresFile.getConfig().getString("OresPlaced." + e.getBlock().getLocation().toString().replace(".", ",") + ".Ore")));
              cancel();
            }
          }).runTaskLater(Main.getPlugin(), 1L);
        int ores = Main.oresFile.getConfig().getInt("OresPlaced." + e.getBlock().getLocation().toString().replace(".", ",") + ".OresLeft");
        int left = ores - 1;
        Main.oresFile.getConfig().set("OresPlaced." + e.getBlock().getLocation().toString().replace(".", ",") + ".OresLeft", Integer.valueOf(left));
        Main.oresFile.saveConfig();
        Main.oresFile.reloadConfig();
        int max = Main.oresFile.getConfig().getInt("OresPlaced." + e.getBlock().getLocation().toString().replace(".", ",") + ".Max") - 1;
        if (Main.oresFile.getConfig().getInt("OresPlaced." + loc + ".OresLeft") >= max)
          BlockSet.start(e.getBlock()); 
      } 
    } 
  }
  
  @EventHandler
  public void onEntityExplode(EntityExplodeEvent event) {
    for (Block block : (Block[])event.blockList().toArray((Object[])new Block[event.blockList().size()])) {
      for (String loc : Main.oresFile.getConfig().getConfigurationSection("OresPlaced.").getKeys(false)) {
        String locup = loc.replace(",", ".");
        if (block.getLocation().toString().replace(",", ".").equalsIgnoreCase(locup)) {
          if (Main.oresFile.getConfig().getInt("OresPlaced." + loc + ".OresLeft") <= 1) {
            (new BukkitRunnable() {
                public void run() {
                  block.setType(Material.CLAY);
                  cancel();
                }
              }).runTaskLater(Main.getPlugin(), 1L);
            return;
          } 
          (new BukkitRunnable() {
              public void run() {
                block.setType(OreGen.getOre(Main.oresFile.getConfig().getString("OresPlaced." + block.getLocation().toString().replace(".", ",") + ".Ore")));
                cancel();
              }
            }).runTaskLater(Main.getPlugin(), 1L);
          int max = Main.oresFile.getConfig().getInt("OresPlaced." + block.getLocation().toString().replace(".", ",") + ".Max");
          if (Main.oresFile.getConfig().getInt("OresPlaced." + loc + ".OresLeft") >= max)
            BlockSet.start(block); 
        } 
      } 
    } 
  }
}
