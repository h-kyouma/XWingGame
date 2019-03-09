/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x.wing.game;

import java.net.URL;
import java.util.HashMap;
import java.applet.Applet;
import java.applet.AudioClip;

public class SoundCache {
    
    protected HashMap sounds;
    
    public SoundCache()
    {
        sounds = new HashMap();
    }
    
    private AudioClip loadSound(String path)
    {
        URL url = null;
        try
        {
            url = getClass().getClassLoader().getResource(path);
            return Applet.newAudioClip(url);
        } catch (Exception e) {
            System.out.println("Error while opening "+path+" as "+url);
            System.out.println("Error: "+e.getClass().getName()+" "+e.getMessage());
            System.exit(0);
            return null;
        }
    }
    
    public AudioClip getAudioClip(String path)
    {
        AudioClip clip = (AudioClip)sounds.get(path);
        if (clip==null)
        {
            clip = (AudioClip) loadSound("sound/"+path);
            sounds.put(path, clip);
        }
        return clip;
    }
    
    public void playSound(final String name)
    {
        new Thread(new Runnable() {
            public void run() {
                getAudioClip(name).play();
            }
        }).start();
    }
    
    public void loopSound(final String name)
    {
        new Thread(new Runnable() {
            public void run() {
                getAudioClip(name).loop();
            }
        }).start();
    }
    
}