package fr.modeetratheure.mineidi.commands;

import fr.modeetratheure.mineidi.midi.SongBuilder;
import fr.modeetratheure.mineidi.midi.SongManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBuildExecutor implements CommandExecutor {

    SongManager songManager;

    public CommandBuildExecutor(SongManager songManager){
        this.songManager = songManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(args.length == 1){
                String songName = args[0].startsWith("\"") && args[0].endsWith("\"") ? args[0].substring(1, args[0].length() - 2) : args[0];
                SongBuilder songBuilder = new SongBuilder();
                if(songManager.contains(songName)){
                    songBuilder.build(songManager.getSong(songName), ((Player) sender).getLocation());
                    sender.sendMessage(ChatColor.GREEN + "Successfully built " + songName);
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Unregistered song.\nYou can register a song with the command: /read <songname>");
            }
            if(args.length < 1){
                sender.sendMessage(ChatColor.RED + "Missing song name.\nCorrect usage: /build <songname>");
            }
            if(args.length > 1){
                sender.sendMessage(ChatColor.RED + "Too many args.\nCorrect usage: /build <songname>");
            }
        }
        return false;
    }
}
