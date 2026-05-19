package RateLimiterPractice.strategy;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

public class LeakyBucketRateLimiter implements RateLimiter{
    private final int capacity;
    private final int leakRate;
    private final ConcurrentHashMap<String, BucketData> buckets;

    public LeakyBucketRateLimiter(int capacity, int leakRate) {
        this.capacity = capacity;
        this.leakRate = leakRate;
        this.buckets = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized boolean allowRequest(String userId) {
        long currentTime = System.currentTimeMillis();
        BucketData bucketData = buckets.computeIfAbsent(userId, k -> new BucketData(new ArrayDeque<>(), currentTime));

        // Leak the bucket
        long timeElapsed = currentTime - bucketData.lastLeak;
        long leakedTokens = (timeElapsed / 1000) * leakRate;
        while (leakedTokens > 0 && !bucketData.queue.isEmpty()) {
            bucketData.queue.pollFirst();
            leakedTokens--;
        }
        bucketData.lastLeak = currentTime;

        // Check if we can add a new request
        if (bucketData.queue.size() < capacity) {
            bucketData.queue.offerLast(currentTime);
            return true;
        } else {
            return false; // Bucket is full, reject the request
        }
    }

    private static class BucketData{
        Deque<Long> queue;
        long lastLeak;
        BucketData(Deque<Long> queue, long lastLeak){
            this.queue = queue;
            this.lastLeak = lastLeak;
        }
    }
}
