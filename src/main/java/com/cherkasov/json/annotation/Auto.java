package java.com.cherkasov.json.annotation;


import com.fasterxml.jackson.annotation.JsonTypeInfo;

//@JsonAutoDetect
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "className")
//@JsonSubTypes(@JsonSubTypes.Type(value = Auto.class))
public abstract class Auto {
    protected String name;
    protected String owner;
    protected int age;
}