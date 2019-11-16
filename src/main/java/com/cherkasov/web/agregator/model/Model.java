package com.cherkasov.web.agregator.model;


import com.cherkasov.web.agregator.view.View;
import com.cherkasov.web.agregator.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hawk on 11.07.2016.
 */
public class Model {
    View view;
    com.cherkasov.web.agregator.model.Provider[] providers;

    public Model(View view, Provider... providers) {
        if (view == null || providers == null || providers.length == 0) throw new IllegalArgumentException();

        this.view = view;

        this.providers = providers;
    }

    public void selectCity(String city){
        List<Vacancy> allVacancy = new ArrayList<>();

        for (Provider prov : providers) {
            try {
                for (Vacancy vacancy : prov.getJavaVacancies(city) ){

                    allVacancy.add(vacancy);

                }
            } catch (NullPointerException e) {
//                    e.printStackTrace();
            }

        }

        view.update(allVacancy);
    }
}
