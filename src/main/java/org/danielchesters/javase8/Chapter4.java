package org.danielchesters.javase8;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by daniel on 10/05/14.
 */
public class Chapter4 {
    //Exercise 5
    public static <T, R> ObservableValue<R> observe(Function<T, R> function, ObservableValue<T> observableValue) {
        return new SimpleObjectProperty<R>(function.apply(observableValue.getValue()));
    }

    public static <T, U, R> ObservableValue<R> observe(BiFunction<T, U, R> function, ObservableValue<T> observableValue1, ObservableValue<U> observableValue2) {
        return new SimpleObjectProperty<R>(function.apply(observableValue1.getValue(), observableValue2.getValue()));
    }

}
