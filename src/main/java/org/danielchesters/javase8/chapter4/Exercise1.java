package org.danielchesters.javase8.chapter4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Created by daniel on 10/05/14.
 */
public class Exercise1 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Label label = new Label("Hello, FX");
        label.setFont(new Font(100));
        TextField textField = new TextField(label.getText());
        textField.setOnAction(event -> {
            TextField textField1 = (TextField) event.getSource();
            label.setText(textField1.getText());
            label.setWrapText(true);
        });

        stage.setScene(new Scene(new VBox(label, textField)));
        stage.setTitle("Exercise 1");
        stage.show();
    }
}
