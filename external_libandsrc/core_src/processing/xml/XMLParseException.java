/*    */ package processing.xml;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLParseException
/*    */   extends XMLException
/*    */ {
/*    */   public XMLParseException(String msg)
/*    */   {
/* 51 */     super(msg);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public XMLParseException(String systemID, int lineNr, String msg)
/*    */   {
/* 67 */     super(systemID, lineNr, null, msg, true);
/*    */   }
/*    */ }


/* Location:              D:\accor\Projector_Source\Projector\application.windows\lib\core.jar!\processing\xml\XMLParseException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */