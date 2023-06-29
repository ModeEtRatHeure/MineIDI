package fr.modeetratheure.mineidi.midi;

import fr.modeetratheure.mineidi.music.DelaysBox;
import fr.modeetratheure.mineidi.music.Note;
import fr.modeetratheure.mineidi.utils.storage.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SongManager {

    private Map<String, List<Pair<List<Note>, DelaysBox>>> songs = new HashMap<>();

    public void newSong(String name, List<Pair<List<Note>, DelaysBox>> song){
        songs.put(name, song);
    }

    public List<Pair<List<Note>, DelaysBox>> getSong(String songName){
        return Objects.requireNonNull(songs.get(songName));
    }

    public boolean contains(String key){
        return songs.containsKey(key);
    }

}
