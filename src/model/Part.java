package model;

import javafx.beans.property.*;

public abstract class Part {

    private final IntegerProperty partID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty stockAmount;
    private final IntegerProperty min;
    private final IntegerProperty max;


    /*Constructor*/
    public Part() {
        partID = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        stockAmount = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }

    /*Validation of the part fields by checking text fields
     *
     *for valid data types and price amounts*/
    public static String isValidPart(String name, int min, int max, int inv, double price, String errorPrompt) {
        /*Checks if Inventory is less than 1*/
        if (inv < 1) {
            errorPrompt = errorPrompt + "Invalid Inventory amount";
        }
        /*Checks that the Part Name field isn't blank*/
        if (name == null) {
            errorPrompt = errorPrompt + "Invalid Part Name";
        }
        /*Checks that the Price is greater than 0*/
        if (price <= 0) {
            errorPrompt = errorPrompt + "Invalid Price";
        }
        /*Checks that Inventory amount is between Max and Min*/
        if (inv < min || inv > max) {
            errorPrompt = errorPrompt + "Invalid Inventory amount";
        }
        /*Checks that Max is greater than Min*/
        if (max < min) {
            errorPrompt = errorPrompt + "Max must be more than Min";
        }
        return errorPrompt;
    }

    /*Properties*/
    public IntegerProperty intPropPartID() {
        return partID;
    }

    public StringProperty stringPropName() {
        return name;
    }

    public DoubleProperty doublePropPrice() {
        return price;
    }

    public IntegerProperty intPropPartInv() {
        return stockAmount;
    }
    /*Currently unused but if we wish to lookup parts by min and max inventory
    *
    * we would use these.*/
    public IntegerProperty intPropPartMin() {
        return min;
    }

    public IntegerProperty intPropPartMax() {
        return max;
    }

    /*Setters and Getters*/

    public int getPartID() {
        return this.partID.get();
    }

    public void setPartID(int partID) {
        this.partID.set(partID);
    }

    public String getPartName() {
        return this.name.get();
    }

    public void setPartName(String name) {
        this.name.set(name);
    }

    public double getPartPrice() {
        return this.price.get();
    }

    public void setPartPrice(double price) {
        this.price.set(price);
    }

    public int getPartInStock() {
        return this.stockAmount.get();
    }

    public void setPartInStock(int stockAmount) {
        this.stockAmount.set(stockAmount);
    }

    public int getPartMin() {
        return this.min.get();
    }

    public void setPartMin(int min) {
        this.min.set(min);
    }

    public int getPartMax() {
        return this.max.get();
    }

    public void setPartMax(int max) {
        this.max.set(max);
    }

    /*Setting Product ID for Auto-Gen*/

    private static int partIDNumber = 0;

    /*Gets and increases partIDNumber*/

    public static int getPartIDNumberIncrease() {
        System.out.println("getPartIDNumberIncrease called");
        partIDNumber++;
        return partIDNumber;
    }
    public static int getPartIDNumberDecrease() {
        System.out.println("getPartIDNumberIncrease called");
        partIDNumber--;
        return partIDNumber;
    }
}
