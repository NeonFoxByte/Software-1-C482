package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.ValidationException;

public class Product {

    public static ObservableList<Part> parts = FXCollections.observableArrayList();
    private final IntegerProperty productID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty stockAmount;
    private final IntegerProperty min;
    private final IntegerProperty max;


    /*Constructor*/
    public Product() {
        productID = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        stockAmount = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }



    /*Checks if the Product input fields are
     *
     *valid before accepting the product into inventory*/
    public static String isProductValid(String name, int min, int max, int inv, double price, ObservableList<Part> parts, String errorPrompt){
        double totalPartCost = 0.00;
        for (Part part : parts) {
            totalPartCost = totalPartCost + part.getPartPrice();
        }
        /*Checks if the total price of parts is greater than Product cost*/
        if (totalPartCost > price) {
            errorPrompt = errorPrompt + "Price must be greater than all Part Costs";
        }
        /*Checks if there is something input in the product Name field*/
        if (name == null) {
            errorPrompt = errorPrompt + "Invalid Product Name";
        }
        /*Checks if the price is less than 0*/
        if (price <= 0) {
            errorPrompt = errorPrompt + "Invalid Price";
        }
        /*Checks if Inventory amount is less than 1*/
        if (inv < 1) {
            errorPrompt = errorPrompt + "Invalid Inventory Amount";
        }
        /*Checks that Max is greater than Min*/
        if (max < min) {
            errorPrompt = errorPrompt + "Max must be more than Min";
        }
        /*Checks that Inventory amount is between Min and Max*/
        if (inv < min || inv > max) {
            errorPrompt = errorPrompt + "Invalid Inventory";
        }
        return errorPrompt;
    }

    /*Properties*/
    public IntegerProperty productIDProp() {
        return productID;
    }

    public StringProperty productNameProp() {
        return name;
    }

    public DoubleProperty productPriceProp() {
        return price;
    }

    public IntegerProperty productInvProp() {
        return stockAmount;
    }

    /*Currently unused but left in for potential
    *
    * future use.*/
    public IntegerProperty productMinProp() {
        return min;
    }

    public IntegerProperty productMaxProp() {
        return max;
    }

    public int getProductID() {
        return this.productID.get();
    }


    //Getters and setters
    public void setProductID(int productID) {
        this.productID.set(productID);
    }

    public String getProductName() {
        return this.name.get();
    }

    public void setProductName(String name) {
        this.name.set(name);
    }

    public double getProductPrice() {
        return this.price.get();
    }

    public void setProductPrice(double price) {
        this.price.set(price);
    }

    public int getProductInStock() {
        return this.stockAmount.get();
    }

    public void setProductInStock(int stockAmount) {
        this.stockAmount.set(stockAmount);
    }

    public int getProductMin() {
        return this.min.get();
    }

    public void setProductMin(int min) {
        this.min.set(min);
    }

    public int getProductMax() {
        return this.max.get();
    }

    public void setProductMax(int max) {
        this.max.set(max);
    }

    public ObservableList getProductParts() {
        return parts;
    }

    public void setProductParts(ObservableList<Part> parts) {
        Product.parts = parts;
    }

    /*Setting Product ID for Auto-Gen*/

    private static int productIDNumber = 0;

    /*Gets and increases productIDNumber*/

    public static int getProductIDNumberIncrease() {
        System.out.println("getProductIDNumberIncrease called");
        productIDNumber++;
        return productIDNumber;
    }
    public static int getProductIDNumberDecrease() {
        System.out.println("getProductIDNumberIncrease called");
        productIDNumber--;
        return productIDNumber;
    }
}
