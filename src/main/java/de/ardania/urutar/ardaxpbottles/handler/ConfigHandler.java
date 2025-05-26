package de.ardania.urutar.ardaxpbottles.handler;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * Handler class for the default config of this plugin
 */
public class ConfigHandler {

    //The main class of this plugin
    JavaPlugin plugin;
    //The FileConfiguration for the default config
    FileConfiguration config;

    /**
     * Default constructor
     *
     * @param plugin the main class of this plugin
     */
    public ConfigHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
    }

    /**
     * @return the default config of this plugin
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * @return the Euronen cost of one XP bottle
     */
    public int getEuronenCost() {
        return config.getInt("EuronenCost");
    }

    /**
     * Sets the cost of the EXP-Bottle in the config file
     *
     * @param costs the amount of how much a EXP-Bottle should cost
     */
    public void setEuronenCost(int costs) {
        config.set("EuronenCost", costs);
        plugin.saveConfig();
    }

    /**
     * @return the amount of XP that is needed for one XP bottle
     */
    public int getExperienceCost() {
        return config.getInt("ExperienceCost");
    }

    /**
     * Sets the Exchange amount of an EXP-Bottle in the config file
     *
     * @param exchangeAmount the amount of how much EXP will be decrease of the player from getting one EXP-Bottle
     */
    public void setExperienceCost(int exchangeAmount) {
        config.set("ExperienceCost", exchangeAmount);
        plugin.saveConfig();
    }

    /**
     * @return the Diplayedname of the XP bottle
     */
    public String getDisplayName() {
        return config.getString("DisplayName");
    }

    /**
     * Sets the Displayname of the XP bottle
     *
     * @param displayName the name of the XP bottle
     */
    public void setDisplayName(String displayName) {
        config.set("DisplayName", displayName);
        plugin.saveConfig();
    }

    /**
     * @return the Lore of the XP bottle
     */
    public List<String> getLore() {
        return Arrays.asList(config.getString("Lore"));
    }

    /**
     * Sets the Lore of the XP bottle
     *
     * @param lore the lore of the XP bottle
     */
    public void setLore(String lore) {
        config.set("Lore", lore);
        plugin.saveConfig();
    }
}
