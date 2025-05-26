package de.ardania.urutar.ardaxpbottles.commands;

import de.ardania.urutar.ardaxpbottles.util.Converter;
import org.bukkit.entity.Player;

import static de.ardania.urutar.ardaxpbottles.ArdaXpBottles.CONFIGHANDLER;
import static de.ardania.urutar.ardaxpbottles.ArdaXpBottles.MESSAGEHANDLER;

public class SetCommand {

    public static boolean execute(Player player, String variable, String value) {
        // Missing permission
        if (player.hasPermission("ardaxpbottles.set")) {
            MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.permissionError);
            return false;
        }

        // Missing arguments
        if (variable == null || variable.isEmpty() || value == null || value.isEmpty()) {
            MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.unfinishedCommandError);
            return false;
        }

        switch (variable.toLowerCase()) {
            case "euronencost" : {
                Integer amount = Converter.tryParseInteger(value);
                if (amount == null) {
                    MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.noNumberError);
                    return false;
                }
                if (amount <= 0) {
                    MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.negativeNumberError);
                    return false;
                }
                CONFIGHANDLER.setEuronenCost(amount);
                MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.valueChangedMessage);
                return true;
            }
            case "experiencecost": {
                Integer amount = Converter.tryParseInteger(value);
                if (amount == null) {
                    MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.noNumberError);
                    return false;
                }
                if (amount <= 0) {
                    MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.negativeNumberError);
                    return false;
                }
                CONFIGHANDLER.setExperienceCost(amount);
                MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.valueChangedMessage);
                return true;
            }
            case "displayname": {
                CONFIGHANDLER.setDisplayName(value);
                MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.valueChangedMessage);
                return true;
            }
            case "lore": {
                CONFIGHANDLER.setLore(value);
                MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.valueChangedMessage);
                return true;
            }
            default: {
                return false;
            }
        }
    }
}
