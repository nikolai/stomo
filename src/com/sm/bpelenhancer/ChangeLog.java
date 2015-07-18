package com.sm.bpelenhancer;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * User: mikola
 * Date: 25.01.15
 * Time: 19:55
 */
public class ChangeLog {
    private SortedMap<Date, ChangeLogRecord> changelog = new TreeMap<>();

    void addChangesComment(BPELEnhancer currentEnhancer, String comment) {
        changelog.put(new Date(), new ChangeLogRecord(currentEnhancer, comment));
    }

    @Override
    public String toString() {
        if (changelog.isEmpty()) {
            return "empty";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Date, ChangeLogRecord> record : changelog.entrySet()) {
            sb.append(record.getKey()).append(" - ").append(record.getValue().comment)
                    .append("(").append(record.getValue().enhancer).append(")");
        }
        return sb.toString();
    }

    private static class ChangeLogRecord {
        final String comment;
        final BPELEnhancer enhancer;

        public ChangeLogRecord(BPELEnhancer enhancer, String comment) {
            this.comment = comment;
            this.enhancer = enhancer;
        }
    }
}
