public class Point
{
    public final int x;
    public final int y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object myObject)
    {
        if(myObject instanceof Point)
        {
            if (myObject == this)
            {
                return true;
            }
            else
            {
                Point other = (Point) myObject;
                return x == other.x && y == other.y;
            }
        }

        else
        {
            return false;
        }
    }


    public String toString()
    {
        return "(" + x + "," + y + ")";
    }

    public double distance(Point p2)
    {
        double xSquared = (this.x -p2.x) * (this.x - p2.x);
        double ySquared = (this.y - p2.y) * (this.y - p2.y);

        return (Math.sqrt(xSquared + ySquared));
    }

}

