package RateLimiterPractice.strategy;

import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowRateLimiter implements RateLimiter{
    private final int maxRequests;
    private final int windowSizeSeconds;
    private final ConcurrentHashMap<String,WindowData> request;

    public FixedWindowRateLimiter(int maxRequests, int windowSizeSeconds) {
        this.maxRequests = maxRequests;
        this.windowSizeSeconds = windowSizeSeconds;
        this.request = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized boolean allowRequest(String userId){
        long currentTime = System.currentTimeMillis()/1000;
        long windowStart = (currentTime/windowSizeSeconds)*windowSizeSeconds;

        request.putIfAbsent(userId, new WindowData(windowStart, 0));
        WindowData data = request.get(userId);

        if(data.windowStart != windowStart){
            data.windowStart = windowStart;
            data.requestCount = 0;
        }
        if(data.requestCount < maxRequests){
            data.requestCount++;
            return true;
        }
        return false;
    }

    private static class WindowData{
        long windowStart;
        int requestCount;

        public WindowData(long windowStart, int requestCount) {
            this.windowStart = windowStart;
            this.requestCount = requestCount;
        }
    }
}
