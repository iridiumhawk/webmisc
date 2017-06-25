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
 * Created by hawk on 14.07.2016.
 */
public class MoikrugStrategy implements Strategy{
//    private static final String URL_FORMAT = "http://javarush.ru/testdata/big28data2.html";
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?q=java+%s&page=%d";
/*    https://moikrug.ru/vacancies?q=java+Dnepropetrovsk
https://moikrug.ru/vacancies?page=1&q=java+Dnepropetrovsk
    https://moikrug.ru/vacancies/560164256*/

    @Override
    public List<Vacancy> getVacancies(String searchString) {

        int currentPage = 1;
        List<Vacancy> vacancies = new ArrayList<>();
        Document doc = null;

        while (true) {


            try {
                doc = getDocument(searchString, currentPage);
            } catch (IOException e) {
            }



            Elements elementsVac = doc.getElementsByClass("job");
//            Elements elementsVac = doc.select("[class=\"job  \"]");

            if (!elementsVac.isEmpty()) {
                for (Element el : elementsVac) {

                    Vacancy nextVac = new Vacancy();

                    nextVac.setTitle(el.select("[class=\"title\"]").attr("title"));
                    nextVac.setSiteName("moikrug.ru");
//                    nextVac.setCity(el.select("[class=\"location\"]").select("a[href]").text());
                    Element cityEl = el.getElementsByClass("location").first();
                    nextVac.setCity(cityEl != null ? cityEl.text() : "");


                    nextVac.setCompanyName(el.select("[class=\"company_name\"]").select("a[href]").text());
                    nextVac.setUrl("https://moikrug.ru" + el.select("[class=\"inner\"]").select("a").attr("href"));

                    Element salary = el.select("[class=\"salary\"]").select("[title=\"Зарплата\"]").first();
                    nextVac.setSalary(salary != null ? salary.text() : "");


                    vacancies.add(nextVac);
                }



            } else break;


            currentPage++;
//            break;
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
