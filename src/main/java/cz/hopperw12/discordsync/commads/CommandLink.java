package cz.hopperw12.discordsync.commads;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLink implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player))
            return false;



        /*

        if (je registrovany?)
            Jsi registrovany!
        else (token je vytvoren)
            Pouzij tento token
        else
            Vytvorit token
         */

        return false;
    }
}
