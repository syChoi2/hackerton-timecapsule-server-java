package com.coronacapsule.api.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum MarbleColor {

	RED(0), YELLOW(1), GREEN(2), BLUE(3), PURPLE(4);
	
    final int ordinal;

    private MarbleColor(int ordinal) {
        this.ordinal = ordinal;
    }
    
    public int getOrdinal() {
    	return ordinal;
    }

    static Map<Integer, MarbleColor> lookup = null;

    public static MarbleColor lookup(int ordinal) {
        // Could just run through the array of values but I will us a Map.
        if (lookup == null) {
            // Late construction - not thread-safe.
            lookup = Arrays.stream(MarbleColor.values())
                    .collect(Collectors.toMap(s -> s.ordinal, s -> s));
        }
        return lookup.get(ordinal);
    }
    
}
