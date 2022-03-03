package com.bakeacake.bakeacaketest.controller;

import com.bakeacake.bakeacaketest.model.Cake;
import com.bakeacake.bakeacaketest.repository.DataManager;
import com.bakeacake.bakeacaketest.service.CakeRecipeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AllRecipesController extends ViewController implements Initializable {

    private CakeRecipeService cakeRecipeService = new CakeRecipeService();
    private Integer user_id = DataManager.getInstance().getLoggedInUserId();
    public ChoiceBox allCakesChoiceBox;
    public Button homeButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ImageView imageView = new ImageView(getClass().getResource("/images/favicon.png").toExternalForm());
        homeButton.setGraphic(imageView);
        homeButton.setTooltip(new Tooltip("Home"));
        viewAllRecipes();


    }

    public void viewAllRecipes() {
        try {

            ObservableList<Cake> cakes = FXCollections.observableArrayList(this.cakeRecipeService.viewAllRecipes(user_id));
            allCakesChoiceBox.getItems().addAll(cakes);
            for (Cake cake : cakes) allCakesChoiceBox.setValue(cake);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRecipe(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete recipe?!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            try {

                cakeRecipeService.deleteRecipeTin18(user_id, String.valueOf(allCakesChoiceBox.getValue()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                cakeRecipeService.deleteRecipeTin20(user_id, String.valueOf(allCakesChoiceBox.getValue()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                cakeRecipeService.deleteRecipeTin22(user_id, String.valueOf(allCakesChoiceBox.getValue()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            allCakesChoiceBox.getItems().clear();
            viewAllRecipes();

        }


    }


    public void returnHome(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "home");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void viewRecipe(ActionEvent actionEvent) {

        DataManager.getInstance().setSelectedCakeTitle(String.valueOf(allCakesChoiceBox.getValue()));


        try {
            changeScene(actionEvent, "view_recipe");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void updateRecipe(ActionEvent actionEvent) {

        DataManager.getInstance().setSelectedCakeTitle(String.valueOf(allCakesChoiceBox.getValue()));

        try {
            changeScene(actionEvent, "update_recipe");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void addToShoppingList(ActionEvent actionEvent) {


        try {
            if (allCakesChoiceBox.getValue() == null) {
                showAlert(null, "Please choose Cake from choice box", Alert.AlertType.ERROR);
                return;
            }

            changeScene(actionEvent, "view_recipe");
        } catch (Exception ex) {
            ex.printStackTrace();
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


}
