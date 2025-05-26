package de.ardania.urutar.ardaxpbottles;

import de.ardania.urutar.ardaxpbottles.handler.CommandHandler;
import de.ardania.urutar.ardaxpbottles.handler.ConfigHandler;
import de.ardania.urutar.ardaxpbottles.handler.MessageHandler;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import static de.ardania.urutar.ardaxpbottles.util.Registry.LOGGER;
import static de.ardania.urutar.ardaxpbottles.util.Registry.initVault;

public class ArdaXpBottles extends JavaPlugin {

    public static MessageHandler MESSAGEHANDLER;
    public static ConfigHandler CONFIGHANDLER;
    CommandHandler commandHandler = new CommandHandler(this);

    @Override
    public void onEnable() {
        LOGGER = getLogger();
        initVault(getServer().getPluginManager(), getServer().getServicesManager());
        MESSAGEHANDLER = new MessageHandler(this);
        CONFIGHANDLER = new ConfigHandler(this);

        saveDefaultConfig();

        PluginCommand command = getCommand("bottle");
        if(command != null)
            command.setExecutor(commandHandler);
    }
}