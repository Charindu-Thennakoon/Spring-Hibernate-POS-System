package com.myorg.dummy.pos.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.myorg.dummy.pos.util.OrderTM;

import java.io.IOException;
import java.net.URL;

public class SearchOrdersFormController {
    public TextField txtSearch;
    public TableView<OrderTM> tblOrders;

    public void initialize() {

        tblOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblOrders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("orderTotal"));

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

            }
        });

    }

    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/com/myorg/dummy/pos/view/MainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.txtSearch.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void tblOrders_OnMouseClicked(MouseEvent mouseEvent) throws IOException {
        if (tblOrders.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (mouseEvent.getClickCount() == 2) {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/com/myorg/dummy/pos/view/PlaceOrderForm.fxml"));
            Parent root = fxmlLoader.load();
            PlaceOrderFormController controller = fxmlLoader.getController();
            controller.initializeWithSearchOrderForm(tblOrders.getSelectionModel().getSelectedItem().getOrderId());
            Scene orderScene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(orderScene);
            stage.centerOnScreen();
            stage.show();
        }
    }

}
