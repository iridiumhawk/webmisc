package com.cherkasov.web.agregator;


import com.cherkasov.web.agregator.model.HHStrategy;
import com.cherkasov.web.agregator.model.Model;
import com.cherkasov.web.agregator.model.MoikrugStrategy;
import com.cherkasov.web.agregator.model.Provider;
import com.cherkasov.web.agregator.view.HtmlView;
import com.cherkasov.web.agregator.view.View;

/**
 * Created by hawk on 07.07.2016.
 */
public class Aggregator {
    public static void main(String[] args) {

        View view = new HtmlView();

        Provider hhProvider = new Provider(new HHStrategy());
        Provider mkProvider = new Provider(new MoikrugStrategy());


//        Model model = new Model(view, new Provider(new HHStrategy()));
        Model model = new Model(view, new Provider[] {hhProvider,mkProvider});

        Controller controller = new Controller(model);

        view.setController(controller);

        view.userCitySelectEmulationMethod();


/*        Provider prov = new Provider(new HHStrategy());

        Controller controller = new Controller(prov);

        controller.scan();*/

    }
}
