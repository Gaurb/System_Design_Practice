package RateLimiterPractice.strategy;

import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowCounterRateLimiter implements RateLimiter {
    private final int maxRequests;
    private final int windowSizeSeconds;
    private final ConcurrentHashMap<String,WindowCounterData> userRequestData;

    public SlidingWindowCounterRateLimiter(int maxRequests, int windowSizeSeconds) {
        this.maxRequests = maxRequests;
        this.windowSizeSeconds = windowSizeSeconds;
        this.userRequestData = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized boolean allowRequest(String userId) {
        long currentTimeSeconds = System.currentTimeMillis()/1000;
        long currentWindow = currentTimeSeconds/windowSizeSeconds;
        double windowProgress = (double) (currentTimeSeconds%windowSizeSeconds) / windowSizeSeconds;
        userRequestData.putIfAbsent(userId, new WindowCounterData(currentWindow, 0, 0));
        WindowCounterData data = userRequestData.get(userId);
        if(data.currentWindowStart < currentWindow) {
            data.previousWindowCount = (data.currentWindowCount == currentWindow - 1) ? data.currentWindowCount : 0;
            data.currentWindowCount = 0;
            data.currentWindowStart = currentWindow;
        }
        // Calculate the effective request count for the current window
        int effectiveCount = (int) (data.previousWindowCount * (1 - windowProgress) + data.currentWindowCount);
        if(effectiveCount < maxRequests) {
            data.currentWindowCount++;
            return true;
        }
        return false;
    }

    private static class WindowCounterData {
        long currentWindowStart;
        int currentWindowCount;
        int previousWindowCount;

        public WindowCounterData(long currentWindowStart, int currentWindowCount, int previousWindowCount) {
            this.currentWindowStart = currentWindowStart;
            this.currentWindowCount = currentWindowCount;
            this.previousWindowCount = previousWindowCount;
        }
    }
}
