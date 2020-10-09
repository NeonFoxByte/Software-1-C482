package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/*C482 Software 1: Jordan Brewer*/

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        addTestData();
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /*Adding test data to test the program
    *
    * The Part and Product IDs are currently
    *
    * Throwing off the overall ID counts
    *
    * The Program doesn't currently see the
    *
    * ID counts so it starts at 1*/

    void addTestData(){
        ObservableList<Part> addTestParts1 = FXCollections.observableArrayList();
        ObservableList<Part> addTestParts2 = FXCollections.observableArrayList();

        InHousePart inHousePart1 = new InHousePart();
        inHousePart1.setPartID(1);
        inHousePart1.setPartName("inHousePart1");
        inHousePart1.setPartPrice(Double.parseDouble("2.99"));
        inHousePart1.setPartInStock(Integer.parseInt("1"));
        inHousePart1.setPartMin(Integer.parseInt("1"));
        inHousePart1.setPartMax(Integer.parseInt("10"));
        inHousePart1.setPartMachineID(Integer.parseInt("10"));
        Inventory.addPart(inHousePart1);

        InHousePart inHousePart2 = new InHousePart();
        inHousePart2.setPartID(2);
        inHousePart2.setPartName("inHousePart2");
        inHousePart2.setPartPrice(Double.parseDouble("5.99"));
        inHousePart2.setPartInStock(Integer.parseInt("5"));
        inHousePart2.setPartMin(Integer.parseInt("1"));
        inHousePart2.setPartMax(Integer.parseInt("10"));
        inHousePart2.setPartMachineID(Integer.parseInt("101"));
        Inventory.addPart(inHousePart2);

        InHousePart inHousePart3 = new InHousePart();
        inHousePart3.setPartID(3);
        inHousePart3.setPartName("inHousePart3");
        inHousePart3.setPartPrice(Double.parseDouble("3.99"));
        inHousePart3.setPartInStock(Integer.parseInt("1"));
        inHousePart3.setPartMin(Integer.parseInt("1"));
        inHousePart3.setPartMax(Integer.parseInt("10"));
        inHousePart3.setPartMachineID(Integer.parseInt("54"));
        Inventory.addPart(inHousePart3);

        OutsourcedPart outsourcedPart1 = new OutsourcedPart();
        outsourcedPart1.setPartID(4);
        outsourcedPart1.setPartName("outsourcedPart1");
        outsourcedPart1.setPartPrice(Double.parseDouble("8.99"));
        outsourcedPart1.setPartInStock(Integer.parseInt("5"));
        outsourcedPart1.setPartMin(Integer.parseInt("2"));
        outsourcedPart1.setPartMax(Integer.parseInt("8"));
        outsourcedPart1.setPartCompanyName("Company 1");
        Inventory.addPart(outsourcedPart1);

        OutsourcedPart outsourcedPart2 = new OutsourcedPart();
        outsourcedPart2.setPartID(5);
        outsourcedPart2.setPartName("outsourcedPart2");
        outsourcedPart2.setPartPrice(Double.parseDouble("6.99"));
        outsourcedPart2.setPartInStock(Integer.parseInt("4"));
        outsourcedPart2.setPartMin(Integer.parseInt("3"));
        outsourcedPart2.setPartMax(Integer.parseInt("7"));
        outsourcedPart2.setPartCompanyName("Company 2");
        Inventory.addPart(outsourcedPart2);

        addTestParts1.add(outsourcedPart2);
        addTestParts1.add(inHousePart1);
        addTestParts2.add(inHousePart2);
        addTestParts2.add(outsourcedPart1);

        Product product1 = new Product();
        product1.setProductID(6);
        product1.setProductName("product1");
        product1.setProductInStock(Integer.parseInt("1"));
        product1.setProductPrice(Double.parseDouble("39.99"));
        product1.setProductMin(Integer.parseInt("1"));
        product1.setProductMax(Integer.parseInt("9"));
        product1.setProductParts(addTestParts1);
        Inventory.addProduct(product1);


        Product product2 = new Product();
        product2.setProductID(7);
        product2.setProductName("product2");
        product2.setProductInStock(Integer.parseInt("2"));
        product2.setProductPrice(Double.parseDouble("29.99"));
        product2.setProductMin(Integer.parseInt("2"));
        product2.setProductMax(Integer.parseInt("12"));
        product2.setProductParts(addTestParts2);
        Inventory.addProduct(product2);
    }
}
