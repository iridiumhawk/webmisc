package com.cherkasov.annotation;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by cherkasov_as on 29.06.2017.
 */
public class WikiMarkup {

    String handler;
    String pageTemplate;
    String chartTemplate;

    public WikiMarkup(String handler) {
        this.handler = handler;
    }

    public WikiMarkup() {
    }

    public void get() {

        handler.toString();
    }

    //load template from disk

    //extract chart template

    //for each source do:
    //parse patterns from chart template

    public String parseChartTemplate() {
        Set<String> sources = new TreeSet<>();
        sources.add("EHD");
        sources.add("File");
        String chartPattern = "test #source# \n <br> #data(0)# \n <br>";

        StringBuilder result = new StringBuilder();
        for (String source : sources) {
            //handler.getBySource(source)
            result
                    .append(new OneSourceWiki(source, getBySource(source), chartPattern).parse())
                    .append("\n");
        }

        //for each pattern #...# get annotation and run method
        result.append("\n");

        return result.toString();
    }

    private List<String> getBySource(String source) {
        return Collections.nCopies(10, "test");
    }

    //  @Template(value = "#source#")
    public String getSourceName(String stat) {
        return "work - " + stat;
//    return stat.getSource();
    }


    class OneSourceWiki {
        String source;
        List<String> stats;
        String chartPattern;

        public OneSourceWiki(String source, List<String> stats, String chartPattern) {
            this.source = source;
            this.stats = stats;
            this.chartPattern = chartPattern;
        }

        @Template("source")
        public String getSource() {
            return source;
        }

        @Template("datebegin")
        public String getDateBegin() {
            return "10/10/2017";
        }

        @Template("date")
        public String produceTable(String... dates) {
            return statisticToWikiTable(dates);
        }

        public String statisticToWikiTable(String... dates) {

            StringBuilder result = new StringBuilder();

            if (dates.length == 0){
                result.append("0");

            } else {
                for (String date : dates) {

                    result.append("||").append(date);
                }
            }

            return result.toString();
        }

        private String[] getArgs(String line) {
            String result = line.replaceAll("#", "");
            String[] args = result.split(".*\\(.*\\)");

            //test
            return new String[]{"1", "2", "3"};
        }

        private String getAnnotationName(String name) {
//            return name.toLowerCase().substring(1);
            return "date";
        }

        public String parse() {
            Processor proc = new Processor(this);
            StringBuilder result = new StringBuilder();

            //        chartPattern;
            // for each #...#
            {
                String test = "#date(1,2,3)#";
                String[] args = getArgs(test);
                String annotationName = getAnnotationName(test);

                result.append(proc.runByAnnotation(annotationName,  args));
                result.append(proc.runByAnnotation("source"));
                result.append(proc.runByAnnotation("date"));
                result.append(proc.runByAnnotation("test"));
//                result.append(proc.runByAnnotation("date", Template.class, new String[] {"0"}));

            }
            //test
            return result.toString();
        }
    }
}
