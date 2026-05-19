package LoggingSystemPractice.formatter;

public class ColoredFormatter implements Formatter{
    @Override
    public String format(LoggingSystemPractice.LogMessage message) {
        String colorCode = switch (message.level) {
            case DEBUG -> "\u001B[34m"; // Blue
            case INFO -> "\u001B[32m"; // Green
            case WARNING -> "\u001B[33m"; // Yellow
            case ERROR -> "\u001B[31m"; // Red
            default -> "\u001B[0m"; // Reset
        };
        return String.format("%s[%s] %s %s: %s\u001B[0m", colorCode, message.timestamp, message.level.name(),message.level.getLevel(), message.message);
    }
}
