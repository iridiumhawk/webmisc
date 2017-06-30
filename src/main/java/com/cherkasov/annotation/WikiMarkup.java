package com.cherkasov.annotation;

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

  public void get(){

    handler.toString();
  }

  //load template from disk

  //extract chart template

  //for each source do:
  //parse patterns from chart template

  public String parseChartTemplate(){


    StringBuilder result = new StringBuilder();
    //for each pattern #...# get annotation and run method
    result.append("");

  return result.toString();
  }

  @Template(value = "#source#")
  public String getSourceName(String stat){
    return "work - "+ stat;
//    return stat.getSource();
  }

}
