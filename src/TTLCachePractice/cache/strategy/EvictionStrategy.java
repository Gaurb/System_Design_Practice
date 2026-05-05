package TTLCachePractice.cache.strategy;

public interface EvictionStrategy<K> {
    void onkeyAccessed(K key);

    void onkeyAdded(K key);

    K getEvictionCandidate();

    void onkeyRemoved(K key);

    int size();
}
