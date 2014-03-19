package org.aksw.palmetto.calculations;

import java.util.Arrays;
import java.util.Collection;

import org.aksw.palmetto.subsets.AnyAny;
import org.aksw.palmetto.subsets.OneAll;
import org.aksw.palmetto.subsets.OneAny;
import org.aksw.palmetto.subsets.SubsetCreator;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class DifferenceBasedCoherenceCalculationTest extends AbstractCalculationTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                /*
                 * word1 1 1 1
                 * 
                 * word2 0 1 1
                 * 
                 * word3 0 1 1
                 * 
                 * C_d,oneall= 1/3 * ((P(w_1|w_2,w_3)-P(w_1)) +
                 * (P(w_2|w_1,w_3)-P(w_2)) + (P(w_3|w_1,w_2)-P(w_3))) = 1/3 *
                 * ((1 - 1) + (1 - 2/3) + (1 - 2/3)) = 2/9
                 */
                { new OneAll(), 3,
                        new double[] { 0, 1.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0, 2.0 / 3.0 },
                        2.0 / 9.0 },

                /*
                 * word1 0 1 1
                 * 
                 * word2 1 0 1
                 * 
                 * word3 1 1 0
                 * 
                 * C_d,oneall= 1/3 * ((P(w_1|w_2,w_3)-P(w_1)) +
                 * (P(w_2|w_1,w_3)-P(w_2)) + (P(w_3|w_1,w_2)-P(w_3))) = 1/3 *
                 * ((0 - 2/3) + (0 - 2/3) + (0 - 2/3)) = -2/3
                 */{ new OneAll(), 3,
                        new double[] { 0, 2.0 / 3.0, 2.0 / 3.0, 1.0 / 3.0, 2.0 / 3.0, 1.0 / 3.0, 1.0 / 3.0, 0 },
                        -2.0 / 3.0 },
                /*
                 * word1 0 0 0 1
                 * 
                 * word2 0 1 0 1
                 * 
                 * word3 0 0 1 1
                 * 
                 * C_d,oneall= 1/3 * ((P(w_1|w_2,w_3)-P(w_1)) +
                 * (P(w_2|w_1,w_3)-P(w_2)) + (P(w_3|w_1,w_2)-P(w_3))) = 1/3 *
                 * ((1 - 0.25) + (1 - 0.5) + (1 - 0.5)) = 1.75/3
                 */
                { new OneAll(), 3, new double[] { 0, 0.25, 0.5, 0.25, 0.5, 0.25, 0.25, 0.25 }, 1.75 / 3.0 },
                /*
                 * word1 0 0 0 1
                 * 
                 * word2 0 1 0 1
                 * 
                 * word3 0 0 1 1
                 * 
                 * C_d,oneany= 1/9 * ((P(w_1|w_2,w_3)-P(w_1)) +
                 * (P(w_1|w_2)-P(w_1)) + (P(w_1|w_3)-P(w_1)) +
                 * (P(w_2|w_1,w_3)-P(w_2)) + (P(w_2|w_1)-P(w_2)) +
                 * (P(w_2|w_3)-P(w_2)) + (P(w_3|w_1,w_2)-P(w_3)) +
                 * (P(w_3|w_1)-P(w_3)) + (P(w_3|w_2)-P(w_3))) = 1/9 * ((1 -
                 * 0.25) + (0.5 - 0.25) + (0.5 - 0.25) + (1 - 0.5) + (1 - 0.5) +
                 * (0.5 - 0.5) + (1 - 0.5) + (1 - 0.5) + (0.5 - 0.5)) = 1/9 *
                 * (0.75 + 0.25 + 0.25 + 0.5 + 0.5 + 0 + 0.5 + 0.5 + 0) = 3.25/9
                 */
                { new OneAny(), 3, new double[] { 0, 0.25, 0.5, 0.25, 0.5, 0.25, 0.25, 0.25 }, 3.25 / 9.0 },
                /*
                 * word1 0 0 0 1
                 * 
                 * word2 0 1 0 1
                 * 
                 * word3 0 0 1 1
                 * 
                 * C_d,anyany= 1/12 * ((P(w_1|w_2,w_3)-P(w_1)) +
                 * (P(w_1|w_2)-P(w_1)) + (P(w_1|w_3)-P(w_1)) +
                 * (P(w_2|w_1,w_3)-P(w_2)) + (P(w_2|w_1)-P(w_2)) +
                 * (P(w_2|w_3)-P(w_2)) + (P(w_3|w_1,w_2)-P(w_3)) +
                 * (P(w_3|w_1)-P(w_3)) + (P(w_3|w_2)-P(w_3)) +
                 * (P(w_1,w_2|w_3)-P(w_1,w_2)) + (P(w_1,w_3|w_2)-P(w_1,w_3)) +
                 * (P(w_2,w_3|w_1)-P(w_2,w_3))) = 1/12 * ((1 - 0.25) + (0.5 -
                 * 0.25) + (0.5 - 0.25) + (1 - 0.5) + (1 - 0.5) + (0.5 - 0.5) +
                 * (1 - 0.5) + (1 - 0.5) + (0.5 - 0.5) + (0.5 - 0.25) + (0.5 -
                 * 0.25) + (1 - 0.25)) = 1/12 * (0.75 + 0.25 + 0.25 + 0.5 + 0.5
                 * + 0 + 0.5 + 0.5 + 0 + 0.25 + 0.25 + 0.75) = 4.5/12
                 */
                { new AnyAny(), 3, new double[] { 0, 0.25, 0.5, 0.25, 0.5, 0.25, 0.25, 0.25 }, 4.5 / 12.0 } });
    }

    public DifferenceBasedCoherenceCalculationTest(SubsetCreator subsetCreator, int wordsetSize,
            double[] probabilities, double expectedCoherence) {
        super(new DifferenceBasedCoherenceCalculation(), subsetCreator, wordsetSize, probabilities, expectedCoherence);
    }
}
