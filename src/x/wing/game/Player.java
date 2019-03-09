/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x.wing.game;

import java.awt.event.KeyEvent;

public class Player extends Object {
    protected static final int PLAYER_SPEED = 4;
    protected int vx;
    private boolean left,right;
    public static final int MAX_SHIELD = 200;
    private int score;
    private int shield;
    
    public Player(Stage stage)
    {
        super(stage);
        setSpriteName ("xwing.gif");
        shield = MAX_SHIELD;
    }
    
    public int getVx()
    {
        return vx;
    }
    
    public void setVx(int i)
    {
        vx=i;
    }
    
    public void addScore(int i)
    {
        score+=i;
    }
    
    public int getScore()
    {
        return score;
    }
    
    public void setScore(int i)
    {
        score = i;
    }
    
    public void addShield(int i)
    {
        shield+=i;
    }
    
    public int getShield()
    {
        return shield;
    }
    
    public void setShield(int i)
    {
        shield = i;
    }
    
    public void act()
    {
        super.act();
        x+=vx;
        if (x<0||x>Stage.WIDTH)
        {
            vx=-vx;
        }
    }
    
    public void fire()
    {
        Laser a = new Laser(stage);
        a.setX(x);
        a.setY(y-a.getHeight());
        stage.addObject(a);
        Laser b = new Laser(stage);
        b.setX(x+60);
        b.setY(y-b.getHeight());
        stage.addObject(b);
        stage.getSoundCache().playSound("playerlaser.wav");
    }
    
    public void collision(Object a)
    {
        if (a instanceof InterceptorLaser)
        {
            a.remove();
            addShield(-40);
            explosion();
            stage.getSoundCache().playSound("explosion.wav");
            if (getShield()<=0)
            {
                stage.gameOver();
            }
        }
        if (a instanceof RepairKit)
        {
            a.remove();
            if (getShield()<200)
            {
                addShield(40);
            }
        }
    }
    
    protected void updateSpeed()
    {
        vx=0;
        if (left)
        {
            vx=-PLAYER_SPEED;
        }
        if (right)
        {
            vx=PLAYER_SPEED;
        }
    }
    
    public void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_LEFT : left=false; break;
            case KeyEvent.VK_RIGHT : right=false; break;
        }
        updateSpeed();
    }
    
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_LEFT : left=true; break;
            case KeyEvent.VK_RIGHT : right=true; break;
            case KeyEvent.VK_SPACE : fire(); break; 
        }
        updateSpeed();
    }
}