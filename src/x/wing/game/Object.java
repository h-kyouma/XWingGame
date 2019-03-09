/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x.wing.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Object {
    
    protected int x,y;
    protected int width, height;
    protected String spriteName;
    protected Stage stage;
    protected SpriteCache spriteCache;
    protected boolean markedForRemoval;
    
    public Object (Stage stage)
    {
        this.stage = stage;
        spriteCache = stage.getSpriteCache();
    }
    
    public String getSpriteName()
    {
        return spriteName;
    }
    
    public void setSpriteName(String string)
    {
        spriteName = string;
        height=0;
        width=0;
        for (int i=0; i<string.length();i++)
        { 
            BufferedImage image = spriteCache.getSprite(spriteName);
            height = Math.max(height, image.getHeight());
            width = Math.max(width, image.getWidth());
        }

    }
    
    public void paint(Graphics2D g)
    {
        g.drawImage(spriteCache.getSprite(spriteName), x, y, stage);
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setY(int i)
    {
        y=i;
    }
    
    public int getX()
    {
        return x;
    }
    
    public void setX(int i)
    {
        x=i;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public void setHeight(int i)
    {
        height = i;
    }
    
    public void setWidth(int i)
    {
        width = i;
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(x, y, width, height);
    }
    
    public boolean isMarkedForRemoval()
    {
        return markedForRemoval;
    }
    
    public void act() {}
    
    public void remove()
    {
        markedForRemoval = true;
    }
    
    public void collision(Object a) {}
    
    public void explosion()
    {
        Explosion a = new Explosion(stage);
        a.setX(x-a.getWidth()/4);
        a.setY(y-a.getHeight()/4);
        stage.addObject(a);
    }
}