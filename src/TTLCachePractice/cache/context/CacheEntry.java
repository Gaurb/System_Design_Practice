package TTLCachePractice.cache.context;

public class CacheEntry<K, V> {
    private final K key;
    private final V value;
    private final long expirationTime;
    private long lastAccessTime;
    private int accessCount;


    public CacheEntry(K key, V value, long expirationTime) {
        this.key = key;
        this.value = value;
        this.expirationTime = expirationTime;
        this.lastAccessTime = System.currentTimeMillis();
        this.accessCount = 1;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void updateAccessTime() {
        this.lastAccessTime = System.currentTimeMillis();
    }

    public void incrementAccessCount() {
        this.accessCount++;
    }

    public boolean isExpired(long now) {
        return now >= expirationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CacheEntry<?, ?> that = (CacheEntry<?, ?>) o;

        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

}
