import processing.core.PImage;

import java.util.List;

public abstract class EntityActionsAndActivities extends Entity {
    private int actionPeriod;
    public EntityActionsAndActivities(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images);
        this.actionPeriod = actionPeriod;
    }
    public int getActionPeriod() { return this.actionPeriod; }
    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    public abstract void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}
