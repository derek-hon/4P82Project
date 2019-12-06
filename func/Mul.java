/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.Project4P82.func;

import ec.*;
import ec.Project4P82.*;
import ec.gp.*;
import ec.util.*;

/*
 * Mul.java
 *
 * Created: Wed Nov  3 18:26:37 1999
 * By: Sean Luke
 */

/**
 * @author Derek Hon
 * @version 1.0
 */

public class Mul extends GPNode {
    public String toString() {
        return "*";
    }

    public int expectedChildren() {
        return 2;
    }

    public void eval(final EvolutionState state,
                     final int thread,
                     final GPData input,
                     final ADFStack stack,
                     final GPIndividual individual,
                     final Problem problem) {
        double result;
        CVProjectData cvd = ((CVProjectData) (input));

        children[0].eval(state, thread, input, stack, individual, problem);
        result = cvd.d;

        // can't shortcut because of NaN or +-Infinity

        children[1].eval(state, thread, input, stack, individual, problem);
        cvd.d = result * cvd.d;
    }
}



