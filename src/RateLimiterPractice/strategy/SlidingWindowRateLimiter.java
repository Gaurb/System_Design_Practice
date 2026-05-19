package RateLimiterPractice.strategy;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimiter implements RateLimiter {
    private final int maxRequests;
    private final int windowSizeSeconds;
    private final ConcurrentHashMap<String, Deque<Long>> requestData;

    public SlidingWindowRateLimiter(int maxRequests, int windowSizeSeconds) {
        this.maxRequests = maxRequests;
        this.windowSizeSeconds = windowSizeSeconds;
        this.requestData = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized boolean allowRequest(String userId) {
        long currentTime = System.currentTimeMillis() ;
        long windowStart = currentTime - windowSizeSeconds*1000L;

        requestData.putIfAbsent(userId,new LinkedList <>());
        Deque<Long> timestamps = requestData.get(userId);

        while (!timestamps.isEmpty() && timestamps.peekFirst() < windowStart) {
            timestamps.pollFirst();
        }

        if (timestamps.size() < maxRequests) {
            timestamps.offerLast(currentTime);
            return true;
        }
        return false;
    }
}
