package williamsasteroids;
//William's Asteroid game May 5, 2011
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Bullet
{
    private AffineTransform bulletAffineTransform = new AffineTransform();
    private Ellipse2D.Double bulletShape = new Ellipse2D.Double(0, 0, 4, 4);
    private Area bulletArea = new Area(bulletShape);
    private int bulletDeltaX;
    private int bulletDeltaY;
    private double bulletCourse;
    private double bulletSpeed;
    private int bulletX;
    private int bulletY;

    public Bullet(int shipCourse, int shipSpeed, int shipX, int shipY)
    {
        bulletCourse = shipCourse;
        bulletSpeed = shipSpeed + 4;
        bulletDeltaX = (int)(bulletSpeed * Math.sin(Math.toRadians(bulletCourse)));
        bulletDeltaY = (int)(-bulletSpeed * Math.cos(Math.toRadians(bulletCourse)));
        bulletY = shipY;
        bulletX = shipX;
    }
// TODO:Fix bullets so that we can turn and fire at the same time

    public void paintSelf(Graphics2D g2)
    {
        g2.setTransform(bulletAffineTransform);
        g2.setColor(Color.ORANGE);
        g2.fill(bulletArea);
        g2.setTransform(new AffineTransform());
    }

    public void moveSelf()
    {
        bulletX += bulletDeltaX;
        bulletY += bulletDeltaY;
        bulletAffineTransform.setToTranslation(bulletX, bulletY);
    }

    public Area getBulletArea()
    {
        return bulletArea;
    }

    public AffineTransform getBulletAffineTransform()
    {
        return bulletAffineTransform;
    }
}
