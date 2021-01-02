package OreGenerator.Commands;

import OreGenerator.Main;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OreGen implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
    Player p = (Player)sender;
    if (!(p instanceof Player)) {
      sender.sendMessage("must be a Player");
      return true;
    } 
    if (!p.isOp() && 
      !p.hasPermission("oregen.use")) {
      p.sendMessage(FixColor(Main.message("Messages.NoPermission")));
      return true;
    } 
    if (strings.length != 3) {
      p.sendMessage(FixColor(Main.message("Messages.OreGenUsage")));
      return true;
    } 
    if (getOre(strings[0]) == null) {
      p.sendMessage(FixColor(Main.message("Messages.OreGenUsage")));
      return true;
    } 
    ItemStack item = null;
    if (getOre(strings[0]) == Material.LOG) {
      item = new ItemStack(Material.LOG, 1, (short)0);
    } else {
      item = new ItemStack(getOre(strings[0]), 1);
    } 
    List<String> lores = new ArrayList<>();
    for (String lore : Main.configFil.getConfig().getStringList("ItemFromCommand.Lore")) {
      String lore1 = ChatColor.translateAlternateColorCodes('&', lore).replace("%ore%", getOre(strings[0]).name().toLowerCase()).replace("%amount%", strings[2]).replace("%max%", strings[1]);
      lores.add(lore1);
    } 
    lores.add(" ");
    lores.add("to mine this back up)");
    ItemMeta im = item.getItemMeta();
    im.setLore(lores);
    im.setDisplayName(FixColor(Main.config("ItemFromCommand.Name").replace("%ore%", getOrs(item.getType()))));
    item.setItemMeta(im);
    p.getInventory().addItem(new ItemStack[] { item });
    return true;
  }
  
  public static Material getOre(String s) {
    if (s.equalsIgnoreCase("log"))
      return Material.LOG; 
    if (s.equalsIgnoreCase("stone"))
      return Material.STONE; 
    if (s.equalsIgnoreCase("coal"))
      return Material.COAL_ORE; 
    if (s.equalsIgnoreCase("iron"))
      return Material.IRON_ORE; 
    if (s.equalsIgnoreCase("emerald"))
      return Material.EMERALD_ORE; 
    if (s.equalsIgnoreCase("lapis"))
      return Material.LAPIS_ORE; 
    if (s.equalsIgnoreCase("gold"))
      return Material.GOLD_ORE; 
    if (s.equalsIgnoreCase("redstone"))
      return Material.REDSTONE_ORE; 
    if (s.equalsIgnoreCase("diamond"))
      return Material.DIAMOND_ORE; 
    return null;
  }
  
  public static String getOrs(Material s) {
    if (s.equals(Material.LOG))
      return "Log"; 
    if (s.equals(Material.STONE))
      return "Stone"; 
    if (s.equals(Material.COAL_ORE))
      return "Coal"; 
    if (s.equals(Material.IRON_ORE))
      return "Iron"; 
    if (s.equals(Material.EMERALD_ORE))
      return "Emerald"; 
    if (s.equals(Material.LAPIS_ORE))
      return "Lapis"; 
    if (s.equals(Material.GOLD_ORE))
      return "Gold"; 
    if (s.equals(Material.REDSTONE_ORE))
      return "Redstone"; 
    if (s.equals(Material.DIAMOND_ORE))
      return "Diamond"; 
    return null;
  }
  
  public static String FixColor(String s) {
    String n = ChatColor.translateAlternateColorCodes('&', s);
    return n;
  }
}
