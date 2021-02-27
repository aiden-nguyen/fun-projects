import processing.core.PImage;

import java.util.*;


//keeps track of the actual size of our virtual world and what entities are in it



final class WorldModel {
   private final int numRows;
   private final int numCols;
   private final Background[][] background;
   private final Entity[][] occupancy;
   private final Set<Entity> entities;

   private static final int PENGUIN_REACH = 1;

   private static final String BGND_KEY = "background";
   private static final int BGND_NUM_PROPERTIES = 4;
   private static final int BGND_ID = 1;
   private static final int BGND_COL = 2;
   private static final int BGND_ROW = 3;

   private static final String JELLYFISH_KEY = "jellyfish";
   private static final int JELLYFISH_NUM_PROPERTIES = 7;
   private static final int JELLYFISH_ID = 1;
   private static final int JELLYFISH_COL = 2;
   private static final int JELLYFISH_ROW = 3;
   private static final int JELLYFISH_LIMIT = 4;
   private static final int JELLYFISH_ACTION_PERIOD = 5;
   private static final int JELLYFISH_ANIMATION_PERIOD = 6;

   private static final int BIGBERTHA_NUM_PROPERTIES = 6;

   private static final String BIGBERTHA_KEY = "bigbertha";
   private static final int BIGBERTHA_ID = 1;
   private static final int BIGBERTHA_COL = 2;
   private static final int BIGBERTHA_ROW = 3;
   private static final int BIGBERTHA_ACTION_PERIOD = 4;
   private static final int BIGBERTHA_ANIMATION_PERIOD = 5;


   private static final String OBSTACLE_KEY = "obstacle";
   private static final int OBSTACLE_NUM_PROPERTIES = 4;
   private static final int OBSTACLE_ID = 1;
   private static final int OBSTACLE_COL = 2;
   private static final int OBSTACLE_ROW = 3;

   private static final String PENGUIN_KEY = "penguin";
   private static final int PENGUIN_NUM_PROPERTIES = 5;
   private static final int PENGUIN_ID = 1;
   private static final int PENGUIN_COL = 2;
   private static final int PENGUIN_ROW = 3;
   private static final int PENGUIN_ACTION_PERIOD = 4;

   private static final String ATLANTIS_KEY = "atlantis";
   private static final int ATLANTIS_NUM_PROPERTIES = 4;
   private static final int ATLANTIS_ID = 1;
   private static final int ATLANTIS_COL = 2;
   private static final int ATLANTIS_ROW = 3;

   private static final String FLOWER_KEY = "flower";
   private static final int FLOWER_NUM_PROPERTIES = 5;
   private static final int FLOWER_ID = 1;
   private static final int FLOWER_COL = 2;
   private static final int FLOWER_ROW = 3;
   private static final int FLOWER_ACTION_PERIOD = 4;

   private static final int PROPERTY_KEY = 0;
   public static final String BOAT_NAME = "boat";


   public WorldModel(int numRows, int numCols, Background defaultBackground) {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++) {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   public int getNumRows() {
      return numRows;
   }

   public int getNumCols() {
      return numCols;
   }

   public Set<Entity> getEntities() {
      return entities;
   }

   public Optional<Point> findOpenAround(Point pos) {
      for (int dy = -PENGUIN_REACH; dy <= PENGUIN_REACH; dy++) {
         for (int dx = -PENGUIN_REACH; dx <= PENGUIN_REACH; dx++) {
            Point newPt = new Point(pos.x + dx, pos.y + dy);
            if (this.withinBounds(newPt) &&
                    !this.isOccupied(newPt)) {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }

   public void load(Scanner in, ImageStore imageStore) {
      int lineNumber = 0;
      while (in.hasNextLine()) {
         try {
            if (!this.processLine(in.nextLine(), imageStore)) {
               System.err.println(String.format("invalid entry on line %d",
                       lineNumber));
            }
         } catch (NumberFormatException e) {
            System.err.println(String.format("invalid entry on line %d",
                    lineNumber));
         } catch (IllegalArgumentException e) {
            System.err.println(String.format("issue on line %d: %s",
                    lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }

   private boolean processLine(String line, ImageStore imageStore) {
      String[] properties = line.split("\\s");
      if (properties.length > 0) {
         switch (properties[PROPERTY_KEY]) {
            case BGND_KEY:
               return this.parseBackground(properties, imageStore);
            case JELLYFISH_KEY:
               return this.parseJellyFish(properties, imageStore);
            case OBSTACLE_KEY:
               return this.parseObstacle(properties, imageStore);
            case PENGUIN_KEY:
               return this.parsePenguin(properties, imageStore);
            case ATLANTIS_KEY:
               return this.parseAtlantis(properties, imageStore);
            case FLOWER_KEY:
               return this.parseFlower(properties, imageStore);
            case BIGBERTHA_KEY:
               return this.parseBigBertha(properties, imageStore);
         }
      }

      return false;
   }



   private boolean parseBackground(String[] properties, ImageStore imageStore) {
      if (properties.length == BGND_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                 Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         this.setBackground(pt,
                 new Background(id, imageStore.getImageList(id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }

   private boolean parseJellyFish(String[] properties, ImageStore imageStore) {
      if (properties.length == JELLYFISH_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[JELLYFISH_COL]),
                 Integer.parseInt(properties[JELLYFISH_ROW]));
         Entity entity = JellyFish_Not_Full.createJellyFishNotFull(properties[JELLYFISH_ID],
                 Integer.parseInt(properties[JELLYFISH_LIMIT]),
                 pt,
                 Integer.parseInt(properties[JELLYFISH_ACTION_PERIOD]),
                 Integer.parseInt(properties[JELLYFISH_ANIMATION_PERIOD]),
                 imageStore.getImageList(JELLYFISH_KEY));
         this.tryAddEntity(entity);
      }

      return properties.length == JELLYFISH_NUM_PROPERTIES;
   }

   private boolean parseBigBertha(String[] properties, ImageStore imageStore) {
      if (properties.length == BIGBERTHA_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[BIGBERTHA_COL]),
                 Integer.parseInt(properties[BIGBERTHA_ROW]));
         Entity entity = BigBertha.createBigBertha(properties[BIGBERTHA_ID],
                 pt,
                 Integer.parseInt(properties[BIGBERTHA_ACTION_PERIOD]),
                 Integer.parseInt(properties[BIGBERTHA_ANIMATION_PERIOD]),
                 imageStore.getImageList(BIGBERTHA_KEY));
         this.tryAddEntity(entity);
      }

      return properties.length == BIGBERTHA_NUM_PROPERTIES;
   }

   private boolean parseObstacle(String[] properties, ImageStore imageStore) {
      if (properties.length == OBSTACLE_NUM_PROPERTIES) {
         Point pt = new Point(
                 Integer.parseInt(properties[OBSTACLE_COL]),
                 Integer.parseInt(properties[OBSTACLE_ROW]));
         Entity entity = Obstacle.createObstacle(properties[OBSTACLE_ID], pt, imageStore.getImageList(OBSTACLE_KEY));
         this.tryAddEntity(entity);
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }

   private boolean parsePenguin(String[] properties, ImageStore imageStore) {
      if (properties.length == PENGUIN_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[PENGUIN_COL]),
                 Integer.parseInt(properties[PENGUIN_ROW]));
         Entity entity = Penguin.createPenguin(properties[PENGUIN_ID],
                 pt,
                 Integer.parseInt(properties[PENGUIN_ACTION_PERIOD]),
                 imageStore.getImageList(PENGUIN_KEY));
         this.tryAddEntity(entity);
      }

      return properties.length == PENGUIN_NUM_PROPERTIES;
   }

   private boolean parseAtlantis(String[] properties, ImageStore imageStore) {
      if (properties.length == ATLANTIS_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[ATLANTIS_COL]),
                 Integer.parseInt(properties[ATLANTIS_ROW]));
         Entity entity = Atlantis.createAtlantis(properties[ATLANTIS_ID],
                 pt,
                 imageStore.getImageList(ATLANTIS_KEY));
         this.tryAddEntity(entity);
      }

      return properties.length == ATLANTIS_NUM_PROPERTIES;
   }

   private boolean parseFlower(String[] properties, ImageStore imageStore) {
      if (properties.length == FLOWER_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[FLOWER_COL]),
                 Integer.parseInt(properties[FLOWER_ROW]));
         Entity entity = Flower.createFlower(properties[FLOWER_ID],
                 pt,
                 Integer.parseInt(properties[FLOWER_ACTION_PERIOD]),
                 imageStore.getImageList(FLOWER_KEY));
         this.tryAddEntity(entity);
      }

      return properties.length == FLOWER_NUM_PROPERTIES;
   }

   private void tryAddEntity(Entity entity) {
      if (this.isOccupied(entity.getPosition())) {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      this.addEntity(entity);
   }

   public boolean withinBounds(Point pos) {
      return pos.y >= 0 && pos.y < this.numRows &&
              pos.x >= 0 && pos.x < this.numCols;
   }

   public boolean isOccupied(Point pos) {
      return this.withinBounds(pos) &&
              this.getOccupancyCell(pos) != null;
   }

   public Optional<Entity> findNearest(Point pos, Class type) {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : this.entities) {
         if (entity.getClass() == type) {
            ofType.add(entity);
         }
      }

      return pos.nearestEntity(ofType);
   }

   /*
         Assumes that there is no entity currently occupying the
         intended destination cell.
      */
   public void addEntity(Entity entity) {
      if (this.withinBounds(entity.getPosition())) {
         this.setOccupancyCell(entity.getPosition(), entity);
         this.entities.add(entity);
      }
   }

   public void moveEntity(Entity entity, Point pos) {
      Point oldPos = entity.getPosition();
      if (this.withinBounds(pos) && !pos.equals(oldPos)) {
         this.setOccupancyCell(oldPos, null);
         this.removeEntityAt(pos);
         this.setOccupancyCell(pos, entity);
         entity.setPosition(pos);
      }
   }

   public void removeEntity(Entity entity) {
      this.removeEntityAt(entity.getPosition());
   }

   private void removeEntityAt(Point pos) {
      if (this.withinBounds(pos)
              && this.getOccupancyCell(pos) != null) {
         Entity entity = this.getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         this.entities.remove(entity);
         this.setOccupancyCell(pos, null);
      }
   }

   public Optional<PImage> getBackgroundImage(Point pos) {
      if (this.withinBounds(pos)) {
         return Optional.of(Background.getCurrentImage(this.getBackgroundCell(pos)));
      } else {
         return Optional.empty();
      }
   }

   private void setBackground(Point pos,
                              Background background) {
      if (this.withinBounds(pos)) {
         this.setBackgroundCell(pos, background);
      }
   }

   public Optional<Entity> getOccupant(Point pos) {
      if (this.isOccupied(pos)) {
         return Optional.of(this.getOccupancyCell(pos));
      } else {
         return Optional.empty();
      }
   }

   public Entity getOccupancyCell(Point pos) {
      return this.occupancy[pos.y][pos.x];
   }

   private void setOccupancyCell(Point pos,
                                 Entity entity) {
      try {
         this.occupancy[pos.y][pos.x] = entity;
      }
      catch(Exception e){
         e.getMessage();
      }
   }

   private Background getBackgroundCell(Point pos) {
      return this.background[pos.y][pos.x];
   }

   private void setBackgroundCell(Point pos,
                                  Background background) {
      this.background[pos.y][pos.x] = background;
   }



   public void drawBoatWorld(int x, int y, Viewport view, List<PImage> listImage) {
      Point worldPoint = view.viewportToWorld(x, y);

      if (withinBounds(worldPoint)) {
         setBackgroundCell(worldPoint, new Background(BOAT_NAME, listImage));
      }
   }
}