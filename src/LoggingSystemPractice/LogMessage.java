package LoggingSystemPractice;

public class LogMessage {
    public LogLevel level;
    public String message;
    public String timestamp;
    public String loggerName;
    public String threadName;


    public LogMessage(LogLevel level, String message, String timestamp, String loggerName) {
        this.level = level;
        this.message = message;
        this.timestamp = timestamp;
        this.loggerName = loggerName;
        this.threadName = Thread.currentThread().getName();
    }
}
