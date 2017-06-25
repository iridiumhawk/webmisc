package com.cherkasov.web.agregator;


import com.cherkasov.web.agregator.model.Model;

/**
 * Created by hawk on 07.07.2016.
 */
public class Controller {
    private Model model;

    public Controller(Model model) {
        if (model == null) throw new IllegalArgumentException();
        this.model = model;
    }

    public void onCitySelect(String cityName) {
        model.selectCity(cityName);

    }
}
