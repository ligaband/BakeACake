package com.bakeacake.bakeacaketest.controller;
import com.bakeacake.bakeacaketest.model.Cake;
import com.bakeacake.bakeacaketest.model.Client;
import com.bakeacake.bakeacaketest.model.Order;
import com.bakeacake.bakeacaketest.repository.DataManager;
import com.bakeacake.bakeacaketest.service.CakeRecipeService;
import com.bakeacake.bakeacaketest.service.OrderService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public Button homeButton;
    private OrderService orderService = new OrderService();
    private CakeRecipeService cakeRecipeService = new CakeRecipeService();
    private Integer user_id = DataManager.getInstance().getLoggedInUserId();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewAllClients();
        viewAllCakes();
        tinSizeField.getItems().addAll(tins);
        tinSizeField.setValue("18.0");
        deliveryField.getItems().addAll(choice);
        deliveryField.setValue("Pick-up");
        statusField.getItems().addAll(status);
        statusField.setValue("Pending");
        ImageView imageView = new ImageView(getClass().getResource("/images/favicon.png").toExternalForm());
        homeButton.setGraphic(imageView);
        homeButton.setTooltip(new Tooltip("Home"));
    }

    public void handleAddOrder(ActionEvent actionEvent) {
        try {
            Client name = clientListField.getSelectionModel().getSelectedItem();
            ObservableList<Client> clients = FXCollections.observableArrayList(this.orderService.viewAllClient(this.user_id));
            String name1 = String.valueOf(clientListField.getSelectionModel().getSelectedItem());
            for (Client client : clients) {
                if (client.getName().equals(name1)) {
                    DataManager.getInstance().setClientId(client.getId());
                }
            }
            Integer id = DataManager.getInstance().getClientId();
            Cake title = cakeTitleField.getSelectionModel().getSelectedItem();
            String tinSize = tinSizeField.getValue();
            String myDate = String.valueOf(datePicker.getValue());
            String deliveryTime = timeField.getText();
            String deliverOptions = deliveryField.getValue();
            String description = descriptionField.getText();
            String status = statusField.getSelectionModel().getSelectedItem();

            if (datePicker.getValue() == null) {
                showAlert(null, "Please add a delivery date!", Alert.AlertType.ERROR);
            } else if (timeField.getText().isEmpty()) {
                showAlert(null, "Please add delivery time!", Alert.AlertType.ERROR);
            } else {
                Order order = new Order(id, tinSize, myDate, deliveryTime,
                        deliverOptions, description, status);
                orderService.addOrder(this.user_id, order, title, name);
                showAlert(null, "Order added successfully!", Alert.AlertType.INFORMATION);
                changeScene(actionEvent, "order_screen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            ObservableList<Client> clients = FXCollections.observableArrayList(this.orderService.viewAllClient(this.user_id));
            clientListField.getItems().addAll(clients);
            for (Client client : clients) clientListField.setValue(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAllCakes() {
        try {
            ObservableList<Cake> cakes = FXCollections.observableArrayList(this.cakeRecipeService.viewAllRecipes(this.user_id));
            cakeTitleField.getItems().addAll(cakes);
            for (Cake cake : cakes) cakeTitleField.setValue(cake);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
