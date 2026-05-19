package LoggingSystemPractice.formatter;

import LoggingSystemPractice.LogMessage;

public class SimpleFormatter implements Formatter {
    @Override
    public String format(LogMessage message) {
        return String.format("[%s] %s %s: %s", message.timestamp, message.level.name(),message.level.getLevel(), message.message);
    }
}
