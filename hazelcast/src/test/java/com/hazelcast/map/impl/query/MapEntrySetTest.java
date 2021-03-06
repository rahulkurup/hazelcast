package com.hazelcast.map.impl.query;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.internal.serialization.SerializationService;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.TruePredicate;
import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.HazelcastTestSupport;
import com.hazelcast.test.annotation.ParallelTest;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(HazelcastParallelClassRunner.class)
@Category({QuickTest.class, ParallelTest.class})
public class MapEntrySetTest extends HazelcastTestSupport {

    private HazelcastInstance hz;
    private IMap<String, String> map;
    private SerializationService serializationService;

    @Before
    public void setup() {
        hz = createHazelcastInstance();
        map = hz.getMap(randomName());
        serializationService = getSerializationService(hz);
    }

    @Test(expected = NullPointerException.class)
    public void whenPredicateNull() {
        map.entrySet(null);
    }

    @Test
    public void whenMapEmpty() {
        Set<Map.Entry<String, String>> result = map.entrySet(TruePredicate.INSTANCE);
        assertTrue(result.isEmpty());
    }

    @Test
    public void whenSelecting_withoutPredicate() {
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");

        Set<Map.Entry<String, String>> result = map.entrySet();

        assertEquals(3, result.size());
        assertResultContains(result, "1", "a");
        assertResultContains(result, "2", "b");
        assertResultContains(result, "3", "c");
    }

    @Test
    public void whenSelectingAllEntries() {
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");

        Set<Map.Entry<String, String>> result = map.entrySet(TruePredicate.INSTANCE);

        assertEquals(3, result.size());
        assertResultContains(result, "1", "a");
        assertResultContains(result, "2", "b");
        assertResultContains(result, "3", "c");
    }

    private void assertResultContains(Set<Map.Entry<String, String>> result, String key, String value) {
        assertTrue(result.contains(new SimpleEntry(key, value)));
    }

    @Test
    public void whenSelectingSomeEntries() {
        map.put("1", "good1");
        map.put("2", "bad");
        map.put("3", "good2");

        Set<Map.Entry<String, String>> result = map.entrySet(new GoodPredicate());

        assertEquals(2, result.size());
        assertResultContains(result, "1", "good1");
        assertResultContains(result, "3", "good2");
    }

    @Test
    public void testResultType() {
        map.put("1", "a");
        Set<Map.Entry<String, String>> result = map.entrySet(TruePredicate.INSTANCE);

        QueryResultCollection collection = assertInstanceOf(QueryResultCollection.class, result);
        QueryResultRow row = (QueryResultRow)collection.getRows().iterator().next();
        assertEquals(serializationService.toData("1"), row.getKey());
        assertEquals(serializationService.toData("a"), row.getValue());
    }

    static class GoodPredicate implements Predicate<String, String> {
        @Override
        public boolean apply(Map.Entry<String, String> mapEntry) {
            return mapEntry.getValue().startsWith("good");
        }
    }
}
