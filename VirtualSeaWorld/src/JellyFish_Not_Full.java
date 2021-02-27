import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class JellyFish_Not_Full extends NextPositionOcto{


    public JellyFish_Not_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest( this.getPosition(),
                Penguin.class);

        if (!notFullTarget.isPresent() ||
                !this.moveToNotFull(world, notFullTarget.get(), scheduler) ||
                !this.transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    private boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.getResourceCount() >= this.getResourceLimit())
        {
            Entity jellyFish = JellyFish_Full.createJellyFishFull(this.getId(), this.getResourceLimit(), this.getPosition(),
                    this.getActionPeriod(), this.getAnimationPeriod(),
                    this.getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(jellyFish);
            ((JellyFish_Full)jellyFish).scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    private boolean moveToNotFull(WorldModel world,
                                  Entity target, EventScheduler scheduler)
    {
        if (adjacentToEachOther(this.getPosition(), target.getPosition())) //(AStar Implementation/Diagonals and Cardinals)
        //if (this.getPosition().adjacent(target.getPosition())) //(SingleStep Implementation/Cardinals)
        {
            this.incResourceCount();
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

    public static JellyFish_Not_Full createJellyFishNotFull(String id, int resourceLimit, Point position,
                                    int actionPeriod, int animationPeriod,
                                    List<PImage> images)
    {
        return new JellyFish_Not_Full(id, position, images,
                resourceLimit, 0, actionPeriod, animationPeriod);
    }


}
