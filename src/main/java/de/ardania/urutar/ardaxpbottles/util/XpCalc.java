package de.ardania.urutar.ardaxpbottles.util;

import org.bukkit.entity.Player;

public class XpCalc {

    // Calculate amount of EXP needed to level up
    public static long getExpToLevelUp(int level) {
        if (level > 30)
            return 9L * level - 158;
        if (level > 15)
            return 5L * level - 38;
        return 2L * level + 7;
    }

    // Calculate total experience up to a level
    public static long getExpAtLevel(int level){
        if (level > 31) {
            return (long) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220.0);
        }

        if (level > 16) {
            return (long) (2.5 * Math.pow(level, 2) - 40.5 * level + 360.0);
        }

        return (long) (Math.pow(level, 2) + (long) 6 * level);
    }

    // Calculate player's current EXP amount
    public static long getPlayerExp(Player player){
        if (player == null)
            return -1;

        long exp = 0;
        int level = player.getLevel();

        // Get the amount of XP in past levels
        exp += getExpAtLevel(level);

        // Get amount of XP towards next level
        exp += Math.round(getExpToLevelUp(level) * player.getExp());

        return exp;
    }
}
