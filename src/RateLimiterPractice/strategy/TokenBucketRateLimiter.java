package RateLimiterPractice.strategy;

import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRateLimiter implements RateLimiter{
    private final int capacity;
    private final double refillRate;
    private final ConcurrentHashMap<String,Bucket> buckets;

    public TokenBucketRateLimiter(int capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.buckets = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized boolean allowRequest(String userId) {
        long currentTimeSeconds = System.currentTimeMillis() / 1000;
        Bucket bucket = buckets.computeIfAbsent(userId,
                k -> new Bucket(capacity, currentTimeSeconds));

        // Refill token
        long timePassed = currentTimeSeconds - bucket.lastRefill;
        double tokensToAdd = timePassed * refillRate;
        bucket.tokens = Math.min(capacity, bucket.tokens + tokensToAdd);
        bucket.lastRefill = currentTimeSeconds;
        if (bucket.tokens >= 1) {
            bucket.tokens -= 1;
            return true;
        }
        return false;
    }

    public int getRemainingTokens(String userId) {
        Bucket bucket = buckets.get(userId);
        if (bucket == null) {
            return capacity;
        }
        return (int) bucket.tokens;
    }

    private static class Bucket {
       double tokens;
       long lastRefill;
         public Bucket(double bucket, long lastRefill) {
              this.tokens = bucket;
              this.lastRefill = lastRefill;
         }


    }
}
