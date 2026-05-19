package LoggingSystemPractice.logger.chainOfResponsibility;

import LoggingSystemPractice.LogLevel;
import LoggingSystemPractice.LogMessage;
import LoggingSystemPractice.formatter.Formatter;

public class ConsoleChainHandler extends ChainHandler {
    String dbConnectionString;
    public ConsoleChainHandler(LogLevel logLevel, Formatter formatter,String dbConnectionString) {
        super(logLevel, formatter);
        this.dbConnectionString = dbConnectionString;
    }

    @Override
    public void log(String message) {
       lock.lock();
       try {
           System.out.println("DB INSERT " + dbConnectionString);;
       } finally {
           lock.unlock();
       }
    }
}
