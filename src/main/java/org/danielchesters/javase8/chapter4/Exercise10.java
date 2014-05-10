package org.danielchesters.javase8.chapter4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Created by daniel on 10/05/14.
 */
public class Exercise10 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane pane = new BorderPane();
        TextField urlTextField = new TextField();
        Button backButton = new Button("←");
        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        HBox box = new HBox(backButton, urlTextField);
        pane.setTop(box);
        pane.setCenter(webView);

        urlTextField.setOnAction(event -> {
            engine.load(((TextField) event.getSource()).getText());
        });

        backButton.setOnAction(event -> {
            engine.getHistory().go(-1);
        });

        stage.setScene(new Scene(pane));
        stage.show();
    }
}
