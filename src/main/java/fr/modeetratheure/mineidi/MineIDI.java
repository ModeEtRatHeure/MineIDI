package fr.modeetratheure.mineidi;

import fr.modeetratheure.mineidi.commands.CommandBuildExecutor;
import fr.modeetratheure.mineidi.commands.CommandReadExecutor;
import fr.modeetratheure.mineidi.midi.SongManager;
import fr.modeetratheure.mineidi.utils.Helper;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MineIDI extends JavaPlugin {

    @Override
    public void onEnable() {
        Helper.init(this);

        SongManager songManager = new SongManager();
        Objects.requireNonNull(getCommand("read")).setExecutor(new CommandReadExecutor(songManager));
        Objects.requireNonNull(getCommand("build")).setExecutor(new CommandBuildExecutor(songManager, this));
    }

}
