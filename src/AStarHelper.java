/**
 * Created by Lara on 5/27/2015.
 */
public class AStarHelper
{
    private int gScore;
    private double fScore;

    private AStarHelper cameFrom;

    private Point myPoint;

    public AStarHelper(Point myPoint)
    {
        this.myPoint = myPoint;
    }



    public Point get_myPoint()
    {
        return this.myPoint;
    }

    public void set_myPoint(Point newPoint)
    {
        this.myPoint = newPoint;
    }

    public int get_gScore()
    {
        return this.gScore;
    }

    public void set_gScore(int newScore)
    {
        this.gScore = newScore;
    }

    public double get_fScore()
    {
        return this.fScore;
    }

    public void set_fScore(double newScore)
    {
        this.fScore = newScore;
    }

    public AStarHelper get_cameFrom()
    {
        return this.cameFrom;
    }

    public void set_cameFrom(AStarHelper newHelper)
    {
        this.cameFrom = newHelper;
    }

}
