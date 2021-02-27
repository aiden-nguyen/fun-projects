import processing.core.PImage;
import java.util.function.Predicate;

import java.util.List;

public abstract class NextPositionOcto extends ScheduleActions_Moving {

    private int resourceLimit;
    private int resourceCount;

    public NextPositionOcto(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

   public Point nextPosition(WorldModel world, Point destPos) {
        Point nextPos = getPosition();

        PathingStrategy strategy = new AStarPathingStrategy(); //Implementation of AStarPathingStrategy
        //PathingStrategy strategy = new SingleStepPathingStrategy(); //Implementation of SingleStepPathingStrategy

        List<Point> points = strategy.computePath(getPosition(), destPos, canPassThrough(world), (p1, p2) -> neighbors(p1,p2),
                    //PathingStrategy.CARDINAL_NEIGHBORS); //Uncomment this out if you're using SingleStepPathingStrategy and don't want to include diagonals to mimic the original movement
                    PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS); //This is for AStarPathingStrategy, comment this out if you want to use SingleStep with only cardinal neighbors

        if (points.size() > 0) nextPos = points.remove(0);
        return nextPos;
        }


    private static Predicate<Point> canPassThrough(WorldModel world) {
        return p -> (world.withinBounds(p) && !world.isOccupied(p));
    }

    private static boolean neighbors(Point p1, Point p2)
    {
        return p1.x+1 == p2.x && p1.y == p2.y ||
                p1.x-1 == p2.x && p1.y == p2.y ||
                p1.x == p2.x && p1.y+1 == p2.y ||
                p1.x == p2.x && p1.y-1 == p2.y|| //; //uncomment this out and comment the next 4 statements out if you want to test SingleStep with only cardinal neighbors
                p1.x-1 == p2.x && p1.y-1 == p2.y ||
                p1.x+1 == p2.x && p1.y+1 == p2.y ||
                p1.x-1 == p2.x && p1.y+1 == p2.y ||
                p1.x+1 == p2.x && p1.y-1 == p2.y;
    }

    public int getResourceLimit() { return this.resourceLimit; }
    public int getResourceCount() { return this.resourceCount; }
    public void incResourceCount() { this.resourceCount = this.resourceCount + 1; }
}
