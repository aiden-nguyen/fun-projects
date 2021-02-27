public class Activity implements Action{

    private final Entity entity;
    private final WorldModel world;
    private final ImageStore imageStore;


    public Activity(Entity entity, WorldModel world, ImageStore imageStore)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler)
    {
        if(this.entity instanceof JellyFish_Full){
            ((JellyFish_Full) this.entity).execute(this.world, this.imageStore, scheduler);
        }
        if(this.entity instanceof JellyFish_Not_Full){
            ((JellyFish_Not_Full) this.entity).execute(this.world, this.imageStore, scheduler);
        }
        if(this.entity instanceof Penguin){
            ((Penguin) this.entity).execute(this.world, this.imageStore, scheduler);
        }
        if(this.entity instanceof Corgi){
            ((Corgi) this.entity).execute(this.world, this.imageStore, scheduler);
        }
        if(this.entity instanceof Quake){
            ((Quake) this.entity).execute(this.world, this.imageStore, scheduler);
        }
        if(this.entity instanceof Flower){
            ((Flower) this.entity).execute(this.world, this.imageStore, scheduler);
        }
        if(this.entity instanceof Atlantis){
            ((Atlantis) this.entity).execute(this.world, this.imageStore, scheduler);
        }
        if(this.entity instanceof BigBertha){
            ((BigBertha) this.entity).execute(this.world, this.imageStore, scheduler);
        }
        if(this.entity instanceof Panda){
            ((Panda) this.entity).execute(this.world, this.imageStore, scheduler);
        }
        if(this.entity instanceof CuteUgly){
            ((CuteUgly) this.entity).execute(this.world, this.imageStore, scheduler);
        }

    }

    public static Activity createActivityAction(Entity entity, WorldModel world,
                                              ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore);
    }

}
