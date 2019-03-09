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
public class RepairKit extends Object {
    protected static final int REPKIT_SPEED = 5;
    
    public RepairKit(Stage stage)
    {
        super(stage);
        setSpriteName("repairkit1.gif");
    }
    
    public void act()
    {
        super.act();
        y+=REPKIT_SPEED;
        if (y>Stage.GAME_HEIGHT)
        {
            remove();
        }
    }
}
