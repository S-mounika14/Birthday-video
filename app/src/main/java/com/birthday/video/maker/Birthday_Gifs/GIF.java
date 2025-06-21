package com.birthday.video.maker.Birthday_Gifs;

import java.io.Serializable;
import java.util.ArrayList;

public class GIF implements Serializable
{
    private int resource;
    private String path;
    private ArrayList<String> frames;
    private boolean isInSDcard;
    private int[] res_frames;
    private String id;

    public void setResource(int resource) {
        this.resource = resource;
    }

    public int getResource() {
        return resource;
    }

    public boolean isInSDcard() {
        return isInSDcard;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        isInSDcard=true;
        this.path = path;
    }

    public void setFrames(ArrayList<String> frames) {
        this.frames = frames;

    }

    public ArrayList<String> getFrames() {
        return frames;
    }


    public void setFrames(int[] frames) {
        this.res_frames=frames;
    }

    public int[] getRes_frames() {
        return res_frames;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
