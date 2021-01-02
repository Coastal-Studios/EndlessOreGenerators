package OreGenerator.Files;

import OreGenerator.Main;
import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class oreFile {
  private File configFile;
  
  private FileConfiguration config;
  
  private Main main;
  
  public oreFile(Main main) {
    this.main = main;
    this.configFile = new File(main.getDataFolder(), "ores.yml");
    this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
  }
  
  public void setupConfig() {
    if (!this.configFile.exists())
      this.main.saveResource("ores.yml", false); 
    this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
  }
  
  public FileConfiguration getConfig() {
    return this.config;
  }
  
  public void saveConfig() {
    try {
      this.config.save(this.configFile);
    } catch (IOException iOException) {}
  }
  
  public void reloadConfig() {
    this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
  }
}
