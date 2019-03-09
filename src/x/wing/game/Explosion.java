/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x.wing.game;

/**
 *
 * @author Kacper
 */
public class Explosion extends Object {
    
    public Explosion (Stage stage)
    {
        super(stage);
        setSpriteName("explosion.gif");
    }
    
    public void act()
    {
        super.act();
        try {
                Thread.sleep(40);
            } catch (InterruptedException e) {}
        remove();
    }
}