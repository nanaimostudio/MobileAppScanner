package tk.eugenehp.errors;
/*   */ 
/*   */ public class StandardErrorReportingStrategy
/*   */   implements ErrorReportingStrategy
/*   */ {
/*   */   public void reportThrowable(Throwable throwable)
/*   */   {
/* 5 */     throwable.printStackTrace();
/*   */   }
/*   */ }

/* Location:           /Volumes/DATA/BACKUP/2012_downloads/ios-app-scanner-1.0.5 copy.jar/
 * Qualified Name:     appolicious.errors.StandardErrorReportingStrategy
 * JD-Core Version:    0.6.0
 */