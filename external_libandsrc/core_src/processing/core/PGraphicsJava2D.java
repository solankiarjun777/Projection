/*      */ package processing.core;
/*      */ 
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Image;
/*      */ import java.awt.Paint;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Shape;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Arc2D.Float;
/*      */ import java.awt.geom.Ellipse2D.Float;
/*      */ import java.awt.geom.GeneralPath;
/*      */ import java.awt.geom.Line2D.Float;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D.Float;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.util.Arrays;
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
/*      */ public class PGraphicsJava2D
/*      */   extends PGraphics
/*      */ {
/*      */   public Graphics2D g2;
/*      */   protected BufferedImage offscreen;
/*      */   GeneralPath gpath;
/*      */   boolean breakShape;
/*      */   float[] curveCoordX;
/*      */   float[] curveCoordY;
/*      */   float[] curveDrawX;
/*      */   float[] curveDrawY;
/*      */   int transformCount;
/*   68 */   AffineTransform[] transformStack = new AffineTransform[32];
/*   69 */   double[] transform = new double[6];
/*      */   
/*   71 */   Line2D.Float line = new Line2D.Float();
/*   72 */   Ellipse2D.Float ellipse = new Ellipse2D.Float();
/*   73 */   Rectangle2D.Float rect = new Rectangle2D.Float();
/*   74 */   Arc2D.Float arc = new Arc2D.Float();
/*      */   
/*      */ 
/*      */ 
/*      */   protected Color tintColorObject;
/*      */   
/*      */ 
/*      */ 
/*      */   protected Color fillColorObject;
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean fillGradient;
/*      */   
/*      */ 
/*      */ 
/*      */   public Paint fillGradientObject;
/*      */   
/*      */ 
/*      */ 
/*      */   protected Color strokeColorObject;
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean strokeGradient;
/*      */   
/*      */ 
/*      */ 
/*      */   public Paint strokeGradientObject;
/*      */   
/*      */ 
/*      */ 
/*      */   int[] clearPixels;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSize(int iwidth, int iheight)
/*      */   {
/*  113 */     this.width = iwidth;
/*  114 */     this.height = iheight;
/*  115 */     this.width1 = (this.width - 1);
/*  116 */     this.height1 = (this.height - 1);
/*      */     
/*  118 */     allocate();
/*  119 */     reapplySettings();
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
/*      */   protected void allocate()
/*      */   {
/*  132 */     this.image = new BufferedImage(this.width, this.height, 2);
/*  133 */     if (this.primarySurface)
/*      */     {
/*      */ 
/*  136 */       this.offscreen = new BufferedImage(this.width, this.height, 2);
/*  137 */       this.g2 = ((Graphics2D)this.offscreen.getGraphics());
/*      */     }
/*      */     else
/*      */     {
/*  141 */       this.g2 = ((Graphics2D)this.image.getGraphics());
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
/*      */   public boolean canDraw()
/*      */   {
/*  162 */     return true;
/*      */   }
/*      */   
/*      */   public void beginDraw()
/*      */   {
/*  167 */     checkSettings();
/*      */     
/*  169 */     resetMatrix();
/*      */     
/*      */ 
/*  172 */     this.vertexCount = 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void endDraw()
/*      */   {
/*  181 */     if (this.primarySurface)
/*      */     {
/*      */ 
/*      */ 
/*  185 */       synchronized (this.image)
/*      */       {
/*  187 */         this.image.getGraphics().drawImage(this.offscreen, 0, 0, null);
/*      */       }
/*      */     }
/*      */     
/*  191 */     loadPixels();
/*      */     
/*  193 */     this.modified = true;
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
/*      */   public void beginShape(int kind)
/*      */   {
/*  232 */     this.shape = kind;
/*  233 */     this.vertexCount = 0;
/*  234 */     this.curveVertexCount = 0;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  241 */     this.gpath = null;
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
/*      */   public void texture(PImage image)
/*      */   {
/*  255 */     showMethodWarning("texture");
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y)
/*      */   {
/*  260 */     this.curveVertexCount = 0;
/*      */     
/*      */ 
/*  263 */     if (this.vertexCount == this.vertices.length) {
/*  264 */       float[][] temp = new float[this.vertexCount << 1][37];
/*  265 */       System.arraycopy(this.vertices, 0, temp, 0, this.vertexCount);
/*  266 */       this.vertices = temp;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  271 */     this.vertices[this.vertexCount][0] = x;
/*  272 */     this.vertices[this.vertexCount][1] = y;
/*  273 */     this.vertexCount += 1;
/*      */     
/*  275 */     switch (this.shape)
/*      */     {
/*      */     case 2: 
/*  278 */       point(x, y);
/*  279 */       break;
/*      */     
/*      */     case 4: 
/*  282 */       if (this.vertexCount % 2 == 0) {
/*  283 */         line(this.vertices[(this.vertexCount - 2)][0], 
/*  284 */           this.vertices[(this.vertexCount - 2)][1], x, y);
/*      */       }
/*  286 */       break;
/*      */     
/*      */     case 9: 
/*  289 */       if (this.vertexCount % 3 == 0) {
/*  290 */         triangle(this.vertices[(this.vertexCount - 3)][0], 
/*  291 */           this.vertices[(this.vertexCount - 3)][1], 
/*  292 */           this.vertices[(this.vertexCount - 2)][0], 
/*  293 */           this.vertices[(this.vertexCount - 2)][1], 
/*  294 */           x, y);
/*      */       }
/*  296 */       break;
/*      */     
/*      */     case 10: 
/*  299 */       if (this.vertexCount >= 3) {
/*  300 */         triangle(this.vertices[(this.vertexCount - 2)][0], 
/*  301 */           this.vertices[(this.vertexCount - 2)][1], 
/*  302 */           this.vertices[(this.vertexCount - 1)][0], 
/*  303 */           this.vertices[(this.vertexCount - 1)][1], 
/*  304 */           this.vertices[(this.vertexCount - 3)][0], 
/*  305 */           this.vertices[(this.vertexCount - 3)][1]);
/*      */       }
/*  307 */       break;
/*      */     
/*      */     case 11: 
/*  310 */       if (this.vertexCount == 3) {
/*  311 */         triangle(this.vertices[0][0], this.vertices[0][1], 
/*  312 */           this.vertices[1][0], this.vertices[1][1], 
/*  313 */           x, y);
/*  314 */       } else if (this.vertexCount > 3) {
/*  315 */         this.gpath = new GeneralPath();
/*      */         
/*      */ 
/*  318 */         this.gpath.moveTo(this.vertices[0][0], 
/*  319 */           this.vertices[0][1]);
/*  320 */         this.gpath.lineTo(this.vertices[(this.vertexCount - 2)][0], 
/*  321 */           this.vertices[(this.vertexCount - 2)][1]);
/*  322 */         this.gpath.lineTo(x, y);
/*  323 */         drawShape(this.gpath);
/*      */       }
/*  325 */       break;
/*      */     
/*      */     case 16: 
/*  328 */       if (this.vertexCount % 4 == 0) {
/*  329 */         quad(this.vertices[(this.vertexCount - 4)][0], 
/*  330 */           this.vertices[(this.vertexCount - 4)][1], 
/*  331 */           this.vertices[(this.vertexCount - 3)][0], 
/*  332 */           this.vertices[(this.vertexCount - 3)][1], 
/*  333 */           this.vertices[(this.vertexCount - 2)][0], 
/*  334 */           this.vertices[(this.vertexCount - 2)][1], 
/*  335 */           x, y);
/*      */       }
/*  337 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 17: 
/*  343 */       if ((this.vertexCount >= 4) && (this.vertexCount % 2 == 0)) {
/*  344 */         quad(this.vertices[(this.vertexCount - 4)][0], 
/*  345 */           this.vertices[(this.vertexCount - 4)][1], 
/*  346 */           this.vertices[(this.vertexCount - 2)][0], 
/*  347 */           this.vertices[(this.vertexCount - 2)][1], 
/*  348 */           x, y, 
/*  349 */           this.vertices[(this.vertexCount - 3)][0], 
/*  350 */           this.vertices[(this.vertexCount - 3)][1]);
/*      */       }
/*  352 */       break;
/*      */     
/*      */     case 20: 
/*  355 */       if (this.gpath == null) {
/*  356 */         this.gpath = new GeneralPath();
/*  357 */         this.gpath.moveTo(x, y);
/*  358 */       } else if (this.breakShape) {
/*  359 */         this.gpath.moveTo(x, y);
/*  360 */         this.breakShape = false;
/*      */       } else {
/*  362 */         this.gpath.lineTo(x, y);
/*      */       }
/*      */       break;
/*      */     }
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y, float z)
/*      */   {
/*  370 */     showDepthWarningXYZ("vertex");
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y, float u, float v)
/*      */   {
/*  375 */     showVariationWarning("vertex(x, y, u, v)");
/*      */   }
/*      */   
/*      */   public void vertex(float x, float y, float z, float u, float v)
/*      */   {
/*  380 */     showDepthWarningXYZ("vertex");
/*      */   }
/*      */   
/*      */   public void breakShape()
/*      */   {
/*  385 */     this.breakShape = true;
/*      */   }
/*      */   
/*      */   public void endShape(int mode)
/*      */   {
/*  390 */     if ((this.gpath != null) && 
/*  391 */       (this.shape == 20)) {
/*  392 */       if (mode == 2) {
/*  393 */         this.gpath.closePath();
/*      */       }
/*  395 */       drawShape(this.gpath);
/*      */     }
/*      */     
/*  398 */     this.shape = 0;
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
/*      */   public void bezierVertex(float x1, float y1, float x2, float y2, float x3, float y3)
/*      */   {
/*  411 */     bezierVertexCheck();
/*  412 */     this.gpath.curveTo(x1, y1, x2, y2, x3, y3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void bezierVertex(float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4)
/*      */   {
/*  419 */     showDepthWarningXYZ("bezierVertex");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void quadVertex(float ctrlX, float ctrlY, float endX, float endY)
/*      */   {
/*  431 */     bezierVertexCheck();
/*  432 */     Point2D cur = this.gpath.getCurrentPoint();
/*      */     
/*  434 */     float x1 = (float)cur.getX();
/*  435 */     float y1 = (float)cur.getY();
/*      */     
/*  437 */     bezierVertex(x1 + (ctrlX - x1) * 2.0F / 3.0F, y1 + (ctrlY - y1) * 2.0F / 3.0F, 
/*  438 */       endX + (ctrlX - endX) * 2.0F / 3.0F, endY + (ctrlY - endY) * 2.0F / 3.0F, 
/*  439 */       endX, endY);
/*      */   }
/*      */   
/*      */ 
/*      */   public void quadVertex(float x2, float y2, float z2, float x4, float y4, float z4)
/*      */   {
/*  445 */     showDepthWarningXYZ("quadVertex");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void curveVertexCheck()
/*      */   {
/*  456 */     super.curveVertexCheck();
/*      */     
/*  458 */     if (this.curveCoordX == null) {
/*  459 */       this.curveCoordX = new float[4];
/*  460 */       this.curveCoordY = new float[4];
/*  461 */       this.curveDrawX = new float[4];
/*  462 */       this.curveDrawY = new float[4];
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void curveVertexSegment(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*      */   {
/*  471 */     this.curveCoordX[0] = x1;
/*  472 */     this.curveCoordY[0] = y1;
/*      */     
/*  474 */     this.curveCoordX[1] = x2;
/*  475 */     this.curveCoordY[1] = y2;
/*      */     
/*  477 */     this.curveCoordX[2] = x3;
/*  478 */     this.curveCoordY[2] = y3;
/*      */     
/*  480 */     this.curveCoordX[3] = x4;
/*  481 */     this.curveCoordY[3] = y4;
/*      */     
/*  483 */     this.curveToBezierMatrix.mult(this.curveCoordX, this.curveDrawX);
/*  484 */     this.curveToBezierMatrix.mult(this.curveCoordY, this.curveDrawY);
/*      */     
/*      */ 
/*      */ 
/*  488 */     if (this.gpath == null) {
/*  489 */       this.gpath = new GeneralPath();
/*  490 */       this.gpath.moveTo(this.curveDrawX[0], this.curveDrawY[0]);
/*      */     }
/*      */     
/*  493 */     this.gpath.curveTo(this.curveDrawX[1], this.curveDrawY[1], 
/*  494 */       this.curveDrawX[2], this.curveDrawY[2], 
/*  495 */       this.curveDrawX[3], this.curveDrawY[3]);
/*      */   }
/*      */   
/*      */   public void curveVertex(float x, float y, float z)
/*      */   {
/*  500 */     showDepthWarningXYZ("curveVertex");
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
/*      */   public void point(float x, float y)
/*      */   {
/*  520 */     if (this.stroke)
/*      */     {
/*  522 */       line(x, y, x + 1.0E-4F, y + 1.0E-4F);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void line(float x1, float y1, float x2, float y2)
/*      */   {
/*  531 */     this.line.setLine(x1, y1, x2, y2);
/*  532 */     strokeShape(this.line);
/*      */   }
/*      */   
/*      */ 
/*      */   public void triangle(float x1, float y1, float x2, float y2, float x3, float y3)
/*      */   {
/*  538 */     this.gpath = new GeneralPath();
/*  539 */     this.gpath.moveTo(x1, y1);
/*  540 */     this.gpath.lineTo(x2, y2);
/*  541 */     this.gpath.lineTo(x3, y3);
/*  542 */     this.gpath.closePath();
/*  543 */     drawShape(this.gpath);
/*      */   }
/*      */   
/*      */ 
/*      */   public void quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
/*      */   {
/*  549 */     GeneralPath gp = new GeneralPath();
/*  550 */     gp.moveTo(x1, y1);
/*  551 */     gp.lineTo(x2, y2);
/*  552 */     gp.lineTo(x3, y3);
/*  553 */     gp.lineTo(x4, y4);
/*  554 */     gp.closePath();
/*  555 */     drawShape(gp);
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
/*      */   protected void rectImpl(float x1, float y1, float x2, float y2)
/*      */   {
/*  572 */     this.rect.setFrame(x1, y1, x2 - x1, y2 - y1);
/*  573 */     drawShape(this.rect);
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
/*      */   protected void ellipseImpl(float x, float y, float w, float h)
/*      */   {
/*  590 */     this.ellipse.setFrame(x, y, w, h);
/*  591 */     drawShape(this.ellipse);
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
/*      */   protected void arcImpl(float x, float y, float w, float h, float start, float stop)
/*      */   {
/*  610 */     start = -start * 57.295776F;
/*  611 */     stop = -stop * 57.295776F;
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
/*  623 */     float sweep = stop - start;
/*      */     
/*      */ 
/*  626 */     if (this.fill)
/*      */     {
/*  628 */       this.arc.setArc(x, y, w, h, start, sweep, 2);
/*  629 */       fillShape(this.arc);
/*      */     }
/*  631 */     if (this.stroke)
/*      */     {
/*  633 */       this.arc.setArc(x, y, w, h, start, sweep, 0);
/*  634 */       strokeShape(this.arc);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void fillShape(Shape s)
/*      */   {
/*  646 */     if (this.fillGradient) {
/*  647 */       this.g2.setPaint(this.fillGradientObject);
/*  648 */       this.g2.fill(s);
/*  649 */     } else if (this.fill) {
/*  650 */       this.g2.setColor(this.fillColorObject);
/*  651 */       this.g2.fill(s);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void strokeShape(Shape s)
/*      */   {
/*  657 */     if (this.strokeGradient) {
/*  658 */       this.g2.setPaint(this.strokeGradientObject);
/*  659 */       this.g2.draw(s);
/*  660 */     } else if (this.stroke) {
/*  661 */       this.g2.setColor(this.strokeColorObject);
/*  662 */       this.g2.draw(s);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void drawShape(Shape s)
/*      */   {
/*  668 */     if (this.fillGradient) {
/*  669 */       this.g2.setPaint(this.fillGradientObject);
/*  670 */       this.g2.fill(s);
/*  671 */     } else if (this.fill) {
/*  672 */       this.g2.setColor(this.fillColorObject);
/*  673 */       this.g2.fill(s);
/*      */     }
/*  675 */     if (this.strokeGradient) {
/*  676 */       this.g2.setPaint(this.strokeGradientObject);
/*  677 */       this.g2.draw(s);
/*  678 */     } else if (this.stroke) {
/*  679 */       this.g2.setColor(this.strokeColorObject);
/*  680 */       this.g2.draw(s);
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
/*      */   public void box(float w, float h, float d)
/*      */   {
/*  695 */     showMethodWarning("box");
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
/*      */   public void sphere(float r)
/*      */   {
/*  712 */     showMethodWarning("sphere");
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
/*      */   public void bezierDetail(int detail) {}
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
/*      */   public void curveDetail(int detail) {}
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
/*      */   public void smooth()
/*      */   {
/*  795 */     this.smooth = true;
/*  796 */     this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/*  797 */       RenderingHints.VALUE_ANTIALIAS_ON);
/*  798 */     this.g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
/*      */     
/*  800 */       RenderingHints.VALUE_INTERPOLATION_BICUBIC);
/*      */   }
/*      */   
/*      */   public void noSmooth()
/*      */   {
/*  805 */     this.smooth = false;
/*  806 */     this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/*  807 */       RenderingHints.VALUE_ANTIALIAS_OFF);
/*  808 */     this.g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
/*  809 */       RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
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
/*      */   protected void imageImpl(PImage who, float x1, float y1, float x2, float y2, int u1, int v1, int u2, int v2)
/*      */   {
/*  840 */     if ((who.width <= 0) || (who.height <= 0)) { return;
/*      */     }
/*  842 */     if (who.getCache(this) == null)
/*      */     {
/*  844 */       who.setCache(this, new ImageCache(who));
/*  845 */       who.updatePixels();
/*  846 */       who.modified = true;
/*      */     }
/*      */     
/*  849 */     ImageCache cash = (ImageCache)who.getCache(this);
/*      */     
/*      */ 
/*  852 */     if (((this.tint) && (!cash.tinted)) || 
/*  853 */       ((this.tint) && (cash.tintedColor != this.tintColor)) || (
/*  854 */       (!this.tint) && (cash.tinted)))
/*      */     {
/*  856 */       who.updatePixels();
/*      */     }
/*      */     
/*  859 */     if (who.modified) {
/*  860 */       cash.update(this.tint, this.tintColor);
/*  861 */       who.modified = false;
/*      */     }
/*      */     
/*  864 */     this.g2.drawImage(((ImageCache)who.getCache(this)).image, 
/*  865 */       (int)x1, (int)y1, (int)x2, (int)y2, 
/*  866 */       u1, v1, u2, v2, null);
/*      */   }
/*      */   
/*      */   class ImageCache
/*      */   {
/*      */     PImage source;
/*      */     boolean tinted;
/*      */     int tintedColor;
/*      */     int[] tintedPixels;
/*      */     BufferedImage image;
/*      */     
/*      */     public ImageCache(PImage source) {
/*  878 */       this.source = source;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void delete() {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void update(boolean tint, int tintColor)
/*      */     {
/*  895 */       int bufferType = 2;
/*  896 */       boolean opaque = (tintColor & 0xFF000000) == -16777216;
/*  897 */       if ((this.source.format == 1) && (
/*  898 */         (!tint) || ((tint) && (opaque)))) {
/*  899 */         bufferType = 1;
/*      */       }
/*      */       
/*  902 */       boolean wrongType = (this.image != null) && (this.image.getType() != bufferType);
/*  903 */       if ((this.image == null) || (wrongType)) {
/*  904 */         this.image = new BufferedImage(this.source.width, this.source.height, bufferType);
/*      */       }
/*      */       
/*  907 */       WritableRaster wr = this.image.getRaster();
/*  908 */       if (tint) {
/*  909 */         if ((this.tintedPixels == null) || (this.tintedPixels.length != this.source.width)) {
/*  910 */           this.tintedPixels = new int[this.source.width];
/*      */         }
/*  912 */         int a2 = tintColor >> 24 & 0xFF;
/*  913 */         int r2 = tintColor >> 16 & 0xFF;
/*  914 */         int g2 = tintColor >> 8 & 0xFF;
/*  915 */         int b2 = tintColor & 0xFF;
/*      */         
/*  917 */         if (bufferType == 1)
/*      */         {
/*  919 */           int index = 0;
/*  920 */           for (int y = 0; y < this.source.height; y++) {
/*  921 */             for (int x = 0; x < this.source.width; x++) {
/*  922 */               int argb1 = this.source.pixels[(index++)];
/*  923 */               int r1 = argb1 >> 16 & 0xFF;
/*  924 */               int g1 = argb1 >> 8 & 0xFF;
/*  925 */               int b1 = argb1 & 0xFF;
/*      */               
/*  927 */               this.tintedPixels[x] = 
/*  928 */                 ((r2 * r1 & 0xFF00) << 8 | 
/*  929 */                 g2 * g1 & 0xFF00 | 
/*  930 */                 (b2 * b1 & 0xFF00) >> 8);
/*      */             }
/*  932 */             wr.setDataElements(0, y, this.source.width, 1, this.tintedPixels);
/*      */ 
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */         }
/*  940 */         else if (bufferType == 2) {
/*  941 */           int index = 0;
/*  942 */           for (int y = 0; y < this.source.height; y++) {
/*  943 */             if (this.source.format == 1) {
/*  944 */               int alpha = tintColor & 0xFF000000;
/*  945 */               for (int x = 0; x < this.source.width; x++) {
/*  946 */                 int argb1 = this.source.pixels[(index++)];
/*  947 */                 int r1 = argb1 >> 16 & 0xFF;
/*  948 */                 int g1 = argb1 >> 8 & 0xFF;
/*  949 */                 int b1 = argb1 & 0xFF;
/*  950 */                 this.tintedPixels[x] = 
/*      */                 
/*      */ 
/*  953 */                   (alpha | (r2 * r1 & 0xFF00) << 8 | g2 * g1 & 0xFF00 | (b2 * b1 & 0xFF00) >> 8);
/*      */               }
/*  955 */             } else if (this.source.format == 2) {
/*  956 */               for (int x = 0; x < this.source.width; x++) {
/*  957 */                 int argb1 = this.source.pixels[(index++)];
/*  958 */                 int a1 = argb1 >> 24 & 0xFF;
/*  959 */                 int r1 = argb1 >> 16 & 0xFF;
/*  960 */                 int g1 = argb1 >> 8 & 0xFF;
/*  961 */                 int b1 = argb1 & 0xFF;
/*  962 */                 this.tintedPixels[x] = 
/*  963 */                   ((a2 * a1 & 0xFF00) << 16 | 
/*  964 */                   (r2 * r1 & 0xFF00) << 8 | 
/*  965 */                   g2 * g1 & 0xFF00 | 
/*  966 */                   (b2 * b1 & 0xFF00) >> 8);
/*      */               }
/*  968 */             } else if (this.source.format == 4) {
/*  969 */               int lower = tintColor & 0xFFFFFF;
/*  970 */               for (int x = 0; x < this.source.width; x++) {
/*  971 */                 int a1 = this.source.pixels[(index++)];
/*  972 */                 this.tintedPixels[x] = 
/*  973 */                   ((a2 * a1 & 0xFF00) << 16 | lower);
/*      */               }
/*      */             }
/*  976 */             wr.setDataElements(0, y, this.source.width, 1, this.tintedPixels);
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  985 */         wr.setDataElements(0, 0, this.source.width, this.source.height, this.source.pixels);
/*      */       }
/*  987 */       this.tinted = tint;
/*  988 */       this.tintedColor = tintColor;
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
/*      */   public float textAscent()
/*      */   {
/* 1024 */     if (this.textFont == null) {
/* 1025 */       defaultFontOrDeath("textAscent");
/*      */     }
/*      */     
/* 1028 */     Font font = this.textFont.getFont();
/*      */     
/* 1030 */     if (font != null) {
/* 1031 */       FontMetrics metrics = this.parent.getFontMetrics(font);
/* 1032 */       return metrics.getAscent();
/*      */     }
/* 1034 */     return super.textAscent();
/*      */   }
/*      */   
/*      */   public float textDescent()
/*      */   {
/* 1039 */     if (this.textFont == null) {
/* 1040 */       defaultFontOrDeath("textAscent");
/*      */     }
/* 1042 */     Font font = this.textFont.getFont();
/*      */     
/* 1044 */     if (font != null) {
/* 1045 */       FontMetrics metrics = this.parent.getFontMetrics(font);
/* 1046 */       return metrics.getDescent();
/*      */     }
/* 1048 */     return super.textDescent();
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
/*      */   protected boolean textModeCheck(int mode)
/*      */   {
/* 1065 */     return (mode == 4) || (mode == 256);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void textSize(float size)
/*      */   {
/* 1076 */     if (this.textFont == null) {
/* 1077 */       defaultFontOrDeath("textAscent", size);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1086 */     Font font = this.textFont.getFont();
/*      */     
/* 1088 */     if (font != null) {
/* 1089 */       Font dfont = font.deriveFont(size);
/* 1090 */       this.g2.setFont(dfont);
/* 1091 */       this.textFont.setFont(dfont);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1097 */     super.textSize(size);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected float textWidthImpl(char[] buffer, int start, int stop)
/*      */   {
/* 1108 */     Font font = this.textFont.getFont();
/*      */     
/* 1110 */     if (font != null)
/*      */     {
/* 1112 */       int length = stop - start;
/* 1113 */       FontMetrics metrics = this.g2.getFontMetrics(font);
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
/* 1127 */       return metrics.charsWidth(buffer, start, length);
/*      */     }
/*      */     
/* 1130 */     return super.textWidthImpl(buffer, start, stop);
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
/*      */   protected void textLineImpl(char[] buffer, int start, int stop, float x, float y)
/*      */   {
/* 1154 */     Font font = this.textFont.getFont();
/*      */     
/* 1156 */     if (font != null)
/*      */     {
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
/* 1172 */       Object antialias = 
/* 1173 */         this.g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
/* 1174 */       if (antialias == null)
/*      */       {
/* 1176 */         antialias = RenderingHints.VALUE_ANTIALIAS_DEFAULT;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1182 */       this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/* 1183 */         this.textFont.smooth ? 
/* 1184 */         RenderingHints.VALUE_ANTIALIAS_ON : 
/* 1185 */         RenderingHints.VALUE_ANTIALIAS_OFF);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1191 */       this.g2.setColor(this.fillColorObject);
/* 1192 */       int length = stop - start;
/* 1193 */       this.g2.drawChars(buffer, start, length, (int)(x + 0.5F), (int)(y + 0.5F));
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
/* 1206 */       this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antialias);
/*      */       
/* 1208 */       this.textX = (x + textWidthImpl(buffer, start, stop));
/* 1209 */       this.textY = y;
/* 1210 */       this.textZ = 0.0F;
/*      */     }
/*      */     else {
/* 1213 */       super.textLineImpl(buffer, start, stop, x, y);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void pushMatrix()
/*      */   {
/* 1225 */     if (this.transformCount == this.transformStack.length) {
/* 1226 */       throw new RuntimeException("pushMatrix() cannot use push more than " + 
/* 1227 */         this.transformStack.length + " times");
/*      */     }
/* 1229 */     this.transformStack[this.transformCount] = this.g2.getTransform();
/* 1230 */     this.transformCount += 1;
/*      */   }
/*      */   
/*      */   public void popMatrix()
/*      */   {
/* 1235 */     if (this.transformCount == 0) {
/* 1236 */       throw new RuntimeException("missing a popMatrix() to go with that pushMatrix()");
/*      */     }
/*      */     
/* 1239 */     this.transformCount -= 1;
/* 1240 */     this.g2.setTransform(this.transformStack[this.transformCount]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void translate(float tx, float ty)
/*      */   {
/* 1251 */     this.g2.translate(tx, ty);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void rotate(float angle)
/*      */   {
/* 1259 */     this.g2.rotate(angle);
/*      */   }
/*      */   
/*      */   public void rotateX(float angle)
/*      */   {
/* 1264 */     showDepthWarning("rotateX");
/*      */   }
/*      */   
/*      */   public void rotateY(float angle)
/*      */   {
/* 1269 */     showDepthWarning("rotateY");
/*      */   }
/*      */   
/*      */   public void rotateZ(float angle)
/*      */   {
/* 1274 */     showDepthWarning("rotateZ");
/*      */   }
/*      */   
/*      */   public void rotate(float angle, float vx, float vy, float vz)
/*      */   {
/* 1279 */     showVariationWarning("rotate");
/*      */   }
/*      */   
/*      */   public void scale(float s)
/*      */   {
/* 1284 */     this.g2.scale(s, s);
/*      */   }
/*      */   
/*      */   public void scale(float sx, float sy)
/*      */   {
/* 1289 */     this.g2.scale(sx, sy);
/*      */   }
/*      */   
/*      */   public void scale(float sx, float sy, float sz)
/*      */   {
/* 1294 */     showDepthWarningXYZ("scale");
/*      */   }
/*      */   
/*      */   public void skewX(float angle)
/*      */   {
/* 1299 */     this.g2.shear(Math.tan(angle), 0.0D);
/*      */   }
/*      */   
/*      */   public void skewY(float angle)
/*      */   {
/* 1304 */     this.g2.shear(0.0D, Math.tan(angle));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void resetMatrix()
/*      */   {
/* 1315 */     this.g2.setTransform(new AffineTransform());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyMatrix(float n00, float n01, float n02, float n10, float n11, float n12)
/*      */   {
/* 1326 */     this.g2.transform(new AffineTransform(n00, n10, n01, n11, n02, n12));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyMatrix(float n00, float n01, float n02, float n03, float n10, float n11, float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31, float n32, float n33)
/*      */   {
/* 1338 */     showVariationWarning("applyMatrix");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PMatrix getMatrix()
/*      */   {
/* 1349 */     return getMatrix(null);
/*      */   }
/*      */   
/*      */   public PMatrix2D getMatrix(PMatrix2D target)
/*      */   {
/* 1354 */     if (target == null) {
/* 1355 */       target = new PMatrix2D();
/*      */     }
/* 1357 */     this.g2.getTransform().getMatrix(this.transform);
/* 1358 */     target.set((float)this.transform[0], (float)this.transform[2], (float)this.transform[4], 
/* 1359 */       (float)this.transform[1], (float)this.transform[3], (float)this.transform[5]);
/* 1360 */     return target;
/*      */   }
/*      */   
/*      */   public PMatrix3D getMatrix(PMatrix3D target)
/*      */   {
/* 1365 */     showVariationWarning("getMatrix");
/* 1366 */     return target;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMatrix(PMatrix2D source)
/*      */   {
/* 1374 */     this.g2.setTransform(new AffineTransform(source.m00, source.m10, 
/* 1375 */       source.m01, source.m11, 
/* 1376 */       source.m02, source.m12));
/*      */   }
/*      */   
/*      */   public void setMatrix(PMatrix3D source)
/*      */   {
/* 1381 */     showVariationWarning("setMatrix");
/*      */   }
/*      */   
/*      */   public void printMatrix()
/*      */   {
/* 1386 */     getMatrix(null).print();
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
/*      */   public float screenX(float x, float y)
/*      */   {
/* 1425 */     this.g2.getTransform().getMatrix(this.transform);
/* 1426 */     return (float)this.transform[0] * x + (float)this.transform[2] * y + (float)this.transform[4];
/*      */   }
/*      */   
/*      */   public float screenY(float x, float y)
/*      */   {
/* 1431 */     this.g2.getTransform().getMatrix(this.transform);
/* 1432 */     return (float)this.transform[1] * x + (float)this.transform[3] * y + (float)this.transform[5];
/*      */   }
/*      */   
/*      */   public float screenX(float x, float y, float z)
/*      */   {
/* 1437 */     showDepthWarningXYZ("screenX");
/* 1438 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public float screenY(float x, float y, float z)
/*      */   {
/* 1443 */     showDepthWarningXYZ("screenY");
/* 1444 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public float screenZ(float x, float y, float z)
/*      */   {
/* 1449 */     showDepthWarningXYZ("screenZ");
/* 1450 */     return 0.0F;
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
/*      */   public void strokeCap(int cap)
/*      */   {
/* 1478 */     super.strokeCap(cap);
/* 1479 */     strokeImpl();
/*      */   }
/*      */   
/*      */   public void strokeJoin(int join)
/*      */   {
/* 1484 */     super.strokeJoin(join);
/* 1485 */     strokeImpl();
/*      */   }
/*      */   
/*      */   public void strokeWeight(float weight)
/*      */   {
/* 1490 */     super.strokeWeight(weight);
/* 1491 */     strokeImpl();
/*      */   }
/*      */   
/*      */   protected void strokeImpl()
/*      */   {
/* 1496 */     int cap = 0;
/* 1497 */     if (this.strokeCap == 2) {
/* 1498 */       cap = 1;
/* 1499 */     } else if (this.strokeCap == 4) {
/* 1500 */       cap = 2;
/*      */     }
/*      */     
/* 1503 */     int join = 2;
/* 1504 */     if (this.strokeJoin == 8) {
/* 1505 */       join = 0;
/* 1506 */     } else if (this.strokeJoin == 2) {
/* 1507 */       join = 1;
/*      */     }
/*      */     
/* 1510 */     this.g2.setStroke(new BasicStroke(this.strokeWeight, cap, join));
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
/*      */   protected void strokeFromCalc()
/*      */   {
/* 1523 */     super.strokeFromCalc();
/* 1524 */     this.strokeColorObject = new Color(this.strokeColor, true);
/* 1525 */     this.strokeGradient = false;
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
/*      */   protected void tintFromCalc()
/*      */   {
/* 1538 */     super.tintFromCalc();
/*      */     
/* 1540 */     this.tintColorObject = new Color(this.tintColor, true);
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
/*      */   protected void fillFromCalc()
/*      */   {
/* 1553 */     super.fillFromCalc();
/* 1554 */     this.fillColorObject = new Color(this.fillColor, true);
/* 1555 */     this.fillGradient = false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public void backgroundImpl()
/*      */   {
/* 1620 */     if (this.backgroundAlpha)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1627 */       WritableRaster raster = ((BufferedImage)this.image).getRaster();
/* 1628 */       if ((this.clearPixels == null) || (this.clearPixels.length < this.width)) {
/* 1629 */         this.clearPixels = new int[this.width];
/*      */       }
/* 1631 */       Arrays.fill(this.clearPixels, this.backgroundColor);
/* 1632 */       for (int i = 0; i < this.height; i++) {
/* 1633 */         raster.setDataElements(0, i, this.width, 1, this.clearPixels);
/*      */       }
/*      */       
/*      */     }
/*      */     else
/*      */     {
/* 1639 */       pushMatrix();
/* 1640 */       resetMatrix();
/* 1641 */       this.g2.setColor(new Color(this.backgroundColor));
/* 1642 */       this.g2.fillRect(0, 0, this.width, this.height);
/* 1643 */       popMatrix();
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
/*      */   public void beginRaw(PGraphics recorderRaw)
/*      */   {
/* 1696 */     showMethodWarning("beginRaw");
/*      */   }
/*      */   
/*      */   public void endRaw()
/*      */   {
/* 1701 */     showMethodWarning("endRaw");
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
/*      */   public void loadPixels()
/*      */   {
/* 1738 */     if ((this.pixels == null) || (this.pixels.length != this.width * this.height)) {
/* 1739 */       this.pixels = new int[this.width * this.height];
/*      */     }
/*      */     
/* 1742 */     WritableRaster raster = ((BufferedImage)(this.primarySurface ? this.offscreen : this.image)).getRaster();
/* 1743 */     raster.getDataElements(0, 0, this.width, this.height, this.pixels);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void updatePixels()
/*      */   {
/* 1755 */     WritableRaster raster = ((BufferedImage)(this.primarySurface ? this.offscreen : this.image)).getRaster();
/* 1756 */     raster.setDataElements(0, 0, this.width, this.height, this.pixels);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void updatePixels(int x, int y, int c, int d)
/*      */   {
/* 1768 */     if ((x != 0) || (y != 0) || (c != this.width) || (d != this.height))
/*      */     {
/* 1770 */       showVariationWarning("updatePixels(x, y, w, h)");
/*      */     }
/* 1772 */     updatePixels();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1780 */   static int[] getset = new int[1];
/*      */   
/*      */   public int get(int x, int y)
/*      */   {
/* 1784 */     if ((x < 0) || (y < 0) || (x >= this.width) || (y >= this.height)) { return 0;
/*      */     }
/* 1786 */     WritableRaster raster = ((BufferedImage)(this.primarySurface ? this.offscreen : this.image)).getRaster();
/* 1787 */     raster.getDataElements(x, y, getset);
/* 1788 */     return getset[0];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public PImage getImpl(int x, int y, int w, int h)
/*      */   {
/* 1796 */     PImage output = new PImage(w, h);
/* 1797 */     output.parent = this.parent;
/*      */     
/*      */ 
/*      */ 
/* 1801 */     WritableRaster raster = ((BufferedImage)(this.primarySurface ? this.offscreen : this.image)).getRaster();
/* 1802 */     raster.getDataElements(x, y, w, h, output.pixels);
/*      */     
/* 1804 */     return output;
/*      */   }
/*      */   
/*      */   public PImage get()
/*      */   {
/* 1809 */     return get(0, 0, this.width, this.height);
/*      */   }
/*      */   
/*      */   public void set(int x, int y, int argb)
/*      */   {
/* 1814 */     if ((x < 0) || (y < 0) || (x >= this.width) || (y >= this.height)) { return;
/*      */     }
/* 1816 */     getset[0] = argb;
/* 1817 */     WritableRaster raster = ((BufferedImage)(this.primarySurface ? this.offscreen : this.image)).getRaster();
/* 1818 */     raster.setDataElements(x, y, getset);
/*      */   }
/*      */   
/*      */ 
/*      */   protected void setImpl(int dx, int dy, int sx, int sy, int sw, int sh, PImage src)
/*      */   {
/* 1824 */     WritableRaster raster = ((BufferedImage)(this.primarySurface ? this.offscreen : this.image)).getRaster();
/* 1825 */     if ((sx == 0) && (sy == 0) && (sw == src.width) && (sh == src.height)) {
/* 1826 */       raster.setDataElements(dx, dy, src.width, src.height, src.pixels);
/*      */     }
/*      */     else {
/* 1829 */       PImage temp = src.get(sx, sy, sw, sh);
/* 1830 */       raster.setDataElements(dx, dy, temp.width, temp.height, temp.pixels);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void mask(int[] alpha)
/*      */   {
/* 1842 */     showMethodWarning("mask");
/*      */   }
/*      */   
/*      */   public void mask(PImage alpha)
/*      */   {
/* 1847 */     showMethodWarning("mask");
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
/*      */   public void copy(int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh)
/*      */   {
/* 1874 */     if ((sw != dw) || (sh != dh))
/*      */     {
/* 1876 */       copy(this, sx, sy, sw, sh, dx, dy, dw, dh);
/*      */     }
/*      */     else {
/* 1879 */       dx -= sx;
/* 1880 */       dy -= sy;
/* 1881 */       this.g2.copyArea(sx, sy, sw, sh, dx, dy);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\core\PGraphicsJava2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */