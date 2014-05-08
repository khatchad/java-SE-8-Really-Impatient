package org.danielchesters.javase8.org.danielchesters.javase8.chapter3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.function.UnaryOperator;

/**
 * Created by daniel on 08/05/14.
 */
@FunctionalInterface
interface ColorTransformer {
    Color apply(int x, int y, Color colorAtXY);
}

//Ctrl-c Ctrl-v from source code of book
//The solution is in from source code of Chapter 3 section 3 on the website of the author
public class Exercise5 extends Application {

    public static Image transform(Image in, UnaryOperator<Color> f) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(
                width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                out.getPixelWriter().setColor(x, y,
                        f.apply(in.getPixelReader().getColor(x, y)));
        return out;
    }

    public static Image transform(Image in, ColorTransformer f) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(
                width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                out.getPixelWriter().setColor(x, y,
                        f.apply(x, y, in.getPixelReader().getColor(x, y)));
        return out;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("queen-mary.png"));
        Image brightenedImage = transform(image, Color::brighter);
        Image image2 = transform(image,
                (x, y, c) -> x < 10 || x > image.getWidth() - 10
                        || y < 10 || y > image.getHeight() - 10 ? Color.GRAY : c
        );

        stage.setScene(new Scene(new HBox(new ImageView(image), new ImageView(brightenedImage), new ImageView(image2))));
        stage.show();
    }
}
