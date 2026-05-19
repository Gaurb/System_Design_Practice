package LoggingSystemPractice;

public enum LogLevel {
    DEBUG (1),
    INFO (2),
    WARNING (3),
    ERROR (4),
    CRITICAL (5);

    private final int level;

    LogLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean isGreaterOrEqual(LogLevel other) {
        return this.level >= other.level;
    }

    @Override
    public String toString() {
        return this.name() + "(" + level + ")";
    }
}
