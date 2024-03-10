package fr.modeetratheure.mineidi.builder;

import fr.modeetratheure.mineidi.MineIDI;
import fr.modeetratheure.mineidi.music.DelaysBox;
import fr.modeetratheure.mineidi.music.Note;
import fr.modeetratheure.mineidi.utils.storage.Pair;
import fr.modeetratheure.mineidi.utils.BlockManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.util.Vector;

import java.util.List;

public class SongBuilder {

    private BlockManager blockManager;
    private final MineIDI mineIDI;

    public SongBuilder(MineIDI mineIDI){
        blockManager = new BlockManager();
        this.mineIDI = mineIDI;
    }

    public void build(List<Pair<List<Note>, DelaysBox>> song, Location placeToStartBuilding){
        blockManager = new BlockManager();
        Vector direction = blockManager.simplifyVector(placeToStartBuilding.getDirection());
        blockManager.placeButton(placeToStartBuilding, blockManager.getBlockFaceFromDirection(direction));
        Location newLoc = blockManager.offsetLocationAccordingDirection(placeToStartBuilding, direction, 1);
        blockManager.placeRepeater(newLoc, 0, blockManager.getRepeaterRotationFromDirection(direction));
        new BuildRunnable(song, blockManager, newLoc, direction).runTaskTimer(mineIDI, 0, 1);
    }

}
