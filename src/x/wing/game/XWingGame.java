/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x.wing.game;

import java.applet.AudioClip;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class XWingGame extends Canvas implements Stage, KeyListener {

    public long usedTime;
    public BufferStrategy strategy;
    private SpriteCache spriteCache;
    private SoundCache soundCache;
    private ArrayList Objects;
    private boolean gameLost = false;
    private boolean gameWon = false;
    private BufferedImage background;
    private int t;
    private AudioClip maintheme;
    private static final double REPKIT_FREQ = 0.001;
    
    private Player player;
    
    public XWingGame() /*Constructor*/
    {
        spriteCache = new SpriteCache();
        soundCache = new SoundCache();
        
        JFrame window = new JFrame ("X-Wing Game");
        window.setBounds(0, 0, Stage.WIDTH, Stage.HEIGHT);
        window.setVisible(true);
        window.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        window.setResizable(false);

        JPanel panel = (JPanel) window.getContentPane();
        setBounds(0,0,Stage.WIDTH,Stage.HEIGHT);
        panel.setPreferredSize(new Dimension(Stage.WIDTH, Stage.HEIGHT));
        panel.setLayout(null);
        panel.add(this);
              
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        requestFocus();
        
        addKeyListener(this);
    }
    
    public void initWorld() /*Method used to initialize world*/
    {
        Objects = new ArrayList();
        for (int i=0;i<5;i++)
        {
            Interceptor1 m = new Interceptor1(this);
            m.setX((int)(Math.random()*Stage.WIDTH)-100);
            m.setY(i*50);
            m.setVx((int)(Math.random()*3)+1);
            Objects.add(m);
        }
        
        player = new Player(this);
        player.setX(Stage.WIDTH/2);
        player.setY(Stage.GAME_HEIGHT-player.getHeight());
    }
    
    public void playMainTheme() /*Method used to play background music*/
    {
        maintheme = soundCache.getAudioClip("imperialmarch.wav");
        maintheme.loop();
    }
    
    public void paintWorld()
    {
        Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
        background = spriteCache.getSprite("universe1.gif");
        g.setPaint(new TexturePaint(background, new Rectangle(0, t, background.getWidth(), getHeight())));
        g.fillRect(0, 0, getWidth(), getHeight());
        for (int i=0; i<Objects.size();i++)
        {
            Object m = (Object)Objects.get(i);
            m.paint(g);
        }
        player.paint(g);
        paintStatus(g);
        strategy.show();
    }
    
    public void paintFPS(Graphics2D g)
    {
        g.setFont(new Font("Verdana", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        if (usedTime>0)
        {
            g.drawString(String.valueOf(1000/usedTime)+" fps", Stage.WIDTH-100, Stage.HEIGHT-50);
        }
        else
        {
            g.drawString("--- fps", Stage.WIDTH-100, Stage.HEIGHT-50);
        }
    }
    
    public void paintShield(Graphics2D g)
    {
        g.setPaint(Color.RED);
        g.fillRect(280, Stage.GAME_HEIGHT+10, Player.MAX_SHIELD, 20);
        g.setPaint(Color.GREEN);
        g.fillRect(280+Player.MAX_SHIELD-player.getShield(), Stage.GAME_HEIGHT+10, player.getShield(), 20);
        g.setFont(new Font("Verdana", Font.BOLD, 20));
        g.setPaint(Color.GREEN);
        g.drawString("Shield: ", 170, Stage.GAME_HEIGHT+28);
    }
    
    public void paintScore(Graphics2D g)
    {
        g.setFont(new Font("Verdana", Font.BOLD, 20));
        g.setPaint(Color.GREEN);
        g.drawString("Score: ", 20, Stage.GAME_HEIGHT+28);
        g.setPaint(Color.RED);
        g.drawString(player.getScore()+"", 100, Stage.GAME_HEIGHT+30);
    }
    
    public void paintGameOver()
    {
        Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
        g.setColor(Color.RED);
        g.setFont(new Font("Verdana", Font.BOLD,20));
        g.drawString("GAME OVER", Stage.WIDTH/2-50, Stage.HEIGHT/2);
        strategy.show();
    }
    
    public void paintWon()
    {
        Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
        g.setColor(Color.GREEN);
        g.setFont(new Font("Verdana", Font.BOLD,20));
        g.drawString("CONGRATULATIONS! YOU WON", Stage.WIDTH/2-200, Stage.HEIGHT/2);
        strategy.show();
    }
    
    public void paintBestScore()
    {
        reader Reader = new reader();
        try {
            String bestScoreString = Reader.read();
            Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
            g.setColor(Color.GREEN);
            g.setFont(new Font("Verdana", Font.BOLD,20));
            g.drawString("BEST SCORE! "+bestScoreString, Stage.WIDTH/2-80, Stage.HEIGHT/2+25);
            strategy.show();
        } catch (IOException ex) {
            Logger.getLogger(XWingGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void checkBestScore() throws IOException
    {
        reader Reader = new reader();
        writer Writer = new writer();
        String bestScoreString = Reader.read();
        int bestScoreInt = Integer.valueOf(bestScoreString.trim());
        if (player.getScore()>bestScoreInt)
        {
            Writer.write(Integer.toString(player.getScore()));
            paintBestScore();
        }
    }
    
    public void paintStatus(Graphics2D g)
    {
        paintScore(g);
        paintShield(g);
        paintFPS(g);
    }
    
    public void addObject(Object a)
    {
        Objects.add(a);
    }
    
    public void updateWorld() /*Actions of player and enemies*/
    {
        int i = 0;
        while (i <Objects.size())
        {
            Object m = (Object)Objects.get(i);
            if (m.isMarkedForRemoval())
            {
                Objects.remove(i);
                if (Objects.isEmpty())
                {
                    gameWon();
                }
            }
            else
            {
                m.act();
                i++;
            }
        }
        player.act();
    }
    
    public void spawnRepKit()
    {
        if (Math.random()<REPKIT_FREQ)
        {
            RepairKit m = new RepairKit(this);
            m.setX((int)(Math.random()*Stage.WIDTH));
            m.setY(0);
            Objects.add(m);
        }
    }
    
    public void checkCollisions()
    {
        Rectangle playerBounds = player.getBounds();
        for (int i=0; i < Objects.size(); i++)
        {
            Object a1 = (Object)Objects.get(i);
            Rectangle r1 = a1.getBounds();
            if (r1.intersects(playerBounds))
            {
                player.collision(a1);
                a1.collision(player);
            }
            for (int j=i+1;j<Objects.size(); j++)
            {
                Object a2 = (Object)Objects.get(j);
                Rectangle r2 = a2.getBounds();
                if (r1.intersects(r2))
                {
                    a1.collision(a2);
                    a2.collision(a1);
                }
            }
        }
    }
    
    public SpriteCache getSpriteCache()
    {
        return spriteCache;
    }
    
    public SoundCache getSoundCache()
    {
        return soundCache;
    }
    
    public Player getPlayer()
    {
        return player;
    }
    
    public void keyPressed(KeyEvent e)
    {
        player.keyPressed(e);
    }
    
    public void keyReleased(KeyEvent e)
    {
        player.keyReleased(e);
    }
    
    public void keyTyped(KeyEvent e) {};
    
    public void gameOver()
    {
        gameLost = true;  
    }
    
    public void gameWon()
    {
        gameWon = true;
    }
    
    public void game()
    {
        usedTime=1000;
        t=0;
        initWorld();
        playMainTheme();
        while (isVisible() && !gameLost && !gameWon)
        {
            t++;
            long startTime = System.currentTimeMillis();
            updateWorld();
            checkCollisions();
            paintWorld();
            spawnRepKit();
            usedTime = System.currentTimeMillis()-startTime; /*To calculate FPS*/
            try {
                Thread.sleep(Stage.SPEED); /*To slow down movements of sprites*/
            } catch (InterruptedException e) {}
        }
        if (gameLost)
        {
            paintGameOver();
        }
        else if (gameWon)
        {
            paintWon();
        }
        try {
            checkBestScore();
        } catch (IOException ex) {
            Logger.getLogger(XWingGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        XWingGame xwing = new XWingGame();
        xwing.game();
    }
    
}