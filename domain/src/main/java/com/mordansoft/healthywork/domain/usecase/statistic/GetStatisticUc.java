package com.mordansoft.healthywork.domain.usecase.statistic;

import com.mordansoft.healthywork.domain.model.Statistic;
import com.mordansoft.healthywork.domain.repository.StatisticRepository;

public class GetStatisticUc {
    StatisticRepository statisticRepository;

    public GetStatisticUc(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    public Statistic execute(){
        return statisticRepository.getStatistic();
    }
}
