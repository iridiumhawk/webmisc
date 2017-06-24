package com.cherkasov.mvc.view;


import com.cherkasov.mvc.controller.Controller;
import com.cherkasov.mvc.model.ModelData;

/**
 * Created by hawk on 25.02.2017.
 */
public interface View {
    void refresh(ModelData modelData);
    void setController(Controller controller);
}
