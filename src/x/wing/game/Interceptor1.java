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
public class Interceptor1 extends Object {
    
    protected int vx;
    protected static final double FIRING_FREQ = 0.01;
    
    public Interceptor1(Stage stage)
    {
        super(stage);
        setSpriteName("interceptor.gif");
    }
    
    public int getVx()
    {
        return vx;
    }
    
    public void setVx(int i)
    {
        vx=i;
    }
    
    public void fire()
    {
        InterceptorLaser m = new InterceptorLaser(stage);
        m.setX(x+getWidth()/2);
        m.setY(y+getHeight()/2);
        stage.addObject(m);
        stage.getSoundCache().playSound("interceptorlaser1.wav");
    }
    public void act()
    {
        super.act();
        x += vx;
        if (x < 0 || x > Stage.WIDTH-50)
        {
           vx = -vx; 
        }
        if(Math.random()<FIRING_FREQ)
        {
            fire();
        }
    }
    
    public void collision(Object a)
    {
        if (a instanceof Laser)
        {
            remove();
            explosion();
            stage.getSoundCache().playSound("explosion.wav");
            spawn();
            stage.getPlayer().addScore(20);
        }
    }
    
    public void spawn()
    {
        if (getVx()!=0)
        {
            Interceptor1 m = new Interceptor1(stage);
            m.setX((int)(Math.random()*Stage.WIDTH));
            m.setY((int)(Math.random()*Stage.GAME_HEIGHT/2));
            m.setVx((int)(Math.random()*20-10)+1);
            stage.addObject(m);
        }
    }
    
}