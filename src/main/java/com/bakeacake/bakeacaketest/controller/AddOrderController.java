package com.bakeacake.bakeacaketest.controller;

import com.bakeacake.bakeacaketest.model.Cake;
import com.bakeacake.bakeacaketest.model.Client;
import com.bakeacake.bakeacaketest.model.Order;
import com.bakeacake.bakeacaketest.repository.DataManager;
import com.bakeacake.bakeacaketest.service.CakeRecipeService;
import com.bakeacake.bakeacaketest.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AddOrderController extends ViewController implements Initializable {
    @FXML
    ChoiceBox<Client> clientListField;
    @FXML
    ChoiceBox<Cake> cakeTitleField;
    @FXML
    ComboBox<String> tinSizeField;
    private final String[] tins = {"18.0", "20.0", "22.0"};
    @FXML
    DatePicker datePicker;
    @FXML
    TextField timeField;
    @FXML
    ChoiceBox<String> deliveryField;
    private final String[] choice = {"Pick-up", "Delivery"};
    @FXML
    TextArea descriptionField;
    @FXML
    ChoiceBox<String> statusField;
    private final String[] status = {"Pending", "Delivered", "Canceled"};
    @FXML
    Label labelNote;
    public Button homeButton;
    private UserService userService = new UserService();
    private CakeRecipeService cakeRecipeService = new CakeRecipeService();
    private Integer user_id = DataManager.getInstance().getLoggedInUserId();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewAllClients();
        viewAllCakes();
        tinSizeField.getItems().addAll(tins);
        deliveryField.getItems().addAll(choice);
        statusField.getItems().addAll(status);

        ImageView imageView = new ImageView(getClass().getResource("/images/favicon.png").toExternalForm());
        homeButton.setGraphic(imageView);
    }

    public void handleAddOrder(ActionEvent actionEvent) {


        try {

            Client name = clientListField.getSelectionModel().getSelectedItem();
            Cake title = cakeTitleField.getSelectionModel().getSelectedItem();
            String tinSize = tinSizeField.getValue();
            String myDate = String.valueOf(datePicker.getValue());
            String deliveryTime = timeField.getText();
            String deliverOptions = deliveryField.getValue();
            String description = descriptionField.getText();
            String status = statusField.getSelectionModel().getSelectedItem();

//            if(clientListField.getItems().isEmpty() || cakeTitleField.getItems().isEmpty()
//            || tinSizeField.getItems().isEmpty() || String.valueOf(datePicker.getValue()).isEmpty()
//            || timeField.getText().isEmpty() || deliveryField.getItems().isEmpty()
//            || statusField.getItems().isEmpty()){
//                showAlert(null, "Please fill all fields", Alert.AlertType.ERROR);
//            } else{
            if (!checkEntry()) {
                Order order = new Order(tinSize, myDate, deliveryTime,
                        deliverOptions, description, status);
                userService.addOrder(this.user_id, order, title, name);
                showAlert(null, "Order added successfully", Alert.AlertType.CONFIRMATION);
                changeScene(actionEvent, "order_screen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public boolean checkEntry() {
        if (String.valueOf(clientListField.getItems()).isEmpty()) {
            labelNote.setText("Choose client");
            showAlert(null, "Please choose client", Alert.AlertType.ERROR);
        } else if (cakeTitleField.getItems().isEmpty()) {
            showAlert(null, "Please choose cake", Alert.AlertType.ERROR);
        } else if (tinSizeField.getItems().isEmpty()) {
            showAlert(null, "Please choose tin size", Alert.AlertType.ERROR);
        } else if (String.valueOf(datePicker.getValue()).isEmpty()) {
            showAlert(null, "Please select due date", Alert.AlertType.ERROR);
        } else if (timeField.getText().isEmpty()) {
            showAlert(null, "Please add due time", Alert.AlertType.ERROR);
        } else if (deliveryField.getItems().isEmpty()) {
            showAlert(null, "Please choose delivery options", Alert.AlertType.ERROR);
        } else if (statusField.getItems().isEmpty()) {
            showAlert(null, "Please choose status", Alert.AlertType.ERROR);
        }
        return false;
    }

    public void handleReturn(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "order_screen");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void viewAllClients() {
        try {

            clientListField.getItems().addAll(this.userService.viewAllClient(this.user_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAllCakes() {
        try {

            cakeTitleField.getItems().addAll(this.cakeRecipeService.viewAllRecipes(this.user_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        timeField.clear();
        descriptionField.clear();
    }

    public void handleLogout(ActionEvent actionEvent) {
        try {
            DataManager.getInstance().setLoggedInUserId(null);
            changeScene(actionEvent, "login");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void returnHome(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "home");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
