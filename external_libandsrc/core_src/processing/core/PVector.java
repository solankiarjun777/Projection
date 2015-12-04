/*     */ package processing.core;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PVector
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6717872085945400694L;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   protected transient float[] array;
/*     */   
/*     */   public PVector() {}
/*     */   
/*     */   public PVector(float x, float y, float z)
/*     */   {
/*  79 */     this.x = x;
/*  80 */     this.y = y;
/*  81 */     this.z = z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PVector(float x, float y)
/*     */   {
/*  92 */     this.x = x;
/*  93 */     this.y = y;
/*  94 */     this.z = 0.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void set(float x, float y, float z)
/*     */   {
/* 106 */     this.x = x;
/* 107 */     this.y = y;
/* 108 */     this.z = z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void set(PVector v)
/*     */   {
/* 118 */     this.x = v.x;
/* 119 */     this.y = v.y;
/* 120 */     this.z = v.z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void set(float[] source)
/*     */   {
/* 129 */     if (source.length >= 2) {
/* 130 */       this.x = source[0];
/* 131 */       this.y = source[1];
/*     */     }
/* 133 */     if (source.length >= 3) {
/* 134 */       this.z = source[2];
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PVector get()
/*     */   {
/* 143 */     return new PVector(this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public float[] get(float[] target)
/*     */   {
/* 148 */     if (target == null) {
/* 149 */       return new float[] { this.x, this.y, this.z };
/*     */     }
/* 151 */     if (target.length >= 2) {
/* 152 */       target[0] = this.x;
/* 153 */       target[1] = this.y;
/*     */     }
/* 155 */     if (target.length >= 3) {
/* 156 */       target[2] = this.z;
/*     */     }
/* 158 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float mag()
/*     */   {
/* 167 */     return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void add(PVector v)
/*     */   {
/* 176 */     this.x += v.x;
/* 177 */     this.y += v.y;
/* 178 */     this.z += v.z;
/*     */   }
/*     */   
/*     */   public void add(float x, float y, float z)
/*     */   {
/* 183 */     this.x += x;
/* 184 */     this.y += y;
/* 185 */     this.z += z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector add(PVector v1, PVector v2)
/*     */   {
/* 196 */     return add(v1, v2, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector add(PVector v1, PVector v2, PVector target)
/*     */   {
/* 208 */     if (target == null) {
/* 209 */       target = new PVector(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
/*     */     } else {
/* 211 */       target.set(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
/*     */     }
/* 213 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void sub(PVector v)
/*     */   {
/* 222 */     this.x -= v.x;
/* 223 */     this.y -= v.y;
/* 224 */     this.z -= v.z;
/*     */   }
/*     */   
/*     */   public void sub(float x, float y, float z)
/*     */   {
/* 229 */     this.x -= x;
/* 230 */     this.y -= y;
/* 231 */     this.z -= z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector sub(PVector v1, PVector v2)
/*     */   {
/* 242 */     return sub(v1, v2, null);
/*     */   }
/*     */   
/*     */   public static PVector sub(PVector v1, PVector v2, PVector target)
/*     */   {
/* 247 */     if (target == null) {
/* 248 */       target = new PVector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
/*     */     } else {
/* 250 */       target.set(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
/*     */     }
/* 252 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mult(float n)
/*     */   {
/* 261 */     this.x *= n;
/* 262 */     this.y *= n;
/* 263 */     this.z *= n;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector mult(PVector v, float n)
/*     */   {
/* 274 */     return mult(v, n, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector mult(PVector v, float n, PVector target)
/*     */   {
/* 286 */     if (target == null) {
/* 287 */       target = new PVector(v.x * n, v.y * n, v.z * n);
/*     */     } else {
/* 289 */       target.set(v.x * n, v.y * n, v.z * n);
/*     */     }
/* 291 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mult(PVector v)
/*     */   {
/* 300 */     this.x *= v.x;
/* 301 */     this.y *= v.y;
/* 302 */     this.z *= v.z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector mult(PVector v1, PVector v2)
/*     */   {
/* 311 */     return mult(v1, v2, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector mult(PVector v1, PVector v2, PVector target)
/*     */   {
/* 323 */     if (target == null) {
/* 324 */       target = new PVector(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z);
/*     */     } else {
/* 326 */       target.set(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z);
/*     */     }
/* 328 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void div(float n)
/*     */   {
/* 337 */     this.x /= n;
/* 338 */     this.y /= n;
/* 339 */     this.z /= n;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector div(PVector v, float n)
/*     */   {
/* 350 */     return div(v, n, null);
/*     */   }
/*     */   
/*     */   public static PVector div(PVector v, float n, PVector target)
/*     */   {
/* 355 */     if (target == null) {
/* 356 */       target = new PVector(v.x / n, v.y / n, v.z / n);
/*     */     } else {
/* 358 */       target.set(v.x / n, v.y / n, v.z / n);
/*     */     }
/* 360 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void div(PVector v)
/*     */   {
/* 368 */     this.x /= v.x;
/* 369 */     this.y /= v.y;
/* 370 */     this.z /= v.z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector div(PVector v1, PVector v2)
/*     */   {
/* 379 */     return div(v1, v2, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PVector div(PVector v1, PVector v2, PVector target)
/*     */   {
/* 391 */     if (target == null) {
/* 392 */       target = new PVector(v1.x / v2.x, v1.y / v2.y, v1.z / v2.z);
/*     */     } else {
/* 394 */       target.set(v1.x / v2.x, v1.y / v2.y, v1.z / v2.z);
/*     */     }
/* 396 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float dist(PVector v)
/*     */   {
/* 406 */     float dx = this.x - v.x;
/* 407 */     float dy = this.y - v.y;
/* 408 */     float dz = this.z - v.z;
/* 409 */     return (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static float dist(PVector v1, PVector v2)
/*     */   {
/* 420 */     float dx = v1.x - v2.x;
/* 421 */     float dy = v1.y - v2.y;
/* 422 */     float dz = v1.z - v2.z;
/* 423 */     return (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float dot(PVector v)
/*     */   {
/* 432 */     return this.x * v.x + this.y * v.y + this.z * v.z;
/*     */   }
/*     */   
/*     */   public float dot(float x, float y, float z)
/*     */   {
/* 437 */     return this.x * x + this.y * y + this.z * z;
/*     */   }
/*     */   
/*     */   public static float dot(PVector v1, PVector v2)
/*     */   {
/* 442 */     return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PVector cross(PVector v)
/*     */   {
/* 450 */     return cross(v, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PVector cross(PVector v, PVector target)
/*     */   {
/* 459 */     float crossX = this.y * v.z - v.y * this.z;
/* 460 */     float crossY = this.z * v.x - v.z * this.x;
/* 461 */     float crossZ = this.x * v.y - v.x * this.y;
/*     */     
/* 463 */     if (target == null) {
/* 464 */       target = new PVector(crossX, crossY, crossZ);
/*     */     } else {
/* 466 */       target.set(crossX, crossY, crossZ);
/*     */     }
/* 468 */     return target;
/*     */   }
/*     */   
/*     */   public static PVector cross(PVector v1, PVector v2, PVector target)
/*     */   {
/* 473 */     float crossX = v1.y * v2.z - v2.y * v1.z;
/* 474 */     float crossY = v1.z * v2.x - v2.z * v1.x;
/* 475 */     float crossZ = v1.x * v2.y - v2.x * v1.y;
/*     */     
/* 477 */     if (target == null) {
/* 478 */       target = new PVector(crossX, crossY, crossZ);
/*     */     } else {
/* 480 */       target.set(crossX, crossY, crossZ);
/*     */     }
/* 482 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void normalize()
/*     */   {
/* 490 */     float m = mag();
/* 491 */     if ((m != 0.0F) && (m != 1.0F)) {
/* 492 */       div(m);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PVector normalize(PVector target)
/*     */   {
/* 503 */     if (target == null) {
/* 504 */       target = new PVector();
/*     */     }
/* 506 */     float m = mag();
/* 507 */     if (m > 0.0F) {
/* 508 */       target.set(this.x / m, this.y / m, this.z / m);
/*     */     } else {
/* 510 */       target.set(this.x, this.y, this.z);
/*     */     }
/* 512 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void limit(float max)
/*     */   {
/* 521 */     if (mag() > max) {
/* 522 */       normalize();
/* 523 */       mult(max);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void scaleTo(float len)
/*     */   {
/* 532 */     normalize();
/* 533 */     mult(len);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PVector scaleTo(PVector target, float len)
/*     */   {
/* 543 */     target = normalize(target);
/* 544 */     target.mult(len);
/* 545 */     return target;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float heading2D()
/*     */   {
/* 553 */     float angle = (float)Math.atan2(-this.y, this.x);
/* 554 */     return -1.0F * angle;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void rotate(float theta)
/*     */   {
/* 562 */     float xTemp = this.x;
/*     */     
/* 564 */     this.x = (this.x * PApplet.cos(theta) - this.y * PApplet.sin(theta));
/* 565 */     this.y = (xTemp * PApplet.sin(theta) + this.y * PApplet.cos(theta));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static float angleBetween(PVector v1, PVector v2)
/*     */   {
/* 575 */     double dot = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
/* 576 */     double v1mag = Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z);
/* 577 */     double v2mag = Math.sqrt(v2.x * v2.x + v2.y * v2.y + v2.z * v2.z);
/*     */     
/* 579 */     double amt = dot / (v1mag * v2mag);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 584 */     if (amt <= -1.0D)
/* 585 */       return 3.1415927F;
/* 586 */     if (amt >= 1.0D)
/*     */     {
/* 588 */       return 0.0F;
/*     */     }
/* 590 */     return (float)Math.acos(amt);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 595 */     return "[ " + this.x + ", " + this.y + ", " + this.z + " ]";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float[] array()
/*     */   {
/* 605 */     if (this.array == null) {
/* 606 */       this.array = new float[3];
/*     */     }
/* 608 */     this.array[0] = this.x;
/* 609 */     this.array[1] = this.y;
/* 610 */     this.array[2] = this.z;
/* 611 */     return this.array;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 616 */     if (!(obj instanceof PVector))
/* 617 */       return false;
/* 618 */     PVector p = (PVector)obj;
/* 619 */     return (this.x == p.x) && (this.y == p.y) && (this.z == p.z);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 624 */     int result = 1;
/* 625 */     result = 31 * result + Float.floatToIntBits(this.x);
/* 626 */     result = 31 * result + Float.floatToIntBits(this.y);
/* 627 */     result = 31 * result + Float.floatToIntBits(this.z);
/* 628 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\core\PVector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */