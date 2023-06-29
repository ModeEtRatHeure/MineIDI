package fr.modeetratheure.mineidi.midi;

import fr.modeetratheure.mineidi.music.DelaysBox;
import fr.modeetratheure.mineidi.music.Note;
import fr.modeetratheure.mineidi.music.Sounds;
import fr.modeetratheure.mineidi.utils.storage.Pair;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MidiFileReader {

    private List<Pair<List<Note>, DelaysBox>> pairList;
    private final HashMap<Long, List<Note>> decodedSequence = new HashMap<>();
    private final int[] channelArray = new int[16];

    public List<Pair<List<Note>, DelaysBox>> read(String fileName) throws InvalidMidiDataException, IOException {
        Sequence seq = MidiSystem.getSequence(new File(fileName));
        for(Track t:seq.getTracks()){
            for(int i = 0; i < t.size(); i++){
                readMidiEvent(t.get(i));
            }
        }
        generatePairList();
        return pairList;
    }

    private void readMidiEvent(MidiEvent event){
        if(event.getMessage() instanceof ShortMessage msg){
            if(msg.getCommand() == ShortMessage.PROGRAM_CHANGE){
                channelArray[msg.getChannel()] = msg.getData1();
                return;
            }
            if(msg.getCommand() == ShortMessage.NOTE_ON) {
                List<Note> notes = decodedSequence.computeIfAbsent(event.getTick(), k -> new ArrayList<>());
                notes.add(new
                        Note(Sounds.getSoundFromInstrumentNumber(channelArray[msg.getChannel()]), msg.getData1()));
                return;
            }
        }
    }

    private void generatePairList(){
        pairList = new ArrayList<>();
        List<Map.Entry<Long, List<Note>>> list = new ArrayList<>(decodedSequence.entrySet());
        list.sort(Map.Entry.comparingByKey());
        for(int i = 0; i < list.size(); i++){
            if(i == 0){
                pairList.add(new Pair<>(list.get(i).getValue(), new DelaysBox(list.get(i).getKey(), list.get(i).getValue().size())));
                continue;
            }
            pairList.add(new Pair<>(list.get(i).getValue(), new DelaysBox(list.get(i).getKey() - list.get(i - 1).getKey(), list.get(i).getValue().size())));
        }
    }

}
