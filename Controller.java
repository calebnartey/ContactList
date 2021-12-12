package com.example.javafxhello;

import com.example.javafxhello.datamodel.Contact;
import com.example.javafxhello.datamodel.ContactData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {
    private List<Contact> contactList;

    @FXML
    private TableView<Contact> contactTableView;

    @FXML
    private TableColumn<Contact, String> firstNameDescrip;

    @FXML
    private TableColumn<Contact, String> lastNameDescrip;

    @FXML
    private TableColumn<Contact, String> phoneNumberText;

    @FXML
    private TableColumn<Contact, String> notesDescrip;


    @FXML
    private BorderPane mainBorderPane;


    public void initialize() {
        firstNameDescrip.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        lastNameDescrip.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        phoneNumberText.setCellValueFactory(new PropertyValueFactory<Contact, String>("phoneNumber"));
        notesDescrip.setCellValueFactory(new PropertyValueFactory<Contact, String>("notes"));

        contactTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
            @Override
            public void changed(ObservableValue<? extends Contact> observableValue, Contact oldValue, Contact newValue) {
                if (newValue != null) {
                    Contact item = contactTableView.getSelectionModel().getSelectedItem();
                }
            }
        });

        contactTableView.getItems().setAll(ContactData.getInstance().getContacts());
        contactTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void deleteSelectedContact() throws IOException {
        ArrayList<String> contacts = new ArrayList<>();
        for (int i = 0; i < ContactData.getInstance().getContacts().size(); i++) {
            //contacts.add(ContactData.getInstance().getContacts().get(i).toString());
            contacts.add(ContactData.getInstance().getContacts().get(i).list());
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<String>(contacts.get(1), contacts);
        dialog.setTitle("Delete Contact");
        dialog.setContentText("Choose your Contact:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        for(int i = 0; i < ContactData.getInstance().getContacts().size(); i++){
            if(result.get().equals(ContactData.getInstance().getContacts().get(i).list())){
                Contact test = ContactData.getInstance().getContacts().get(i);
                ContactData.getInstance().getContacts().remove(test);
                TableView.TableViewSelectionModel selectionModel = contactTableView.getSelectionModel();
                selectionModel.select(test);
                contactTableView.getItems().remove(test);
            }
        }
        //Once result is chosen then it will iterate through the list and delete contact from list

    }

    @FXML
    public void editItemDialog() throws IOException {
        Contact selectedContact = contactTableView.getSelectionModel().getSelectedItem();
        if(selectedContact == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the contact you want to edit");
            alert.showAndWait();
            return;
        }
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        DialogController dialogController = fxmlLoader.getController();
        dialogController.editContact(selectedContact);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            dialogController.updateContact(selectedContact);
            ContactData.getInstance().saveContacts();
        }

    }


    @FXML
    public void showNewItemDialog() throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            Contact newContact = controller.processResults();
            contactTableView.getItems().addAll(ContactData.getInstance().getContacts());
            contactTableView.getSelectionModel().select(newContact);
            System.out.println("OK pressed");
        } else {
            System.out.println("Cancel pressed");
        }

    }
}
