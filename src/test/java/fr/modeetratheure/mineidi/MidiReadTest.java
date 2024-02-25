package fr.modeetratheure.mineidi;

import fr.modeetratheure.mineidi.midi.MidiFileReader;
import fr.modeetratheure.mineidi.music.DelaysBox;
import fr.modeetratheure.mineidi.music.Note;
import fr.modeetratheure.mineidi.utils.storage.Pair;
import org.junit.jupiter.api.Test;

import javax.sound.midi.InvalidMidiDataException;
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

}
