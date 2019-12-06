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
 * Div.java
 *
 * Created: Wed Nov  3 18:26:37 1999
 * By: Sean Luke
 */

/**
 * @author Derek Hon
 * @version 1.0
 */

public class Div extends GPNode {
    public String toString() {
        return "%";
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
        CVProjectData cvd = ((CVProjectData) (input));

        // evaluate children[1] first to determine if the demoniator is 0
        children[1].eval(state, thread, input, stack, individual, problem);
        if (cvd.d == 0.0)
            // the answer is 1.0 since the denominator was 0.0
            cvd.d = 1.0;
        else {
            double result;
            result = cvd.d;

            children[0].eval(state, thread, input, stack, individual, problem);
            cvd.d = cvd.d / result;
        }
    }
}



