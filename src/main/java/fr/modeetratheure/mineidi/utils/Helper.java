package fr.modeetratheure.mineidi.utils;

import fr.modeetratheure.mineidi.MineIDI;

import java.io.File;
import java.io.IOException;

public class Helper {

    private static String songsFolder;

    public static void init(MineIDI main){
        songsFolder = main.getDataFolder() + File.separator + "songs";
        new File(songsFolder).mkdir();
    }

    public static String getSongsFolder(){
        return songsFolder;
    }

}
