package fr.modeetratheure.mineidi.music;

public record Note(Sounds sound, int pitch) {

    public Note(Sounds sound, int pitch){
        this.sound = sound;
        this.pitch = pitch;
    }

}
