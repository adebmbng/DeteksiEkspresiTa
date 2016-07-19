package com.ta.debam.deteksiekspresitugasakhirfinal.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Debam on 5/2/2016.
 */
public class SongManager {
    final String MEDIA_PATH2 = Environment.getExternalStorageDirectory().getPath()+"/";
    final String MEDIA_PATH = "/storage/";

    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private String mp3Pattern = ".mp3";

    public SongManager(){

    }

    public ArrayList<HashMap<String, String>> getPlayList(){
        Log.i("Path", MEDIA_PATH);
        if(MEDIA_PATH != null){
            File home = new File(MEDIA_PATH);
            File[] listFiles = home.listFiles();

            if(listFiles!=null && listFiles.length>0){
                for(File file: listFiles){
                    Log.i("Path",file.getAbsolutePath());
                    if(file.isDirectory()){
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        } else if(MEDIA_PATH2 != null){
            File home = new File(MEDIA_PATH2);
            File[] listFiles = home.listFiles();

            if(listFiles!=null && listFiles.length>0){
                for(File file: listFiles){
                    Log.i("Path",file.getAbsolutePath());
                    if(file.isDirectory()){
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
        return songsList;
    }

    private void addSongToList(File song) {
        if(song.getName().endsWith(mp3Pattern)){
            HashMap<String, String> songmap = new HashMap<String,String>();
            songmap.put("songTitle",song.getName().substring(0, (song.getName().length()-4)));
            songmap.put("songPath",song.getPath());

            songsList.add(songmap);
        }
    }

    private void scanDirectory(File directory) {
        if(directory!= null){
            File[] listFiles = directory.listFiles();

            if(listFiles != null && listFiles.length>0){
                for(File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
    }

}
