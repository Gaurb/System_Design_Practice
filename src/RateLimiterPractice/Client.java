
package RateLimiterPractice;

import RateLimiterPractice.enums.UserTier;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/** * Multithreaded test client for the TieredRateLimiter. * * Adjust REQUESTS_PER_USER and THREADS_PER_USER to stress the limiter. */
public class Client {
    // per-user total attempts
    private static final int REQUESTS_PER_USER = 200;
    // number of worker threads issuing requests per user (concurrent)
    private static final int THREADS_PER_USER = 8;
    // time to wait for all tasks to finish (seconds)
    private static final int WAIT_SECONDS = 30;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting multi-threaded rate limiter test at " + Instant.now());

        TieredRateLimiter rateLimiter = new TieredRateLimiter();

        List<User> users = new ArrayList<>();
        users.add(new User( "Alice", UserTier.FREE));
        users.add(new User( "Bob", UserTier.BASIC));
        users.add(new User( "Carol", UserTier.PREMIUM));
        users.add(new User( "Dave", UserTier.ENTERPRISE));

        ExecutorService executor = Executors.newCachedThreadPool();

        // Per-user counters
        ConcurrentMap<String, AtomicInteger> allowed = new ConcurrentHashMap<>();
        ConcurrentMap<String, AtomicInteger> blocked = new ConcurrentHashMap<>();

        CountDownLatch startLatch = new CountDownLatch(1);
        List<Future<?>> futures = new ArrayList<>();

        for (User user : users) {
            allowed.put(user.getUserId(), new AtomicInteger(0));
            blocked.put(user.getUserId(), new AtomicInteger(0));

            // Create THREADS_PER_USER tasks per user
            for (int t = 0; t < THREADS_PER_USER; t++) {
                Future<?> f = executor.submit(() -> {
                    try {
                        // Wait for the coordinated start so all threads stress concurrently
                        startLatch.await();

                        for (int i = 0; i < REQUESTS_PER_USER / THREADS_PER_USER; i++) {
                            boolean ok = rateLimiter.allowRequest(user);
                            if (ok) {
                                allowed.get(user.getUserId()).incrementAndGet();
                                // sample log (avoid flooding)
                                if (i % 50 == 0) {
                                    System.out.printf("%s [%s] ALLOWED user=%s tier=%s%n",
                                            Instant.now(), Thread.currentThread().getName(), user.getUserId(), user.getUserTier());
                                }
                            } else {
                                blocked.get(user.getUserId()).incrementAndGet();
                                if (i % 50 == 0) {
                                    System.out.printf("%s [%s] BLOCKED  user=%s tier=%s%n",
                                            Instant.now(), Thread.currentThread().getName(), user.getUserId(), user.getUserTier());
                                }
                                // short backoff so requests are not busy-waiting and to let refill happen
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException ignored) {}
                            }

                            // small random jitter to spread requests slightly (remove if you want a heavy burst)
                            try {
                                Thread.sleep(ThreadLocalRandom.current().nextInt(0, 8));
                            } catch (InterruptedException ignored) {}
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                futures.add(f);
            }
        }

        // start all threads at once
        long testStart = System.currentTimeMillis();
        startLatch.countDown();

        // wait for completion
        executor.shutdown();
        boolean finished = executor.awaitTermination(WAIT_SECONDS, TimeUnit.SECONDS);
        if (!finished) {
            System.out.println("Tasks did not finish in time; attempting to cancel remaining tasks...");
            futures.forEach(f -> f.cancel(true));
            executor.shutdownNow();
        }

        long testEnd = System.currentTimeMillis();

        System.out.println("------ Test Summary ------");
        for (User user : users) {
            int a = allowed.get(user.getUserId()).get();
            int b = blocked.get(user.getUserId()).get();
            System.out.printf("User %s (tier=%s) -> allowed=%d, blocked=%d, total=%d%n",
                    user.getUserId(), user.getUserTier(), a, b, a + b);
        }
        System.out.printf("Duration: %d ms%n", (testEnd - testStart));
        System.out.println("Finished at " + Instant.now());
    }
}