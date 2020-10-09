package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class InHousePart extends Part {

    private final IntegerProperty partMachineID;

    /*Sets the partMachineID*/
    public InHousePart() {
        super();
        System.out.println("InHousePart called");
        partMachineID = new SimpleIntegerProperty();
    }
    /*Getter and Setter*/
    public int getPartMachineID() {
        System.out.println("getPartMachineID called");
        return this.partMachineID.get();
    }

    public void setPartMachineID(int partMachineID) {
        System.out.println("setPartMachineID called");
        this.partMachineID.set(partMachineID);
    }
}
