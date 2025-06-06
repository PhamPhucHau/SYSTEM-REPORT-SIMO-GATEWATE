package SHINHAN_PORTAL.REPORT_SIMO;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class ReportSimoApplication {

	private static final Logger logger = LoggerFactory.getLogger(ReportSimoApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ReportSimoApplication.class, args);
		System.out.println("Hello World!");
		logger.debug("Debug log message");
		logger.info("Info log message");
		logger.error("Error log message");
		logger.warn("Warn log message");
		logger.trace("Trace log message");

	}

}
