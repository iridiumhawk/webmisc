package com.cherkasov.web.agregator.model;


import com.cherkasov.web.agregator.vo.Vacancy;

import java.util.List;

/**
 * Created by hawk on 07.07.2016.
 */
public class Provider {
    private Strategy strategy;

    public List<Vacancy> getJavaVacancies(String searchString) {

        return strategy.getVacancies(searchString);
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Provider(Strategy strategy) {
        this.strategy = strategy;
    }
}
