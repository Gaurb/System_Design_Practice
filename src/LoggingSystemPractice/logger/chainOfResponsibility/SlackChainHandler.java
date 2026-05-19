package LoggingSystemPractice.logger.chainOfResponsibility;

import LoggingSystemPractice.LogLevel;
import LoggingSystemPractice.formatter.Formatter;

public class SlackChainHandler extends ChainHandler {
    String slackWebhookUrl;
    public SlackChainHandler(LogLevel logLevel, Formatter formatter, String slackWebhookUrl) {
        super(logLevel, formatter);
        this.slackWebhookUrl = slackWebhookUrl;
    }

    @Override
    public void log(String message) {
       lock.lock();
       try {
           System.out.println("SLACK POST " + slackWebhookUrl);;
       } finally {
           lock.unlock();
       }
    }
}
