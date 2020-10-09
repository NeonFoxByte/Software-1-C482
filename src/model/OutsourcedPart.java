package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//OutsourcedPart is derivative of Part, adds the machine ID to the instance.

public class OutsourcedPart extends Part {

    public final StringProperty partCompanyName;

    public OutsourcedPart() {
        super();
        System.out.println("OutsourcedPart called");
        partCompanyName = new SimpleStringProperty();
    }
    /*Getter and Setter*/
    public String getPartCompanyName() {
        return this.partCompanyName.get();
    }

    public void setPartCompanyName(String partCompanyName) {
        this.partCompanyName.set(partCompanyName);
    }
}

