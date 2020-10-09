package controller;

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
import model.InHousePart;
import model.Inventory;
import model.OutsourcedPart;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.getPartInventory;

public class ModifyPartController implements Initializable {

    /*initialize populates the ModifyPart screen with the data
    *
    *selected on MainScreen's Part table*/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Part part = getPartInventory().get(partIndex);
        /*Populates the Auto-Gen Label with the Part ID*/
        labelModifyPartIDNumber.setText("Auto-Gen: " + getPartInventory().get(partIndex).getPartID());
        /*Populates the Part Name Text Box*/
        textFieldModifyPartName.setText(part.getPartName());
        /*Populates the Part Inventory Text Box*/
        textFieldModifyPartInv.setText(Integer.toString(part.getPartInStock()));
        /*Populates the Part Price Text Box*/
        textFieldModifyPartPrice.setText(Double.toString(part.getPartPrice()));
        /*Populates the Part Min Text Box*/
        textFieldModifyPartMin.setText(Integer.toString(part.getPartMin()));
        /*Populates the Part Max Text Box*/
        textFieldModifyPartMax.setText(Integer.toString(part.getPartMax()));

        /*Checks if the Part is an In-house Part or an Outsourced Part
        *
        * via radioButtons*/

        if (part instanceof InHousePart) {
            labelModifyPartDyn.setText("Machine ID");
            textFieldModifyPartDyn.setText(Integer.toString(((InHousePart) getPartInventory().get(partIndex)).getPartMachineID()));
            radioModifyPartInHouse.setSelected(true);
        } else {
            labelModifyPartDyn.setText("Company Name");
            textFieldModifyPartDyn.setText(((OutsourcedPart) getPartInventory().get(partIndex)).getPartCompanyName());
            radioModifyPartOutsourced.setSelected(true);
        }
    }

    int partIndex = MainScreenController.partToModifyIndex();
    private int partID;
    private String exceptionMessage = "";
    private boolean isOutsourced;

    @FXML
    private Label labelModifyPartDyn;
    @FXML
    private Label labelModifyPartIDNumber;
    @FXML
    private RadioButton radioModifyPartInHouse;
    @FXML
    private RadioButton radioModifyPartOutsourced;
    @FXML
    private TextField textFieldModifyPartName;
    @FXML
    private TextField textFieldModifyPartInv;
    @FXML
    private TextField textFieldModifyPartPrice;
    @FXML
    private TextField textFieldModifyPartMin;
    @FXML
    private TextField textFieldModifyPartMax;
    @FXML
    private TextField textFieldModifyPartDyn;

    /*Save button for the ModifyParts Screen*/
    @FXML
    void modifyPartSave(ActionEvent event) throws IOException {
        /*Sets the text field data to variables */
        System.out.println("modifyPartSave called");
        String partName = textFieldModifyPartName.getText();
        String partInv = textFieldModifyPartInv.getText();
        String partPrice = textFieldModifyPartPrice.getText();
        String partMin = textFieldModifyPartMin.getText();
        String partMax = textFieldModifyPartMax.getText();
        String partDyn = textFieldModifyPartDyn.getText();

        /*Checks to see if the part is valid by calling isValidPart from the Part class*/
        try {
            exceptionMessage = Part.isValidPart(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), exceptionMessage);
            if (exceptionMessage.length() > 0) {
                System.out.println("Error Modifying Part");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Modifying Part");
                alert.setHeaderText("Error Modifying Part");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            } else {
                if (isOutsourced) {
                    /*Changes the Outsourced Part's values*/
                    System.out.println("Part name: " + partName);
                    OutsourcedPart outsourcedPart = new OutsourcedPart();
                    outsourcedPart.setPartID(partID);
                    outsourcedPart.setPartName(partName);
                    outsourcedPart.setPartInStock(Integer.parseInt(partInv));
                    outsourcedPart.setPartPrice(Double.parseDouble(partPrice));
                    outsourcedPart.setPartMin(Integer.parseInt(partMin));
                    outsourcedPart.setPartMax(Integer.parseInt(partMax));
                    outsourcedPart.setPartCompanyName(partDyn);
                    Inventory.updatePart(partIndex, outsourcedPart);
                } else {
                    /*Changes the In-House Part's values*/
                    System.out.println("Part name: " + partName);
                    InHousePart inHousePart = new InHousePart();
                    inHousePart.setPartID(partID);
                    inHousePart.setPartName(partName);
                    inHousePart.setPartInStock(Integer.parseInt(partInv));
                    inHousePart.setPartPrice(Double.parseDouble(partPrice));
                    inHousePart.setPartMin(Integer.parseInt(partMin));
                    inHousePart.setPartMax(Integer.parseInt(partMax));
                    inHousePart.setPartMachineID(Integer.parseInt(partDyn));
                    Inventory.updatePart(partIndex, inHousePart);
                }
                /*Pulls the mainscreen*/
                System.out.println("MainScreen");
                Parent modifyProductSave = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
                Scene scene = new Scene(modifyProductSave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } catch (NumberFormatException exception) {
            System.out.println("Error Modifying Part");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Modifying Part");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }
    }
    /*Cancel button for the ModifyPart Screen*/
    @FXML
    private void modifyPartCancel(ActionEvent event) throws IOException {
        System.out.println("modifyPartCancel called");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel modifying the part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.out.println("MainScreen");
            Parent modifyPartCancel = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
            Scene scene = new Scene(modifyPartCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            System.out.println("Cancel Clicked.");
        }
    }
    /*ModifyParts Radio Buttons toggle between an instance of Outsourced
    *
    * and In-house parts*/

    @FXML
    void modifyPartInHouseRadio(ActionEvent event) {
        System.out.println("modifyPartInHouseRadio called");
        isOutsourced = false;
        radioModifyPartOutsourced.setSelected(false);
        labelModifyPartDyn.setText("Machine ID");
        textFieldModifyPartDyn.setText("");
        textFieldModifyPartDyn.setPromptText("Machine ID");
    }

    @FXML
    void modifyPartOutsourcedRadio(ActionEvent event) {
        System.out.println("modifyPartOutsourcedRadio called");
        isOutsourced = true;
        radioModifyPartInHouse.setSelected(false);
        labelModifyPartDyn.setText("Company Name");
        textFieldModifyPartDyn.setText("");
        textFieldModifyPartDyn.setPromptText("Company Name");
    }
}
