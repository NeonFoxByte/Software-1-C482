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

public class MainScreenController implements Initializable {

    /*Initialize populates the Parts and Products tables with the inventory*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Adds the Part ID column*/
        partsIDColumn.setCellValueFactory(cellData -> cellData.getValue().intPropPartID().asObject());
        /*Adds the Part Name column*/
        partsNameColumn.setCellValueFactory(cellData -> cellData.getValue().stringPropName());
        /*Adds the Part Inventory column*/
        partsInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().intPropPartInv().asObject());
        /*Adds the Part Price column*/
        partsPriceColumn.setCellValueFactory(cellData -> cellData.getValue().doublePropPrice().asObject());

        /*Adds the Product ID*/
        productsIDColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProp().asObject());
        /*Adds the Product Name*/
        productsNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProp());
        /*Adds the Product Inventory column*/
        productsInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().productInvProp().asObject());
        /*Adds the Product Price column*/
        productsPriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProp().asObject());

        updatePartsTable();
        updateProductsTable();
    }

    private static int modifyPartIndex;
    private static int modifyProductIndex;
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partsIDColumn;
    @FXML
    private TableColumn<Part, String> partsNameColumn;
    @FXML
    private TableColumn<Part, Integer> partsInventoryColumn;
    @FXML
    private TableColumn<Part, Double> partsPriceColumn;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, Integer> productsIDColumn;
    @FXML
    private TableColumn<Product, String> productsNameColumn;
    @FXML
    private TableColumn<Product, Integer> productsInventoryColumn;
    @FXML
    private TableColumn<Product, Double> productsPriceColumn;
    @FXML
    private TextField searchPartsTextField;
    @FXML
    private TextField searchProductsTextField;


    public static int partToModifyIndex() {
        return modifyPartIndex;
    }

    public static int productToModifyIndex() {
        return modifyProductIndex;
    }


    /*Parts Search by searching by calling partLookup from Inventory
    *
    * partLookup searches via partID and Name*/
    @FXML
    private void partsSearch(ActionEvent event) {
        System.out.println("partSearch called");
        String searchPart = searchPartsTextField.getText();
        int partIndex = -1;
        if (Inventory.partLookup(searchPart) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Part not found");
            alert.setContentText("Part not found.");
            alert.showAndWait();
        } else {
            partIndex = Inventory.partLookup(searchPart);
            Part tempPart = Inventory.getPartInventory().get(partIndex);
            ObservableList<Part> temporaryPartList = FXCollections.observableArrayList();
            temporaryPartList.add(tempPart);
            partsTableView.setItems(temporaryPartList);
        }
    }

    //Deleting Parts
    @FXML
    private void mainPartsDelete(ActionEvent event) {
        System.out.println("mainPartsDelete called");
        Part part = partsTableView.getSelectionModel().getSelectedItem();
        if (Inventory.canDeletePart(part)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion Error");
            alert.setHeaderText("Cannot Delete Part!");
            alert.setContentText("Part is in use by one or more Products.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Delete Part");
            alert.setHeaderText("Confirm?");
            alert.setContentText("Delete " + part.getPartName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Inventory.deletePart(part);
                updatePartsTable();
                System.out.println("Part " + part.getPartName() + " Deleted.");
            } else {
                System.out.println("Part " + part.getPartName() + " was not Deleted.");
            }
        }
    }

    //Add Parts Screen
    @FXML
    private void addPartScreen(ActionEvent event) throws IOException {
        System.out.println("addPartScreen called");
        Parent addPartParent = FXMLLoader.load(getClass().getResource("../view/AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }

    //Modify Parts Screen
    @FXML
    private void modifyPartScreen(ActionEvent event) throws IOException {
        System.out.println("modifyPartScreen called");
        Part modifyPart = partsTableView.getSelectionModel().getSelectedItem();
        modifyPartIndex = Inventory.getPartInventory().indexOf(modifyPart);
        Parent modifyPartParent = FXMLLoader.load(getClass().getResource("../view/ModifyPart.fxml"));
        Scene modifyPartScene = new Scene(modifyPartParent);
        Stage modifyPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modifyPartStage.setScene(modifyPartScene);
        modifyPartStage.show();
    }

    //Products text field
    @FXML
    private void mainProductsSearch(ActionEvent event) {
        System.out.println("mainProductsSearch called");
        String searchProduct = searchProductsTextField.getText();
        int productIndex = -1;
        if (Inventory.productLookup(searchProduct) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Product Not Found");
            alert.setContentText("Product Not Found.");
            alert.showAndWait();
        } else {
            productIndex = Inventory.productLookup(searchProduct);
            Product tempProduct = Inventory.getProductInventory().get(productIndex);
            ObservableList<Product> temporaryProductList = FXCollections.observableArrayList();
            temporaryProductList.add(tempProduct);
            productsTableView.setItems(temporaryProductList);
        }
    }

    //Delete Products
    @FXML
    private void mainProductsDelete(ActionEvent event) {
        System.out.println("mainProductsDelete called");
        Product product = productsTableView.getSelectionModel().getSelectedItem();
        if (Inventory.canDeleteProduct(product)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion Error");
            alert.setHeaderText("Cannot Delete Product!");
            alert.setContentText("Product contains parts and cannot be deleted.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Delete Product?");
            alert.setHeaderText("Confirm Delete?");
            alert.setContentText("Delete Product " + product.getProductName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deleteProduct(product);
                updateProductsTable();
                System.out.println("Product " + product.getProductName() + " was deleted.");
            } else {
                System.out.println("Product " + product.getProductName() + " was deleted.");
            }
        }
    }

   /*Add Product Screen*/
    @FXML
    private void addProductScreen(ActionEvent event) throws IOException {
        System.out.println("addProductScreen called");
        Parent addProductParent = FXMLLoader.load(getClass().getResource("../view/AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
    }

   /* Modify Product Screen*/
    @FXML
    private void modifyProductScreen(ActionEvent event) throws IOException {
        System.out.println("modifyProductScreen called");
        Product modifyProduct = productsTableView.getSelectionModel().getSelectedItem();
        modifyProductIndex = Inventory.getProductInventory().indexOf(modifyProduct);
        Parent modifyProductParent = FXMLLoader.load(getClass().getResource("../view/ModifyProduct.fxml"));
        Scene modifyProductScene = new Scene(modifyProductParent);
        Stage modifyProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modifyProductStage.setScene(modifyProductScene);
        modifyProductStage.show();
    }


    /* Updates table views by pulling the latest parts inventory*/
    public void updatePartsTable() {
        System.out.println("updatePartsTable called");
        partsTableView.setItems(Inventory.getPartInventory());
    }
    /* Updates table views by pulling the latest products inventory*/
    public void updateProductsTable() {
        System.out.println("updateProductsTable called");
        productsTableView.setItems(Inventory.getProductInventory());
    }


    /*Confirm exit on main.Main screen*/
    @FXML
    private void mainExitButton(ActionEvent event) {
        System.out.println("mainExitButton called");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Confirm Exit");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {
            System.out.println("Cancel Clicked.");
        }
    }


}