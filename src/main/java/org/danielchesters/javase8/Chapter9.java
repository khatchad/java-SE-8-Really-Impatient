package org.danielchesters.javase8;

import java.util.Objects;

/**
 * Created by daniel on 17/05/14.
 */
public class Chapter9 {
    //Exercise 9 & 10
    public class LabeledPoint {
        private String label;
        private int x;
        private int y;

        @Override
        public boolean equals(Object otherObject) {
            if (this == otherObject) return true;
            if (otherObject == null) return false;
            if (getClass() != otherObject.getClass()) return false;
            LabeledPoint otherLabeledPoint = (LabeledPoint) otherObject;
            return Objects.equals(label, otherLabeledPoint.label) && Objects.equals(x, otherLabeledPoint.x) && Objects.equals(y, otherLabeledPoint.y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(label, x, y);
        }
    }

}
