package org.danielchesters.javase8;

import java.util.Objects;

/**
 * Created by daniel on 17/05/14.
 */
public class Chapter9 {
    //Exercise 9 & 10
    public class LabeledPoint implements Comparable<LabeledPoint> {
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

        @Override
        public int compareTo(LabeledPoint point) {
            int diff = label.compareTo(point.label);
            if (diff != 0) {
                return diff;
            } else {
                diff = Integer.compare(x, point.x);
                if (diff != 0) {
                    return diff;
                } else {
                    return Integer.compare(y, point.y);
                }
            }
        }
    }

}
