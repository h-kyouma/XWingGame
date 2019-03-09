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
public class Laser extends Object {
    protected static final int BULLET_SPEED = 10;
    
    public Laser(Stage stage)
    {
        super(stage);
        setSpriteName ("laser.gif");
    }
    
    public void act()
    {
        super.act();
        y-=BULLET_SPEED;
        if (y<0)
        {
            remove();
        }
    }
}