import processing.core.PImage;

import java.util.List;

public abstract class ScheduleActions_Still extends EntityActionsAndActivities {
    public ScheduleActions_Still(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), this.getActionPeriod());
    }
}
