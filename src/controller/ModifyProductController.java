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

public class ModifyProductController implements Initializable {

    /*initialize populates the ModifyProducts screen with the data selected on MainScreen's Part table
    *
    * The column are set up with the add part table and the delete part table respectively*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Product product = Inventory.getProductInventory().get(productIndex);
        /*Populates the Product Auto-Gen with Product ID*/
        LabelModifyProductIDNumber.setText("Auto-Gen: " + Inventory.getProductInventory().get(productIndex).getProductID());
        /*Populates the Product name text field*/
        textFieldModifyProductName.setText(product.getProductName());
        /*populates the product inventory text field*/
        textFieldModifyProductInv.setText(Integer.toString(product.getProductInStock()));
        /*populates the product price text field*/
        textFieldModifyProductPrice.setText(Double.toString(product.getProductPrice()));
        /*populates the product min text field*/
        textFieldModifyProductMin.setText(Integer.toString(product.getProductMin()));
        /*populates the product max text field*/
        textFieldModifyProductMax.setText(Integer.toString(product.getProductMax()));

        /*Sets allParts with ProductParts*/
        allParts = product.getProductParts();
        /*populates add ID column*/
        modifyProductAddIDColumn.setCellValueFactory(cellData -> cellData.getValue().intPropPartID().asObject());
        /*populates the add name column*/
        modifyProductAddNameColumn.setCellValueFactory(cellData -> cellData.getValue().stringPropName());
        /*populates the add inventory column*/
        modifyProductAddInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().intPropPartInv().asObject());
        /*populates the add price column*/
        modifyProductAddPriceColumn.setCellValueFactory(cellData -> cellData.getValue().doublePropPrice().asObject());
        /*populates the delete ID column*/
        modifyProductDeleteIDColumn.setCellValueFactory(cellData -> cellData.getValue().intPropPartID().asObject());
        /*populates the delete name column*/
        modifyProductDeleteNameColumn.setCellValueFactory(cellData -> cellData.getValue().stringPropName());
        /*populates the delete inventory column*/
        modifyProductDeleteInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().intPropPartInv().asObject());
        /*populates the delete price column*/
        modifyProductDeletePriceColumn.setCellValueFactory(cellData -> cellData.getValue().doublePropPrice().asObject());
        updateAddPartsTableView();
        updateDeletePartsTableView();
    }

    private final int productIndex = MainScreenController.productToModifyIndex();
    private ObservableList allParts = FXCollections.observableArrayList();
    private String exceptionMessage = "";

    @FXML
    private Label LabelModifyProductIDNumber;
    @FXML
    private TextField textFieldModifyProductName;
    @FXML
    private TextField textFieldModifyProductInv;
    @FXML
    private TextField textFieldModifyProductPrice;
    @FXML
    private TextField textFieldModifyProductMin;
    @FXML
    private TextField textFieldModifyProductMax;
    @FXML
    private TextField textFieldModifyProductSearch;
    @FXML
    private TableView<Part> tableViewModifyProductDelete;
    @FXML
    private TableView<Part> tableViewModifyProductAdd;
    @FXML
    private TableColumn<Part, Integer> modifyProductAddIDColumn;
    @FXML
    private TableColumn<Part, String> modifyProductAddNameColumn;
    @FXML
    private TableColumn<Part, Integer> modifyProductAddInventoryColumn;
    @FXML
    private TableColumn<Part, Double> modifyProductAddPriceColumn;
    @FXML
    private TableColumn<Part, Integer> modifyProductDeleteIDColumn;
    @FXML
    private TableColumn<Part, String> modifyProductDeleteNameColumn;
    @FXML
    private TableColumn<Part, Integer> modifyProductDeleteInventoryColumn;
    @FXML
    private TableColumn<Part, Double> modifyProductDeletePriceColumn;

    /*Searches products within the modify product screen
    *
    * by looking up part */

    @FXML
    void modifyProductSearch(ActionEvent event) {
        System.out.println("modifyProductSearch called");
        int partIndex;
        if (Inventory.partLookup( textFieldModifyProductSearch.getText()) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Part not found");
            alert.setContentText("Part not found");
            alert.showAndWait();
        } else {
            partIndex = Inventory.partLookup( textFieldModifyProductSearch.getText());
            Part tempPart = Inventory.getPartInventory().get(partIndex);
            ObservableList<Part> temporaryPartList = FXCollections.observableArrayList();
            temporaryPartList.add(tempPart);
            tableViewModifyProductAdd.setItems(temporaryPartList);
        }
    }

    /*adds selected part to the product's parts table*/

    @FXML
    void modifyProductAdd(ActionEvent event) {
        System.out.println("modifyProductAdd called");
        Part part = tableViewModifyProductAdd.getSelectionModel().getSelectedItem();
        allParts.add(part);
        updateDeletePartsTableView();
    }

    /*deletes the selected part from the product's parts table*/

    @FXML
    void modifyProductDelete(ActionEvent event) {
        System.out.println("modifyProductDelete called");
        Part part = tableViewModifyProductDelete.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Delete Part");
        alert.setHeaderText("Confirm");
        alert.setContentText("Delete " + part.getPartName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            allParts.remove(part);
        } else {
            System.out.println("Cancel Clicked");
        }
    }

    /*Saves the modified product with the new values*/

    @FXML
    private void modifyProductSave(ActionEvent event) throws IOException {
        /*Sets the text field text to variables */
        System.out.println("modifyProductSave called");
        String productName = textFieldModifyProductName.getText();
        String productInv = textFieldModifyProductInv.getText();
        String productPrice = textFieldModifyProductPrice.getText();
        String productMin = textFieldModifyProductMin.getText();
        String productMax = textFieldModifyProductMax.getText();

        try {
            exceptionMessage = Product.isProductValid(productName, Integer.parseInt(productMin), Integer.parseInt(productMax), Integer.parseInt(productInv), Double.parseDouble(productPrice), allParts, exceptionMessage);
            if (exceptionMessage.length() > 0) {
                System.out.println("Error Modifying Product");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Modifying Product");
                alert.setHeaderText("Error Modifying Product");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            } else {
                /*Updates the Product with the data in the text fields*/
                System.out.println("Product name: " + productName);
                Product newProduct = new Product();
                newProduct.setProductID(Inventory.getProductInventory().get(productIndex).getProductID());
                newProduct.setProductName(productName);
                newProduct.setProductInStock(Integer.parseInt(productInv));
                newProduct.setProductPrice(Double.parseDouble(productPrice));
                newProduct.setProductMin(Integer.parseInt(productMin));
                newProduct.setProductMax(Integer.parseInt(productMax));
                newProduct.setProductParts(allParts);
                Inventory.updateProduct(productIndex, newProduct);

                /*Pulls the mainscreen*/
                System.out.println("MainScreen");
                Parent modifyProductSaveParent = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
                Scene scene = new Scene(modifyProductSaveParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } catch (NumberFormatException exception) {
            System.out.println("Error Modifying Product");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Modifying Product");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }
    }

    @FXML
    private void modifyProductCancel(ActionEvent event) throws IOException {
        System.out.println("modifyProductCancel called");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel modifying the product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.out.println("MainScreen");
            Parent modifyProductCancelParent = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
            Scene scene = new Scene(modifyProductCancelParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            System.out.println("Cancel Clicked");
        }
    }



    public void updateAddPartsTableView() {
        System.out.println("updateAddPartsTableView called");
        tableViewModifyProductAdd.setItems(Inventory.getPartInventory());
    }

    public void updateDeletePartsTableView() {
        System.out.println("updateDeletePartsTableView called");
        tableViewModifyProductDelete.setItems(allParts);
    }
}

