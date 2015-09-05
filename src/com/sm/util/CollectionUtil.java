package com.sm.util;

import java.util.Collection;

/**
 * User: mikola
 * Date: 26.07.15
 * Time: 18:42
 */
public class CollectionUtil {
    public static boolean containsAny(Collection searchIn, Collection searchFor) {
        for (Object f : searchFor) {
            if (searchIn.contains(f)) {
                return true;
            }
        }
        return false;
    }
}
