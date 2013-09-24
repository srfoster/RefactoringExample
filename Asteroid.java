package williamsasteroids;
//William's Asteroid game May 5, 2011
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Asteroid
{
    private AffineTransform asteroidAffineTransform = new AffineTransform();//Start off at identity
    private Ellipse2D.Double asteroidShape;
    private int asteroidDeltaX;
    private int asteroidDeltaY;
    private int asteroidX;
    private int asteroidY;
    private int asteroidCourse;
    private int asteroidSpeed = 4;
    private Area asteroidArea;
    private Area transformedAsteroidArea;
    private int asteroidSize = 25;

    public Asteroid(int asteroidXArg, int asteroidYArg, int asteroidCourseArg, int asteroidSizeArg)//Gets set when asteroids are made in Main asteroid ticker
    {
        asteroidCourse = asteroidCourseArg;
        asteroidDeltaX = (int) (asteroidSpeed * Math.sin(Math.toRadians(asteroidCourse)));
        asteroidDeltaY = (int) (-asteroidSpeed * Math.cos(Math.toRadians(asteroidCourse)));
        asteroidX = asteroidXArg;
        asteroidY = asteroidYArg;
        asteroidSize = asteroidSizeArg;
        asteroidShape = new Ellipse2D.Double(0, 0, asteroidSize, asteroidSize);
        asteroidArea = new Area(asteroidShape);
    }

    public void paintSelf(Graphics2D g2)
    {
        g2.setTransform(asteroidAffineTransform);
        g2.setColor(Color.DARK_GRAY);
        g2.fill(asteroidArea);
        g2.setTransform(new AffineTransform());
    }

    public void moveSelf()
    {
        asteroidX += asteroidDeltaX;
        asteroidY += asteroidDeltaY;
        asteroidAffineTransform.setToTranslation(asteroidX, asteroidY);//Concatenates deltax/y to asteroidx/y (starting position)
    }

    public Area getTransformedAsteroidArea()
    {
        return transformedAsteroidArea;
    }

    public Area getAsteroidArea()
    {
        return asteroidArea;
    }

    public AffineTransform getAsteroidAffineTransform()
    {
        return asteroidAffineTransform;
    }
}
