package williamsasteroids;
//William's Asteroid game May 5, 2011
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Main extends JComponent implements ActionListener, KeyListener
{
    private JFrame mainWindow;
    private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public Ship ship;
    private Timer painterTicker;
    public Timer asteroidMakerTicker;
    public Timer displayTicker;
    public Asteroid asteroid;
    private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public Bullet bullet;
    private URL explosionSoundAdress;
    private URL bulletSoundAdress;
    private AudioClip bulletSoundfile;
    private AudioClip explosionSoundfile;
    private Random r = new Random();
    private Area shipArea;
    public Area asteroidArea;
    private Area bulletArea;
    private AffineTransform asteroidAffineTransform = new AffineTransform();
    private AffineTransform shipAffineTransform = new AffineTransform();
    private AffineTransform bulletAffineTransform = new AffineTransform();
    private boolean isShipDead = false;
    private int asteroidBulletCollisionScore;
    public boolean display = true;

    public static void main(String[] args)
    {
        new Main().getGoing();
    }

    private void getGoing()
    {
        mainWindow = new JFrame("Asteroids");//Make JFrame
        mainWindow.setVisible(true);
        mainWindow.setSize(width, height);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.add(this);//Add painter
        mainWindow.addKeyListener(this);//Add keylistener
        ship = new Ship();//Make ship
        painterTicker = new Timer(20, this);//Add tickers
        painterTicker.start();
        asteroidMakerTicker = new Timer(1000, this);
        asteroidMakerTicker.start();
        displayTicker = new Timer(5000, this);
        displayTicker.start();
        explosionSoundAdress = getClass().getResource("explosion.aiff");//Add sounds
        bulletSoundAdress = getClass().getResource("fire.wav");
        explosionSoundfile = Applet.newAudioClip(explosionSoundAdress);
        bulletSoundfile = Applet.newAudioClip(bulletSoundAdress);
    }

    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, height);//Paint space black
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Bank Gothic", Font.BOLD, 200));
        g2.drawString(asteroidBulletCollisionScore + "", (int) (width * .5), ((int) (height * .5)));
        ship.moveSelf();
        ship.paintSelf(g2);
        shipArea = ship.getShipArea();//Gets ship geometry from ship
        shipAffineTransform = ship.getShipAffineTransform();
        shipArea = shipArea.createTransformedArea(shipAffineTransform);
        g2.setTransform(new AffineTransform());
        for (int j = 0; j < bullets.size(); j++)//Paint all bullets
        {
            bullets.get(j).moveSelf();
            bullets.get(j).paintSelf(g2);
        }
        for (int i = 0; i < asteroids.size(); i++)//check all asteroids for intersections
        {
            if (asteroids.get(i).getAsteroidArea() != null)//TODO:fix band aid
            {
                asteroids.get(i).moveSelf();
                asteroids.get(i).paintSelf(g2);
                asteroidArea = asteroids.get(i).getAsteroidArea();
                asteroidAffineTransform = asteroids.get(i).getAsteroidAffineTransform();
                asteroidArea = asteroidArea.createTransformedArea(asteroidAffineTransform);
                if (asteroidArea.getBounds2D().getX() > width || asteroidArea.getBounds2D().getX() < 0 || asteroidArea.getBounds2D().getY() > height || asteroidArea.getBounds2D().getY() < 0)//remove asteroids that go off screen
                {
                    asteroids.remove(i);
                }
                for (int j = 0; j < bullets.size(); j++)//check all bullets for intersections with asteroids
                {
                    bulletArea = bullets.get(j).getBulletArea();
                    bulletAffineTransform = bullets.get(j).getBulletAffineTransform();
                    bulletArea = bulletArea.createTransformedArea(bulletAffineTransform);
                    if (bulletArea.getBounds2D().getX() > width || bulletArea.getBounds2D().getX() < 0 || bulletArea.getBounds2D().getY() > height || bulletArea.getBounds2D().getY() < 0)//remove bullets that go off screen
                    {
                        bullets.remove(j);
                    }
                    if (collision(asteroidArea, bulletArea))//Asteroid/bullet collision
                    {
                        asteroidBulletCollisionScore += 1;
                        explosionSoundfile.play();
                        for (int k = 0; k < r.nextInt(7); k++)
                        {

                            int asteroidXdispersion = r.nextInt(200) + (int) asteroidArea.getBounds2D().getCenterX();
                            int asteroidYdispersion = r.nextInt(200) + (int) asteroidArea.getBounds2D().getCenterY();
                            asteroids.add(new Asteroid(asteroidXdispersion, asteroidYdispersion, r.nextInt(360), r.nextInt(50) + 10));
                        }
                        asteroids.remove(i);
                        bullets.remove(j);
                    }
                }
                if (collision(asteroidArea, shipArea))//ship asteroid collision
                {
                    isShipDead = true;
                }
            }
        }
        if (isShipDead)
        {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Bank Gothic", Font.BOLD, 400));
            g2.drawString("Game Over", (int) (width * .01), ((int) (height * .3)));
        }
    }

    public boolean collision(Area area1Arg, Area area2Arg)
    {
        Area area1clone = (Area) area1Arg.clone();//Prevents area1Arg and in turn asteroidArea from getting clobbered when the intersect operation happens
        area1clone.intersect(area2Arg);
        if (!area1clone.isEmpty())
        {
            return true;
        } else
        {
            return false;
        }
    }

    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource().equals(displayTicker))
        {
            System.out.println("display");
        }
        if (ae.getSource().equals(painterTicker))
        {
            repaint();
        }
        if (ae.getSource().equals(asteroidMakerTicker))
        {
            int switchNumber = (int) (Math.random() * 4);
            switch (switchNumber)
            {
                case 0: //top
                    asteroids.add(new Asteroid((int) (Math.random() * width), 0, (int) (Math.random() * 90) + 135, (int) (Math.random() * 175) + 15));//x y course size
                    break;
                case 1: //right
                    asteroids.add(new Asteroid(width, (int) (Math.random() * height), (int) (Math.random() * 90) + 225, (int) (Math.random() * 175) + 15));//x y course size
                    break;
                case 2: //bottom
                    asteroids.add(new Asteroid((int) (Math.random() * width), height, (int) (Math.random() * 90) + 315, (int) (Math.random() * 175) + 15));//x y course size
                    break;
                case 3: //left
                    asteroids.add(new Asteroid(0, (int) (Math.random() * height), (int) (Math.random() * 90) + 45, (int) (Math.random() * 175) + 15));//x y course size
                    break;
            }
        }
    }

    public void keyTyped(KeyEvent ke)
    {
    }

    public void keyPressed(KeyEvent ke)
    {
        if (ke.getKeyCode() == 39)//right arrow
        {
            ship.setShipDirection(ship.getShipDirection() + 10);
        }
        if (ke.getKeyCode() == 37)//left arrow
        {
            ship.setShipDirection(ship.getShipDirection() - 10);
        }
        if (ke.getKeyCode() == 40)//down arrow
        {
            ship.setShipSpeed(ship.getShipSpeed() - 1);
        }
        if (ke.getKeyCode() == 38)//up arrow
        {
            ship.setShipSpeed(ship.getShipSpeed() + 1);
        }
        if (ke.getKeyCode() == 32)//space
        {
            bulletSoundfile.play();
            bullets.add(new Bullet(ship.getShipDirection(), ship.getShipSpeed(), ship.getShipXpostion(), ship.getShipYpostion()));
        }
    }

    public void keyReleased(KeyEvent ke)
    {
    }
}
