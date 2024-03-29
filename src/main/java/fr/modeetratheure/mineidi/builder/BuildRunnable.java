package fr.modeetratheure.mineidi.builder;

import fr.modeetratheure.mineidi.music.DelaysBox;
import fr.modeetratheure.mineidi.music.Note;
import fr.modeetratheure.mineidi.utils.BlockManager;
import fr.modeetratheure.mineidi.utils.storage.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

class BuildRunnable extends BukkitRunnable {

    private final int iterationOffset = 10;
    private int currentStartingIndex = 0;
    private int currentEndingIndex = iterationOffset;
    private final List<Pair<List<Note>, DelaysBox>> song;
    private final BlockManager blockManager;
    private final Location placeToStartBuilding;
    private final Vector direction;
    private int locOffset = 0;

    public BuildRunnable(List<Pair<List<Note>, DelaysBox>> song, BlockManager blockManager, Location placeToStartBuilding, Vector direction){
        this.song = song;
        this.blockManager = blockManager;
        this.placeToStartBuilding = placeToStartBuilding;
        this.direction = direction;
    }

    @Override
    public void run() {
        for(Pair<List<Note>, DelaysBox> p:song.subList(currentStartingIndex, currentEndingIndex < song.size() ? currentEndingIndex : song.size() - 1)){
            Pair<Integer, Integer> redstoneCircuitData = p.getSecond().getRedstoneCircuitData();
            if(redstoneCircuitData.getFirst() > 0 || redstoneCircuitData.getSecond() != 0) {
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
                        blockManager.getRepeaterRotationFromDirection(direction)
                );
            }
            locOffset += 1;
            int noteBlockOffset = - p.getFirst().size() / 2; //pour placer la ligne de noteblock de manière relativement symétrique par rapport à la ligne de redstone
            Location noteLineLoc = blockManager.offsetLocationAccordingDirection(
                    placeToStartBuilding, direction, locOffset
            );
            for(Note ignored:p.getFirst()){
                if(noteBlockOffset == 0){
                    blockManager.placeRepeater(
                            noteLineLoc,
                            0,
                            StructureRotation.NONE
                    );
                    noteBlockOffset += 1;
                }
                blockManager.placeRepeater(
                        blockManager.offsetLocationAccordingNormalDirection(noteLineLoc, direction, noteBlockOffset),
                        0,
                        StructureRotation.NONE
                );
                noteBlockOffset += 1;
            }
            locOffset += 1;
            noteBlockOffset = - p.getFirst().size() / 2; //pour placer la ligne de noteblock de manière relativement symétrique par rapport à la ligne de redstone
            noteLineLoc = blockManager.offsetLocationAccordingDirection(
                    placeToStartBuilding, direction, locOffset
            );
            for(Note note:p.getFirst()){
                if(noteBlockOffset == 0){
                    blockManager.placeRepeater(
                            noteLineLoc,
                            0,
                            StructureRotation.NONE
                    );
                    noteBlockOffset += 1;
                }
                placeNoteBlock(
                        blockManager.offsetLocationAccordingNormalDirection(noteLineLoc, direction, noteBlockOffset)
                                .add(new Vector(0, -1, 0)),
                        note
                );
                noteBlockOffset += 1;
            }
        }
        currentStartingIndex = currentEndingIndex;
        currentEndingIndex += iterationOffset;
        if(currentStartingIndex >= song.size()){
            cancel();
        }
    }

    private void placeNoteBlock(Location loc, Note note){
        Block noteBlock = blockManager.placeBlock(Material.NOTE_BLOCK, blockManager.yLocChange(1, loc));
        NoteBlock nB = (NoteBlock) blockManager.getBlockData(blockManager.yLocChange(1, loc));
        int pitch = normalizePitch(note.pitch());
        nB.setNote(new org.bukkit.Note(pitch));
        noteBlock.setBlockData(nB);
        blockManager.placeBlock(note.sound().block(), loc);
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
