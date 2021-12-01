package com.example.javafxhello;

import com.example.javafxhello.datamodel.ContactData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Contacts List");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void stop() throws Exception {
        try {
            ContactData.getInstance().saveContacts();

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void init() throws Exception {
        try {
            ContactData.getInstance().loadContacts();

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}