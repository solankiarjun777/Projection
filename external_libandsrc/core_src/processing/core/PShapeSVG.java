/*      */ package processing.core;
/*      */ 
/*      */ import java.awt.Paint;
/*      */ import java.awt.PaintContext;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Point2D.Float;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.Raster;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.io.PrintStream;
/*      */ import java.util.HashMap;
/*      */ import processing.xml.XMLElement;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PShapeSVG
/*      */   extends PShape
/*      */ {
/*      */   XMLElement element;
/*      */   float opacity;
/*      */   float strokeOpacity;
/*      */   float fillOpacity;
/*      */   Gradient strokeGradient;
/*      */   Paint strokeGradientPaint;
/*      */   String strokeName;
/*      */   Gradient fillGradient;
/*      */   Paint fillGradientPaint;
/*      */   String fillName;
/*      */   
/*      */   public PShapeSVG(PApplet parent, String filename)
/*      */   {
/*  168 */     this(new XMLElement(parent, filename));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public PShapeSVG(XMLElement svg)
/*      */   {
/*  176 */     this(null, svg, true);
/*      */     
/*  178 */     if (!svg.getName().equals("svg")) {
/*  179 */       throw new RuntimeException("root is not <svg>, it's <" + svg.getName() + ">");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  184 */     String viewBoxStr = svg.getString("viewBox");
/*  185 */     if (viewBoxStr != null) {
/*  186 */       int[] viewBox = PApplet.parseInt(PApplet.splitTokens(viewBoxStr));
/*  187 */       this.width = viewBox[2];
/*  188 */       this.height = viewBox[3];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  194 */     String unitWidth = svg.getString("width");
/*  195 */     String unitHeight = svg.getString("height");
/*  196 */     if (unitWidth != null) {
/*  197 */       this.width = parseUnitSize(unitWidth);
/*  198 */       this.height = parseUnitSize(unitHeight);
/*      */     }
/*  200 */     else if ((this.width == 0.0F) || (this.height == 0.0F))
/*      */     {
/*  202 */       PGraphics.showWarning("The width and/or height is not readable in the <svg> tag of this file.");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  207 */       this.width = 1.0F;
/*  208 */       this.height = 1.0F;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PShapeSVG(PShapeSVG parent, XMLElement properties, boolean parseKids)
/*      */   {
/*  220 */     this.parent = parent;
/*      */     
/*  222 */     if (parent == null)
/*      */     {
/*  224 */       this.stroke = false;
/*  225 */       this.strokeColor = -16777216;
/*  226 */       this.strokeWeight = 1.0F;
/*  227 */       this.strokeCap = 1;
/*  228 */       this.strokeJoin = 8;
/*  229 */       this.strokeGradient = null;
/*  230 */       this.strokeGradientPaint = null;
/*  231 */       this.strokeName = null;
/*      */       
/*  233 */       this.fill = true;
/*  234 */       this.fillColor = -16777216;
/*  235 */       this.fillGradient = null;
/*  236 */       this.fillGradientPaint = null;
/*  237 */       this.fillName = null;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  242 */       this.strokeOpacity = 1.0F;
/*  243 */       this.fillOpacity = 1.0F;
/*  244 */       this.opacity = 1.0F;
/*      */     }
/*      */     else {
/*  247 */       this.stroke = parent.stroke;
/*  248 */       this.strokeColor = parent.strokeColor;
/*  249 */       this.strokeWeight = parent.strokeWeight;
/*  250 */       this.strokeCap = parent.strokeCap;
/*  251 */       this.strokeJoin = parent.strokeJoin;
/*  252 */       this.strokeGradient = parent.strokeGradient;
/*  253 */       this.strokeGradientPaint = parent.strokeGradientPaint;
/*  254 */       this.strokeName = parent.strokeName;
/*      */       
/*  256 */       this.fill = parent.fill;
/*  257 */       this.fillColor = parent.fillColor;
/*  258 */       this.fillGradient = parent.fillGradient;
/*  259 */       this.fillGradientPaint = parent.fillGradientPaint;
/*  260 */       this.fillName = parent.fillName;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  265 */       this.opacity = parent.opacity;
/*      */     }
/*      */     
/*  268 */     this.element = properties;
/*  269 */     this.name = properties.getString("id");
/*      */     
/*  271 */     if (this.name != null) {
/*      */       for (;;) {
/*  273 */         String[] m = PApplet.match(this.name, "_x([A-Za-z0-9]{2})_");
/*  274 */         if (m == null) break;
/*  275 */         char repair = (char)PApplet.unhex(m[1]);
/*  276 */         this.name = this.name.replace(m[0], repair);
/*      */       }
/*      */     }
/*      */     
/*  280 */     String displayStr = properties.getString("display", "inline");
/*  281 */     this.visible = (!displayStr.equals("none"));
/*      */     
/*  283 */     String transformStr = properties.getString("transform");
/*  284 */     if (transformStr != null) {
/*  285 */       this.matrix = parseTransform(transformStr);
/*      */     }
/*      */     
/*  288 */     if (parseKids) {
/*  289 */       parseColors(properties);
/*  290 */       parseChildren(properties);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void parseChildren(XMLElement graphics)
/*      */   {
/*  296 */     XMLElement[] elements = graphics.getChildren();
/*  297 */     this.children = new PShape[elements.length];
/*  298 */     this.childCount = 0;
/*      */     XMLElement[] arrayOfXMLElement1;
/*  300 */     int j = (arrayOfXMLElement1 = elements).length; for (int i = 0; i < j; i++) { XMLElement elem = arrayOfXMLElement1[i];
/*  301 */       PShape kid = parseChild(elem);
/*  302 */       if (kid != null)
/*      */       {
/*      */ 
/*      */ 
/*  306 */         addChild(kid);
/*      */       }
/*      */     }
/*  309 */     this.children = ((PShape[])PApplet.subset(this.children, 0, this.childCount));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected PShape parseChild(XMLElement elem)
/*      */   {
/*  319 */     String name = elem.getName();
/*  320 */     PShapeSVG shape = null;
/*      */     
/*  322 */     if (name.equals("g"))
/*      */     {
/*  324 */       shape = new PShapeSVG(this, elem, true);
/*      */     }
/*  326 */     else if (name.equals("defs"))
/*      */     {
/*      */ 
/*      */ 
/*  330 */       shape = new PShapeSVG(this, elem, true);
/*      */     }
/*  332 */     else if (name.equals("line"))
/*      */     {
/*      */ 
/*  335 */       shape = new PShapeSVG(this, elem, true);
/*  336 */       shape.parseLine();
/*      */     }
/*  338 */     else if (name.equals("circle"))
/*      */     {
/*  340 */       shape = new PShapeSVG(this, elem, true);
/*  341 */       shape.parseEllipse(true);
/*      */     }
/*  343 */     else if (name.equals("ellipse"))
/*      */     {
/*  345 */       shape = new PShapeSVG(this, elem, true);
/*  346 */       shape.parseEllipse(false);
/*      */     }
/*  348 */     else if (name.equals("rect"))
/*      */     {
/*  350 */       shape = new PShapeSVG(this, elem, true);
/*  351 */       shape.parseRect();
/*      */     }
/*  353 */     else if (name.equals("polygon"))
/*      */     {
/*  355 */       shape = new PShapeSVG(this, elem, true);
/*  356 */       shape.parsePoly(true);
/*      */     }
/*  358 */     else if (name.equals("polyline"))
/*      */     {
/*  360 */       shape = new PShapeSVG(this, elem, true);
/*  361 */       shape.parsePoly(false);
/*      */     }
/*  363 */     else if (name.equals("path"))
/*      */     {
/*  365 */       shape = new PShapeSVG(this, elem, true);
/*  366 */       shape.parsePath();
/*      */     } else {
/*  368 */       if (name.equals("radialGradient")) {
/*  369 */         return new RadialGradient(this, elem);
/*      */       }
/*  371 */       if (name.equals("linearGradient")) {
/*  372 */         return new LinearGradient(this, elem);
/*      */       }
/*  374 */       if (name.equals("font")) {
/*  375 */         return new Font(this, elem);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  383 */       if (name.equals("metadata"))
/*      */       {
/*  385 */         return null;
/*      */       }
/*  387 */       if (name.equals("text")) {
/*  388 */         PGraphics.showWarning("Text and fonts in SVG files are not currently supported, convert text to outlines instead.");
/*      */ 
/*      */ 
/*      */       }
/*  392 */       else if (name.equals("filter")) {
/*  393 */         PGraphics.showWarning("Filters are not supported.");
/*      */       }
/*  395 */       else if (name.equals("mask")) {
/*  396 */         PGraphics.showWarning("Masks are not supported.");
/*      */       }
/*  398 */       else if (name.equals("pattern")) {
/*  399 */         PGraphics.showWarning("Patterns are not supported.");
/*      */       }
/*  401 */       else if (!name.equals("stop"))
/*      */       {
/*      */ 
/*  404 */         if (!name.equals("sodipodi:namedview"))
/*      */         {
/*      */ 
/*      */ 
/*  408 */           PGraphics.showWarning("Ignoring <" + name + "> tag."); }
/*      */       }
/*      */     }
/*  411 */     return shape;
/*      */   }
/*      */   
/*      */   protected void parseLine()
/*      */   {
/*  416 */     this.primitive = 4;
/*  417 */     this.family = 1;
/*  418 */     this.params = new float[] {
/*  419 */       getFloatWithUnit(this.element, "x1"), 
/*  420 */       getFloatWithUnit(this.element, "y1"), 
/*  421 */       getFloatWithUnit(this.element, "x2"), 
/*  422 */       getFloatWithUnit(this.element, "y2") };
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void parseEllipse(boolean circle)
/*      */   {
/*  432 */     this.primitive = 31;
/*  433 */     this.family = 1;
/*  434 */     this.params = new float[4];
/*      */     
/*  436 */     this.params[0] = getFloatWithUnit(this.element, "cx");
/*  437 */     this.params[1] = getFloatWithUnit(this.element, "cy");
/*      */     float rx;
/*      */     float rx;
/*  440 */     float ry; if (circle) { float ry;
/*  441 */       rx = ry = getFloatWithUnit(this.element, "r");
/*      */     } else {
/*  443 */       rx = getFloatWithUnit(this.element, "rx");
/*  444 */       ry = getFloatWithUnit(this.element, "ry");
/*      */     }
/*  446 */     this.params[0] -= rx;
/*  447 */     this.params[1] -= ry;
/*      */     
/*  449 */     this.params[2] = (rx * 2.0F);
/*  450 */     this.params[3] = (ry * 2.0F);
/*      */   }
/*      */   
/*      */   protected void parseRect()
/*      */   {
/*  455 */     this.primitive = 30;
/*  456 */     this.family = 1;
/*  457 */     this.params = new float[] {
/*  458 */       getFloatWithUnit(this.element, "x"), 
/*  459 */       getFloatWithUnit(this.element, "y"), 
/*  460 */       getFloatWithUnit(this.element, "width"), 
/*  461 */       getFloatWithUnit(this.element, "height") };
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void parsePoly(boolean close)
/*      */   {
/*  471 */     this.family = 2;
/*  472 */     this.close = close;
/*      */     
/*  474 */     String pointsAttr = this.element.getString("points");
/*  475 */     if (pointsAttr != null) {
/*  476 */       String[] pointsBuffer = PApplet.splitTokens(pointsAttr);
/*  477 */       this.vertexCount = pointsBuffer.length;
/*  478 */       this.vertices = new float[this.vertexCount][2];
/*  479 */       for (int i = 0; i < this.vertexCount; i++) {
/*  480 */         String[] pb = PApplet.split(pointsBuffer[i], ',');
/*  481 */         this.vertices[i][0] = Float.valueOf(pb[0]).floatValue();
/*  482 */         this.vertices[i][1] = Float.valueOf(pb[1]).floatValue();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void parsePath()
/*      */   {
/*  489 */     this.family = 2;
/*  490 */     this.primitive = 0;
/*      */     
/*  492 */     String pathData = this.element.getString("d");
/*  493 */     if ((pathData == null) || (PApplet.trim(pathData).length() == 0)) {
/*  494 */       return;
/*      */     }
/*  496 */     char[] pathDataChars = pathData.toCharArray();
/*      */     
/*  498 */     StringBuffer pathBuffer = new StringBuffer();
/*  499 */     boolean lastSeparate = false;
/*      */     
/*  501 */     for (int i = 0; i < pathDataChars.length; i++) {
/*  502 */       char c = pathDataChars[i];
/*  503 */       boolean separate = false;
/*      */       
/*  505 */       if ((c == 'M') || (c == 'm') || 
/*  506 */         (c == 'L') || (c == 'l') || 
/*  507 */         (c == 'H') || (c == 'h') || 
/*  508 */         (c == 'V') || (c == 'v') || 
/*  509 */         (c == 'C') || (c == 'c') || 
/*  510 */         (c == 'S') || (c == 's') || 
/*  511 */         (c == 'Q') || (c == 'q') || 
/*  512 */         (c == 'T') || (c == 't') || 
/*      */         
/*  514 */         (c == 'Z') || (c == 'z') || 
/*  515 */         (c == ',')) {
/*  516 */         separate = true;
/*  517 */         if (i != 0) {
/*  518 */           pathBuffer.append("|");
/*      */         }
/*      */       }
/*  521 */       if ((c == 'Z') || (c == 'z')) {
/*  522 */         separate = false;
/*      */       }
/*  524 */       if ((c == '-') && (!lastSeparate))
/*      */       {
/*      */ 
/*  527 */         if ((i == 0) || (pathDataChars[(i - 1)] != 'e')) {
/*  528 */           pathBuffer.append("|");
/*      */         }
/*      */       }
/*  531 */       if (c != ',') {
/*  532 */         pathBuffer.append(c);
/*      */       }
/*  534 */       if ((separate) && (c != ',') && (c != '-')) {
/*  535 */         pathBuffer.append("|");
/*      */       }
/*  537 */       lastSeparate = separate;
/*      */     }
/*      */     
/*      */ 
/*  541 */     String[] pathTokens = 
/*  542 */       PApplet.splitTokens(pathBuffer.toString(), "| \t\n\r\f ");
/*  543 */     this.vertices = new float[pathTokens.length][2];
/*  544 */     this.vertexCodes = new int[pathTokens.length];
/*      */     
/*  546 */     float cx = 0.0F;
/*  547 */     float cy = 0.0F;
/*  548 */     int i = 0;
/*      */     
/*  550 */     char implicitCommand = '\000';
/*      */     
/*  552 */     boolean prevCurve = false;
/*      */     
/*      */ 
/*  555 */     while (i < pathTokens.length) {
/*  556 */       char c = pathTokens[i].charAt(0);
/*  557 */       if (((c >= '0') && (c <= '9')) || ((c == '-') && (implicitCommand != 0))) {
/*  558 */         c = implicitCommand;
/*  559 */         i--;
/*      */       } else {
/*  561 */         implicitCommand = c;
/*      */       }
/*  563 */       switch (c)
/*      */       {
/*      */       case 'M': 
/*  566 */         cx = PApplet.parseFloat(pathTokens[(i + 1)]);
/*  567 */         cy = PApplet.parseFloat(pathTokens[(i + 2)]);
/*  568 */         parsePathMoveto(cx, cy);
/*  569 */         implicitCommand = 'L';
/*  570 */         i += 3;
/*  571 */         break;
/*      */       
/*      */       case 'm': 
/*  574 */         cx += PApplet.parseFloat(pathTokens[(i + 1)]);
/*  575 */         cy += PApplet.parseFloat(pathTokens[(i + 2)]);
/*  576 */         parsePathMoveto(cx, cy);
/*  577 */         implicitCommand = 'l';
/*  578 */         i += 3;
/*  579 */         break;
/*      */       
/*      */       case 'L': 
/*  582 */         cx = PApplet.parseFloat(pathTokens[(i + 1)]);
/*  583 */         cy = PApplet.parseFloat(pathTokens[(i + 2)]);
/*  584 */         parsePathLineto(cx, cy);
/*  585 */         i += 3;
/*  586 */         break;
/*      */       
/*      */       case 'l': 
/*  589 */         cx += PApplet.parseFloat(pathTokens[(i + 1)]);
/*  590 */         cy += PApplet.parseFloat(pathTokens[(i + 2)]);
/*  591 */         parsePathLineto(cx, cy);
/*  592 */         i += 3;
/*  593 */         break;
/*      */       
/*      */ 
/*      */       case 'H': 
/*  597 */         cx = PApplet.parseFloat(pathTokens[(i + 1)]);
/*  598 */         parsePathLineto(cx, cy);
/*  599 */         i += 2;
/*  600 */         break;
/*      */       
/*      */ 
/*      */       case 'h': 
/*  604 */         cx += PApplet.parseFloat(pathTokens[(i + 1)]);
/*  605 */         parsePathLineto(cx, cy);
/*  606 */         i += 2;
/*  607 */         break;
/*      */       
/*      */       case 'V': 
/*  610 */         cy = PApplet.parseFloat(pathTokens[(i + 1)]);
/*  611 */         parsePathLineto(cx, cy);
/*  612 */         i += 2;
/*  613 */         break;
/*      */       
/*      */       case 'v': 
/*  616 */         cy += PApplet.parseFloat(pathTokens[(i + 1)]);
/*  617 */         parsePathLineto(cx, cy);
/*  618 */         i += 2;
/*  619 */         break;
/*      */       
/*      */ 
/*      */       case 'C': 
/*  623 */         float ctrlX1 = PApplet.parseFloat(pathTokens[(i + 1)]);
/*  624 */         float ctrlY1 = PApplet.parseFloat(pathTokens[(i + 2)]);
/*  625 */         float ctrlX2 = PApplet.parseFloat(pathTokens[(i + 3)]);
/*  626 */         float ctrlY2 = PApplet.parseFloat(pathTokens[(i + 4)]);
/*  627 */         float endX = PApplet.parseFloat(pathTokens[(i + 5)]);
/*  628 */         float endY = PApplet.parseFloat(pathTokens[(i + 6)]);
/*  629 */         parsePathCurveto(ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
/*  630 */         cx = endX;
/*  631 */         cy = endY;
/*  632 */         i += 7;
/*  633 */         prevCurve = true;
/*      */         
/*  635 */         break;
/*      */       
/*      */ 
/*      */       case 'c': 
/*  639 */         float ctrlX1 = cx + PApplet.parseFloat(pathTokens[(i + 1)]);
/*  640 */         float ctrlY1 = cy + PApplet.parseFloat(pathTokens[(i + 2)]);
/*  641 */         float ctrlX2 = cx + PApplet.parseFloat(pathTokens[(i + 3)]);
/*  642 */         float ctrlY2 = cy + PApplet.parseFloat(pathTokens[(i + 4)]);
/*  643 */         float endX = cx + PApplet.parseFloat(pathTokens[(i + 5)]);
/*  644 */         float endY = cy + PApplet.parseFloat(pathTokens[(i + 6)]);
/*  645 */         parsePathCurveto(ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
/*  646 */         cx = endX;
/*  647 */         cy = endY;
/*  648 */         i += 7;
/*  649 */         prevCurve = true;
/*      */         
/*  651 */         break;
/*      */       case 'S': 
/*      */         float ctrlY;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */         float ctrlX;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */         float ctrlY;
/*      */         
/*      */ 
/*      */ 
/*  667 */         if (!prevCurve) {
/*  668 */           float ctrlX = cx;
/*  669 */           ctrlY = cy;
/*      */         } else {
/*  671 */           float ppx = this.vertices[(this.vertexCount - 2)][0];
/*  672 */           float ppy = this.vertices[(this.vertexCount - 2)][1];
/*  673 */           float px = this.vertices[(this.vertexCount - 1)][0];
/*  674 */           float py = this.vertices[(this.vertexCount - 1)][1];
/*  675 */           ctrlX = px + (px - ppx);
/*  676 */           ctrlY = py + (py - ppy);
/*      */         }
/*  678 */         float ctrlX2 = PApplet.parseFloat(pathTokens[(i + 1)]);
/*  679 */         float ctrlY2 = PApplet.parseFloat(pathTokens[(i + 2)]);
/*  680 */         float endX = PApplet.parseFloat(pathTokens[(i + 3)]);
/*  681 */         float endY = PApplet.parseFloat(pathTokens[(i + 4)]);
/*  682 */         parsePathCurveto(ctrlX, ctrlY, ctrlX2, ctrlY2, endX, endY);
/*  683 */         cx = endX;
/*  684 */         cy = endY;
/*  685 */         i += 5;
/*  686 */         prevCurve = true;
/*      */         
/*  688 */         break;
/*      */       case 's':  float ctrlY;
/*      */         float ctrlX;
/*      */         float ctrlY;
/*  692 */         if (!prevCurve) {
/*  693 */           float ctrlX = cx;
/*  694 */           ctrlY = cy;
/*      */         } else {
/*  696 */           float ppx = this.vertices[(this.vertexCount - 2)][0];
/*  697 */           float ppy = this.vertices[(this.vertexCount - 2)][1];
/*  698 */           float px = this.vertices[(this.vertexCount - 1)][0];
/*  699 */           float py = this.vertices[(this.vertexCount - 1)][1];
/*  700 */           ctrlX = px + (px - ppx);
/*  701 */           ctrlY = py + (py - ppy);
/*      */         }
/*  703 */         float ctrlX2 = cx + PApplet.parseFloat(pathTokens[(i + 1)]);
/*  704 */         float ctrlY2 = cy + PApplet.parseFloat(pathTokens[(i + 2)]);
/*  705 */         float endX = cx + PApplet.parseFloat(pathTokens[(i + 3)]);
/*  706 */         float endY = cy + PApplet.parseFloat(pathTokens[(i + 4)]);
/*  707 */         parsePathCurveto(ctrlX, ctrlY, ctrlX2, ctrlY2, endX, endY);
/*  708 */         cx = endX;
/*  709 */         cy = endY;
/*  710 */         i += 5;
/*  711 */         prevCurve = true;
/*      */         
/*  713 */         break;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       case 'Q': 
/*  723 */         float ctrlX = PApplet.parseFloat(pathTokens[(i + 1)]);
/*  724 */         float ctrlY = PApplet.parseFloat(pathTokens[(i + 2)]);
/*  725 */         float endX = PApplet.parseFloat(pathTokens[(i + 3)]);
/*  726 */         float endY = PApplet.parseFloat(pathTokens[(i + 4)]);
/*      */         
/*  728 */         parsePathQuadto(ctrlX, ctrlY, endX, endY);
/*  729 */         cx = endX;
/*  730 */         cy = endY;
/*  731 */         i += 5;
/*  732 */         prevCurve = true;
/*      */         
/*  734 */         break;
/*      */       
/*      */ 
/*      */       case 'q': 
/*  738 */         float ctrlX = cx + PApplet.parseFloat(pathTokens[(i + 1)]);
/*  739 */         float ctrlY = cy + PApplet.parseFloat(pathTokens[(i + 2)]);
/*  740 */         float endX = cx + PApplet.parseFloat(pathTokens[(i + 3)]);
/*  741 */         float endY = cy + PApplet.parseFloat(pathTokens[(i + 4)]);
/*      */         
/*  743 */         parsePathQuadto(ctrlX, ctrlY, endX, endY);
/*  744 */         cx = endX;
/*  745 */         cy = endY;
/*  746 */         i += 5;
/*  747 */         prevCurve = true;
/*      */         
/*  749 */         break;
/*      */       case 'T': 
/*      */         float ctrlY;
/*      */         
/*      */ 
/*      */         float ctrlX;
/*      */         
/*      */         float ctrlY;
/*      */         
/*  758 */         if (!prevCurve) {
/*  759 */           float ctrlX = cx;
/*  760 */           ctrlY = cy;
/*      */         } else {
/*  762 */           float ppx = this.vertices[(this.vertexCount - 2)][0];
/*  763 */           float ppy = this.vertices[(this.vertexCount - 2)][1];
/*  764 */           float px = this.vertices[(this.vertexCount - 1)][0];
/*  765 */           float py = this.vertices[(this.vertexCount - 1)][1];
/*  766 */           ctrlX = px + (px - ppx);
/*  767 */           ctrlY = py + (py - ppy);
/*      */         }
/*  769 */         float endX = PApplet.parseFloat(pathTokens[(i + 1)]);
/*  770 */         float endY = PApplet.parseFloat(pathTokens[(i + 2)]);
/*      */         
/*  772 */         parsePathQuadto(ctrlX, ctrlY, endX, endY);
/*  773 */         cx = endX;
/*  774 */         cy = endY;
/*  775 */         i += 3;
/*  776 */         prevCurve = true;
/*      */         
/*  778 */         break;
/*      */       case 't':  float ctrlY;
/*      */         float ctrlX;
/*      */         float ctrlY;
/*  782 */         if (!prevCurve) {
/*  783 */           float ctrlX = cx;
/*  784 */           ctrlY = cy;
/*      */         } else {
/*  786 */           float ppx = this.vertices[(this.vertexCount - 2)][0];
/*  787 */           float ppy = this.vertices[(this.vertexCount - 2)][1];
/*  788 */           float px = this.vertices[(this.vertexCount - 1)][0];
/*  789 */           float py = this.vertices[(this.vertexCount - 1)][1];
/*  790 */           ctrlX = px + (px - ppx);
/*  791 */           ctrlY = py + (py - ppy);
/*      */         }
/*  793 */         float endX = cx + PApplet.parseFloat(pathTokens[(i + 1)]);
/*  794 */         float endY = cy + PApplet.parseFloat(pathTokens[(i + 2)]);
/*      */         
/*  796 */         parsePathQuadto(ctrlX, ctrlY, endX, endY);
/*  797 */         cx = endX;
/*  798 */         cy = endY;
/*  799 */         i += 3;
/*  800 */         prevCurve = true;
/*      */         
/*  802 */         break;
/*      */       
/*      */       case 'Z': 
/*      */       case 'z': 
/*  806 */         this.close = true;
/*  807 */         i++;
/*  808 */         break;
/*      */       
/*      */       default: 
/*  811 */         String parsed = 
/*  812 */           PApplet.join(PApplet.subset(pathTokens, 0, i), ",");
/*  813 */         String unparsed = 
/*  814 */           PApplet.join(PApplet.subset(pathTokens, i), ",");
/*  815 */         System.err.println("parsed: " + parsed);
/*  816 */         System.err.println("unparsed: " + unparsed);
/*  817 */         if ((pathTokens[i].equals("a")) || (pathTokens[i].equals("A"))) {
/*  818 */           String msg = "Sorry, elliptical arc support for SVG files is not yet implemented (See issue #130 for updates)";
/*      */           
/*  820 */           throw new RuntimeException(msg);
/*      */         }
/*  822 */         throw new RuntimeException("shape command not handled: " + pathTokens[i]);
/*      */       }
/*      */       
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
/*      */   private void parsePathVertex(float x, float y)
/*      */   {
/*  839 */     if (this.vertexCount == this.vertices.length)
/*      */     {
/*  841 */       float[][] temp = new float[this.vertexCount << 1][2];
/*  842 */       System.arraycopy(this.vertices, 0, temp, 0, this.vertexCount);
/*  843 */       this.vertices = temp;
/*      */     }
/*  845 */     this.vertices[this.vertexCount][0] = x;
/*  846 */     this.vertices[this.vertexCount][1] = y;
/*  847 */     this.vertexCount += 1;
/*      */   }
/*      */   
/*      */   private void parsePathCode(int what)
/*      */   {
/*  852 */     if (this.vertexCodeCount == this.vertexCodes.length) {
/*  853 */       this.vertexCodes = PApplet.expand(this.vertexCodes);
/*      */     }
/*  855 */     this.vertexCodes[(this.vertexCodeCount++)] = what;
/*      */   }
/*      */   
/*      */   private void parsePathMoveto(float px, float py)
/*      */   {
/*  860 */     if (this.vertexCount > 0) {
/*  861 */       parsePathCode(4);
/*      */     }
/*  863 */     parsePathCode(0);
/*  864 */     parsePathVertex(px, py);
/*      */   }
/*      */   
/*      */   private void parsePathLineto(float px, float py)
/*      */   {
/*  869 */     parsePathCode(0);
/*  870 */     parsePathVertex(px, py);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void parsePathCurveto(float x1, float y1, float x2, float y2, float x3, float y3)
/*      */   {
/*  877 */     parsePathCode(1);
/*  878 */     parsePathVertex(x1, y1);
/*  879 */     parsePathVertex(x2, y2);
/*  880 */     parsePathVertex(x3, y3);
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
/*      */   private void parsePathQuadto(float cx, float cy, float x2, float y2)
/*      */   {
/*  900 */     parsePathCode(2);
/*      */     
/*  902 */     parsePathVertex(cx, cy);
/*  903 */     parsePathVertex(x2, y2);
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
/*      */   protected static PMatrix2D parseTransform(String matrixStr)
/*      */   {
/*  917 */     matrixStr = matrixStr.trim();
/*  918 */     PMatrix2D outgoing = null;
/*  919 */     int start = 0;
/*  920 */     int stop = -1;
/*  921 */     while ((stop = matrixStr.indexOf(')', start)) != -1) {
/*  922 */       PMatrix2D m = parseSingleTransform(matrixStr.substring(start, stop + 1));
/*  923 */       if (outgoing == null) {
/*  924 */         outgoing = m;
/*      */       } else {
/*  926 */         outgoing.apply(m);
/*      */       }
/*  928 */       start = stop + 1;
/*      */     }
/*  930 */     return outgoing;
/*      */   }
/*      */   
/*      */ 
/*      */   protected static PMatrix2D parseSingleTransform(String matrixStr)
/*      */   {
/*  936 */     String[] pieces = PApplet.match(matrixStr, "[,\\s]*(\\w+)\\((.*)\\)");
/*  937 */     if (pieces == null) {
/*  938 */       System.err.println("Could not parse transform " + matrixStr);
/*  939 */       return null;
/*      */     }
/*  941 */     float[] m = PApplet.parseFloat(PApplet.splitTokens(pieces[2], ", "));
/*  942 */     if (pieces[1].equals("matrix")) {
/*  943 */       return new PMatrix2D(m[0], m[2], m[4], m[1], m[3], m[5]);
/*      */     }
/*  945 */     if (pieces[1].equals("translate")) {
/*  946 */       float tx = m[0];
/*  947 */       float ty = m.length == 2 ? m[1] : m[0];
/*      */       
/*  949 */       return new PMatrix2D(1.0F, 0.0F, tx, 0.0F, 1.0F, ty);
/*      */     }
/*  951 */     if (pieces[1].equals("scale")) {
/*  952 */       float sx = m[0];
/*  953 */       float sy = m.length == 2 ? m[1] : m[0];
/*      */       
/*  955 */       return new PMatrix2D(sx, 0.0F, 0.0F, 0.0F, sy, 0.0F);
/*      */     }
/*  957 */     if (pieces[1].equals("rotate")) {
/*  958 */       float angle = m[0];
/*      */       
/*  960 */       if (m.length == 1) {
/*  961 */         float c = PApplet.cos(angle);
/*  962 */         float s = PApplet.sin(angle);
/*      */         
/*  964 */         return new PMatrix2D(c, -s, 0.0F, s, c, 0.0F);
/*      */       }
/*  966 */       if (m.length == 3) {
/*  967 */         PMatrix2D mat = new PMatrix2D(0.0F, 1.0F, m[1], 1.0F, 0.0F, m[2]);
/*  968 */         mat.rotate(m[0]);
/*  969 */         mat.translate(-m[1], -m[2]);
/*  970 */         return mat;
/*      */       }
/*      */     } else {
/*  973 */       if (pieces[1].equals("skewX")) {
/*  974 */         return new PMatrix2D(1.0F, 0.0F, 1.0F, PApplet.tan(m[0]), 0.0F, 0.0F);
/*      */       }
/*  976 */       if (pieces[1].equals("skewY"))
/*  977 */         return new PMatrix2D(1.0F, 0.0F, 1.0F, 0.0F, PApplet.tan(m[0]), 0.0F);
/*      */     }
/*  979 */     return null;
/*      */   }
/*      */   
/*      */   protected void parseColors(XMLElement properties)
/*      */   {
/*  984 */     if (properties.hasAttribute("opacity")) {
/*  985 */       String opacityText = properties.getString("opacity");
/*  986 */       setOpacity(opacityText);
/*      */     }
/*      */     
/*  989 */     if (properties.hasAttribute("stroke")) {
/*  990 */       String strokeText = properties.getString("stroke");
/*  991 */       setColor(strokeText, false);
/*      */     }
/*      */     
/*  994 */     if (properties.hasAttribute("stroke-opacity")) {
/*  995 */       String strokeOpacityText = properties.getString("stroke-opacity");
/*  996 */       setStrokeOpacity(strokeOpacityText);
/*      */     }
/*      */     
/*  999 */     if (properties.hasAttribute("stroke-width"))
/*      */     {
/* 1001 */       String lineweight = properties.getString("stroke-width");
/* 1002 */       setStrokeWeight(lineweight);
/*      */     }
/*      */     
/* 1005 */     if (properties.hasAttribute("stroke-linejoin")) {
/* 1006 */       String linejoin = properties.getString("stroke-linejoin");
/* 1007 */       setStrokeJoin(linejoin);
/*      */     }
/*      */     
/* 1010 */     if (properties.hasAttribute("stroke-linecap")) {
/* 1011 */       String linecap = properties.getString("stroke-linecap");
/* 1012 */       setStrokeCap(linecap);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1017 */     if (properties.hasAttribute("fill")) {
/* 1018 */       String fillText = properties.getString("fill");
/* 1019 */       setColor(fillText, true);
/*      */     }
/*      */     
/* 1022 */     if (properties.hasAttribute("fill-opacity")) {
/* 1023 */       String fillOpacityText = properties.getString("fill-opacity");
/* 1024 */       setFillOpacity(fillOpacityText);
/*      */     }
/*      */     
/* 1027 */     if (properties.hasAttribute("style")) {
/* 1028 */       String styleText = properties.getString("style");
/* 1029 */       String[] styleTokens = PApplet.splitTokens(styleText, ";");
/*      */       
/*      */ 
/* 1032 */       for (int i = 0; i < styleTokens.length; i++) {
/* 1033 */         String[] tokens = PApplet.splitTokens(styleTokens[i], ":");
/*      */         
/*      */ 
/* 1036 */         tokens[0] = PApplet.trim(tokens[0]);
/*      */         
/* 1038 */         if (tokens[0].equals("fill")) {
/* 1039 */           setColor(tokens[1], true);
/*      */         }
/* 1041 */         else if (tokens[0].equals("fill-opacity")) {
/* 1042 */           setFillOpacity(tokens[1]);
/*      */         }
/* 1044 */         else if (tokens[0].equals("stroke")) {
/* 1045 */           setColor(tokens[1], false);
/*      */         }
/* 1047 */         else if (tokens[0].equals("stroke-width")) {
/* 1048 */           setStrokeWeight(tokens[1]);
/*      */         }
/* 1050 */         else if (tokens[0].equals("stroke-linecap")) {
/* 1051 */           setStrokeCap(tokens[1]);
/*      */         }
/* 1053 */         else if (tokens[0].equals("stroke-linejoin")) {
/* 1054 */           setStrokeJoin(tokens[1]);
/*      */         }
/* 1056 */         else if (tokens[0].equals("stroke-opacity")) {
/* 1057 */           setStrokeOpacity(tokens[1]);
/*      */         }
/* 1059 */         else if (tokens[0].equals("opacity")) {
/* 1060 */           setOpacity(tokens[1]);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void setOpacity(String opacityText)
/*      */   {
/* 1071 */     this.opacity = PApplet.parseFloat(opacityText);
/* 1072 */     this.strokeColor = ((int)(this.opacity * 255.0F) << 24 | this.strokeColor & 0xFFFFFF);
/* 1073 */     this.fillColor = ((int)(this.opacity * 255.0F) << 24 | this.fillColor & 0xFFFFFF);
/*      */   }
/*      */   
/*      */   void setStrokeWeight(String lineweight)
/*      */   {
/* 1078 */     this.strokeWeight = parseUnitSize(lineweight);
/*      */   }
/*      */   
/*      */   void setStrokeOpacity(String opacityText)
/*      */   {
/* 1083 */     this.strokeOpacity = PApplet.parseFloat(opacityText);
/* 1084 */     this.strokeColor = ((int)(this.strokeOpacity * 255.0F) << 24 | this.strokeColor & 0xFFFFFF);
/*      */   }
/*      */   
/*      */   void setStrokeJoin(String linejoin)
/*      */   {
/* 1089 */     if (!linejoin.equals("inherit"))
/*      */     {
/*      */ 
/* 1092 */       if (linejoin.equals("miter")) {
/* 1093 */         this.strokeJoin = 8;
/*      */       }
/* 1095 */       else if (linejoin.equals("round")) {
/* 1096 */         this.strokeJoin = 2;
/*      */       }
/* 1098 */       else if (linejoin.equals("bevel")) {
/* 1099 */         this.strokeJoin = 32;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   void setStrokeCap(String linecap) {
/* 1105 */     if (!linecap.equals("inherit"))
/*      */     {
/*      */ 
/* 1108 */       if (linecap.equals("butt")) {
/* 1109 */         this.strokeCap = 1;
/*      */       }
/* 1111 */       else if (linecap.equals("round")) {
/* 1112 */         this.strokeCap = 2;
/*      */       }
/* 1114 */       else if (linecap.equals("square")) {
/* 1115 */         this.strokeCap = 4;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   void setFillOpacity(String opacityText) {
/* 1121 */     this.fillOpacity = PApplet.parseFloat(opacityText);
/* 1122 */     this.fillColor = ((int)(this.fillOpacity * 255.0F) << 24 | this.fillColor & 0xFFFFFF);
/*      */   }
/*      */   
/*      */   void setColor(String colorText, boolean isFill)
/*      */   {
/* 1127 */     int opacityMask = this.fillColor & 0xFF000000;
/* 1128 */     boolean visible = true;
/* 1129 */     int color = 0;
/* 1130 */     String name = "";
/* 1131 */     Gradient gradient = null;
/* 1132 */     Paint paint = null;
/* 1133 */     if (colorText.equals("none")) {
/* 1134 */       visible = false;
/* 1135 */     } else if (colorText.equals("black")) {
/* 1136 */       color = opacityMask;
/* 1137 */     } else if (colorText.equals("white")) {
/* 1138 */       color = opacityMask | 0xFFFFFF;
/* 1139 */     } else if (colorText.startsWith("#")) {
/* 1140 */       if (colorText.length() == 4)
/*      */       {
/* 1142 */         colorText = colorText.replaceAll("^#(.)(.)(.)$", "#$1$1$2$2$3$3");
/*      */       }
/* 1144 */       color = opacityMask | 
/* 1145 */         Integer.parseInt(colorText.substring(1), 16) & 0xFFFFFF;
/*      */     }
/* 1147 */     else if (colorText.startsWith("rgb")) {
/* 1148 */       color = opacityMask | parseRGB(colorText);
/* 1149 */     } else if (colorText.startsWith("url(#")) {
/* 1150 */       name = colorText.substring(5, colorText.length() - 1);
/*      */       
/* 1152 */       Object object = findChild(name);
/*      */       
/* 1154 */       if ((object instanceof Gradient)) {
/* 1155 */         gradient = (Gradient)object;
/* 1156 */         paint = calcGradientPaint(gradient);
/*      */       }
/*      */       else
/*      */       {
/* 1160 */         System.err.println("url " + name + " refers to unexpected data: " + object);
/*      */       }
/*      */     }
/* 1163 */     if (isFill) {
/* 1164 */       this.fill = visible;
/* 1165 */       this.fillColor = color;
/* 1166 */       this.fillName = name;
/* 1167 */       this.fillGradient = gradient;
/* 1168 */       this.fillGradientPaint = paint;
/*      */     } else {
/* 1170 */       this.stroke = visible;
/* 1171 */       this.strokeColor = color;
/* 1172 */       this.strokeName = name;
/* 1173 */       this.strokeGradient = gradient;
/* 1174 */       this.strokeGradientPaint = paint;
/*      */     }
/*      */   }
/*      */   
/*      */   protected static int parseRGB(String what)
/*      */   {
/* 1180 */     int leftParen = what.indexOf('(') + 1;
/* 1181 */     int rightParen = what.indexOf(')');
/* 1182 */     String sub = what.substring(leftParen, rightParen);
/* 1183 */     int[] values = PApplet.parseInt(PApplet.splitTokens(sub, ", "));
/* 1184 */     return values[0] << 16 | values[1] << 8 | values[2];
/*      */   }
/*      */   
/*      */   protected static HashMap<String, String> parseStyleAttributes(String style)
/*      */   {
/* 1189 */     HashMap<String, String> table = new HashMap();
/* 1190 */     String[] pieces = style.split(";");
/* 1191 */     for (int i = 0; i < pieces.length; i++) {
/* 1192 */       String[] parts = pieces[i].split(":");
/* 1193 */       table.put(parts[0], parts[1]);
/*      */     }
/* 1195 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static float getFloatWithUnit(XMLElement element, String attribute)
/*      */   {
/* 1207 */     String val = element.getString(attribute);
/* 1208 */     return val == null ? 0.0F : parseUnitSize(val);
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
/*      */   protected static float parseUnitSize(String text)
/*      */   {
/* 1225 */     int len = text.length() - 2;
/*      */     
/* 1227 */     if (text.endsWith("pt"))
/* 1228 */       return PApplet.parseFloat(text.substring(0, len)) * 1.25F;
/* 1229 */     if (text.endsWith("pc"))
/* 1230 */       return PApplet.parseFloat(text.substring(0, len)) * 15.0F;
/* 1231 */     if (text.endsWith("mm"))
/* 1232 */       return PApplet.parseFloat(text.substring(0, len)) * 3.543307F;
/* 1233 */     if (text.endsWith("cm"))
/* 1234 */       return PApplet.parseFloat(text.substring(0, len)) * 35.43307F;
/* 1235 */     if (text.endsWith("in"))
/* 1236 */       return PApplet.parseFloat(text.substring(0, len)) * 90.0F;
/* 1237 */     if (text.endsWith("px")) {
/* 1238 */       return PApplet.parseFloat(text.substring(0, len));
/*      */     }
/* 1240 */     return PApplet.parseFloat(text);
/*      */   }
/*      */   
/*      */ 
/*      */   static class Gradient
/*      */     extends PShapeSVG
/*      */   {
/*      */     AffineTransform transform;
/*      */     
/*      */     float[] offset;
/*      */     
/*      */     int[] color;
/*      */     int count;
/*      */     
/*      */     public Gradient(PShapeSVG parent, XMLElement properties)
/*      */     {
/* 1256 */       super(properties, true);
/*      */       
/* 1258 */       XMLElement[] elements = properties.getChildren();
/* 1259 */       this.offset = new float[elements.length];
/* 1260 */       this.color = new int[elements.length];
/*      */       
/*      */ 
/* 1263 */       for (int i = 0; i < elements.length; i++) {
/* 1264 */         XMLElement elem = elements[i];
/* 1265 */         String name = elem.getName();
/* 1266 */         if (name.equals("stop")) {
/* 1267 */           String offsetAttr = elem.getString("offset");
/* 1268 */           float div = 1.0F;
/* 1269 */           if (offsetAttr.endsWith("%")) {
/* 1270 */             div = 100.0F;
/* 1271 */             offsetAttr = offsetAttr.substring(0, offsetAttr.length() - 1);
/*      */           }
/* 1273 */           this.offset[this.count] = (PApplet.parseFloat(offsetAttr) / div);
/* 1274 */           String style = elem.getString("style");
/* 1275 */           HashMap<String, String> styles = parseStyleAttributes(style);
/*      */           
/* 1277 */           String colorStr = (String)styles.get("stop-color");
/* 1278 */           if (colorStr == null) colorStr = "#000000";
/* 1279 */           String opacityStr = (String)styles.get("stop-opacity");
/* 1280 */           if (opacityStr == null) opacityStr = "1";
/* 1281 */           int tupacity = (int)(PApplet.parseFloat(opacityStr) * 255.0F);
/* 1282 */           this.color[this.count] = 
/* 1283 */             (tupacity << 24 | Integer.parseInt(colorStr.substring(1), 16));
/* 1284 */           this.count += 1;
/*      */         }
/*      */       }
/* 1287 */       this.offset = PApplet.subset(this.offset, 0, this.count);
/* 1288 */       this.color = PApplet.subset(this.color, 0, this.count);
/*      */     }
/*      */   }
/*      */   
/*      */   class LinearGradient extends PShapeSVG.Gradient { float x1;
/*      */     float y1;
/*      */     float x2;
/*      */     float y2;
/*      */     
/* 1297 */     public LinearGradient(PShapeSVG parent, XMLElement properties) { super(properties);
/*      */       
/* 1299 */       this.x1 = getFloatWithUnit(properties, "x1");
/* 1300 */       this.y1 = getFloatWithUnit(properties, "y1");
/* 1301 */       this.x2 = getFloatWithUnit(properties, "x2");
/* 1302 */       this.y2 = getFloatWithUnit(properties, "y2");
/*      */       
/* 1304 */       String transformStr = 
/* 1305 */         properties.getString("gradientTransform");
/*      */       
/* 1307 */       if (transformStr != null) {
/* 1308 */         float[] t = parseTransform(transformStr).get(null);
/* 1309 */         this.transform = new AffineTransform(t[0], t[3], t[1], t[4], t[2], t[5]);
/*      */         
/* 1311 */         Point2D t1 = this.transform.transform(new Point2D.Float(this.x1, this.y1), null);
/* 1312 */         Point2D t2 = this.transform.transform(new Point2D.Float(this.x2, this.y2), null);
/*      */         
/* 1314 */         this.x1 = ((float)t1.getX());
/* 1315 */         this.y1 = ((float)t1.getY());
/* 1316 */         this.x2 = ((float)t2.getX());
/* 1317 */         this.y2 = ((float)t2.getY());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   class RadialGradient extends PShapeSVG.Gradient {
/*      */     float cx;
/*      */     float cy;
/*      */     float r;
/*      */     
/* 1327 */     public RadialGradient(PShapeSVG parent, XMLElement properties) { super(properties);
/*      */       
/* 1329 */       this.cx = getFloatWithUnit(properties, "cx");
/* 1330 */       this.cy = getFloatWithUnit(properties, "cy");
/* 1331 */       this.r = getFloatWithUnit(properties, "r");
/*      */       
/* 1333 */       String transformStr = 
/* 1334 */         properties.getString("gradientTransform");
/*      */       
/* 1336 */       if (transformStr != null) {
/* 1337 */         float[] t = parseTransform(transformStr).get(null);
/* 1338 */         this.transform = new AffineTransform(t[0], t[3], t[1], t[4], t[2], t[5]);
/*      */         
/* 1340 */         Point2D t1 = this.transform.transform(new Point2D.Float(this.cx, this.cy), null);
/* 1341 */         Point2D t2 = this.transform.transform(new Point2D.Float(this.cx + this.r, this.cy), null);
/*      */         
/* 1343 */         this.cx = ((float)t1.getX());
/* 1344 */         this.cy = ((float)t1.getY());
/* 1345 */         this.r = ((float)(t2.getX() - t1.getX()));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   class LinearGradientPaint implements Paint
/*      */   {
/*      */     float x1;
/*      */     float y1;
/*      */     float x2;
/*      */     float y2;
/*      */     float[] offset;
/*      */     int[] color;
/*      */     int count;
/*      */     float opacity;
/*      */     
/*      */     public LinearGradientPaint(float x1, float y1, float x2, float y2, float[] offset, int[] color, int count, float opacity) {
/* 1362 */       this.x1 = x1;
/* 1363 */       this.y1 = y1;
/* 1364 */       this.x2 = x2;
/* 1365 */       this.y2 = y2;
/* 1366 */       this.offset = offset;
/* 1367 */       this.color = color;
/* 1368 */       this.count = count;
/* 1369 */       this.opacity = opacity;
/*      */     }
/*      */     
/*      */ 
/*      */     public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints)
/*      */     {
/* 1375 */       Point2D t1 = xform.transform(new Point2D.Float(this.x1, this.y1), null);
/* 1376 */       Point2D t2 = xform.transform(new Point2D.Float(this.x2, this.y2), null);
/* 1377 */       return new LinearGradientContext((float)t1.getX(), (float)t1.getY(), 
/* 1378 */         (float)t2.getX(), (float)t2.getY());
/*      */     }
/*      */     
/*      */     public int getTransparency() {
/* 1382 */       return 3;
/*      */     }
/*      */     
/*      */     public class LinearGradientContext implements PaintContext {
/* 1386 */       int ACCURACY = 2;
/*      */       float tx1;
/*      */       float ty1;
/*      */       
/* 1390 */       public LinearGradientContext(float tx1, float ty1, float tx2, float ty2) { this.tx1 = tx1;
/* 1391 */         this.ty1 = ty1;
/* 1392 */         this.tx2 = tx2;
/* 1393 */         this.ty2 = ty2;
/*      */       }
/*      */       
/*      */       public void dispose() {}
/*      */       
/* 1398 */       public ColorModel getColorModel() { return ColorModel.getRGBdefault(); }
/*      */       
/*      */       public Raster getRaster(int x, int y, int w, int h) {
/* 1401 */         WritableRaster raster = 
/* 1402 */           getColorModel().createCompatibleWritableRaster(w, h);
/*      */         
/* 1404 */         int[] data = new int[w * h * 4];
/*      */         
/*      */ 
/* 1407 */         float nx = this.tx2 - this.tx1;
/* 1408 */         float ny = this.ty2 - this.ty1;
/* 1409 */         float len = (float)Math.sqrt(nx * nx + ny * ny);
/* 1410 */         if (len != 0.0F) {
/* 1411 */           nx /= len;
/* 1412 */           ny /= len;
/*      */         }
/*      */         
/* 1415 */         int span = (int)PApplet.dist(this.tx1, this.ty1, this.tx2, this.ty2) * this.ACCURACY;
/* 1416 */         if (span <= 0)
/*      */         {
/*      */ 
/* 1419 */           int index = 0;
/* 1420 */           for (int j = 0; j < h; j++) {
/* 1421 */             for (int i = 0; i < w; i++) {
/* 1422 */               data[(index++)] = 0;
/* 1423 */               data[(index++)] = 0;
/* 1424 */               data[(index++)] = 0;
/* 1425 */               data[(index++)] = 255;
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/* 1430 */           int[][] interp = new int[span][4];
/* 1431 */           int prev = 0;
/* 1432 */           for (int i = 1; i < PShapeSVG.LinearGradientPaint.this.count; i++) {
/* 1433 */             int c0 = PShapeSVG.LinearGradientPaint.this.color[(i - 1)];
/* 1434 */             int c1 = PShapeSVG.LinearGradientPaint.this.color[i];
/* 1435 */             int last = (int)(PShapeSVG.LinearGradientPaint.this.offset[i] * (span - 1));
/*      */             
/* 1437 */             for (int j = prev; j <= last; j++) {
/* 1438 */               float btwn = PApplet.norm(j, prev, last);
/* 1439 */               interp[j][0] = ((int)PApplet.lerp(c0 >> 16 & 0xFF, c1 >> 16 & 0xFF, btwn));
/* 1440 */               interp[j][1] = ((int)PApplet.lerp(c0 >> 8 & 0xFF, c1 >> 8 & 0xFF, btwn));
/* 1441 */               interp[j][2] = ((int)PApplet.lerp(c0 & 0xFF, c1 & 0xFF, btwn));
/* 1442 */               interp[j][3] = ((int)(PApplet.lerp(c0 >> 24 & 0xFF, c1 >> 24 & 0xFF, btwn) * PShapeSVG.LinearGradientPaint.this.opacity));
/*      */             }
/*      */             
/* 1445 */             prev = last;
/*      */           }
/*      */           
/* 1448 */           int index = 0;
/* 1449 */           for (int j = 0; j < h; j++) {
/* 1450 */             for (int i = 0; i < w; i++)
/*      */             {
/*      */ 
/* 1453 */               float px = x + i - this.tx1;
/* 1454 */               float py = y + j - this.ty1;
/*      */               
/*      */ 
/* 1457 */               int which = (int)((px * nx + py * ny) * this.ACCURACY);
/* 1458 */               if (which < 0) which = 0;
/* 1459 */               if (which > interp.length - 1) { which = interp.length - 1;
/*      */               }
/*      */               
/* 1462 */               data[(index++)] = interp[which][0];
/* 1463 */               data[(index++)] = interp[which][1];
/* 1464 */               data[(index++)] = interp[which][2];
/* 1465 */               data[(index++)] = interp[which][3];
/*      */             }
/*      */           }
/*      */         }
/* 1469 */         raster.setPixels(0, 0, w, h, data);
/*      */         
/* 1471 */         return raster;
/*      */       }
/*      */       
/*      */       float tx2;
/*      */       float ty2;
/*      */     }
/*      */   }
/*      */   
/*      */   class RadialGradientPaint implements Paint { float cx;
/*      */     float cy;
/*      */     float radius;
/*      */     float[] offset;
/*      */     int[] color;
/*      */     int count;
/*      */     float opacity;
/*      */     
/* 1487 */     public RadialGradientPaint(float cx, float cy, float radius, float[] offset, int[] color, int count, float opacity) { this.cx = cx;
/* 1488 */       this.cy = cy;
/* 1489 */       this.radius = radius;
/* 1490 */       this.offset = offset;
/* 1491 */       this.color = color;
/* 1492 */       this.count = count;
/* 1493 */       this.opacity = opacity;
/*      */     }
/*      */     
/*      */ 
/*      */     public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints)
/*      */     {
/* 1499 */       return new RadialGradientContext();
/*      */     }
/*      */     
/*      */ 
/* 1503 */     public int getTransparency() { return 3; }
/*      */     
/*      */     public class RadialGradientContext implements PaintContext { public RadialGradientContext() {}
/*      */       
/* 1507 */       int ACCURACY = 5;
/*      */       
/*      */       public void dispose() {}
/*      */       
/* 1511 */       public ColorModel getColorModel() { return ColorModel.getRGBdefault(); }
/*      */       
/*      */       public Raster getRaster(int x, int y, int w, int h) {
/* 1514 */         WritableRaster raster = 
/* 1515 */           getColorModel().createCompatibleWritableRaster(w, h);
/*      */         
/* 1517 */         int span = (int)PShapeSVG.RadialGradientPaint.this.radius * this.ACCURACY;
/* 1518 */         int[][] interp = new int[span][4];
/* 1519 */         int prev = 0;
/* 1520 */         for (int i = 1; i < PShapeSVG.RadialGradientPaint.this.count; i++) {
/* 1521 */           int c0 = PShapeSVG.RadialGradientPaint.this.color[(i - 1)];
/* 1522 */           int c1 = PShapeSVG.RadialGradientPaint.this.color[i];
/* 1523 */           int last = (int)(PShapeSVG.RadialGradientPaint.this.offset[i] * (span - 1));
/* 1524 */           for (int j = prev; j <= last; j++) {
/* 1525 */             float btwn = PApplet.norm(j, prev, last);
/* 1526 */             interp[j][0] = ((int)PApplet.lerp(c0 >> 16 & 0xFF, c1 >> 16 & 0xFF, btwn));
/* 1527 */             interp[j][1] = ((int)PApplet.lerp(c0 >> 8 & 0xFF, c1 >> 8 & 0xFF, btwn));
/* 1528 */             interp[j][2] = ((int)PApplet.lerp(c0 & 0xFF, c1 & 0xFF, btwn));
/* 1529 */             interp[j][3] = ((int)(PApplet.lerp(c0 >> 24 & 0xFF, c1 >> 24 & 0xFF, btwn) * PShapeSVG.RadialGradientPaint.this.opacity));
/*      */           }
/* 1531 */           prev = last;
/*      */         }
/*      */         
/* 1534 */         int[] data = new int[w * h * 4];
/* 1535 */         int index = 0;
/* 1536 */         for (int j = 0; j < h; j++) {
/* 1537 */           for (int i = 0; i < w; i++) {
/* 1538 */             float distance = PApplet.dist(PShapeSVG.RadialGradientPaint.this.cx, PShapeSVG.RadialGradientPaint.this.cy, x + i, y + j);
/* 1539 */             int which = PApplet.min((int)(distance * this.ACCURACY), interp.length - 1);
/*      */             
/* 1541 */             data[(index++)] = interp[which][0];
/* 1542 */             data[(index++)] = interp[which][1];
/* 1543 */             data[(index++)] = interp[which][2];
/* 1544 */             data[(index++)] = interp[which][3];
/*      */           }
/*      */         }
/* 1547 */         raster.setPixels(0, 0, w, h, data);
/*      */         
/* 1549 */         return raster;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected Paint calcGradientPaint(Gradient gradient)
/*      */   {
/* 1556 */     if ((gradient instanceof LinearGradient)) {
/* 1557 */       LinearGradient grad = (LinearGradient)gradient;
/* 1558 */       return new LinearGradientPaint(grad.x1, grad.y1, grad.x2, grad.y2, 
/* 1559 */         grad.offset, grad.color, grad.count, 
/* 1560 */         this.opacity);
/*      */     }
/* 1562 */     if ((gradient instanceof RadialGradient)) {
/* 1563 */       RadialGradient grad = (RadialGradient)gradient;
/* 1564 */       return new RadialGradientPaint(grad.cx, grad.cy, grad.r, 
/* 1565 */         grad.offset, grad.color, grad.count, 
/* 1566 */         this.opacity);
/*      */     }
/* 1568 */     return null;
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
/*      */   protected void styles(PGraphics g)
/*      */   {
/* 1600 */     super.styles(g);
/*      */     
/* 1602 */     if ((g instanceof PGraphicsJava2D)) {
/* 1603 */       PGraphicsJava2D p2d = (PGraphicsJava2D)g;
/*      */       
/* 1605 */       if (this.strokeGradient != null) {
/* 1606 */         p2d.strokeGradient = true;
/* 1607 */         p2d.strokeGradientObject = this.strokeGradientPaint;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1612 */       if (this.fillGradient != null) {
/* 1613 */         p2d.fillGradient = true;
/* 1614 */         p2d.fillGradientObject = this.fillGradientPaint;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public class Font
/*      */     extends PShapeSVG
/*      */   {
/*      */     public PShapeSVG.FontFace face;
/*      */     
/*      */ 
/*      */     public HashMap<String, PShapeSVG.FontGlyph> namedGlyphs;
/*      */     
/*      */ 
/*      */     public HashMap<Character, PShapeSVG.FontGlyph> unicodeGlyphs;
/*      */     
/*      */ 
/*      */     public int glyphCount;
/*      */     
/*      */ 
/*      */     public PShapeSVG.FontGlyph[] glyphs;
/*      */     
/*      */ 
/*      */     public PShapeSVG.FontGlyph missingGlyph;
/*      */     
/*      */ 
/*      */     int horizAdvX;
/*      */     
/*      */ 
/*      */ 
/*      */     public Font(PShapeSVG parent, XMLElement properties)
/*      */     {
/* 1648 */       super(properties, false);
/*      */       
/*      */ 
/* 1651 */       XMLElement[] elements = properties.getChildren();
/*      */       
/* 1653 */       this.horizAdvX = properties.getInt("horiz-adv-x", 0);
/*      */       
/* 1655 */       this.namedGlyphs = new HashMap();
/* 1656 */       this.unicodeGlyphs = new HashMap();
/* 1657 */       this.glyphCount = 0;
/* 1658 */       this.glyphs = new PShapeSVG.FontGlyph[elements.length];
/*      */       
/* 1660 */       for (int i = 0; i < elements.length; i++) {
/* 1661 */         String name = elements[i].getName();
/* 1662 */         XMLElement elem = elements[i];
/* 1663 */         if (name.equals("glyph")) {
/* 1664 */           PShapeSVG.FontGlyph fg = new PShapeSVG.FontGlyph(this, this, elem, this);
/* 1665 */           if (fg.isLegit()) {
/* 1666 */             if (fg.name != null) {
/* 1667 */               this.namedGlyphs.put(fg.name, fg);
/*      */             }
/* 1669 */             if (fg.unicode != 0) {
/* 1670 */               this.unicodeGlyphs.put(new Character(fg.unicode), fg);
/*      */             }
/*      */           }
/* 1673 */           this.glyphs[(this.glyphCount++)] = fg;
/*      */         }
/* 1675 */         else if (name.equals("missing-glyph"))
/*      */         {
/* 1677 */           this.missingGlyph = new PShapeSVG.FontGlyph(this, this, elem, this);
/* 1678 */         } else if (name.equals("font-face")) {
/* 1679 */           this.face = new PShapeSVG.FontFace(this, this, elem);
/*      */         } else {
/* 1681 */           System.err.println("Ignoring " + name + " inside <font>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     protected void drawShape() {}
/*      */     
/*      */ 
/*      */ 
/*      */     public void drawString(PGraphics g, String str, float x, float y, float size)
/*      */     {
/* 1695 */       g.pushMatrix();
/* 1696 */       float s = size / this.face.unitsPerEm;
/*      */       
/*      */ 
/* 1699 */       g.translate(x, y);
/* 1700 */       g.scale(s, -s);
/* 1701 */       char[] c = str.toCharArray();
/* 1702 */       for (int i = 0; i < c.length; i++)
/*      */       {
/* 1704 */         PShapeSVG.FontGlyph fg = (PShapeSVG.FontGlyph)this.unicodeGlyphs.get(new Character(c[i]));
/* 1705 */         if (fg != null) {
/* 1706 */           fg.draw(g);
/*      */           
/* 1708 */           g.translate(fg.horizAdvX, 0.0F);
/*      */         } else {
/* 1710 */           System.err.println("'" + c[i] + "' not available.");
/*      */         }
/*      */       }
/* 1713 */       g.popMatrix();
/*      */     }
/*      */     
/*      */     public void drawChar(PGraphics g, char c, float x, float y, float size)
/*      */     {
/* 1718 */       g.pushMatrix();
/* 1719 */       float s = size / this.face.unitsPerEm;
/* 1720 */       g.translate(x, y);
/* 1721 */       g.scale(s, -s);
/* 1722 */       PShapeSVG.FontGlyph fg = (PShapeSVG.FontGlyph)this.unicodeGlyphs.get(new Character(c));
/* 1723 */       if (fg != null) g.shape(fg);
/* 1724 */       g.popMatrix();
/*      */     }
/*      */     
/*      */     public float textWidth(String str, float size)
/*      */     {
/* 1729 */       float w = 0.0F;
/* 1730 */       char[] c = str.toCharArray();
/* 1731 */       for (int i = 0; i < c.length; i++)
/*      */       {
/* 1733 */         PShapeSVG.FontGlyph fg = (PShapeSVG.FontGlyph)this.unicodeGlyphs.get(new Character(c[i]));
/* 1734 */         if (fg != null) {
/* 1735 */           w += fg.horizAdvX / this.face.unitsPerEm;
/*      */         }
/*      */       }
/* 1738 */       return w * size;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   class FontFace
/*      */     extends PShapeSVG
/*      */   {
/*      */     int horizOriginX;
/*      */     
/*      */     int horizOriginY;
/*      */     
/*      */     int vertOriginX;
/*      */     
/*      */     int vertOriginY;
/*      */     
/*      */     int vertAdvY;
/*      */     String fontFamily;
/*      */     int fontWeight;
/*      */     String fontStretch;
/*      */     int unitsPerEm;
/*      */     int[] panose1;
/*      */     int ascent;
/*      */     int descent;
/*      */     int[] bbox;
/*      */     int underlineThickness;
/*      */     int underlinePosition;
/*      */     
/*      */     public FontFace(PShapeSVG parent, XMLElement properties)
/*      */     {
/* 1768 */       super(properties, true);
/*      */       
/* 1770 */       this.unitsPerEm = properties.getInt("units-per-em", 1000);
/*      */     }
/*      */     
/*      */ 
/*      */     protected void drawShape() {}
/*      */   }
/*      */   
/*      */ 
/*      */   public class FontGlyph
/*      */     extends PShapeSVG
/*      */   {
/*      */     public String name;
/*      */     
/*      */     char unicode;
/*      */     
/*      */     int horizAdvX;
/*      */     
/*      */     public FontGlyph(PShapeSVG parent, XMLElement properties, PShapeSVG.Font font)
/*      */     {
/* 1789 */       super(properties, true);
/* 1790 */       super.parsePath();
/*      */       
/* 1792 */       this.name = properties.getString("glyph-name");
/* 1793 */       String u = properties.getString("unicode");
/* 1794 */       this.unicode = '\000';
/* 1795 */       if (u != null) {
/* 1796 */         if (u.length() == 1) {
/* 1797 */           this.unicode = u.charAt(0);
/*      */         }
/*      */         else {
/* 1800 */           System.err.println("unicode for " + this.name + 
/* 1801 */             " is more than one char: " + u);
/*      */         }
/*      */       }
/* 1804 */       if (properties.hasAttribute("horiz-adv-x")) {
/* 1805 */         this.horizAdvX = properties.getInt("horiz-adv-x");
/*      */       } else {
/* 1807 */         this.horizAdvX = font.horizAdvX;
/*      */       }
/*      */     }
/*      */     
/*      */     protected boolean isLegit()
/*      */     {
/* 1813 */       return this.vertexCount != 0;
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
/*      */   public PShape getChild(String name)
/*      */   {
/* 1833 */     PShape found = super.getChild(name);
/* 1834 */     if (found == null)
/*      */     {
/*      */ 
/* 1837 */       found = super.getChild(name.replace(' ', '_'));
/*      */     }
/*      */     
/* 1840 */     if (found != null)
/*      */     {
/*      */ 
/* 1843 */       found.width = this.width;
/* 1844 */       found.height = this.height;
/*      */     }
/* 1846 */     return found;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void print()
/*      */   {
/* 1854 */     PApplet.println(this.element.toString());
/*      */   }
/*      */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\core\PShapeSVG.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */