package com.cherkasov.json;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/* Конвертация из одного класса в другой используя JSON
НЕОБХОДИМО: подключенные библиотеки Jackson Core, Bind и Annotation версии 2.6.1

*/
public class JsonConvert {
    public static void main(String[] args) throws IOException {
        Second s = (Second) convertOneToAnother(new First(), Second.class);
        First f = (First) convertOneToAnother(new Second(), First.class);
/*        System.out.println(s.toString());
        System.out.println(f.toString());*/

    }

    public static Object convertOneToAnother(Object one, Class resultClassObject) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, one);
//        System.out.println(writer.toString());

      /*  String newClass = resultClassObject.getSimpleName().toLowerCase();

        String objJason = writer.toString();
        String[] arrStr = objJason.split(",");
        String objJasonResult = "";

        for (int i = 0; i < arrStr.length; i++) {

            if (arrStr[i].contains("className")) {
                String[] arrStrClass = arrStr[i].split(":");
                arrStrClass[1] = "\""+newClass+"\"";
                arrStr[i] = arrStrClass[0]+":"+arrStrClass[1];
            }
        }

        for (int i = 0; i < arrStr.length; i++) {
            if (i == arrStr.length-1) {
                objJasonResult+= arrStr[i];
            } else {objJasonResult+= arrStr[i]+",";}

        }*/

//        System.out.println(objJasonResult);

        String objJasonResult = writer.toString();

        objJasonResult = objJasonResult.replaceFirst(one.getClass().getSimpleName().toLowerCase(),resultClassObject.getSimpleName().toLowerCase());

        return mapper.readValue(objJasonResult, resultClassObject);

    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "className")
    @JsonSubTypes(@JsonSubTypes.Type(value = First.class, name = "first"))
    public static class First {
        public int i;
        public String name;

        @Override
        public String toString() {
            return "First{" +
                    "i=" + i +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "className")
    @JsonSubTypes(@JsonSubTypes.Type(value = Second.class, name = "second"))
    public static class Second {
        public int i;
        public String name;

        @Override
        public String toString() {
            return "Second{" +
                    "i=" + i +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
