package com.mordansoft.healthywork.data.repository;

import com.mordansoft.healthywork.data.model.StatisticD;

import com.mordansoft.healthywork.data.storage.StatisticStorage;
import com.mordansoft.healthywork.domain.model.Statistic;
import com.mordansoft.healthywork.domain.repository.StatisticRepository;

public class StatisticRepositoryImpl implements StatisticRepository {

    private final StatisticStorage statisticStorage;

    public StatisticRepositoryImpl(StatisticStorage statisticStorage) {
        this.statisticStorage = statisticStorage;
    }

    @Override
    public boolean updateStatistic(Statistic statistic) {
        statisticStorage.updateStatistic(statisticToStatisticD(statistic));

        return false;
    }

    @Override
    public Statistic getStatistic() {
        return statisticDToStatistic(statisticStorage.getStatistic());
    }


    /*********** mappers *************/

    private Statistic statisticDToStatistic (StatisticD statisticD){
        Statistic statistic = new Statistic(statisticD.getCountOfExerciseDone(),
                                            statisticD.getCountOfExerciseSkipped());
        return statistic;
    }
    private StatisticD statisticToStatisticD (Statistic statistic){
        StatisticD statisticD = new StatisticD(statistic.getCountOfExerciseDone(),
                                               statistic.getCountOfExerciseSkipped());
        return statisticD;
    }

    /* ******* ! mappers *************/
}
