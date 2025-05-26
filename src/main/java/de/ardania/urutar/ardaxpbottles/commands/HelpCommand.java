package de.ardania.urutar.ardaxpbottles.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import static de.ardania.urutar.ardaxpbottles.ArdaXpBottles.MESSAGEHANDLER;

/**
 * Class for info command.
 */
public class HelpCommand {

    public static boolean execute(Player player, JavaPlugin plugin) {
        if (player == null)
            return false;
        if (plugin == null)
            return false;

        // Missing permission
        if (!player.hasPermission("ardaxpbottles.help")) {
            MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.permissionError);
            return false;
        }

        PluginDescriptionFile metaInfo = plugin.getDescription();
        MESSAGEHANDLER.showMessage(player, ChatColor.GOLD + metaInfo.getVersion() + " by " + metaInfo.getAuthors());
        MESSAGEHANDLER.showPlainMessage(player,ChatColor.GRAY + "->" + MESSAGEHANDLER.checkHelpText);

        if (player.hasPermission("ardaxpbottles.check.other"))
            MESSAGEHANDLER.showPlainMessage(player, ChatColor.GRAY + "->" + MESSAGEHANDLER.checkAdminHelpText);
        if (player.hasPermission("ardaxpbottles.set"))
            MESSAGEHANDLER.showPlainMessage(player, ChatColor.GRAY + "->" + MESSAGEHANDLER.setAdminHelpText);
        MESSAGEHANDLER.showPlainMessage(player, ChatColor.GRAY + "->" + MESSAGEHANDLER.createHelpText);

        return true;
    }
}
