package TTLCachePractice;

import TTLCachePractice.cache.TTLCache;
import TTLCachePractice.cache.strategy.LRUEvictionStrategy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Client {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) {
        logSection("Initializing TTL Cache with LRU Eviction Strategy");
        TTLCache<String, String> cache = new TTLCache<>(
            new LRUEvictionStrategy <>(5),
            5  // capacity of 5
        );
        logMessage("Cache created with capacity: 5, TTL: 10000ms");

        logSection("Adding 5 entries to cache");
        addEntry(cache, "key1", "value1");
        addEntry(cache, "key2", "value2");
        addEntry(cache, "key3", "value3");
        addEntry(cache, "key4", "value4");
        addEntry(cache, "key5", "value5");

        logSection("Accessing key1 to mark it as recently used");
        retrieveEntry(cache, "key1");

        logSection("Adding key6 (should trigger LRU eviction of key2)");
        addEntry(cache, "key6", "value6");

        logSection("Verifying cache state after eviction");
        logMessage("Attempting to retrieve key2 (should be evicted)...");
        retrieveEntry(cache, "key2");

        logMessage("Attempting to retrieve key3 (should still exist)...");
        retrieveEntry(cache, "key3");

        logSection("Testing cache hit scenario");
        retrieveEntry(cache, "key1"); // key1 should still exist
        retrieveEntry(cache, "key5"); // key5 should still exist

        logSection("Demonstrating LRU behavior");
        retrieveEntry(cache, "key4"); // Access key4
        logMessage("Adding key7 to trigger eviction...");
        addEntry(cache, "key7", "value7"); // Should evict key3 (least recently used among remaining)

        logSection("Final cache state check");
        retrieveEntry(cache, "key3"); // Should be evicted
        retrieveEntry(cache, "key6"); // Should exist
    }

    private static void addEntry(TTLCache<String, String> cache, String key, String value) {
        logMessage("PUT Operation: key='" + key + "', value='" + value + "'");
        cache.put(key, value);
        logMessage("✓ Successfully added to cache");
    }

    private static void retrieveEntry(TTLCache<String, String> cache, String key) {
        logMessage("GET Operation: key='" + key + "'");
        var result = cache.get(key);
        if (result.isPresent()) {
            logMessage("✓ HIT - Retrieved value: '" + result.get() + "'");
        } else {
            logMessage("✗ MISS - Key not found or expired");
        }
    }

    private static void logSection(String section) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println(section);
        System.out.println("=".repeat(70));
    }

    private static void logMessage(String message) {
        String timestamp = LocalDateTime.now().format(dateFormatter);
        System.out.println("[" + timestamp + "] " + message);
    }
}
