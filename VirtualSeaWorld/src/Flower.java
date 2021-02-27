import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Flower extends ScheduleActions_Still {

    private static final String PENGUIN_KEY = "penguin";
    private static final String PENGUIN_ID_PREFIX = "penguin -- ";
    private static final int PENGUIN_CORRUPT_MIN = 20000;
    private static final int PENGUIN_CORRUPT_MAX = 30000;

    private static final Random rand = new Random();

    public Flower(String id, Point position, List<PImage> images, int actionPeriod)
    {
        super(id, position, images, actionPeriod);
    }

    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.getPosition());

        if (openPt.isPresent())
        {
            Entity penguin = Penguin.createPenguin(PENGUIN_ID_PREFIX + this.getId(),
                    openPt.get(),
                    PENGUIN_CORRUPT_MIN +
                            rand.nextInt(PENGUIN_CORRUPT_MAX - PENGUIN_CORRUPT_MIN),
                    imageStore.getImageList(PENGUIN_KEY));
            world.addEntity(penguin);
            ((Penguin)penguin).scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent( this,
                Activity.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
    }

    public static Flower createFlower(String id, Point position, int actionPeriod,
                               List<PImage> images)
    {
        return new Flower(id, position, images,
                actionPeriod);
    }

}
