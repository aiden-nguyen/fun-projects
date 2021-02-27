import processing.core.PImage;

import java.util.List;

public class Entity
{
   private final String id;
   private Point position;
   private  List<PImage> images;
   private int imageIndex;

   public Entity(String id, Point position, List<PImage> images)
   {
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
   }

   public Point getPosition() { return this.position; };
   public String getId(){ return id;}
   public void nextImage() { this.imageIndex = (this.imageIndex + 1) % this.images.size(); };
   public void setPosition(Point p) { this.position = p; };
   public List <PImage> getImages() { return this.images; };
   public int getImageIndex() { return this.imageIndex; };
   public void setImages(List<PImage> images){this.images=images;}

}
