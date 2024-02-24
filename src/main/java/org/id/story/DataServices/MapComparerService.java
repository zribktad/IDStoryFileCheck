package org.id.story.DataServices;

import java.util.Map;
import java.util.Objects;

public class MapComparerService<K,V> {
    public boolean compareMaps(Map<K, V> map1, Map<K, V> map2) {
        if (map1 == null || map2 == null) {
            return false;
        }

        for (Map.Entry<K, V> entry : map1.entrySet()) {
            K key = entry.getKey();
            V value1 = entry.getValue();
            V value2 = map2.get(key);

            if (value2 == null) {
                return false;
            }

            if (!Objects.equals(value1, value2)) {
                return false;
            }
        }

        return true;
    }
}
