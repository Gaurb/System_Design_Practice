package LoggingSystemPractice.logger.chainOfResponsibility;

import LoggingSystemPractice.LogLevel;
import LoggingSystemPractice.LogMessage;
import LoggingSystemPractice.formatter.Formatter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class ChainHandler {
    public LogLevel logLevel;
    public Formatter formatter;
    public ChainHandler nextHandler;
    public Lock lock;

    public ChainHandler(LogLevel logLevel, Formatter formatter) {
        this.logLevel = logLevel;
        this.formatter = formatter;
        this.lock = new ReentrantLock();
    }

    public void setNextHandler(ChainHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void handle(LogLevel logLevel, LogMessage message) {
        lock.lock();
        try {
            if (this.logLevel == logLevel) {
                String formattedMessage = formatter.format(message);
                System.out.println(formattedMessage);
            } else if (nextHandler != null) {
                nextHandler.handle(logLevel, message);
            }
        } finally {
            lock.unlock();
        }
    }


    public abstract void log(String message);

}
