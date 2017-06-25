package com.cherkasov.web.agregator.view;

import com.cherkasov.web.agregator.Controller;
import com.cherkasov.web.agregator.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by hawk on 11.07.2016.
 */
public class HtmlView implements View {
    Controller controller;

    private final String filePath = "./src/" + this.getClass().getPackage().getName().replaceAll("\\.", "/") + "/vacancies.html";


    @Override
    public void update(List<Vacancy> vacancies) {

        try {
            updateFile(getUpdatedFileContent(vacancies));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(vacancies.size());
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void userCitySelectEmulationMethod() {

        controller.onCitySelect("Yekaterinburg");
//        controller.onCitySelect("Odessa");
    }

    private String getUpdatedFileContent(List<Vacancy> listVac) {
        Document doc = null;

        try {
            doc = getDocument();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Some exception occurred");
        }

        Element element = doc.select("[class=\"vacancy template\"]").first();
        Element template = element.clone();

        template.removeClass("template");
        template.removeAttr("style");

        doc.select("[class=\"vacancy\"]").remove();

        for (Vacancy vacancy : listVac) {

            Element temmplateClone = template.clone();
            temmplateClone.select("[class=\"city\"]").html(vacancy.getCity());
            temmplateClone.select("[class=\"companyName\"]").html(vacancy.getCompanyName());
            temmplateClone.select("[class=\"salary\"]").html(vacancy.getSalary());
            temmplateClone.select("a[href]").html(vacancy.getTitle());
            temmplateClone.select("a").attr("href", vacancy.getUrl());

            doc.select("[class=\"vacancy template\"]").before(temmplateClone.outerHtml());
//int a = 1;
        }


        return doc.html();
    }

    private void updateFile(String htmlCode) {

        if (htmlCode == null || htmlCode.isEmpty()) return;

        File file = new File(filePath);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            fileOutputStream.write(htmlCode.getBytes());

            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }


    }

    protected Document getDocument() throws IOException {
        File input = new File(filePath);

        Document doc = Jsoup.parse(input, "UTF-8");

        return doc;
    }
}
