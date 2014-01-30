package com.sm.model;

import com.sm.DistributionTable;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 19:22
 */
public interface ModelFactory {
    StoModel createStoModel();
    Sequence createSequence();
    Action createAction(DistributionTable distributionTable);
    Alternative createAlternative();
    Parallel createParallel();
}
