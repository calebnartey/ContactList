package com.example.javafxhello;

import com.example.javafxhello.datamodel.Contact;
import com.example.javafxhello.datamodel.ContactData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
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

        contactTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
            @Override
            public void changed(ObservableValue<? extends Contact> observable, Contact oldValue, Contact newValue) {
                if(newValue != null) {
                    Contact item = contactTableView.getSelectionModel().getSelectedItem();
                    firstNameDescrip.setText(item.getFirstName());
                    lastNameDescrip.setText(item.getFirstName());
                    phoneNumberText.setText(item.getPhoneNumber());
                    notesDescrip.setText(item.getNotes());
                }
            }
        });

        contactTableView.getItems().setAll(ContactData.getInstance().getContacts());
        contactTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        contactTableView.getSelectionModel().selectFirst();
    }



    @FXML
    public void showNewItemDialog() throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e) {
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
            firstNameDescrip.setCellValueFactory(new PropertyValueFactory<>(ContactData.getInstance().getContacts().get(0).firstNameProperty().getName()));
            lastNameDescrip.setCellValueFactory(new PropertyValueFactory<>(ContactData.getInstance().getContacts().get(0).lastNameProperty().getName()));
            phoneNumberText.setCellValueFactory(new PropertyValueFactory<>(ContactData.getInstance().getContacts().get(0).phoneNumberProperty().getName()));
            notesDescrip.setCellValueFactory(new PropertyValueFactory<>(ContactData.getInstance().getContacts().get(0).notesProperty().getName()));

            contactTableView.getColumns().setAll(firstNameDescrip, lastNameDescrip, phoneNumberText, notesDescrip);
            /*contactTableView.getItems().setAll(ContactData.getInstance().getContacts());
            contactTableView.getSelectionModel().select(newContact);*/
            System.out.println("OK pressed");
            for(int i = 0; i < ContactData.getInstance().getContacts().size(); i++){
                System.out.println(ContactData.getInstance().getContacts().get(i));
            }
        } else {
            System.out.println("Cancel pressed");
        }


    }

/*    public void processResults(){
        firstNameDescrip.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastNameDescrip.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        phoneNumberText.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        notesDescrip.setCellValueFactory(new PropertyValueFactory<>("noteDescription"));
        //add your data to the table here.
        ContactData.getInstance().addContact(new Contact("Caleb", "Nartey", "0783846", "caldnende"));
    }*/
}