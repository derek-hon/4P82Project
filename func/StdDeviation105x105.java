package ec.Project4P82.func;

import ec.*;
import ec.Project4P82.*;
import ec.gp.*;
import ec.util.*;

/**
 * @author Derek Hon
 * @version 1.0
 */

public class StdDeviation105x105 extends GPNode {
    public String toString() {
        return "std-deviation105x105";
    }
    public int expectedChildren() {
        return 0;
    }

    public void eval(final EvolutionState state,
                     final int thread,
                     final GPData input,
                     final ADFStack stack,
                     final GPIndividual individual,
                     final Problem problem) {
        CVProjectData cvd = ((CVProjectData) (input));
        cvd.d = ((CVProjectGP) problem).currentValue[25];
    }
}



