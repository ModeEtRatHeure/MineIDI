package fr.modeetratheure.mineidi.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.util.Vector;

import java.util.Objects;

public class BlockManager {

    public BlockData getBlockData(Location loc){
        return Objects.requireNonNull(loc.getWorld()).getBlockAt(loc).getBlockData();
    }

    public Block placeBlock(Material type, Location loc){
        Block block = Objects.requireNonNull(loc.getWorld()).getBlockAt(loc);
        block.setType(type);
        return block;
    }

    public void placeRepeater(Location loc, int delay, StructureRotation rotation){
        placeBlock(Material.STONE, loc);
        Location loc2 = yLocChange(1, loc);
        Block repeater = placeBlock(Material.REPEATER, loc2);
        Repeater repeaterData = (Repeater) getBlockData(loc2);
        repeaterData.rotate(rotation);
        repeaterData.setDelay(delay);
        repeater.setBlockData(repeaterData);
    }

    private Location yLocChange(int offset, Location loc){
        return loc.clone().add(0, offset, 0);
    }

    public StructureRotation getRepeaterRotationFromDirection(Vector direction){
        if(Math.abs(direction.getX()) <= Math.abs(direction.getZ())){
            return direction.getX() > 0d ? StructureRotation.NONE : StructureRotation.CLOCKWISE_180;
        }
        return direction.getZ() > 0d ? StructureRotation.CLOCKWISE_90 : StructureRotation.COUNTERCLOCKWISE_90;
    }

    public BlockFace getBlockFaceFromDirection(Vector direction){
        if(Math.abs(direction.getX()) >= Math.abs(direction.getY())){
            return direction.getX() >= 0d ? BlockFace.EAST : BlockFace.WEST;
        }
        return direction.getZ() >= 0d ? BlockFace.SOUTH : BlockFace.NORTH;
    }

    public Location offsetLocationAccordingDirection(Location loc, Vector direction, int offset){
        Vector roundedDirection;
        direction.normalize();
        if(Math.abs(direction.getX()) >= Math.abs(direction.getZ())){
            roundedDirection = new Vector(direction.getX() * offset, 0, 0);
        }else{
            roundedDirection = new Vector(0, 0, direction.getZ() * offset);
        }
        return loc.clone().add(roundedDirection);
    }

    public Location offsetLocationAccordingNormalDirection(Location loc, Vector direction, int offset){
        Vector roundedDirection;
        direction.normalize();
        if(Math.abs(direction.getX()) <= Math.abs(direction.getZ())){
            roundedDirection = new Vector(direction.getX() * offset, 0, 0);
        }else{
            roundedDirection = new Vector(0, 0, direction.getZ() * offset);
        }
        return loc.clone().add(roundedDirection);
    }

}
