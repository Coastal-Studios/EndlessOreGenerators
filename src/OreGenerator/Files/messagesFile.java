package OreGenerator.Files;

import OreGenerator.Main;
import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class messagesFile {
  private File messageFile;
  
  private FileConfiguration message;
  
  private Main main;
  
  public messagesFile(Main main) {
    this.main = main;
    this.messageFile = new File(main.getDataFolder(), "messages.yml");
    this.message = (FileConfiguration)YamlConfiguration.loadConfiguration(this.messageFile);
  }
  
  public void setupConfig() {
    if (!this.messageFile.exists())
      this.main.saveResource("messages.yml", false); 
    this.message = (FileConfiguration)YamlConfiguration.loadConfiguration(this.messageFile);
  }
  
  public FileConfiguration getConfig() {
    return this.message;
  }
  
  public void saveConfig() {
    try {
      this.message.save(this.messageFile);
    } catch (IOException iOException) {}
  }
  
  public void reloadConfig() {
    this.message = (FileConfiguration)YamlConfiguration.loadConfiguration(this.messageFile);
  }
}
