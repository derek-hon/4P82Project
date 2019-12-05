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
 * Neg.java
 * 
 * Created: Mon Apr 23 17:15:35 EDT 2012
 * By: Sean Luke

 <p>This function appears in the Keijzer function set, and is (0 - x)
 <p>M. Keijzer. Improving Symbolic Regression with Interval Arithmetic and Linear Scaling. In <i>Proc. EuroGP.</i> 2003.
*/

/**
 * @author Derek Hon
 * @version 1.0 
 */

public class Neg extends GPNode
    {
    public String toString() { return "0-"; }

    public int expectedChildren() { return 1; }

    public void eval(final EvolutionState state,
        final int thread,
        final GPData input,
        final ADFStack stack,
        final GPIndividual individual,
        final Problem problem)
        {
            CVProjectData cvd = ((CVProjectData)(input));

            children[0].eval(state,thread,input,stack,individual,problem);
            cvd.d = 0.0 - cvd.d;
        }
    }



