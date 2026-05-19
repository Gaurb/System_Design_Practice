package LoggingSystemPractice.formatter;

import LoggingSystemPractice.LogMessage;

public class JsonFormatter implements Formatter {
    @Override
    public String format(LogMessage message) {
        return String.format("{\"timestamp\": \"%s\", \"level\": \"%s\", \"logger\": \"%s\", \"thread\": \"%s\", \"message\": \"%s\"}",
                message.timestamp, message.level.name(), message.loggerName, message.threadName, message.message);
    }
}
