import processing.core.PImage;

import java.util.function.Predicate;

import java.util.List;
import java.util.Optional;

public class Corgi extends ScheduleActions_Moving {

    private static final String QUAKE_KEY = "quake";

    public Corgi(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> corgiTarget = world.findNearest(this.getPosition(), Flower.class);
        long nextPeriod = this.getActionPeriod();

        if (corgiTarget.isPresent())
        {
            Point tgtPos = corgiTarget.get().getPosition();

            if (this.moveToCorgi(world, corgiTarget.get(), scheduler))
            {
                Entity quake = Quake.createQuake(tgtPos, imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.getActionPeriod();
                ((Quake)quake).scheduleActions(scheduler, world, imageStore);
            }
        }
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                nextPeriod);
    }


    private boolean moveToCorgi(WorldModel world, Entity target, EventScheduler scheduler)
    {
        //if (this.getPosition().adjacent(target.getPosition())) //this is for if you're only using cardinals (SingleStep)
        if (adjacentToEachOther(this.getPosition(), target.getPosition())) //this is for if you're using diagonals and cardinals (AStar)
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public static Corgi createCorgi(String id, Point position,
                                        int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Corgi( id, position, images,
               actionPeriod, animationPeriod);
    }



    public Point nextPosition(WorldModel world, Point destPos) {
        Point nextPos = getPosition();
        PathingStrategy strategy = new AStarPathingStrategy(); //Implementation of AStarPathingStrategy
        //PathingStrategy strategy = new SingleStepPathingStrategy(); //Implementation of SingleStepPathingStrategy

        List<Point> points = strategy.computePath(getPosition(), destPos, CorgiCanPassThrough(world),
                (p1, p2) -> neighbors(p1,p2),
                    //PathingStrategy.CARDINAL_NEIGHBORS); //Uncomment this out if you're using SingleStepPathingStrategy and don't want to include diagonals to mimic the original movement
                    PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS); //This is for AStarPathingStrategy, comment this out if you're using SingleStepPathing with cardinal neighbors

        if (points.size() > 0) nextPos = points.remove(0);
        return nextPos;
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


    private static Predicate<Point> CorgiCanPassThrough(WorldModel world) {
        return p ->  (world.withinBounds(p) && !(world.isOccupied(p))) || (world.withinBounds(p) &&
                (world.getOccupancyCell(p) instanceof Penguin));
    }




}
