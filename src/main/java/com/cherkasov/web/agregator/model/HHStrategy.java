package com.cherkasov.web.agregator.model;

import com.cherkasov.web.agregator.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hawk on 08.07.2016.
 */
public class HHStrategy implements Strategy {
    //    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=%s+%s&page=%d";
        private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java+%s&page=%d";
//    private static final String URL_FORMAT = "http://javarush.ru/testdata/big28data.html";&only_with_salary=true


    @Override
    public List<Vacancy> getVacancies(String searchString) {

        int currentPage = 0;
        List<Vacancy> vacancies = new ArrayList<>();
        Document doc = null;

        while (true) {


            try {
                doc = getDocument(searchString, currentPage);
            } catch (IOException e) {
            }



            Elements elementsVac = doc.select("[data-qa=\"vacancy-serp__vacancy\"]");

            if (!elementsVac.isEmpty()) {
                for (Element el : elementsVac) {

                    Vacancy nextVac = new Vacancy();

                    nextVac.setTitle(el.select("[data-qa=\"vacancy-serp__vacancy-title\"]").text());
                    nextVac.setSiteName("hh.ru");
                    nextVac.setCity(el.select("[data-qa=\"vacancy-serp__vacancy-address\"]").text());
                    nextVac.setCompanyName(el.select("[data-qa=\"vacancy-serp__vacancy-employer\"]").text());
                    nextVac.setUrl(el.select("[data-qa=\"vacancy-serp__vacancy-title\"]").attr("href"));

                    Element salary = el.select("[data-qa=vacancy-serp__vacancy-compensation]").first();
                    nextVac.setSalary(salary != null ? salary.text() : "");





                    /*vacancy-serp__vacancy_hrbrand

                    title  vacancy-serp__vacancy-title
                    salary vacancy-serp__vacancy-compensation

                    data-qa="vacancy-serp__vacancy-compensation">
                      <meta itemprop="salaryCurrency" content="UAH">
                      <meta itemprop="baseSalary" content="12000">12&nbsp;000-17&nbsp;000 грн.

vacancy-serp__vacancy-date
vacancy-serp__vacancy_snippet_responsibility
vacancy-serp__vacancy_snippet_requirement

                    city  vacancy-serp__vacancy-address
                    siteName
                    url data-qa="vacancy-serp__vacancy-title" href="http://hh.ua/vacancy/10942746?query=java%20odessa"
                    setCompanyName vacancy-serp__vacancy-employer
*/
                    vacancies.add(nextVac);
                }



            } else break;


            currentPage++;
        }


        return vacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException {

        Document doc;
        String url;


        url = String.format(URL_FORMAT, searchString,  page);

        doc = Jsoup.connect(url)
                .userAgent("User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
                .referrer("http://javarush.ru/")
                .get();


        return doc;
    }

}
