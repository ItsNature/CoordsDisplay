package gg.nature.coordsdisplay;

import gg.nature.coordsdisplay.commands.CoordsCommand;
import gg.nature.coordsdisplay.file.Config;
import gg.nature.coordsdisplay.file.ConfigFile;
import gg.nature.coordsdisplay.managers.CoordsManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class CoordsDisplay extends JavaPlugin {

    @Getter public static CoordsDisplay instance;

    private ConfigFile configFile;
    private ConfigFile locationsFile;
    private CoordsManager coordsManager;

    @Override
    public void onEnable() {
        instance = this;

        this.configFile = new ConfigFile("config.yml");
        this.locationsFile = new ConfigFile("locations.yml");

        new Config();

        this.getCommand("coords").setExecutor(new CoordsCommand());

        this.coordsManager = new CoordsManager();
    }

    @Override
    public void onDisable() {
        this.coordsManager.disable();
    }
}
