/*      */ package processing.core;
/*      */ 
/*      */ import java.util.HashMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PShape
/*      */   implements PConstants
/*      */ {
/*      */   protected String name;
/*      */   protected HashMap<String, PShape> nameTable;
/*      */   public static final int GROUP = 0;
/*      */   public static final int PRIMITIVE = 1;
/*      */   public static final int PATH = 2;
/*      */   public static final int GEOMETRY = 3;
/*      */   protected int family;
/*      */   protected int primitive;
/*      */   protected PMatrix matrix;
/*      */   protected PImage image;
/*      */   public float width;
/*      */   public float height;
/*      */   public float depth;
/*  114 */   protected boolean visible = true;
/*      */   
/*      */   protected boolean stroke;
/*      */   
/*      */   protected int strokeColor;
/*      */   
/*      */   protected float strokeWeight;
/*      */   
/*      */   protected int strokeCap;
/*      */   protected int strokeJoin;
/*      */   protected boolean fill;
/*      */   protected int fillColor;
/*  126 */   protected boolean style = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected float[] params;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int vertexCount;
/*      */   
/*      */ 
/*      */ 
/*      */   protected float[][] vertices;
/*      */   
/*      */ 
/*      */ 
/*      */   public static final int VERTEX = 0;
/*      */   
/*      */ 
/*      */ 
/*      */   public static final int BEZIER_VERTEX = 1;
/*      */   
/*      */ 
/*      */ 
/*      */   public static final int QUAD_BEZIER_VERTEX = 2;
/*      */   
/*      */ 
/*      */ 
/*      */   public static final int CURVE_VERTEX = 3;
/*      */   
/*      */ 
/*      */ 
/*      */   public static final int BREAK = 4;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int vertexCodeCount;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int[] vertexCodes;
/*      */   
/*      */ 
/*      */ 
/*      */   protected boolean close;
/*      */   
/*      */ 
/*      */ 
/*      */   protected PShape parent;
/*      */   
/*      */ 
/*      */ 
/*      */   protected int childCount;
/*      */   
/*      */ 
/*      */ 
/*      */   protected PShape[] children;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public PShape()
/*      */   {
/*  190 */     this.family = 0;
/*      */   }
/*      */   
/*      */   public PShape(int family)
/*      */   {
/*  195 */     this.family = family;
/*      */   }
/*      */   
/*      */   public void setName(String name)
/*      */   {
/*  200 */     this.name = name;
/*      */   }
/*      */   
/*      */   public String getName()
/*      */   {
/*  205 */     return this.name;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isVisible()
/*      */   {
/*  217 */     return this.visible;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setVisible(boolean visible)
/*      */   {
/*  229 */     this.visible = visible;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void disableStyle()
/*      */   {
/*  243 */     this.style = false;
/*      */     
/*  245 */     for (int i = 0; i < this.childCount; i++) {
/*  246 */       this.children[i].disableStyle();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void enableStyle()
/*      */   {
/*  257 */     this.style = true;
/*      */     
/*  259 */     for (int i = 0; i < this.childCount; i++) {
/*  260 */       this.children[i].enableStyle();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getWidth()
/*      */   {
/*  282 */     return this.width;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getHeight()
/*      */   {
/*  291 */     return this.height;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getDepth()
/*      */   {
/*  301 */     return this.depth;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean is3D()
/*      */   {
/*  309 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void pre(PGraphics g)
/*      */   {
/*  332 */     if (this.matrix != null) {
/*  333 */       g.pushMatrix();
/*  334 */       g.applyMatrix(this.matrix);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  351 */     if (this.style) {
/*  352 */       g.pushStyle();
/*  353 */       styles(g);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void styles(PGraphics g)
/*      */   {
/*  362 */     if (this.stroke) {
/*  363 */       g.stroke(this.strokeColor);
/*  364 */       g.strokeWeight(this.strokeWeight);
/*  365 */       g.strokeCap(this.strokeCap);
/*  366 */       g.strokeJoin(this.strokeJoin);
/*      */     } else {
/*  368 */       g.noStroke();
/*      */     }
/*      */     
/*  371 */     if (this.fill)
/*      */     {
/*  373 */       g.fill(this.fillColor);
/*      */     } else {
/*  375 */       g.noFill();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void post(PGraphics g)
/*      */   {
/*  399 */     if (this.matrix != null) {
/*  400 */       g.popMatrix();
/*      */     }
/*      */     
/*  403 */     if (this.style) {
/*  404 */       g.popStyle();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void draw(PGraphics g)
/*      */   {
/*  415 */     if (this.visible) {
/*  416 */       pre(g);
/*  417 */       drawImpl(g);
/*  418 */       post(g);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void drawImpl(PGraphics g)
/*      */   {
/*  428 */     if (this.family == 0) {
/*  429 */       drawGroup(g);
/*  430 */     } else if (this.family == 1) {
/*  431 */       drawPrimitive(g);
/*  432 */     } else if (this.family == 3) {
/*  433 */       drawGeometry(g);
/*  434 */     } else if (this.family == 2) {
/*  435 */       drawPath(g);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void drawGroup(PGraphics g)
/*      */   {
/*  441 */     for (int i = 0; i < this.childCount; i++) {
/*  442 */       this.children[i].draw(g);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void drawPrimitive(PGraphics g)
/*      */   {
/*  448 */     if (this.primitive == 2) {
/*  449 */       g.point(this.params[0], this.params[1]);
/*      */     }
/*  451 */     else if (this.primitive == 4) {
/*  452 */       if (this.params.length == 4) {
/*  453 */         g.line(this.params[0], this.params[1], 
/*  454 */           this.params[2], this.params[3]);
/*      */       } else {
/*  456 */         g.line(this.params[0], this.params[1], this.params[2], 
/*  457 */           this.params[3], this.params[4], this.params[5]);
/*      */       }
/*      */     }
/*  460 */     else if (this.primitive == 8) {
/*  461 */       g.triangle(this.params[0], this.params[1], 
/*  462 */         this.params[2], this.params[3], 
/*  463 */         this.params[4], this.params[5]);
/*      */     }
/*  465 */     else if (this.primitive == 16) {
/*  466 */       g.quad(this.params[0], this.params[1], 
/*  467 */         this.params[2], this.params[3], 
/*  468 */         this.params[4], this.params[5], 
/*  469 */         this.params[6], this.params[7]);
/*      */     }
/*  471 */     else if (this.primitive == 30) {
/*  472 */       if (this.image != null) {
/*  473 */         g.imageMode(0);
/*  474 */         g.image(this.image, this.params[0], this.params[1], this.params[2], this.params[3]);
/*      */       } else {
/*  476 */         g.rectMode(0);
/*  477 */         g.rect(this.params[0], this.params[1], this.params[2], this.params[3]);
/*      */       }
/*      */     }
/*  480 */     else if (this.primitive == 31) {
/*  481 */       g.ellipseMode(0);
/*  482 */       g.ellipse(this.params[0], this.params[1], this.params[2], this.params[3]);
/*      */     }
/*  484 */     else if (this.primitive == 32) {
/*  485 */       g.ellipseMode(0);
/*  486 */       g.arc(this.params[0], this.params[1], this.params[2], this.params[3], this.params[4], this.params[5]);
/*      */     }
/*  488 */     else if (this.primitive == 41) {
/*  489 */       if (this.params.length == 1) {
/*  490 */         g.box(this.params[0]);
/*      */       } else {
/*  492 */         g.box(this.params[0], this.params[1], this.params[2]);
/*      */       }
/*      */     }
/*  495 */     else if (this.primitive == 40) {
/*  496 */       g.sphere(this.params[0]);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void drawGeometry(PGraphics g)
/*      */   {
/*  502 */     g.beginShape(this.primitive);
/*  503 */     if (this.style) {
/*  504 */       for (int i = 0; i < this.vertexCount; i++) {
/*  505 */         g.vertex(this.vertices[i]);
/*      */       }
/*      */     } else {
/*  508 */       for (int i = 0; i < this.vertexCount; i++) {
/*  509 */         float[] vert = this.vertices[i];
/*  510 */         if (vert[2] == 0.0F) {
/*  511 */           g.vertex(vert[0], vert[1]);
/*      */         } else {
/*  513 */           g.vertex(vert[0], vert[1], vert[2]);
/*      */         }
/*      */       }
/*      */     }
/*  517 */     g.endShape();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void drawPath(PGraphics g)
/*      */   {
/*  579 */     if (this.vertices == null) { return;
/*      */     }
/*  581 */     g.beginShape();
/*      */     
/*  583 */     if (this.vertexCodeCount == 0) {
/*  584 */       if (this.vertices[0].length == 2) {
/*  585 */         for (int i = 0; i < this.vertexCount; i++) {
/*  586 */           g.vertex(this.vertices[i][0], this.vertices[i][1]);
/*      */         }
/*      */       } else {
/*  589 */         for (int i = 0; i < this.vertexCount; i++) {
/*  590 */           g.vertex(this.vertices[i][0], this.vertices[i][1], this.vertices[i][2]);
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/*  595 */       int index = 0;
/*      */       
/*  597 */       if (this.vertices[0].length == 2) {
/*  598 */         for (int j = 0; j < this.vertexCodeCount; j++) {
/*  599 */           switch (this.vertexCodes[j])
/*      */           {
/*      */           case 0: 
/*  602 */             g.vertex(this.vertices[index][0], this.vertices[index][1]);
/*      */             
/*      */ 
/*  605 */             index++;
/*  606 */             break;
/*      */           
/*      */           case 2: 
/*  609 */             g.quadVertex(this.vertices[(index + 0)][0], this.vertices[(index + 0)][1], 
/*  610 */               this.vertices[(index + 1)][0], this.vertices[(index + 1)][1]);
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  620 */             index += 2;
/*  621 */             break;
/*      */           
/*      */           case 1: 
/*  624 */             g.bezierVertex(this.vertices[(index + 0)][0], this.vertices[(index + 0)][1], 
/*  625 */               this.vertices[(index + 1)][0], this.vertices[(index + 1)][1], 
/*  626 */               this.vertices[(index + 2)][0], this.vertices[(index + 2)][1]);
/*      */             
/*      */ 
/*  629 */             index += 3;
/*  630 */             break;
/*      */           
/*      */           case 3: 
/*  633 */             g.curveVertex(this.vertices[index][0], this.vertices[index][1]);
/*  634 */             index++;
/*      */           
/*      */           case 4: 
/*  637 */             g.breakShape();
/*      */           }
/*      */         }
/*      */       } else {
/*  641 */         for (int j = 0; j < this.vertexCodeCount; j++) {
/*  642 */           switch (this.vertexCodes[j])
/*      */           {
/*      */           case 0: 
/*  645 */             g.vertex(this.vertices[index][0], this.vertices[index][1], this.vertices[index][2]);
/*      */             
/*      */ 
/*      */ 
/*  649 */             index++;
/*  650 */             break;
/*      */           
/*      */           case 2: 
/*  653 */             g.quadVertex(this.vertices[(index + 0)][0], this.vertices[(index + 0)][1], this.vertices[(index + 0)][2], 
/*  654 */               this.vertices[(index + 1)][0], this.vertices[(index + 1)][1], this.vertices[(index + 0)][2]);
/*  655 */             index += 2;
/*  656 */             break;
/*      */           
/*      */ 
/*      */           case 1: 
/*  660 */             g.bezierVertex(this.vertices[(index + 0)][0], this.vertices[(index + 0)][1], this.vertices[(index + 0)][2], 
/*  661 */               this.vertices[(index + 1)][0], this.vertices[(index + 1)][1], this.vertices[(index + 1)][2], 
/*  662 */               this.vertices[(index + 2)][0], this.vertices[(index + 2)][1], this.vertices[(index + 2)][2]);
/*  663 */             index += 3;
/*  664 */             break;
/*      */           
/*      */           case 3: 
/*  667 */             g.curveVertex(this.vertices[index][0], this.vertices[index][1], this.vertices[index][2]);
/*  668 */             index++;
/*      */           
/*      */           case 4: 
/*  671 */             g.breakShape();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  676 */     g.endShape(this.close ? 2 : 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public PShape getParent()
/*      */   {
/*  684 */     return this.parent;
/*      */   }
/*      */   
/*      */   public int getChildCount() {
/*  688 */     return this.childCount;
/*      */   }
/*      */   
/*      */   public PShape[] getChildren()
/*      */   {
/*  693 */     return this.children;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PShape getChild(int index)
/*      */   {
/*  702 */     return this.children[index];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PShape getChild(String target)
/*      */   {
/*  714 */     if ((this.name != null) && (this.name.equals(target))) {
/*  715 */       return this;
/*      */     }
/*  717 */     if (this.nameTable != null) {
/*  718 */       PShape found = (PShape)this.nameTable.get(target);
/*  719 */       if (found != null) return found;
/*      */     }
/*  721 */     for (int i = 0; i < this.childCount; i++) {
/*  722 */       PShape found = this.children[i].getChild(target);
/*  723 */       if (found != null) return found;
/*      */     }
/*  725 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PShape findChild(String target)
/*      */   {
/*  734 */     if (this.parent == null) {
/*  735 */       return getChild(target);
/*      */     }
/*      */     
/*  738 */     return this.parent.findChild(target);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void addChild(PShape who)
/*      */   {
/*  745 */     if (this.children == null) {
/*  746 */       this.children = new PShape[1];
/*      */     }
/*  748 */     if (this.childCount == this.children.length) {
/*  749 */       this.children = ((PShape[])PApplet.expand(this.children));
/*      */     }
/*  751 */     this.children[(this.childCount++)] = who;
/*  752 */     who.parent = this;
/*      */     
/*  754 */     if (who.getName() != null) {
/*  755 */       addName(who.getName(), who);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void addChild(PShape who, int idx)
/*      */   {
/*  762 */     if (idx < this.childCount) {
/*  763 */       if (this.childCount == this.children.length) {
/*  764 */         this.children = ((PShape[])PApplet.expand(this.children));
/*      */       }
/*      */       
/*      */ 
/*  768 */       for (int i = this.childCount - 1; i >= idx; i--) {
/*  769 */         this.children[(i + 1)] = this.children[i];
/*      */       }
/*  771 */       this.childCount += 1;
/*      */       
/*  773 */       this.children[idx] = who;
/*      */       
/*  775 */       who.parent = this;
/*      */       
/*  777 */       if (who.getName() != null) {
/*  778 */         addName(who.getName(), who);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeChild(int idx)
/*      */   {
/*  788 */     if (idx < this.childCount) {
/*  789 */       PShape child = this.children[idx];
/*      */       
/*      */ 
/*  792 */       for (int i = idx; i < this.childCount - 1; i++) {
/*  793 */         this.children[i] = this.children[(i + 1)];
/*      */       }
/*  795 */       this.childCount -= 1;
/*      */       
/*  797 */       if ((child.getName() != null) && (this.nameTable != null)) {
/*  798 */         this.nameTable.remove(child.getName());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addName(String nom, PShape shape)
/*      */   {
/*  808 */     if (this.parent != null) {
/*  809 */       this.parent.addName(nom, shape);
/*      */     } else {
/*  811 */       if (this.nameTable == null) {
/*  812 */         this.nameTable = new HashMap();
/*      */       }
/*  814 */       this.nameTable.put(nom, shape);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getChildIndex(PShape who)
/*      */   {
/*  823 */     for (int i = 0; i < this.childCount; i++) {
/*  824 */       if (this.children[i] == who) {
/*  825 */         return i;
/*      */       }
/*      */     }
/*  828 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getFamily()
/*      */   {
/*  845 */     return this.family;
/*      */   }
/*      */   
/*      */   public int getPrimitive()
/*      */   {
/*  850 */     return this.primitive;
/*      */   }
/*      */   
/*      */   public float[] getParams()
/*      */   {
/*  855 */     return getParams(null);
/*      */   }
/*      */   
/*      */   public float[] getParams(float[] target)
/*      */   {
/*  860 */     if ((target == null) || (target.length != this.params.length)) {
/*  861 */       target = new float[this.params.length];
/*      */     }
/*  863 */     PApplet.arrayCopy(this.params, target);
/*  864 */     return target;
/*      */   }
/*      */   
/*      */   public float getParam(int index)
/*      */   {
/*  869 */     return this.params[index];
/*      */   }
/*      */   
/*      */   public int getVertexCount()
/*      */   {
/*  874 */     return this.vertexCount;
/*      */   }
/*      */   
/*      */   public float[] getVertex(int index)
/*      */   {
/*  879 */     if ((index < 0) || (index >= this.vertexCount)) {
/*  880 */       String msg = "No vertex " + index + " for this shape, " + 
/*  881 */         "only vertices 0 through " + (this.vertexCount - 1) + ".";
/*  882 */       throw new IllegalArgumentException(msg);
/*      */     }
/*  884 */     return this.vertices[index];
/*      */   }
/*      */   
/*      */   public float getVertexX(int index)
/*      */   {
/*  889 */     return this.vertices[index][0];
/*      */   }
/*      */   
/*      */   public float getVertexY(int index)
/*      */   {
/*  894 */     return this.vertices[index][1];
/*      */   }
/*      */   
/*      */   public float getVertexZ(int index)
/*      */   {
/*  899 */     return this.vertices[index][2];
/*      */   }
/*      */   
/*      */   public int[] getVertexCodes()
/*      */   {
/*  904 */     if (this.vertexCodes == null) {
/*  905 */       return null;
/*      */     }
/*  907 */     if (this.vertexCodes.length != this.vertexCodeCount) {
/*  908 */       this.vertexCodes = PApplet.subset(this.vertexCodes, 0, this.vertexCodeCount);
/*      */     }
/*  910 */     return this.vertexCodes;
/*      */   }
/*      */   
/*      */   public int getVertexCodeCount()
/*      */   {
/*  915 */     return this.vertexCodeCount;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getVertexCode(int index)
/*      */   {
/*  923 */     return this.vertexCodes[index];
/*      */   }
/*      */   
/*      */   public boolean isClosed()
/*      */   {
/*  928 */     return this.close;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean contains(float x, float y)
/*      */   {
/*  937 */     if (this.family == 2) {
/*  938 */       boolean c = false;
/*  939 */       int i = 0; for (int j = this.vertexCount - 1; i < this.vertexCount; j = i++) {
/*  940 */         if ((this.vertices[i][1] > y ? 1 : 0) != (this.vertices[j][1] > y ? 1 : 0))
/*      */         {
/*  942 */           if (x < (this.vertices[j][0] - this.vertices[i][0]) * (
/*  943 */             y - this.vertices[i][1]) / (
/*  944 */             this.vertices[j][1] - this.vertices[i][1]) + 
/*  945 */             this.vertices[i][0])
/*  946 */             c = !c;
/*      */         }
/*      */       }
/*  949 */       return c;
/*      */     }
/*  951 */     throw new IllegalArgumentException("The contains() method is only implemented for paths.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void translate(float tx, float ty)
/*      */   {
/*  965 */     checkMatrix(2);
/*  966 */     this.matrix.translate(tx, ty);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void translate(float tx, float ty, float tz)
/*      */   {
/*  979 */     checkMatrix(3);
/*  980 */     this.matrix.translate(tx, ty, tz);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotateX(float angle)
/*      */   {
/*  994 */     rotate(angle, 1.0F, 0.0F, 0.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotateY(float angle)
/*      */   {
/* 1008 */     rotate(angle, 0.0F, 1.0F, 0.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotateZ(float angle)
/*      */   {
/* 1023 */     rotate(angle, 0.0F, 0.0F, 1.0F);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotate(float angle)
/*      */   {
/* 1037 */     checkMatrix(2);
/* 1038 */     this.matrix.rotate(angle);
/*      */   }
/*      */   
/*      */   public void rotate(float angle, float v0, float v1, float v2)
/*      */   {
/* 1043 */     checkMatrix(3);
/* 1044 */     this.matrix.rotate(angle, v0, v1, v2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scale(float s)
/*      */   {
/* 1054 */     checkMatrix(2);
/* 1055 */     this.matrix.scale(s);
/*      */   }
/*      */   
/*      */   public void scale(float x, float y)
/*      */   {
/* 1060 */     checkMatrix(2);
/* 1061 */     this.matrix.scale(x, y);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void scale(float x, float y, float z)
/*      */   {
/* 1078 */     checkMatrix(3);
/* 1079 */     this.matrix.scale(x, y, z);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void resetMatrix()
/*      */   {
/* 1087 */     checkMatrix(2);
/* 1088 */     this.matrix.reset();
/*      */   }
/*      */   
/*      */   public void applyMatrix(PMatrix source)
/*      */   {
/* 1093 */     if ((source instanceof PMatrix2D)) {
/* 1094 */       applyMatrix((PMatrix2D)source);
/* 1095 */     } else if ((source instanceof PMatrix3D)) {
/* 1096 */       applyMatrix((PMatrix3D)source);
/*      */     }
/*      */   }
/*      */   
/*      */   public void applyMatrix(PMatrix2D source)
/*      */   {
/* 1102 */     applyMatrix(source.m00, source.m01, 0.0F, source.m02, 
/* 1103 */       source.m10, source.m11, 0.0F, source.m12, 
/* 1104 */       0.0F, 0.0F, 1.0F, 0.0F, 
/* 1105 */       0.0F, 0.0F, 0.0F, 1.0F);
/*      */   }
/*      */   
/*      */ 
/*      */   public void applyMatrix(float n00, float n01, float n02, float n10, float n11, float n12)
/*      */   {
/* 1111 */     checkMatrix(2);
/* 1112 */     this.matrix.apply(n00, n01, n02, 0.0F, 
/* 1113 */       n10, n11, n12, 0.0F, 
/* 1114 */       0.0F, 0.0F, 1.0F, 0.0F, 
/* 1115 */       0.0F, 0.0F, 0.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public void apply(PMatrix3D source)
/*      */   {
/* 1120 */     applyMatrix(source.m00, source.m01, source.m02, source.m03, 
/* 1121 */       source.m10, source.m11, source.m12, source.m13, 
/* 1122 */       source.m20, source.m21, source.m22, source.m23, 
/* 1123 */       source.m30, source.m31, source.m32, source.m33);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyMatrix(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33)
/*      */   {
/* 1131 */     checkMatrix(3);
/* 1132 */     this.matrix.apply(n00, n01, n02, n03, 
/* 1133 */       n10, n11, n12, n13, 
/* 1134 */       n20, n21, n22, n23, 
/* 1135 */       n30, n31, n32, n33);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void checkMatrix(int dimensions)
/*      */   {
/* 1147 */     if (this.matrix == null) {
/* 1148 */       if (dimensions == 2) {
/* 1149 */         this.matrix = new PMatrix2D();
/*      */       } else {
/* 1151 */         this.matrix = new PMatrix3D();
/*      */       }
/* 1153 */     } else if ((dimensions == 3) && ((this.matrix instanceof PMatrix2D)))
/*      */     {
/* 1155 */       this.matrix = new PMatrix3D(this.matrix);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\core\PShape.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */