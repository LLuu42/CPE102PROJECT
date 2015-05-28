import processing.core.PImage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;

public abstract class MobileAnimatedActor
        extends AnimatedActor
{

    private AStarHelper[][] node;
    private ArrayList<Point> path;


    public MobileAnimatedActor(String name, Point position, int rate,
                               int animation_rate, List<PImage> imgs)
    {
        super(name, position, rate, animation_rate, imgs);
        this.path = new ArrayList<Point>();
    }


    public ArrayList<Point> get_path()
    {
        return this.path;
    }


    public void aStarAlgorithm(Point myStart, Point myGoal, WorldModel world)
    {
        List<AStarHelper> closedSet = new LinkedList<AStarHelper>();
        List<AStarHelper> openSet = new ArrayList<AStarHelper>();

        node = new AStarHelper[world.getNumRows()][world.getNumCols()];

        for (int y = 0; y < world.getNumRows(); y++)
        {
            for(int x = 0; x < world.getNumCols(); x++)
            {
                node[y][x] = new AStarHelper(new Point(x, y));
            }
        }

        path = new ArrayList<Point>();


        AStarHelper startNode = node[myStart.y][myStart.x];
        startNode.set_gScore(0);
        startNode.set_fScore(startNode.get_gScore() +
                myStart.distance(myGoal));

        openSet.add(startNode);

        while (!openSet.isEmpty())
        {
            int smallestIndex = smallest_index(openSet);

            AStarHelper current = openSet.remove(smallestIndex);

            if (current.get_myPoint().equals(myGoal))
            {
                reconstructPath(current);
                return;
            }

            openSet.remove(current);
            closedSet.add(current);

            for (AStarHelper neighbor: neighbor_nodes(current, world, myGoal))
            {
                if (closedSet.contains(neighbor))
                {
                    continue;
                }

                int tentative_g_score = current.get_gScore() + 1;

                if (!openSet.contains(neighbor) || tentative_g_score < neighbor.get_gScore())
                {
                    neighbor.set_cameFrom(current);
                    neighbor.set_gScore(tentative_g_score);
                    neighbor.set_fScore(neighbor.get_gScore() + neighbor.get_myPoint().distance(myGoal));

                    if (!openSet.contains(neighbor))
                    {
                        openSet.add(neighbor);
                    }
                }
            }
            path = new ArrayList<>();
        }
    }


    public int smallest_index(List<AStarHelper> myList)
    {
        int smallestIndex = 0;
        double smallestNumber = myList.get(0).get_fScore();
        for (int index = 0; index < myList.size(); index ++)
        {
            if(myList.get(index).get_fScore() < smallestNumber)
            {
                smallestNumber = myList.get(index).get_fScore();
                smallestIndex = index;
            }
        }

        return smallestIndex;
    }


    public void reconstructPath(AStarHelper current)
    {
        ArrayList<Point> total_path = new ArrayList<Point>();
        total_path.add(current.get_myPoint());

        while (current.get_cameFrom() != null)
        {
            current = current.get_cameFrom();
            total_path.add(current.get_myPoint());
        }

        this.path = total_path;
    }

    public ArrayList<AStarHelper> neighbor_nodes(AStarHelper current, WorldModel world, Point myGoal)
    {
        ArrayList<AStarHelper> neighbors = new ArrayList<AStarHelper>();
        Point currentPoint = current.get_myPoint();

        Point upPoint = new Point(currentPoint.x, currentPoint.y - 1);
        Point downPoint = new Point(currentPoint.x, currentPoint.y + 1);
        Point leftPoint = new Point(currentPoint.x - 1, currentPoint.y);
        Point rightPoint = new Point(currentPoint.x + 1, currentPoint.y);

        if (world.withinBounds(upPoint) && world.getTileOccupant(upPoint) == null || upPoint.equals(myGoal) )
        {
            neighbors.add(node[upPoint.y][upPoint.x]);
        }

        if (world.withinBounds(downPoint) && world.getTileOccupant(downPoint) == null || downPoint.equals(myGoal) )
        {
            neighbors.add(node[downPoint.y][downPoint.x]);
        }

        if (world.withinBounds(leftPoint) && world.getTileOccupant(leftPoint) == null || leftPoint.equals(myGoal) )
        {
            neighbors.add(node[leftPoint.y][leftPoint.x]);
        }

        if (world.withinBounds(rightPoint) && world.getTileOccupant(rightPoint) == null || rightPoint.equals(myGoal) )
        {
            neighbors.add(node[rightPoint.y][rightPoint.x]);
        }

        return neighbors;
    }


    protected Point nextPosition(WorldModel world, Point dest_pt, Point entityPoint)
    {
        aStarAlgorithm(entityPoint, dest_pt, world);

        Point pathPoint = entityPoint;

        if(!get_path().isEmpty())
        {
            pathPoint = get_path().get(get_path().size() - 2);
        }
        return pathPoint;
    }

    protected static boolean adjacent(Point p1, Point p2)
    {
        return (p1.x == p2.x && abs(p1.y - p2.y) == 1) ||
                (p1.y == p2.y && abs(p1.x - p2.x) == 1);
    }

    protected abstract boolean canPassThrough(WorldModel world, Point new_pt);
}
