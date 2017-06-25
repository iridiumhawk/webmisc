package com.cherkasov.xml.unmarshall;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hawk on 08.09.2016.
 */

@XmlType(name = "shop")
@XmlRootElement
public class Shop {
    @XmlElementWrapper(name = "goods", nillable = true)
    public List<String> names = new ArrayList<>();
    public Integer count;
    public Double profit;

    public List<String> getSecretData() {
        return secretData;
    }

    public void setSecretData(List<String> secretData) {
        this.secretData = secretData;
    }

    private List<String> secretData = new ArrayList<>();

    @Override
    public String toString() {
        return "Shop{" +
                "names=" + names +
                ", count=" + count +
                ", profit=" + profit +
                ", secretData=" + secretData +
                '}';
    }
}
