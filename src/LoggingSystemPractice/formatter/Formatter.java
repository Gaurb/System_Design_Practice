package LoggingSystemPractice.formatter;

import LoggingSystemPractice.LogMessage;

public interface Formatter {
    String format(LogMessage message);
}
