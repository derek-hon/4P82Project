package ec.4P82Project.func;

import ec.*;
import ec.4P82Project.*;
import ec.gp.*;
import ec.util.*;

/**
 * @author Derek Hon
 * @version 1.0
 */

public class Max15x15 extends GPNode {
    public String toString() {
        return "max15x15";
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
        cvd.d = ((CVProjectGP) problem).currentValue[8];
    }
}



