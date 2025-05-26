package de.ardania.urutar.ardaxpbottles.handler;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Handler class for the messages of this plugin.
 */
public class MessageHandler {

    public final String pluginPrefix;
    public final String permissionError;
    public final String negativeNumberError;
    public final String noNumberError;
    public final String unfinishedCommandError;
    public final String noPlayerError;
    public final String valueChangedMessage;
    public final String checkHelpText;
    public final String checkAdminHelpText;
    public final String createHelpText;
    public final String setAdminHelpText;
    public final String noMoneyError;
    public final String noSpaceError;
    public final String noXpError;
    public final String playerCheckMessage;
    public final String adminCheckMessage;
    public final String createError;
    public final String playerCreateMessage;

    FileConfiguration configuration;

    public MessageHandler(JavaPlugin plugin) {
        createCustomConfig(plugin);
        pluginPrefix = tryGetConfigText("PLUGIN_PREFIX", "§7[§aArdaXpBottles§7]§r ");
        permissionError = tryGetConfigText("ERROR_PERMISSIONS", "§cDu hast keine Rechte, dies zu tun!§r");
        negativeNumberError = tryGetConfigText("ERROR_NEGATIVE_NUMBER", "§cBitte gib eine Positive Zahl ein!§r");
        noNumberError = tryGetConfigText("ERROR_NO_NUMBER", "§cBitte gib eine Zahl ein!§r");
        unfinishedCommandError = tryGetConfigText("ERROR_UNFINISHED_COMMAND", "§cDer Befehl ist nicht vollständig!§r");
        noPlayerError = tryGetConfigText("ERROR_NO_PLAYER", "§cDer Spieler konnte nicht gefunden werden!§r");
        valueChangedMessage = tryGetConfigText("VALUE_CHANGE", "§3Der Wert wurde erfolgreich geändert.§r");
        checkHelpText = tryGetConfigText("BOTTLE_PLAYER_CHECK_HELP_TEXT", "§a/bottle check §3- Zeigt dir an wie viele Erfahrungsfläschchen du herstellen kannst und wie viel es dich kosten würde.§r");
        checkAdminHelpText = tryGetConfigText("BOTTLE_ADMIN_CHECK_HELP_TEXT", "§a/bottle check <player> §3- Zeigt die Erfahrungspunkte eines anderen Spielers an.§r");
        createHelpText = tryGetConfigText("BOTTLE_CREATE_HELP_TEXT", "§a/bottle create <amount> §3- Erstellt Erfahrungsfläschchen.§r");
        setAdminHelpText = tryGetConfigText("BOTTLE_ADMIN_SET_HELP_TEXT", "§a/bottle set <Variable> <value> §3- Lässt dich die Variablen in der config datei ingame ändern.§r");
        noMoneyError = tryGetConfigText("ERROR_NO_MONEY", "§cDu hast nicht genug Geld, um dir das zu leisten!§r");
        noSpaceError = tryGetConfigText("ERROR_NO_SPACE", "§cDu hast nicht genug Platz in deinem Inventar!§r");
        noXpError = tryGetConfigText("ERROR_NO_XP", "§cDu hast nicht genug Erfahrungspunkte, um dir etwas herzustellen!§r");
        playerCheckMessage = tryGetConfigText("PLAYER_CHECK_MESSAGE", "§3Du hast §e[Exp]§3 Erfahrungspunkte, mit denen du §e[BottleAmount]§3 Erfahrungsfläschchen herstellen kannst. Das würde dich §e[cost]§3 Euronen kosten.§r");
        adminCheckMessage = tryGetConfigText("ADMIN_CHECK_MESSAGE", "§3Der Spieler §e[playername] §3hat §e[Exp]§3 Erfahrungspunkte, mit denen er §e[BottleAmount]§3 Erfahrungsfläschchen herstellen kann. Das würde §e[Cost]§3 Euronen kosten.§r");
        createError = tryGetConfigText("ERROR_ON_CREATE", "§cDer Befehl konnte nicht ausgeführt werden!§r");
        playerCreateMessage = tryGetConfigText("PLAYER_CREATE_MESSAGE", "§3Du hast §e[Exp]§3 Erfahrungspunkte verwendet, um §e[BottleAmount]§3 Erfahrungsfläschchen herzustellen. Das hat dich §e[Cost]§3 Euronen gekostet.§r");
    }

    private String tryGetConfigText(String path, String fallback) {
        try {
            return configuration.getString(path);
        } catch (Exception ex) {
            return fallback;
        }
    }

    /**
     * @param plugin the main class of this plugin
     * @return the messages.yml file with messages of this plugin
     */
    private File createCustomConfig(JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }

        configuration = new YamlConfiguration();
        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @return the yml file
     */
    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public void showMessage(Player player, String message) {
        if (player != null)
            player.sendMessage(pluginPrefix + message);
    }

    public void showPlainMessage(Player player, String message) {
        if (player != null)
            player.sendMessage(message);
    }

}
