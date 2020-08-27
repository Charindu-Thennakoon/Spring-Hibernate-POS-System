
package com.myorg.dummy.pos.controller;

import com.myorg.dummy.pos.AppInitializer;
import com.myorg.dummy.pos.business.custom.CustomerBO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.myorg.dummy.pos.util.CustomerTM;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class ManageCustomerFormController implements Initializable {

    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;
    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtCustomerId;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TableView<CustomerTM> tblCustomers;

    private CustomerBO customerBO = AppInitializer.getApplicationContext().getBean(CustomerBO.class);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        txtCustomerId.setDisable(true);
        txtCustomerName.setDisable(true);
        txtCustomerAddress.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(true);

        loadAllCustomers();

        tblCustomers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTM>() {
            @Override
            public void changed(ObservableValue<? extends CustomerTM> observable, CustomerTM oldValue, CustomerTM newValue) {
                CustomerTM selectedItem = tblCustomers.getSelectionModel().getSelectedItem();

                if (selectedItem == null) {
                    btnSave.setText("Save");
                    btnDelete.setDisable(true);
                    txtCustomerId.clear();
                    txtCustomerName.clear();
                    txtCustomerAddress.clear();
                    return;
                }

                btnSave.setText("Update");
                btnSave.setDisable(false);
                btnDelete.setDisable(false);
                txtCustomerName.setDisable(false);
                txtCustomerAddress.setDisable(false);
                txtCustomerId.setText(selectedItem.getId());
                txtCustomerName.setText(selectedItem.getName());
                txtCustomerAddress.setText(selectedItem.getAddress());
            }
        });
    }

    private void loadAllCustomers() {
        tblCustomers.getItems().clear();
        List<CustomerTM> allCustomers = null;
        try {
            allCustomers = customerBO.getAllCustomers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<CustomerTM> customers = FXCollections.observableArrayList(allCustomers);
        tblCustomers.setItems(customers);
    }

    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/com/myorg/dummy/pos/view/MainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    @FXML
    private void btnSave_OnAction(ActionEvent event) {
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();

        // Validation
        if (name.trim().length() == 0 || address.trim().length() == 0) {
            new Alert(Alert.AlertType.ERROR, "Customer Name, Address can't be empty", ButtonType.OK).show();
            return;
        }

        if (btnSave.getText().equals("Save")) {

            try {
                customerBO.saveCustomer(txtCustomerId.getText(),
                        txtCustomerName.getText(),
                        txtCustomerAddress.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            btnAddNew_OnAction(event);
        } else {
            CustomerTM selectedItem = tblCustomers.getSelectionModel().getSelectedItem();
            try {
                customerBO.updateCustomer(txtCustomerName.getText(), txtCustomerAddress.getText(), selectedItem.getId());
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer", ButtonType.OK).show();
            }
            tblCustomers.refresh();
            btnAddNew_OnAction(event);
        }
        loadAllCustomers();
    }

    @FXML
    private void btnDelete_OnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this customer?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            CustomerTM selectedItem = tblCustomers.getSelectionModel().getSelectedItem();

            try {
                customerBO.deleteCustomer(selectedItem.getId());
                tblCustomers.getItems().remove(selectedItem);
                tblCustomers.getSelectionModel().clearSelection();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to delete the customer", ButtonType.OK).show();
            }
        }
    }

    @FXML
    private void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        tblCustomers.getSelectionModel().clearSelection();
        txtCustomerName.setDisable(false);
        txtCustomerAddress.setDisable(false);
        txtCustomerName.requestFocus();
        btnSave.setDisable(false);

        // Generate a new id
        try {
            txtCustomerId.setText(customerBO.getNewCustomerId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
