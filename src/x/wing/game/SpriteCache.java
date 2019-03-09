/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x.wing.game;

import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import java.util.HashMap;

public class SpriteCache {
    
    public HashMap sprites;
    
    public SpriteCache()
    {
        sprites = new HashMap();
    }
    
    private BufferedImage loadImage(String path)
    {
        URL url = null;
        try
        {
            url = getClass().getClassLoader().getResource(path);
            return ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("Error while opening "+path+" as "+url);
            System.out.println("Error: "+e.getClass().getName()+" "+e.getMessage());
            System.exit(0);
            return null;
        }
    }
    
    public BufferedImage getSprite(String path)
    {
        BufferedImage img = (BufferedImage)sprites.get(path);
        if (img==null)
        {
            img = loadImage("img/"+path);
            sprites.put(path, img);
        }
        return img;
    }
}