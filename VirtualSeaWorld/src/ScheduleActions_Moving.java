import processing.core.PImage;

import java.util.List;

public abstract class ScheduleActions_Moving extends NextPosition {
    public ScheduleActions_Moving(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0), this.getAnimationPeriod());
    }

    public boolean adjacentToEachOther(Point p1, Point p2) {

        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) ||
                (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1) ||
                Math.abs(p1.y - p2.y) == 1 && Math.abs(p1.x - p2.x) == 1;
    }

}


