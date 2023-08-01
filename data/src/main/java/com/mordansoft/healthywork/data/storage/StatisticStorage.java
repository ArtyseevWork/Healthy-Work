package com.mordansoft.healthywork.data.storage;

import com.mordansoft.healthywork.data.model.StatisticD;
import com.mordansoft.healthywork.domain.model.Statistic;

public interface StatisticStorage {
    public boolean   updateStatistic  (StatisticD statistic);
    public StatisticD getStatistic  ();
}
