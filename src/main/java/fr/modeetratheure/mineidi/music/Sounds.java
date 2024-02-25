package fr.modeetratheure.mineidi.music;

import fr.modeetratheure.mineidi.utils.storage.Pair;
import org.bukkit.Material;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import java.util.Arrays;

public enum Sounds {

    BANJO(Material.HAY_BLOCK, new Pair<>(104, 111)),
    /*BASS_DRUM(Material.STONE),*///TODO comprendre pk il n'est pas dans la liste
    BASS_GUITAR(Material.ACACIA_WOOD, new Pair<>(32, 39)),
    BELL(Material.GOLD_BLOCK, new Pair<>(8, 10)),
    BIT(Material.EMERALD_BLOCK, new Pair<>(80, 103)),
    CHIME(Material.PACKED_ICE, new Pair<>(40, 55)),
    COW_BELL(Material.SOUL_SAND, new Pair<>(14, 23)),
    DIDGERIDOO(Material.PUMPKIN, new Pair<>(56, 71)),
    FLUTE(Material.CLAY, new Pair<>(72, 79)),
    GUITAR(Material.BLACK_WOOL, new Pair<>(24, 31)),
    IRON_XYLOPHONE(Material.IRON_BLOCK, new Pair<>(11, 12)),
    PIANO(Material.GRASS_BLOCK, new Pair<>(0, 1)),
    PLING(Material.GLOWSTONE, new Pair<>(2, 7)),
    SNARE_DRUM(Material.SAND, new Pair<>(112, 119)),
    STICKS(Material.GLASS, new Pair<>(120, 127)),
    XYLOPHONE(Material.BONE_BLOCK, new Pair<>(13, 13));

    private final Material block;
    private final Pair<Integer, Integer> concernedRange;
    private static final Instrument[] instruments;

    static {
        try {
            instruments = MidiSystem.getSynthesizer().getDefaultSoundbank().getInstruments();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException("Failed to load instruments");
        }
    }

    Sounds(Material block, Pair<Integer, Integer> concernedRange) {
        this.block = block;
        this.concernedRange = concernedRange;
    }

    public Material block(){
        return block;
    }

    public static Sounds getSoundFromInstrumentNumber(int i){
        return Arrays.stream(Sounds.values()).filter(e -> e.concernedRange.getFirst() <= i && e.concernedRange.getSecond() >= i).toList().get(0);
    }
}
