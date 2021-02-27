import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class JellyFish_Full extends NextPositionOcto {

    public JellyFish_Full( String id, Point position,
                      List<PImage> images, int resourceLimit, int resourceCount,
                      int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public static JellyFish_Full createJellyFishFull(String id, int resourceLimit, Point position,
                                 int actionPeriod, int animationPeriod,
                                 List<PImage> images)
    {
        return new JellyFish_Full(id, position, images,
                resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest( this.getPosition(),
                Atlantis.class);

        if (fullTarget.isPresent() &&
                this.moveToFull(world, fullTarget.get(), scheduler))
        {
            //at atlantis trigger animation
            ((Atlantis)fullTarget.get()).scheduleActions(scheduler, world, imageStore);

            //transform to unfull
            this.transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    Activity.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }


    private boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (adjacentToEachOther(this.getPosition(), target.getPosition())) //(AStar Implementation/Diagonals and Cardinals)
        //if (this.getPosition().adjacent(target.getPosition())) //(SingleStep Implementation/Cardinals)
        {
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

    private void transformFull(WorldModel world,
                               EventScheduler scheduler, ImageStore imageStore)
    {
        JellyFish_Not_Full jellyfish = JellyFish_Not_Full.createJellyFishNotFull(this.getId(), this.getResourceLimit(),
                this.getPosition(),
                this.getActionPeriod(), this.getAnimationPeriod(),
                this.getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(jellyfish);
        jellyfish.scheduleActions(scheduler, world, imageStore);
    }



}
