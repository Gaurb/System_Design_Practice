package TTLCachePractice.cache.strategy;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUEvictionStrategy<K> implements EvictionStrategy<K>{
    private final Map<K,Integer> frequencyMap;
    private final Map<Integer, LinkedHashSet<K>> frequencyBuckets ;
    private int minFrequency;

    public LFUEvictionStrategy(){
        this.frequencyMap = new HashMap<>();
        this.frequencyBuckets = new HashMap<>();
        this.minFrequency = 1;
    }

    @Override
    public synchronized void onkeyAccessed(K key) {
        if(!frequencyMap.containsKey(key)){
            return;
        }
        int freq = frequencyMap.get(key);
        frequencyMap.put(key, freq + 1);
        frequencyBuckets.get(freq).remove(key);
        if(freq == minFrequency && frequencyBuckets.get(freq).isEmpty()) {
            minFrequency++;
            frequencyBuckets.remove(freq);
        }
        frequencyBuckets.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>()).add(key);
    }

    @Override
    public synchronized void onkeyAdded(K key) {
        frequencyMap.put(key, 1);
        minFrequency = 1;
        frequencyBuckets.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
    }

    @Override
    public synchronized K getEvictionCandidate() {
        LinkedHashSet<K> keys = frequencyBuckets.get(minFrequency);
        if(keys == null || keys.isEmpty()){
            return null;
        }
        return keys.getFirst();
    }

    @Override
    public synchronized void onkeyRemoved(K key) {
        if(!frequencyMap.containsKey(key)){
            return;
        }
        int freq = frequencyMap.get(key);
        frequencyBuckets.get(freq).remove(key);
        if( frequencyBuckets.get(freq).isEmpty()) {
            frequencyBuckets.remove(freq);
        }
    }

    @Override
    public synchronized int size() {
        return frequencyMap.size();
    }


}
