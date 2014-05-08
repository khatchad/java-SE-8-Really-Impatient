package org.danielchesters.javase8.chapter3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

/**
 * Created by daniel on 08/05/14.
 */
@FunctionalInterface
interface ColorTransformer {
    Color apply(int x, int y, Color colorAtXY);
}

//Exercise 12
class LatentImage {
    private Image in;
    private List<ColorTransformer> pendingOperations;

    public static LatentImage from(Image in) {
        LatentImage result = new LatentImage();
        result.in = in;
        result.pendingOperations = new ArrayList<>();
        return result;
    }

    LatentImage transform(UnaryOperator<Color> f) {
        pendingOperations.add((x,y,c) -> f.apply(c));
        return this;
    }

    LatentImage transform(ColorTransformer f) {
        pendingOperations.add(f);
        return this;
    }

    public Image toImage() {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                Color c = in.getPixelReader().getColor(x, y);
                for (ColorTransformer f : pendingOperations) {
                    c = f.apply(x, y, c);
                }
                out.getPixelWriter().setColor(x, y, c);
            }
        return out;
    }
}

//Exercise 5
//Ctrl-c Ctrl-v from source code of book
//The solution is in from source code of Chapter 3 section 3 on the website of the author
public class ImageTransform extends Application {

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

    //Exercise 6
    public static <T> Image transform(Image in, BiFunction<Color, T, Color> f, T arg) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(
                width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                out.getPixelWriter().setColor(x, y,
                        f.apply(in.getPixelReader().getColor(x, y), arg));
        return out;
    }

    //Exercise 8
    public static ColorTransformer frameBorder(Image image, int thickness, Color color) {
        return (x, y, c) -> x < thickness || x > image.getWidth() - thickness
                || y < thickness || y > image.getHeight() - thickness ? color : c;
    }

    //Exercise 10 : not really code

    //Exercise 11
    public static ColorTransformer compose(ColorTransformer transformerAfter, ColorTransformer transformerBefore) {
        return (x, y, c) -> transformerAfter.apply(x, y, transformerBefore.apply(x, y, c));
    }

    public static ColorTransformer convert(UnaryOperator<Color> f) {
        return (x, y, c) -> f.apply(c);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("queen-mary.png"));
        Image brightenedImage = transform(image, Color::brighter);
        Image image2 = transform(image,
                (x, y, c) -> x < 10 || x > image.getWidth() - 10
                        || y < 10 || y > image.getHeight() - 10 ? Color.GRAY : c
        );

        Image image2bis = transform(image, frameBorder(image, 15, Color.BLUE));

        Image image3 = transform(image, (Color c, Double d) -> c.deriveColor(0, 1, d, 1), 1.2);

        Image imageExercise10 = transform(image, compose(frameBorder(image, 10, Color.GRAY), convert(Color::brighter)));

        stage.setScene(new Scene(new HBox(new ImageView(image), new ImageView(brightenedImage), new ImageView(image2), new ImageView(imageExercise10))));
        stage.show();
    }
}
