package williamsasteroids;
//William's Asteroid game May 5, 2011
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public class Ship
{
    private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    private AffineTransform shipAffineTransform = new AffineTransform();
    private int[] shipXpoints =
    {
        0, 5, 3, 2, 1, -1, -2, -3, -5
    };
    private int[] shipYpoints =
    {
        -1, 2, 2, 3, 2, 2, 3, 2, 2
    };
    private Polygon shipShape = new Polygon(shipXpoints, shipYpoints, shipXpoints.length);
    private int shipYpostion = 800;
    private int shipXpostion = 50;
    private int deltaX;
    private int deltaY;
    private int shipSpeed = 0;
    private int shipDirection = 90;
    private Area shipArea = new Area(shipShape);

    public void paintSelf(Graphics2D g2)
    {
        g2.translate(getShipXpostion(), getShipYpostion());
        g2.scale(15, 15);
        g2.rotate(Math.toRadians(getShipDirection()));
        g2.setStroke(new BasicStroke(.01f));
        shipAffineTransform = g2.getTransform();
        g2.setColor(Color.GREEN);
        g2.draw(shipShape);
        g2.setTransform(new AffineTransform());
    }

    public void moveSelf()
    {
        deltaX = (int)(shipSpeed * Math.sin(Math.toRadians(shipDirection)));//shipSpeed and shipDirection get set from keyboardListener in Main
        deltaY = (int)(-shipSpeed * Math.cos(Math.toRadians(shipDirection)));
        shipXpostion = shipXpostion + deltaX;
        shipYpostion = shipYpostion + deltaY;
        if (shipXpostion > width)
        {
            shipXpostion = 0;
        }
        if (getShipYpostion() > height)
        {
            shipYpostion = 0;
        }
        if (getShipXpostion() < 0)
        {
            shipXpostion = width;
        }
        if (getShipYpostion() < 0)
        {
            shipYpostion = height;
        }
    }

    public int getShipSpeed()
    {
        return shipSpeed;
    }

    public void setShipSpeed(int shipSpeed)
    {
        this.shipSpeed = shipSpeed;
    }

    public int getShipDirection()
    {
        return shipDirection;
    }

    public void setShipDirection(int shipDirection)
    {
        this.shipDirection = shipDirection;
    }

    public int getShipYpostion()
    {
        return shipYpostion;
    }

    public int getShipXpostion()
    {
        return shipXpostion;
    }

    public Area getShipArea()
    {
        return shipArea;
    }

    public AffineTransform getShipAffineTransform()
    {
        return shipAffineTransform;
    }
}
