/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x.wing.game;

import java.awt.image.ImageObserver;

public interface Stage extends ImageObserver {
    
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int SPEED = 10;
    public static final int GAME_HEIGHT = 668;
    public SpriteCache getSpriteCache();
    public SoundCache getSoundCache();
    public void addObject (Object a);
    public void gameOver();
    public void gameWon();
    public Player getPlayer();
}