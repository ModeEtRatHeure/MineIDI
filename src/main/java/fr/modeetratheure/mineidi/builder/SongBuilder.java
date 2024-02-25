package fr.modeetratheure.mineidi.builder;

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
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.util.Vector;

import java.util.List;

public class SongBuilder {

    private BlockManager blockManager;

    public SongBuilder(){
        blockManager = new BlockManager();
    }

    public void build(List<Pair<List<Note>, DelaysBox>> song, Location placeToStartBuilding){
        int locOffset = 0;
        Vector direction = placeToStartBuilding.getDirection();
        blockManager = new BlockManager();
        placeButton(placeToStartBuilding, blockManager.getBlockFaceFromDirection(direction));
        for(Pair<List<Note>, DelaysBox> p:song.subList(0, 20)){
            Pair<Integer, Integer> redstoneCircuitData = p.getSecond().getRedstoneCircuitData();
            if(redstoneCircuitData.getFirst() > 0 | redstoneCircuitData.getSecond() != 0) {
                for (int i = 0; i < redstoneCircuitData.getFirst(); i++) {
                    locOffset += 1;
                    blockManager.placeRepeater(
                            blockManager.offsetLocationAccordingDirection(placeToStartBuilding, direction, locOffset),
                            4,
                            blockManager.getRepeaterRotationFromDirection(direction)
                    );
                }
                if (redstoneCircuitData.getSecond() != 0) {
                    locOffset += 1;
                    blockManager.placeRepeater(
                            blockManager.offsetLocationAccordingDirection(placeToStartBuilding, direction, locOffset),
                            redstoneCircuitData.getSecond(),
                            blockManager.getRepeaterRotationFromDirection(direction)
                    );
                }
            }else{
                locOffset += 1;
                blockManager.placeRepeater(
                        blockManager.offsetLocationAccordingDirection(placeToStartBuilding, direction, locOffset),
                        0,
                        StructureRotation.NONE
                );
            }
            locOffset += 1;
            int noteBlockOffset = - (int) (p.getFirst().size() / 2); //pour placer la ligne de noteblock de manière relativement symétrique par rapport à la ligne de redstone
            Location noteLineLoc = blockManager.offsetLocationAccordingDirection(
                    placeToStartBuilding, direction, locOffset
            );
            for(Note note:p.getFirst()){
                placeNoteBlock(
                        blockManager.offsetLocationAccordingNormalDirection(noteLineLoc, direction, noteBlockOffset),
                        note
                );
                noteBlockOffset += 1;
            }
        }
    }

    public void placeNoteBlock(Location loc, Note note){
        Block noteBlock = blockManager.placeBlock(Material.NOTE_BLOCK, yLocChange(1, loc));
        NoteBlock nB = (NoteBlock) blockManager.getBlockData(yLocChange(1, loc));
        int pitch = normalizePitch(note.pitch());
        nB.setNote(new org.bukkit.Note(pitch));
        noteBlock.setBlockData(nB);
        blockManager.placeBlock(note.sound().block(), loc);
    }

    public void placeButton(Location loc, BlockFace face){
        Block button = blockManager.placeBlock(Material.STONE_BUTTON, loc);
        BlockData btn = blockManager.getBlockData(loc);
        ((Directional) btn).setFacing(face);
        button.setBlockData(btn);
    }

    private Location yLocChange(int offset, Location loc){
        return loc.clone().add(0, offset, 0);
    }

	private int normalizePitch(int pitch){
        int normalizedPitch = pitch;
        int baseFValue = 54;
        int maxFValue = baseFValue + 24;
        if(pitch < baseFValue){
            int multiplier = (baseFValue - pitch) / 12 + 1;//The number of octaves to up
            normalizedPitch = pitch + 12 * multiplier;
        }else if(pitch > maxFValue){
            int multiplier = (pitch - maxFValue) / 12 + 1;//The number of octaves to lower
            normalizedPitch = pitch - 12 * multiplier;
        }
        return normalizedPitch - baseFValue;
    }

}
