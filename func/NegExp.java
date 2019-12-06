/*
  Copyright 2012 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.4P82Project.func;

import ec.*;
import ec.4P82Project.*;
import ec.gp.*;
import ec.util.*;

/* 
 * NegExp.java
 * 
 * Created: Mon Apr 23 17:15:35 EDT 2012
 * By: Sean Luke

 <p>This function appears in the Vladislavleva-B and Vladislavleva-C function sets, and is e^(-x)
 <p>E. Vladislavleva, G. Smits, and D. Den Hertog. Order of Nonlinearity as a Complexity Measure for Models Generated by Symbolic Regression via Pareto Genetic Programming. <i>IEEE Trans EC,</i> 13(2):333-349, 2009. */

/**
 * @author Derek Hon
 * @version 1.0
 */

public class NegExp extends GPNode {
    public String toString() {
        return "negexp";
    }

    public int expectedChildren() {
        return 1;
    }

    public void eval(final EvolutionState state,
                     final int thread,
                     final GPData input,
                     final ADFStack stack,
                     final GPIndividual individual,
                     final Problem problem) {
        CVProjectData cvd = ((CVProjectData) (input));

        children[0].eval(state, thread, input, stack, individual, problem);
        cvd.d = Math.exp(0 - cvd.d);
    }
}



