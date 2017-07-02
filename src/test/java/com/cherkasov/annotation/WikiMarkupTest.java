package com.cherkasov.annotation;

import static org.junit.Assert.*;

/**
 * Created by Lu on 30.06.2017.
 */
public class WikiMarkupTest {
    @org.junit.Test
    public void parseChartTemplate() throws Exception {

        WikiMarkup markup = new WikiMarkup();
        System.out.println(markup.parseChartTemplate());

    }

}