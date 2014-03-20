/**
 * Copyright (C) 2014 Michael Röder (michael.roeder@unister.de)
 *
 * Licensed under the Creative Commons Attribution-NonCommercial 4.0
 * International Public License (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://creativecommons.org/licenses/by-nc/4.0/legalcode
 *
 * Unless required by applicable law or agreed to in writing, a file
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aksw.palmetto.evaluate.correlation;

import java.util.Arrays;
import java.util.Comparator;

import org.aksw.palmetto.evaluate.correlation.KendallsTau.ValuePair;

public class KendallsTau implements RankCorrelationCalculator, Comparator<ValuePair> {

    public double calculateRankCorrelation(final double x[], final double y[]) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("The x and y array must have the same size!");
        }

        ValuePair pairs[] = new ValuePair[x.length];
        for (int i = 0; i < x.length; ++i) {
            pairs[i] = new ValuePair(x[i], y[i]);
        }
        Arrays.sort(pairs, this);

        return calculateRankCorrelation(pairs);
    }

    protected double calculateRankCorrelation(ValuePair[] pairs) {
        double concordance = 0, disconcordance = 0, boundInX = 0, boundInY = 0;
        double currentX, currentY;
        // Go through all pairs
        for (int i = 0; i < pairs.length; i++) {
            currentX = pairs[i].first;
            currentY = pairs[i].second;
            // Go through all following pairs and check the order of y
            for (int j = i + 1; j < pairs.length; j++) {
                if (pairs[j].first > currentX) {
                    if (pairs[j].second > currentY) {
                        ++concordance;
                    } else if (pairs[j].second < currentY) {
                        ++disconcordance;
                    } else {
                        ++boundInY;
                    }
                } else {
                    if (pairs[j].second != currentY) {
                        ++boundInX;
                    }
                    // else bound in X and Y, but we don't need this
                }
            }
        }

        if (((concordance + disconcordance + boundInX) == 0) || ((concordance + disconcordance + boundInY) == 0)) {
            return 0;
        } else {
            return (concordance - disconcordance)
                    / Math.sqrt((concordance + disconcordance + boundInX) * (concordance + disconcordance + boundInY));
        }
    }

    @Override
    public int compare(ValuePair pair1, ValuePair pair2) {
        double diff = pair1.first - pair2.first;
        if (diff < 0) {
            return -1;
        } else if (diff > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    protected static class ValuePair implements Comparable<ValuePair> {
        public double first;
        public double second;

        public ValuePair(double first, double second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public int compareTo(ValuePair v) {
            double diff = this.first - v.first;
            if (diff < 0) {
                return -1;
            } else if (diff > 0) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public String toString() {
            return "(" + first + "|" + second + ")";
        }
    }
}
