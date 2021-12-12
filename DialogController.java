package com.example.javafxhello;

import com.example.javafxhello.datamodel.Contact;
import com.example.javafxhello.datamodel.ContactData;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogController {

    @FXML
    private TableView<Contact> contactTableView;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextArea notesDescription;

    public Contact processResults() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String notes = notesDescription.getText().trim();

        Contact newContact = new Contact(firstName, lastName, phoneNumber, notes);
        ContactData.getInstance().addContact(newContact);

        return newContact;


    }


    public void editContact(Contact contact){
        firstNameField.setText(contact.getFirstName());
        lastNameField.setText(contact.getLastName());
        phoneNumberField.setText(contact.getPhoneNumber());
        notesDescription.setText(contact.getNotes());
    }

    public void updateContact(Contact contact){
        contact.setFirstName(firstNameField.getText());
        contact.setLastName(lastNameField.getText());
        contact.setPhoneNumber(phoneNumberField.getText());
        contact.setNotes(notesDescription.getText());
    }


}
