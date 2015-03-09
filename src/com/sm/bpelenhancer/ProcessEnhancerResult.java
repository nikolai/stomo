package com.sm.bpelenhancer;

/**
 * User: mikola
 * Date: 25.01.15
 * Time: 19:55
 */
public class ProcessEnhancerResult {
    private String recommendations;

    public boolean hasEnhancements() {
        return false;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void storeEnhancements(String parentDir, String newBpelFileName) {

    }
}
