package tk.eugenehp.errors;
/*    */ 
/*    */ public class ErrorReporterFactory
/*    */ {
/*    */   private String environment;
/*    */ 
/*    */   public ErrorReporterFactory(String environment)
/*    */   {
/*  8 */     if (environment == null) {
/*  9 */       this.environment = "Unknown";
/*    */     }
/*    */     else
/* 12 */       this.environment = environment;
/*    */   }
/*    */ 
/*    */   public ErrorReporter errorReporter()
/*    */   {
/* 17 */     if (this.environment.equals("development")) {
/* 18 */       return new ErrorReporter(new StandardErrorReportingStrategy());
/*    */     }
/*    */ 
/* 21 */     return new ErrorReporter(new AirbrakeErrorReportingStrategy(this.environment));
/*    */   }
/*    */ }

/* Location:           /Volumes/DATA/BACKUP/2012_downloads/ios-app-scanner-1.0.5 copy.jar/
 * Qualified Name:     appolicious.errors.ErrorReporterFactory
 * JD-Core Version:    0.6.0
 */