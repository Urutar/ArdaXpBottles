package de.ardania.urutar.ardaxpbottles.commands;

import de.ardania.urutar.ardaxpbottles.util.Converter;
import de.ardania.urutar.ardaxpbottles.util.Registry;
import de.ardania.urutar.ardaxpbottles.util.XpCalc;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static de.ardania.urutar.ardaxpbottles.ArdaXpBottles.CONFIGHANDLER;
import static de.ardania.urutar.ardaxpbottles.ArdaXpBottles.MESSAGEHANDLER;

/**
 * Class for create command.
 */
public class CreateCommand {

    public static boolean execute(Player player, String amount) {
        if (player == null)
            return false;

        // Missing permission
        if (!player.hasPermission("ardaxpbottles.create")) {
            MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.permissionError);
            return false;
        }

        // Missing argument
        if (amount == null || amount.isEmpty()) {
            MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.unfinishedCommandError);
            return false;
        }

        Integer desiredAmount = Converter.tryParseInteger(amount);

        if (desiredAmount == null) {
            MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.noNumberError);
            return false;
        }

        if (desiredAmount <= 0) {
            MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.negativeNumberError);
            return false;
        }

        long playerExp = XpCalc.getPlayerExp(player);
        long totalXpCost = (long)desiredAmount * CONFIGHANDLER.getExperienceCost();

        // Player has not enough xp
        if(totalXpCost > playerExp) {
            MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.noXpError);
            return false;
        }

        // Player has not enough space
        if(!validateFreeSpace(player, (long)desiredAmount)) {
            MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.noSpaceError);
            return false;
        }

        long totalMoneyCost = (long)desiredAmount * CONFIGHANDLER.getEuronenCost();
        // Player has not enough money
        if(! Registry.ECONOMY.has(player, totalMoneyCost)) {
            MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.noMoneyError);
            return false;
        }

        ItemStack xpBottles = getXpBottles(desiredAmount);
        if (xpBottles == null) {
            MESSAGEHANDLER.showMessage(player, MESSAGEHANDLER.createError);
            return false;
        }

        String message = MESSAGEHANDLER.playerCreateMessage
                .replace("[Exp]", Long.toString(totalXpCost))
                .replace("[BottleAmount]", Long.toString(desiredAmount))
                .replace("[Cost", Long.toString(totalMoneyCost));

        while (totalXpCost >= Integer.MAX_VALUE){
            player.giveExp(-Integer.MAX_VALUE);
            totalXpCost -= Integer.MAX_VALUE;
        }

        player.giveExp(-(int)totalXpCost);
        player.getInventory().addItem(xpBottles);
        Registry.ECONOMY.withdrawPlayer(player, totalMoneyCost);
        MESSAGEHANDLER.showMessage(player, message);
        return true;
    }


    /**
     * @return the XP bottle that will be created with custom name and lore
     */
    private static ItemStack getXpBottles(int amount) {
        ItemStack xpBottles = new ItemStack(Material.EXPERIENCE_BOTTLE, amount);
        ItemMeta m = xpBottles.getItemMeta();
        if(m == null)
            return null;

        m.setLore(CONFIGHANDLER.getLore());
        m.setDisplayName(CONFIGHANDLER.getDisplayName());
        xpBottles.setItemMeta(m);
        return xpBottles;
    }

    /**
     * Validate if the player has enough space in his inventory
     *
     * @return true if the player has enough space in his inventory
     */
    private static boolean validateFreeSpace(Player player, Long amount) {
        int freeSpace = 0;
        int usedStacks = 0;

        for (ItemStack s : player.getInventory().getStorageContents()) {
            if (s != null) {
                Material itemType = s.getType();
                ItemMeta itemMeta = s.getItemMeta();
                if (itemMeta == null) {
                    usedStacks++;
                    continue;
                }

                java.util.List<String> itemLore = itemMeta.getLore();
                java.util.List<String> configLore = CONFIGHANDLER.getLore();
                if (itemLore == null || configLore == null) {
                    usedStacks++;
                    continue;
                }
                String itemDisplayName = itemMeta.getDisplayName();
                String configDisplayName = CONFIGHANDLER.getDisplayName();
                if (itemType == Material.EXPERIENCE_BOTTLE && itemDisplayName.equals(configDisplayName) && itemLore.equals(configLore)) {
                    freeSpace += (s.getMaxStackSize() - s.getAmount());
                }
                usedStacks++;
            }
        }

        freeSpace += (36 - usedStacks) * 64;
        return freeSpace >= amount;
    }
}