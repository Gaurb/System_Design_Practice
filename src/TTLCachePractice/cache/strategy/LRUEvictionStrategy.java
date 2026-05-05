package TTLCachePractice.cache.strategy;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LRUEvictionStrategy<K> implements EvictionStrategy<K> {

    private final LinkedHashMap<K, Boolean> map;
    private final int capacity;
    private final ReentrantLock lock;


    public LRUEvictionStrategy(int capacity) {
        this.capacity = capacity;
        this.map = new LinkedHashMap<>(capacity, 0.75f, true);
        this.lock = new ReentrantLock();
    }

    @Override
    public void onkeyAccessed(K key) {
        lock.lock();
        try {
            if (map.containsKey(key)) {
                map.get(key); // Access to update order
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void onkeyAdded(K key) {
        lock.lock();
        try {
            map.put(key, true);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public K getEvictionCandidate() {
        lock.lock();
        try {
            if (map.size() > capacity) {
                return map.entrySet().iterator().next().getKey();
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void onkeyRemoved(K key) {
        lock.lock();
        try {
            map.remove(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        lock.lock();
        try {
            return map.size();
        } finally {
            lock.unlock();
        }
    }
}
