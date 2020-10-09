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

public class AddPartController implements Initializable {
    /*Initialize calls the partID and adds it the the parts label for Auto-Gen feature*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partID = Part.getPartIDNumberIncrease();
        addPartIDLabel.setText("Auto-Gen: " + partID);
    }
    @FXML
    private RadioButton addPartInHouseRadio;
    @FXML
    private RadioButton addOutsourcedPartRadio;
    @FXML
    private TextField dynamicAddPartTextField;
    @FXML
    private TextField addPartNameTextField;
    @FXML
    private TextField addPartInventoryTextField;
    @FXML
    private TextField addPartPriceTextField;
    @FXML
    private TextField addPartMinTextField;
    @FXML
    private TextField addPartMaxTextField;
    @FXML
    private Label dynamicAddPartLabel;
    @FXML
    private Label addPartIDLabel;


    private int partID;
    private String exceptionMessage = "";
    private boolean isOutsourced;

    /*AddPart Screen Radio Button for adding in-house part*/
    @FXML
    void addInHousePartRadioButton(ActionEvent event) {
        System.out.println("addInHousePartRadioButton called");
        isOutsourced = false;
        dynamicAddPartLabel.setText("Machine ID");
        dynamicAddPartTextField.setPromptText("Machine ID");
        addOutsourcedPartRadio.setSelected(false);
    }

    /*AddPart Screen Radio Button for adding outsourced part*/
    @FXML
    void addPartOutsourcedRadioButton(ActionEvent event) {
        System.out.println("addPartOutsourcedRadioButton called");
        isOutsourced = true;
        dynamicAddPartLabel.setText("Company Name");
        dynamicAddPartTextField.setPromptText("Company Name");
        addPartInHouseRadio.setSelected(false);
    }

    /*AddPart Screen Save Button*/
    @FXML
    void saveAddPart(ActionEvent event) throws IOException {
        System.out.println("saveAddPart called");
        String partName = addPartNameTextField.getText();
        String partInv = addPartInventoryTextField.getText();
        String partPrice = addPartPriceTextField.getText();
        String partMin = addPartMinTextField.getText();
        String partMax = addPartMaxTextField.getText();
        String partDyn = dynamicAddPartTextField.getText();

        try {
            System.out.println("Checking if part is valid");
            exceptionMessage = Part.isValidPart(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), exceptionMessage);
            if (exceptionMessage.length() > 0) {
                System.out.println("Error Adding Part");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";

            } else {

                /*Checking to see if the part is Outsourced or In-House*/
                System.out.println("Checking if part is Outsourced or In-house");
                if (isOutsourced) {
                    OutsourcedPart outsourcedPart = new OutsourcedPart();
                    outsourcedPart.setPartID(partID);
                    outsourcedPart.setPartName(partName);
                    outsourcedPart.setPartPrice(Double.parseDouble(partPrice));
                    outsourcedPart.setPartInStock(Integer.parseInt(partInv));
                    outsourcedPart.setPartMin(Integer.parseInt(partMin));
                    outsourcedPart.setPartMax(Integer.parseInt(partMax));
                    outsourcedPart.setPartCompanyName(partDyn);
                    Inventory.addPart(outsourcedPart);
                    System.out.println("Outsourced Part: " + partName + " has been added");
                } else {

                    InHousePart inHousePart = new InHousePart();
                    inHousePart.setPartID(partID);
                    inHousePart.setPartName(partName);
                    inHousePart.setPartPrice(Double.parseDouble(partPrice));
                    inHousePart.setPartInStock(Integer.parseInt(partInv));
                    inHousePart.setPartMin(Integer.parseInt(partMin));
                    inHousePart.setPartMax(Integer.parseInt(partMax));
                    inHousePart.setPartMachineID(Integer.parseInt(partDyn));
                    Inventory.addPart(inHousePart);
                    System.out.println("In-house Part: " + partName + " has been added");
                }

                /*Pulls the mainscreen*/
                System.out.println("MainScreen");
                Parent saveAddPart = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
                Scene scene = new Scene(saveAddPart);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();

            }
        } catch (NumberFormatException exception) {
            System.out.println("Error Adding Part");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Adding Part");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText("Error, cannot contain blank fields.");
            alert.showAndWait();
        }
    }

    /*AddPart Cancel Button pulls up a cancel screen prompt to ask the user
    if they are sure they want to cancel adding the new part.*/
    @FXML
    private void cancelAddPart(ActionEvent event) throws IOException {
        System.out.println("Cancel screen");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Cancel adding new part?");
        Optional<ButtonType> result = alert.showAndWait();


        /*checks to see if the cancel button is pressed*/
        if (result.get() == ButtonType.OK) {

            /*Decreases the PartIDNumber Count
            *
            * "fixing" a bug that made canceling
            *
            * adding a part to still increase
            *
            * the count*/

            Part.getPartIDNumberDecrease();

            /*Pulls the mainscreen*/
            System.out.println("MainScreen");
            Parent addPartCancel = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
            Scene scene = new Scene(addPartCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            System.out.println("Cancel Clicked.");
        }
    }
}
