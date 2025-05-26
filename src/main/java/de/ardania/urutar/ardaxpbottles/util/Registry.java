package de.ardania.urutar.ardaxpbottles.util;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Registry {

    public static Logger LOGGER;
    public static Economy ECONOMY;

    public static void initVault(PluginManager plugman, ServicesManager servman) throws IllegalStateException {
        Plugin vault = plugman.getPlugin("Vault");

        if (vault == null || !vault.isEnabled()) {
            throw new IllegalStateException("Vault plugin required");
        }
        if (!servman.isProvidedFor(Economy.class)) {
            throw new IllegalStateException("Economy plugin required");
        }

        RegisteredServiceProvider<Economy> ecoProvider = servman.getRegistration(Economy.class);
        Registry.ECONOMY = ecoProvider.getProvider();

        LOGGER.log(
                Level.INFO,
                "Using {0} as economy on priority {1} provided by {2} ({3})",
                new Object[]{
                        Registry.ECONOMY.getName(),
                        ecoProvider.getPriority(),
                        ecoProvider.getPlugin().getName(),
                        Registry.ECONOMY.getClass().getName()
                }
        );
    }
}
