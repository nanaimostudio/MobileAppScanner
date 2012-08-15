package tk.eugenehp.errors;
/*    */ 
/*    */ public class ErrorReporter
/*    */ {
/*    */   private ErrorReportingStrategy errorReportingStrategy;
/*    */ 
/*    */   public ErrorReporter(ErrorReportingStrategy errorReportingStrategy)
/*    */   {
/*  5 */     this.errorReportingStrategy = errorReportingStrategy;
/*    */   }
/*    */ 
/*    */   public void reportThrowable(Throwable throwable) {
/*  9 */     this.errorReportingStrategy.reportThrowable(throwable);
/*    */   }
/*    */ 
/*    */   public void reportMessage(String message) {
/*    */     try {
/* 14 */       throw new Exception(message);
/*    */     }
/*    */     catch (Throwable throwable) {
/* 17 */       reportThrowable(throwable);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Volumes/DATA/BACKUP/2012_downloads/ios-app-scanner-1.0.5 copy.jar/
 * Qualified Name:     appolicious.errors.ErrorReporter
 * JD-Core Version:    0.6.0
 */