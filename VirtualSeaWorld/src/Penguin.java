import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Penguin extends ScheduleActions_Still {

    private static final String CORGI_KEY = "corgi";
    private static final String CORGI_ID_SUFFIX = " -- corgi";
    private static final int CORGI_PERIOD_SCALE = 4;
    private static final int CORGI_ANIMATION_MIN = 50;
    private static final int CORGI_ANIMATION_MAX = 150;

    private static final Random rand = new Random();

    public Penguin(String id, Point position, List<PImage> images, int actionPeriod)
    {
        super(id, position, images, actionPeriod);
    }

    public static Penguin createPenguin(String id, Point position, int actionPeriod,
                                     List<PImage> images)
    {
        return new Penguin(id, position, images, actionPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.getPosition();  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Entity corgi = Corgi.createCorgi(this.getId() + CORGI_ID_SUFFIX,
                pos,
                this.getActionPeriod() / CORGI_PERIOD_SCALE,
                CORGI_ANIMATION_MIN +
                        rand.nextInt(CORGI_ANIMATION_MAX - CORGI_ANIMATION_MIN),
                imageStore.getImageList(CORGI_KEY));
        world.addEntity(corgi);
        ((Corgi)corgi).scheduleActions(scheduler, world, imageStore);
    }

}
