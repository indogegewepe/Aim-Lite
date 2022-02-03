package com.example.exmpleadhi;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;

    public Sprite()
    {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
    }

    public void setImage(Image i)
    {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename)
    {
        Image i = new Image(filename);
        setImage(i);
    }

    public Image getImage() { return image; }

    public void setPosition(double x,double y)
    {
        positionX = x;
        positionY = y;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setVelocity(double x, double y)
    {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(double x, double y){
        velocityX += x;
        velocityY += y;
    }

    public void update(double width, double height, double time)
    {
        double addX = velocityX * time;
        double addY = velocityY * time;
        if(positionX + addX > 10 && positionX + addX < width - 10)
            positionX += velocityX * time;
        if(positionY + addY > 10 && positionY + addY < height - 10)
            positionY += velocityY * time;
    }

    public void render(GraphicsContext gc) {gc.drawImage( image, positionX, positionY);}

    public Rectangle2D getBoundary() {return new Rectangle2D(positionX,positionY,width,height);}

    public boolean intersects(Sprite s){ return s.getBoundary().intersects( this.getBoundary() );}

    public String toString() {
        return " Position: [" + positionX + "," + positionY + "]" + " Velocity: [" + velocityX + "," + velocityY + "]";
    }
}
