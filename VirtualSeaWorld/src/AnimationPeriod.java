import processing.core.PImage;

import java.util.List;

public abstract class AnimationPeriod extends EntityActionsAndActivities {
    private int animationPeriod;

    public AnimationPeriod(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    public int getAnimationPeriod() { return this.animationPeriod; };
}
