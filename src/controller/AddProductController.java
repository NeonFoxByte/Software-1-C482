package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    /*Populates the tables with  data from the Parts Inventory */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("table data should load");
        /*Adds the Product ID column to the add part table*/
        tableViewAddProductAddIDColumn.setCellValueFactory(cellData -> cellData.getValue().intPropPartID().asObject());
        /*Adds the Product Name column to the add part table*/
        tableViewAddProductAddNameColumn.setCellValueFactory(cellData -> cellData.getValue().stringPropName());
        /*Adds the Product Inventory column to the add part table*/
        tableViewAddProductAddInvColumn.setCellValueFactory(cellData -> cellData.getValue().intPropPartInv().asObject());
        /*Adds the Product Price column to the add part table*/
        tableViewAddProductAddPriceColumn.setCellValueFactory(cellData -> cellData.getValue().doublePropPrice().asObject());


        /*Adds the product ID column to the delete part table*/
        tableViewAddProductDeleteIDColumn.setCellValueFactory(cellData -> cellData.getValue().intPropPartID().asObject());
        /*Adds the Product Name column to the delete part table*/
        tableViewAddProductDeleteNameColumn.setCellValueFactory(cellData -> cellData.getValue().stringPropName());
        /*Adds the Product Inventory Column to the delete part table*/
        tableViewAddProductDeleteInvColumn.setCellValueFactory(cellData -> cellData.getValue().intPropPartInv().asObject());
        /*Adds the Product Price column to the delete part table*/
        tableViewAddProductDeletePriceColumn.setCellValueFactory(cellData -> cellData.getValue().doublePropPrice().asObject());


        updateAddPartTable();
        updateDeletePartTable();
        productID = Product.getProductIDNumberIncrease();
        addProductIDNumberLabel.setText("Auto-Gen: " + productID);
    }

    private final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private String exceptionMessage = "";
    private int productID;
    @FXML
    private Label addProductIDNumberLabel;
    @FXML
    private TextField textAddProductName;
    @FXML
    private TextField textAddProductInventory;
    @FXML
    private TextField textAddProductPrice;
    @FXML
    private TextField textAddProductMin;
    @FXML
    private TextField textAddProductMax;
    @FXML
    private TextField textProductSearch;
    @FXML
    private TableView<Part> tableViewAddProductAdd;
    @FXML
    private TableView<Part> tableViewAddProductDelete;
    @FXML
    private TableColumn<Part, Integer> tableViewAddProductAddIDColumn;
    @FXML
    private TableColumn<Part, String> tableViewAddProductAddNameColumn;
    @FXML
    private TableColumn<Part, Integer> tableViewAddProductAddInvColumn;
    @FXML
    private TableColumn<Part, Double> tableViewAddProductAddPriceColumn;
    @FXML
    private TableColumn<Part, Integer> tableViewAddProductDeleteIDColumn;
    @FXML
    private TableColumn<Part, String> tableViewAddProductDeleteNameColumn;
    @FXML
    private TableColumn<Part, Integer> tableViewAddProductDeleteInvColumn;
    @FXML
    private TableColumn<Part, Double> tableViewAddProductDeletePriceColumn;



    /*Adding Part to Product*/
    @FXML
    void productAdd(ActionEvent event) {
        System.out.println("productAdd called");
        Part part = tableViewAddProductAdd.getSelectionModel().getSelectedItem();
        allParts.add(part);
        updateDeletePartTable();
    }

    /*AddProduct Screen Search button searches parts via partName and partIndex*/
    @FXML
    void addProductSearch(ActionEvent event) {
        System.out.println("addProductSearch called");
        int partIndex;
        if (Inventory.partLookup(textProductSearch.getText()) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Search Error");
            alert.setContentText("Search didn't find any matching parts.");
            alert.showAndWait();
        } else {
            partIndex = Inventory.partLookup(textProductSearch.getText());
            Part tempPart = Inventory.getPartInventory().get(partIndex);
            ObservableList<Part> temporaryPartList = FXCollections.observableArrayList();
            temporaryPartList.add(tempPart);
            tableViewAddProductAdd.setItems(temporaryPartList);
        }
    }

    /*AddParts Screen Save Button*/
    @FXML
    void addProductSave(ActionEvent event) throws IOException {
        System.out.println("addProductSave called");
        String productName = textAddProductName.getText();
        String productInv = textAddProductInventory.getText();
        String productPrice = textAddProductPrice.getText();
        String productMin = textAddProductMin.getText();
        String productMax = textAddProductMax.getText();

        try {
            exceptionMessage = Product.isProductValid(productName, Integer.parseInt(productMin), Integer.parseInt(productMax), Integer.parseInt(productInv), Double.parseDouble(productPrice), allParts, exceptionMessage);
            if (exceptionMessage.length() > 0) {
                /*Checking to see if there is an Error with the product*/
                System.out.println("Error screen");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
                System.out.println("Error Adding Product");
            } else {
                /*If no error exists product is added*/
                Product newProduct = new Product();
                newProduct.setProductID(productID);
                newProduct.setProductName(productName);
                newProduct.setProductInStock(Integer.parseInt(productInv));
                newProduct.setProductPrice(Double.parseDouble(productPrice));
                newProduct.setProductMin(Integer.parseInt(productMin));
                newProduct.setProductMax(Integer.parseInt(productMax));
                newProduct.setProductParts(allParts);
                Inventory.addProduct(newProduct);
                System.out.println("Product Name: " + productName + " has been added");

                System.out.println("MainScreen");
                Parent addProductSaveParent = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
                Scene scene = new Scene(addProductSaveParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } catch (NumberFormatException exception) {
            System.out.println("Error Adding Product");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Adding Product");
            alert.setHeaderText("Error Adding Product");
            alert.setContentText("Form cannot contain blank fields");
            alert.showAndWait();
        }
    }

    //Delete
    @FXML
    void addProductDelete(ActionEvent event) {
        System.out.println("addProductDelete called");

        /*sets selected part to part*/
        Part part = tableViewAddProductDelete.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Delete Part?");
        alert.setHeaderText("Confirm");
        alert.setContentText("Delete " + part.getPartName() + " from Parts?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Part deleted.");
            allParts.remove(part);
        } else {
            System.out.println("Cancel Clicked");
        }
    }

    /*Add Product Cancel Button*/
    @FXML
    private void addProductCancel(ActionEvent event) throws IOException {
        System.out.println("");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Cancel adding a new product?");
        Optional<ButtonType> result = alert.showAndWait();
        System.out.println("addProductCancel Called");

        if (result.get() == ButtonType.OK) {

            /*Decreases the ProductIDNumber Count
             *
             * "fixing" a bug that made canceling
             *
             * adding a Product to still increase
             *
             * the count*/

            Product.getProductIDNumberDecrease();

            Parent addProductCancel = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
            Scene scene = new Scene(addProductCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            System.out.println("MainScreen opened");
        } else {
            System.out.println("Cancel Button Clicked.");
        }
    }
/*    Updates the upper Parts Table with in stock parts*/
    public void updateAddPartTable() {
        tableViewAddProductAdd.setItems(Inventory.getPartInventory());
        System.out.println("updateAddPartTable Called");
    }
/*     Updates the lower Part Table with used parts */
    public void updateDeletePartTable() {
        tableViewAddProductDelete.setItems(allParts);
        System.out.println("updateDeletePartTable Called");
    }
}