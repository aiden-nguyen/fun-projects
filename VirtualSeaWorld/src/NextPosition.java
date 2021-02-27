import processing.core.PImage;

import java.util.List;

public abstract class NextPosition extends AnimationPeriod{
    public NextPosition(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public abstract Point nextPosition(WorldModel world, Point destPos);
}
