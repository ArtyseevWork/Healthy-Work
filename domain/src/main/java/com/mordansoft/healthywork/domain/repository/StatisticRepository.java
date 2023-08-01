package com.mordansoft.healthywork.domain.repository;
import com.mordansoft.healthywork.domain.model.Statistic;

public interface StatisticRepository {

    public boolean   updateStatistic  (Statistic statistic);
    public Statistic   getStatistic  ();

}
