package com.cherkasov.web.agregator.model;


import com.cherkasov.web.agregator.vo.Vacancy;

import java.util.List;

/**
 * Created by hawk on 07.07.2016.
 */
public interface Strategy {
   List<Vacancy> getVacancies(String searchString);
}
