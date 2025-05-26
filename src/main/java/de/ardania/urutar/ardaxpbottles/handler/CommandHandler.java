package de.ardania.urutar.ardaxpbottles.handler;

import de.ardania.urutar.ardaxpbottles.commands.CheckCommand;
import de.ardania.urutar.ardaxpbottles.commands.CreateCommand;
import de.ardania.urutar.ardaxpbottles.commands.HelpCommand;
import de.ardania.urutar.ardaxpbottles.commands.SetCommand;
import de.ardania.urutar.ardaxpbottles.util.Converter;
import de.ardania.urutar.ardaxpbottles.util.XpCalc;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static de.ardania.urutar.ardaxpbottles.ArdaXpBottles.MESSAGEHANDLER;

/**
 * Handler class for all commands.
 */
public class CommandHandler implements CommandExecutor, TabCompleter {

    JavaPlugin plugin;
    CreateCommand createCommand;
    CheckCommand checkCommand;

    public CommandHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
            if (args.length == 1) {
                if (player.hasPermission("ardaxpbottles.help")) {
                    completions.add("help");
                    completions.add("info");
                }
                if (player.hasPermission("ardaxpbottles.check"))
                    completions.add("check");
                if (player.hasPermission("ardaxpbottles.create"))
                    completions.add("create");
                if (player.hasPermission("ardaxpbottles.set"))
                    completions.add("set");
                return completions;
            }
            if (args.length == 2 && args[0].equals("check") && player.hasPermission("ardaxpbottles.check.other")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    completions.add(p.getName());
                }
                return completions;
            }
            if (args.length == 2 && args[0].equals("create")) {
                completions.add("<Amount>");
                return completions;
            }
            if (args.length == 2 && args[0].equals("set") && player.hasPermission("ardaxpbottles.set")) {
                completions.add("euronencost");
                completions.add("experiencecost");
                completions.add("diplayedname");
                completions.add("lore");
                return completions;
            }
            if (args.length == 3 && args[0].equals("set") && player.hasPermission("ardaxpbottles.set")) {
                completions.add("<value>");
                return completions;
            }
        }
        return completions;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player;
        if (!(sender instanceof Player)) {
            return false;
        }
        player = (Player) sender;

        if (args.length == 0)
            return HelpCommand.execute(player, plugin);

        String cmd = args[0].toLowerCase();
        String arg1 = args.length > 1 ? args[1] : null;
        String arg2 = args.length > 2 ? args[2] : null;

        switch (cmd) {
            case "help":
            case "info": {
                return HelpCommand.execute(player, plugin);
            }
            case "check": {
                return CheckCommand.execute(player, arg1);
            }
            case "create": {
                return CreateCommand.execute(player, arg1);
            }
            case "set": {
                return SetCommand.execute(player, arg1, arg2);
            }
            default: {
                return false;
            }
        }
    }
}