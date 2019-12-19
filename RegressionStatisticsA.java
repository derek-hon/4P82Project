/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.Project4P82;

import ec.*;
import ec.gp.*;
import ec.gp.koza.*;
import ec.simple.*;
import ec.util.*;

public class RegressionStatisticsA extends SimpleStatistics {

    public static final String P_DO_GENERATION = "do-generation";
    public static final String P_DO_MESSAGE = "do-message";

    boolean warned = false;

    public boolean doGeneration;
    public boolean doMessage;

    public Individual bestIndividual = null;

    public Individual[] bestIndividuals;

    public Individual getBestIndividual() {
        return bestIndividual;
    }

    public Individual[] getBestIndividuals() {
        return bestIndividuals;
    }

    public void setup(final EvolutionState state, final Parameter base) {
        super.setup(state, base);

        doGeneration = state.parameters.getBoolean(base.push(P_DO_GENERATION), null, true);
        doMessage = state.parameters.getBoolean(base.push(P_DO_MESSAGE), null, true);
    }

    @Override
    public void postEvaluationStatistics(final EvolutionState state) {
        super.postEvaluationStatistics(state);

        // for now we just print the best fitness per subpopulation.
//        bestIndividuals = new Individual[state.population.subpops.length];
        Individual[] best_i = new Individual[state.population.subpops.length];  // quiets compiler complaints
        for (int x = 0; x < state.population.subpops.length; x++) {
            best_i[x] = state.population.subpops[x].individuals[0];
            for (int y = 1; y < state.population.subpops[x].individuals.length; y++) {
                if (state.population.subpops[x].individuals[y] == null) {
                    if (!warned) {
                        state.output.warnOnce("Null individuals found in subpopulation");
                        warned = true;  // we do this rather than relying on warnOnce because it is much faster in a tight loop
                    }
                } else if (best_i[x] == null || state.population.subpops[x].individuals[y].fitness.betterThan(best_i[x].fitness))
                    best_i[x] = state.population.subpops[x].individuals[y];
                if (best_i[x] == null) {
                    if (!warned) {
                        state.output.warnOnce("Null individuals found in subpopulation");
                        warned = true;  // we do this rather than relying on warnOnce because it is much faster in a tight loop
                    }
                }
            }

            // now test to see if it's the new best_of_run

            if (best_of_run[x] == null || best_i[x].fitness.betterThan(best_of_run[x].fitness))
                best_of_run[x] = (Individual) (best_i[x].clone());
        }

        bestIndividuals = best_i;

        // print the best-of-generation individual
        if (doGeneration) state.output.println("\nGeneration: " + state.generation, statisticslog);
        if (doGeneration) state.output.println("Best Individual:", statisticslog);
        for (int x = 0; x < state.population.subpops.length; x++) {
            float avgStandardizedFitness = 0,
                    avgTreeSize = 0,
                    avgAdjustedFitness = 0;

            for (int i = 0 ; i < state.population.subpops[x].individuals.length ; i ++) {
                avgStandardizedFitness += ((KozaFitness) state.population.subpops[x].individuals[i].fitness).standardizedFitness();
                avgAdjustedFitness += ((KozaFitness) state.population.subpops[x].individuals[i].fitness).adjustedFitness();
            }

            avgStandardizedFitness /= state.population.subpops[x].individuals.length;
            avgAdjustedFitness /= state.population.subpops[x].individuals.length;

            if (doGeneration) {
                state.output.println (
                        "Adjusted Fitness: " + "\t" +
                                ((KozaFitness) best_i[x].fitness).adjustedFitness() + "\t" +
                                avgAdjustedFitness + "\n" +
                                "Standardized Fitness" + "\t" +
                                ((KozaFitness) best_i[x].fitness).standardizedFitness() + "\t" +
                                avgStandardizedFitness, statisticslog);
            }
//            if (doGeneration) state.output.println("Subpopulation " + x + ":", statisticslog);
//            if (doGeneration) best_i[x].printIndividualForHumans(state, statisticslog);
            if (doMessage && !silentPrint) {
                state.output.message(
                        "Subpop " + x + " best fitness of generation" +
                                (best_i[x].evaluated ? " " : " (evaluated flag not set): "));
            }
//
            // describe the winner if there is a description
//            if (doGeneration && doPerGenerationDescription) {
//                if (state.evaluator.p_problem instanceof SimpleProblemForm)
//                    ((SimpleProblemForm) (state.evaluator.p_problem.clone())).describe(state, best_i[x], x, 0, statisticslog);
//            }
        }
    }
}