package de.ardania.urutar.ardaxpbottles.commands;

import de.ardania.urutar.ardaxpbottles.util.XpCalc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static de.ardania.urutar.ardaxpbottles.ArdaXpBottles.CONFIGHANDLER;
import static de.ardania.urutar.ardaxpbottles.ArdaXpBottles.MESSAGEHANDLER;

/**
 * Class for check command.
 */
public class CheckCommand {

    public static boolean execute(Player player, String playerName) {
        if (player == null) {
            return false;
        }

        // Missing permission
        if (!player.hasPermission("ardaxpbottles.check")) {
            MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.permissionError);
            return false;
        }

        if (playerName == null || playerName.isEmpty()) {
            long playerExp = XpCalc.getPlayerExp(player);
            int expCost = CONFIGHANDLER.getExperienceCost();
            int moneyCost = CONFIGHANDLER.getEuronenCost();
            if (playerExp < expCost) {
                MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.noXpError);
                return false;
            }

            long maxAmount = (int)playerExp / expCost;
            long maxCost = moneyCost * maxAmount;

            String message = MESSAGEHANDLER.playerCheckMessage
                    .replace("[Exp]", Long.toString(playerExp))
                    .replace("[BottleAmount]", Long.toString(maxAmount))
                    .replace("[Cost", Long.toString(maxCost));

            MESSAGEHANDLER.showMessage(player, message);
            return true;
        }

        // Missing permission
        if (!player.hasPermission("ardaxpbottles.check.other")) {
            MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.permissionError);
            return false;
        }

        Player other = Bukkit.getServer().getPlayer(playerName);
        // Player could not be found
        if(other == null) {
            MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.noPlayerError);
            return false;
        }

        long playerExp = XpCalc.getPlayerExp(other);
        int expCost = CONFIGHANDLER.getExperienceCost();
        int moneyCost = CONFIGHANDLER.getEuronenCost();
        if (playerExp < expCost) {
            MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.noMoneyError);
            return false;
        }

        long maxAmount = (int)playerExp / expCost;
        long maxCost = moneyCost * maxAmount;

        String message = MESSAGEHANDLER.adminCheckMessage
                .replace("[PlayerName]", playerName)
                .replace("[Exp]", Long.toString(playerExp))
                .replace("[BottleAmount]", Long.toString(maxAmount))
                .replace("[Cost", Long.toString(maxCost));

        MESSAGEHANDLER.showMessage(player, message);
        return true;
    }

}
