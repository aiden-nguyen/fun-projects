import processing.core.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

//main wrapper

public final class VirtualWorld
        extends PApplet
{
   private static final int TIMER_ACTION_PERIOD = 100;

   private static final int VIEW_WIDTH = 640;
   private static final int VIEW_HEIGHT = 480;
   private static final int TILE_WIDTH = 32;
   private static final int TILE_HEIGHT = 32;
   private static final int WORLD_WIDTH_SCALE = 2;
   private static final int WORLD_HEIGHT_SCALE = 2;

   private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
   private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
   private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
   private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

   private static final String IMAGE_LIST_FILE_NAME = "imagelist";
   private static final String DEFAULT_IMAGE_NAME = "background_default";
   private static final int DEFAULT_IMAGE_COLOR = 0x808080;

   private static final String LOAD_FILE_NAME = "world.sav";

   private static final String FAST_FLAG = "-fast";
   private static final String FASTER_FLAG = "-faster";
   private static final String FASTEST_FLAG = "-fastest";
   private static final double FAST_SCALE = 0.5;
   private static final double FASTER_SCALE = 0.25;
   private static final double FASTEST_SCALE = 0.10;

   private static final String PANDA_KEY = "panda";
   private static final String CUTEUGLY_KEY = "cuteugly";


   private static double timeScale = 1.0;

   private ImageStore imageStore;
   private WorldModel world;
   private WorldView view;
   private EventScheduler scheduler;

   private long next_time;


   public void settings()
   {
      size(VIEW_WIDTH, VIEW_HEIGHT);
   }

   /*
      Processing entry point for "sketch" setup.
   */
   public void setup()
   {
      this.imageStore = new ImageStore(
              createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
      this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
              createDefaultBackground(imageStore));
      this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world,
              TILE_WIDTH, TILE_HEIGHT);
      this.scheduler = new EventScheduler(timeScale);

      loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
      loadWorld(world, LOAD_FILE_NAME, imageStore);

      scheduleActions(world, scheduler, imageStore);

      next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
   }


   public void draw()
   {
      long time = System.currentTimeMillis();
      if (time >= next_time)
      {
         this.scheduler.updateOnTime(time);
         next_time = time + TIMER_ACTION_PERIOD;
      }

      view.drawViewport();
   }

   public void keyPressed()
   {
      if (key == CODED)
      {
         int dx = 0;
         int dy = 0;

         switch (keyCode)
         {
            case UP:
               dy = -1;
               break;
            case DOWN:
               dy = 1;
               break;
            case LEFT:
               dx = -1;
               break;
            case RIGHT:
               dx = 1;
               break;
         }
         view.shiftView(dx, dy);
      }
   }


   public void mousePressed(processing.event.MouseEvent e)
   {

         int x = e.getX()/TILE_WIDTH;
         int y = e.getY()/TILE_HEIGHT;
         //Point clicked = new Point(x, y);

            try {
               view.drawBoat(x, y, imageStore);
               changeObstaclesToPandas(x, y);
               spawnCuteUgly(x, y);
            } catch (InterruptedException interruptedException) {
               interruptedException.printStackTrace();
            }
   }

   private void spawnCuteUgly(int x, int y){
      Point worldPoint = this.view.getViewport().viewportToWorld(x, y);

      Entity cuteUgly = CuteUgly.createCuteUgly("cuteugly1",
              new Point(worldPoint.x - 1, worldPoint.y), 100, 100,
              imageStore.getImageList(CUTEUGLY_KEY));
      world.addEntity(cuteUgly);
      ((CuteUgly)cuteUgly).scheduleActions(scheduler, world, imageStore);
   }

   private void changeObstaclesToPandas(int x, int y) {
      Point worldPoint = this.view.getViewport().viewportToWorld(x, y);
      List<Entity> entities = world.getEntities().stream().collect(Collectors.toList());
      List<Corgi> corgis = new ArrayList<>();
      entities.forEach(E -> {
         if (E instanceof Corgi) {
            corgis.add((Corgi) E);
         }
      });
      for (Entity o : corgis) {
         Point pos = o.getPosition();  // store current position before removing

         if (((pos.x == worldPoint.x ) && (pos.y == worldPoint.y))) {
            spawnPanda(o, worldPoint.x, worldPoint.y);
         }

           if ((pos.x == worldPoint.x + 1) && (pos.y == worldPoint.y -1)){
              spawnPanda(o, worldPoint.x + 1, worldPoint.y - 1);
           }

           if ((pos.x == worldPoint.x + 2) && (pos.y == worldPoint.y - 2)){
              spawnPanda(o, worldPoint.x + 2, worldPoint.y - 2);
           }

            if ((pos.x == worldPoint.x + 3) && (pos.y == worldPoint.y - 3)){
               spawnPanda(o, worldPoint.x + 3, worldPoint.y - 3);
            }

            if ((pos.x == worldPoint.x + 1) && (pos.y == worldPoint.y + 1)){
               spawnPanda(o, worldPoint.x + 1, worldPoint.y + 1);
            }

            if ((pos.x == worldPoint.x + 2) && (pos.y == worldPoint.y + 2)){
               spawnPanda(o, worldPoint.x + 2, worldPoint.y + 2);
            }

            if ((pos.x == worldPoint.x + 3) && (pos.y == worldPoint.y + 3)){
               spawnPanda(o, worldPoint.x + 3, worldPoint.y + 3);
            }
      }
   }

   private void spawnPanda(Entity o, int x, int y){
      world.removeEntity(o);
      scheduler.unscheduleAllEvents(o);

      Entity panda = Panda.createPanda("panda",
              new Point(x, y), 100, 100,
              imageStore.getImageList(PANDA_KEY));
      world.addEntity(panda);
      ((Panda) panda).scheduleActions(scheduler, world, imageStore);

   }

   private Background createDefaultBackground(ImageStore imageStore)
   {
      return new Background(DEFAULT_IMAGE_NAME,
              imageStore.getImageList(DEFAULT_IMAGE_NAME));
   }

   private PImage createImageColored(int width, int height, int color)
   {
      PImage img = new PImage(width, height, RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         img.pixels[i] = color;
      }
      img.updatePixels();
      return img;
   }

   private void loadImages(String filename, ImageStore imageStore,
                           PApplet screen)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         imageStore.loadImages(in, screen);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   private void loadWorld(WorldModel world, String filename,
                          ImageStore imageStore)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         world.load(in, imageStore);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   private void scheduleActions(WorldModel world,
                                EventScheduler scheduler, ImageStore imageStore)
   {
      for (Entity entity : world.getEntities())
      {
         //Only start actions for entities that include action (not those with just animations)
         if (entity instanceof EntityActionsAndActivities)
            ((EntityActionsAndActivities)entity).scheduleActions(scheduler, world, imageStore);
      }
   }

   private static void parseCommandLine(String [] args)
   {
      for (String arg : args)
      {
         switch (arg)
         {
            case FAST_FLAG:
               timeScale = Math.min(FAST_SCALE, timeScale);
               break;
            case FASTER_FLAG:
               timeScale = Math.min(FASTER_SCALE, timeScale);
               break;
            case FASTEST_FLAG:
               timeScale = Math.min(FASTEST_SCALE, timeScale);
               break;
         }
      }
   }

   public static void main(String [] args)
   {
      parseCommandLine(args);
      PApplet.main(VirtualWorld.class);
   }
}
