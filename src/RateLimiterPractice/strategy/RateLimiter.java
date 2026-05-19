package RateLimiterPractice.strategy;

public interface RateLimiter {
    boolean allowRequest(String userId);
}
