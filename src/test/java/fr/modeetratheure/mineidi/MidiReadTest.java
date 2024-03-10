package fr.modeetratheure.mineidi;

import fr.modeetratheure.mineidi.midi.MidiFileReader;
import fr.modeetratheure.mineidi.music.DelaysBox;
import fr.modeetratheure.mineidi.music.Note;
import fr.modeetratheure.mineidi.utils.storage.Pair;
import org.junit.jupiter.api.Test;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MidiReadTest {

    @Test
    public void midiFileReaderTest() throws InvalidMidiDataException, IOException {
        for(Pair<List<Note>, DelaysBox> p:new MidiFileReader().read("C:\\Users\\clair\\OneDrive\\Bureau\\Java\\serv 1.19.4\\plugins\\MineIDI\\songs\\test.mid")){
            System.out.println("--------");
            System.out.print("notes: ");
            p.getFirst().forEach(n -> System.out.print(n.pitch() + ";"));
            System.out.print("\n delay: ");
            System.out.println(p.getSecond().getDelay());
        }
    }

    @Test
    public void testTickPerSecond(){
        Sequence seq = null;
        try {
            seq = MidiSystem.getSequence(new File("C:\\Users\\clair\\OneDrive\\Bureau\\Java\\serv 1.19.4\\plugins\\MineIDI\\songs\\test.mid"));
        } catch (InvalidMidiDataException | IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(seq.getMicrosecondLength());
        System.out.println((seq.getMicrosecondLength() * 10e-5*5e-1));
        System.out.println(seq.getTickLength()/(seq.getMicrosecondLength()*1e-5));
    }

    @Test
    public void checkIfZeroDelaysInDelaysBox(){
        try {
            System.out.println("TEST");
            new MidiFileReader().read("C:\\Users\\clair\\OneDrive\\Bureau\\Java\\serv 1.19.4\\plugins\\MineIDI\\songs\\test.mid")
                    .forEach(e -> {
                        if(e.getSecond().getDelay() == 0) {
                            System.out.println("0 found");
                        }
                    });
            System.out.println("POTC");
            new MidiFileReader().read("C:\\Users\\clair\\OneDrive\\Bureau\\Java\\serv 1.19.4\\plugins\\MineIDI\\songs\\potc.mid")
                    .forEach(e -> {
                        if(e.getSecond().getDelay() == 0) {
                            System.out.println("0 found");
                        }
                    });
            System.out.println("QUEEN");
            new MidiFileReader().read("C:\\Users\\clair\\OneDrive\\Bureau\\Java\\serv 1.19.4\\plugins\\MineIDI\\songs\\queen.mid")
                    .forEach(e -> {
                        if(e.getSecond().getDelay() == 0) {
                            System.out.println("0 found");
                        }
                    });
        } catch (InvalidMidiDataException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
