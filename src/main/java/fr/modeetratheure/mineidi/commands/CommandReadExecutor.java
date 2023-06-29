package fr.modeetratheure.mineidi.commands;

import fr.modeetratheure.mineidi.midi.MidiFileReader;
import fr.modeetratheure.mineidi.midi.SongManager;
import fr.modeetratheure.mineidi.utils.Helper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.sound.midi.InvalidMidiDataException;
import java.io.File;
import java.io.IOException;

public class CommandReadExecutor implements CommandExecutor {

    SongManager songManager;

    public CommandReadExecutor(SongManager songManager){
        this.songManager = songManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1){
            String songName = args[0].startsWith("\"") && args[0].endsWith("\"") ? args[0].substring(1, args[0].length() - 2) : args[0];
            songName = songName.endsWith(".mid") ? songName.split("\\.")[0] : songName;
            String fileName = Helper.getSongsFolder() + File.separator + songName + ".mid";
            try {
                songManager.newSong(songName, new MidiFileReader().read(fileName));
            } catch (InvalidMidiDataException | IOException e) {
                sender.sendMessage(ChatColor.RED + "Invalid file name");
                return false;
            }
            sender.sendMessage(ChatColor.GREEN + "Succesfully read " + songName);
            return true;
        }
        if(args.length > 1){
            sender.sendMessage(ChatColor.RED + "Too many args.\nCorrect usage: /read <filename>");
        }
        if(args.length < 1){
            sender.sendMessage(ChatColor.RED + "Missing file name.\nCorrect usage: /read <filename>");
        }
        return false;
    }
}
