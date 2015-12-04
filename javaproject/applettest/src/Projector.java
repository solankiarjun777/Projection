import processing.core.*; 
import processing.xml.*; 

import controlP5.*; 

import controlP5.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class Projector extends PApplet {



ControlP5 controlP5;

int horizontal = 150;
int vertical = 150;
int strokeColor = 255;
int thickness = 2;
int spacing = 10;

int xPos = 512;
int yPos = 384;
int circleNum = 5;
int circleSize = 5;

int sides = 10;
int radialSides = 10;
int numColors = 1;

int mode = 1;

Grid myGrid = new Grid(horizontal, vertical);
Rings myRings = new Rings(xPos, yPos, circleNum, spacing);
Burst myBurst = new Burst(sides, numColors);
Radial myRadial = new Radial(sides, numColors);


public void setup()
{
  size(1024, 768);
  background(0);
  frameRate(30);
  userInterface();
  smooth();
}

public void mousePressed()
{
  if (!(((mouseX > 0) && (mouseX < 225)) && ((mouseY > 0) && (mouseY < 225))))
  {
    xPos = mouseX;
    yPos = mouseY;
  }
}

public void draw()
{
  background(0);

  switch(mode)
  {
  case 1:  
    myGrid.render(); 
    break;
  case 2:
    myRings.render();
    break;
  case 3:
    myBurst.render();
    break;
  case 4:
    myRadial.render();
    break;
  }
}




class Grid
{ 
  Grid (int h, int v)
  {
    horizontal = h;
    vertical = v;
  }

  public void render()
  {
    for (int i = 0; i < horizontal; i++) 
    {
      stroke(strokeColor);
      strokeWeight(thickness);
      line(0, (spacing)*i, 1024, (spacing)*i);
      //println("rendered horizontal");
    }

    for (int j = 0; j < vertical; j++)
    {
      stroke(strokeColor);
      strokeWeight(thickness);
      line((spacing)*j, 0, (spacing)*j, 768);
      //println("rendered vertical");
    }
  }
}

class Rings
{
  Rings (int x, int y, int n, int s)
  {
    xPos = x;
    yPos = y;
    circleNum = n;
    circleSize = s;
  }

  public void render()
  {
    for (int i=0; i < circleNum; i++)
    {
      stroke(strokeColor);
      strokeWeight(thickness);
      noFill();
      ellipseMode(CENTER);
      ellipse(xPos, yPos, (spacing*i)+circleSize, (spacing*i)+circleSize);
    }
  }
}


class Burst
{
  int colorCounter = 0;
  int radius = 2048;
  boolean colorToggle = false;

  Burst(int s, int c)
  {
    sides = 2*s;
    numColors = c;
  }

  public void render()
  {
    float angle = 360.0f / sides;

    if (sides%2 != 0) sides++;

    for (int i = 0; i < sides; i++)
    {
      smooth();
      beginShape();
      noFill();
      if (colorToggle) fill(strokeColor);
      strokeWeight(thickness);
      vertex(xPos, yPos);
      vertex(xPos + radius * cos(radians(angle * i)), yPos + radius * sin(radians(angle * i)));
      vertex(xPos + radius * cos(radians(angle * i+angle)), yPos + radius * sin(radians(angle * i+angle)));
      endShape(CLOSE);

      colorToggle = !colorToggle;
    }
  }
}

class Radial
{
  int colorCounter = 0;
  int radius = 2048;
  boolean colorToggle = false;

  Radial(int s, int c)
  {
    radialSides = 2*s;
    numColors = c;
  }

  public void render()
  {
    float angle = 360.0f / radialSides;

    if (radialSides%2 != 0) radialSides++;

    for (int i = 0; i < radialSides; i++)
    {
      smooth();
      beginShape();
      stroke(strokeColor);
      strokeWeight(thickness);
      noFill();
      //vertex(xPos, yPos);
      vertex((xPos + radius/spacing * cos(radians(angle * i))), (yPos + radius/spacing * sin(radians(angle * i)))); 
      vertex(xPos + radius * cos(radians(angle * i)), yPos + radius * sin(radians(angle * i)));
      vertex(xPos + radius * cos(radians(angle * i+angle)), yPos + radius * sin(radians(angle * i+angle)));
      endShape(CLOSE);

      colorToggle = !colorToggle;
    }
  }
}


public void userInterface()
{
  controlP5 = new ControlP5(this);

  controlP5.trigger();

  controlP5.tab("default");
  controlP5.tab("default").setLabel("Grid");
  controlP5.tab("default").activateEvent(true);
  controlP5.tab("default").setId(1);

  controlP5.tab("Rings");
  controlP5.tab("Rings").activateEvent(true);
  controlP5.tab("Rings").setId(2);

  controlP5.tab("Sunburst");
  controlP5.tab("Sunburst").activateEvent(true);
  controlP5.tab("Sunburst").setId(3);

  controlP5.tab("Radial");
  controlP5.tab("Radial").activateEvent(true);
  controlP5.tab("Radial").setId(4);

  controlP5.addSlider("horizontal", 0, 250, horizontal, 10, 30, 200, 20);
  controlP5.controller("horizontal").moveTo("default");

  controlP5.addSlider("vertical", 0, 250, vertical, 10, 60, 200, 20);
  controlP5.controller("vertical").moveTo("default");

  controlP5.addSlider("spacing", 1, 512, spacing, 10, 90, 200, 20);
  controlP5.controller("spacing").moveTo("global");

  controlP5.addSlider("strokeColor", 1, 255, strokeColor, 10, 150, 200, 20);
  controlP5.controller("strokeColor").moveTo("global");

  controlP5.addSlider("thickness", 1, 25, thickness, 10, 120, 200, 20);
  controlP5.controller("thickness").moveTo("global");

  controlP5.addSlider("circleNum", 1, 250, 1, 10, 30, 200, 20);
  controlP5.controller("circleNum").moveTo("Rings");

  controlP5.addSlider("circleSize", 1, 500, 1, 10, 60, 200, 20);
  controlP5.controller("circleSize").moveTo("Rings");

  controlP5.addSlider("sides", 8, 300, 1, 10, 60, 200, 20);
  controlP5.controller("sides").moveTo("Sunburst");

  controlP5.addSlider("radialSides", 8, 300, 1, 10, 60, 200, 20);
  controlP5.controller("radialSides").moveTo("Radial");
  controlP5.controller("radialSides").setLabel("Sides");
}

public void controlEvent(ControlEvent theControlEvent) {
  if (theControlEvent.isController()) {
    //println("controller : "+theControlEvent.controller().id());
  } 
  else if (theControlEvent.isTab()) {
    println("tab : "+theControlEvent.tab().id()+" / "+theControlEvent.tab().name());
    mode = theControlEvent.tab().id();
  }
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--hide-stop", "Projector" });
  }
}
