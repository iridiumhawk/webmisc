package com.cherkasov.web.agregator.view;



import com.cherkasov.web.agregator.Controller;
import com.cherkasov.web.agregator.vo.Vacancy;

import java.util.List;

/**
 * Created by hawk on 11.07.2016.
 */
public interface View {
    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
    void userCitySelectEmulationMethod();
}
