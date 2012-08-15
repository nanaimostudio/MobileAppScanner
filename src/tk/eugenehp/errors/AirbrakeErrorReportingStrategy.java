package tk.eugenehp.errors;

	import code.lucamarrocco.hoptoad.HoptoadNotice;
	import code.lucamarrocco.hoptoad.HoptoadNoticeBuilder;
	import code.lucamarrocco.hoptoad.HoptoadNotifier;
 
 public class AirbrakeErrorReportingStrategy
   implements ErrorReportingStrategy
 {
   private String platform;
 
   public AirbrakeErrorReportingStrategy(String platform)
   {
	   this.platform = platform;
   }
 
   public void reportThrowable(Throwable throwable) {
	   HoptoadNotice notice = new HoptoadNoticeBuilder("c21127cf3735421a4397457a8959a745", throwable, this.platform).newNotice();
	   HoptoadNotifier notifier = new HoptoadNotifier();
	   notifier.notify(notice);
   }
 }