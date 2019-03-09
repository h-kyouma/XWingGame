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
public class InterceptorLaser extends Object {
    protected static final int BULLET_SPEED = 3;
    
    public InterceptorLaser(Stage stage)
    {
        super(stage);
        setSpriteName ("InterceptorLaser.gif");
    }
    
    public void act()
    {
        super.act();
        y+=BULLET_SPEED;
        if (y>Stage.GAME_HEIGHT)
        {
            remove();
        }
    }
}