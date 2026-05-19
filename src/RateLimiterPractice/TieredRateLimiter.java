package RateLimiterPractice;

import RateLimiterPractice.enums.UserTier;
import RateLimiterPractice.strategy.*;

import java.util.HashMap;
import java.util.Map;

public class TieredRateLimiter {

   private final Map<UserTier, RateLimiter> strategyMap;

    public TieredRateLimiter() {
          this.strategyMap = new HashMap<>();
            // Initialize rate limiters for each user tier
            strategyMap.put(UserTier.FREE, new FixedWindowRateLimiter(10,60)); // 10 requests per minute
            strategyMap.put(UserTier.BASIC, new SlidingWindowCounterRateLimiter(100,120)); // 100 requests
            strategyMap.put(UserTier.PREMIUM, new TokenBucketRateLimiter(10,10)); // 10 requests
            strategyMap.put(UserTier.ENTERPRISE, new LeakyBucketRateLimiter(10000,100)); // 10000 requests
     }

     public boolean allowRequest(User user) {
          RateLimiter rateLimiter = strategyMap.get(user.getUserTier());
          if (rateLimiter == null) {
                throw new IllegalArgumentException("No rate limiter defined for user tier: " + user.getUserTier());
          }
          return rateLimiter.allowRequest(user.getUserId());
     }


}
