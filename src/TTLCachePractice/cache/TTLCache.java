package TTLCachePractice.cache;

import TTLCachePractice.cache.context.CacheEntry;
import TTLCachePractice.cache.strategy.EvictionStrategy;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TTLCache<K, V> {
    private final int capacity;
    private final long defaultTTL;
    private final Map<K, CacheEntry<K, V>> datastore;
    private final EvictionStrategy<K> evictionStrategy;
    private final PriorityQueue<CacheEntry<K, V>> ttlHeap;
    private final ScheduledExecutorService scheduler;
    private final ReadWriteLock lock;

    public TTLCache(EvictionStrategy<K> evictionStrategy) {
        this.evictionStrategy = evictionStrategy;
        this.datastore = new ConcurrentHashMap<>();
        this.capacity = 10;
        this.defaultTTL = 10000; // 1 minute
        this.ttlHeap = new PriorityQueue<>(Comparator.comparingLong(CacheEntry::getExpirationTime));

        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.scheduler.scheduleAtFixedRate(this::evictExpiredEntries, 1, 1, java.util.concurrent.TimeUnit.SECONDS);
        this.lock = new ReentrantReadWriteLock();
    }

    public TTLCache(EvictionStrategy<K> evictionStrategy, int capacity) {
        this.evictionStrategy = evictionStrategy;
        this.datastore = new ConcurrentHashMap<>();
        this.capacity = capacity;
        this.defaultTTL = 10000;
        this.ttlHeap = new PriorityQueue<>(Comparator.comparingLong(CacheEntry::getExpirationTime));

        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.scheduler.scheduleAtFixedRate(this::evictExpiredEntries, 1, 1, java.util.concurrent.TimeUnit.SECONDS);
        this.lock = new ReentrantReadWriteLock();
    }

    public void put(K key, V value) {
        put(key, value, defaultTTL);
    }

    public void put(K key, V value, long ttl) {
        lock.writeLock().lock();
        try {
            evictExpiredEntries();

            if (datastore.size() >= capacity && !datastore.containsKey(key)) {
                evictByStrategy(); // Evict based on strategy
            }
            long expirationTime = System.currentTimeMillis() + ttl;
            CacheEntry<K, V> entry = new CacheEntry<>(key, value, expirationTime);

            if (datastore.containsKey(key)) {
                CacheEntry<K, V> oldEntry = datastore.get(key);
                ttlHeap.remove(oldEntry);
                evictionStrategy.onkeyRemoved(key);
            }

            datastore.put(key, entry);
            ttlHeap.offer(entry);
            evictionStrategy.onkeyAdded(key);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Optional<V> get(K key) {
        lock.writeLock().lock();
        try {
            CacheEntry<K, V> entry = datastore.get(key);
            if (entry == null) {
                return Optional.empty();
            }

            if (entry.isExpired(System.currentTimeMillis())) {
                remove(key);
                return Optional.empty();
            }
            entry.updateAccessTime();
            entry.incrementAccessCount();
            evictionStrategy.onkeyAccessed(key);
            return Optional.of(entry.getValue());
        } finally {
            lock.writeLock().unlock();
        }
    }

    void evictExpiredEntries() {
        long now = System.currentTimeMillis();
        while (!ttlHeap.isEmpty() && ttlHeap.peek().isExpired(now)) {
            CacheEntry<K, V> expiredEntry = ttlHeap.poll();
            if (expiredEntry != null) {
                datastore.remove(expiredEntry.getKey());
                evictionStrategy.onkeyRemoved(expiredEntry.getKey());
            }
        }
    }

    void evictByStrategy() {
        K evictionCandidate = evictionStrategy.getEvictionCandidate();
        if (evictionCandidate != null) {
            remove(evictionCandidate);
        }
    }

    boolean remove(K key) {
        lock.writeLock().lock();
        try {
            CacheEntry<K, V> entry = datastore.remove(key);
            if (entry != null) {
                ttlHeap.remove(entry);
                evictionStrategy.onkeyRemoved(key);
                return true;
            }
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }


    // For testing and monitoring
    public void shutdown() {
        scheduler.shutdown();
    }

}

