/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.Project4P82;

import ec.util.*;
import ec.*;
import ec.gp.*;
import ec.gp.koza.*;
import ec.simple.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.util.Arrays;
import java.lang.*;

import ec.app.regression.*;

import ec.Project4P82.PixelInfo;

/**
 * CVProjectGP.java
 * Created: 05/12/2019
 * By: Derek Hon and Marshall Joseph
 **/


public class CVProjectGP extends GPProblem implements SimpleProblemForm {
    public static final String P_SEED = "seed.0";
    public static final String P_GENERALIZE = "generalize";
    public static final String P_TRAIN_SIZE = "trainSize";
    public static final String P_TEST_SIZE = "testSize";
    public static final String P_TRAIN = "train";
    public static final String P_TEST = "test";
    public static final String P_WEIGHT = "weight";
    public static final String P_TEST_NAME = "testName";

    public boolean testGeneralization;

    public File train, test;

    private static final long serialVersionUID = 1;
    public double[] currentValue = new double[2];

    private long startTime, endtime;

    public int seed;
    public int totalHits;

    private PixelInfo info;

    public int weight;
    public String outputName;

    public int resultWidth, resultHeight;

    public int trainSize, testSize;

    public double[][] pixelTrain;
    public double[][] pixelTest;
    public int[][] resultPixelData;

    public int[] trainingAnswers;
    public double[][] trainingInput;
    public int[] testingAnswers;
    public double[][] testingInput;

    public int testingSize, trainingSize;
    public double trainingPercent;

    public int testingPositive;
    public int testingNegative;

    public Random random;

    public void setup(final EvolutionState state,
                      final Parameter base) {

        super.setup(state, base);

        startTime = System.currentTimeMillis();
        totalHits = 0;

        if (!(input instanceof CVProjectData))
            state.output.fatal("GPData class must subclass from " + CVProjectGP.class,
                    base.push(P_DATA), null);

        testingPositive = 0;
        testingNegative = 0;

        seed = state.parameters.getInt(base.push(P_SEED), null, 1);
        train = state.parameters.getFile(base.push(P_TRAIN), null);
        test = state.parameters.getFile(base.push(P_TEST), null);
        trainSize = state.parameters.getInt(base.push(P_TRAIN_SIZE), null, 1);
        testSize = state.parameters.getInt(base.push(P_TEST_SIZE), null, 1);
        weight = state.parameters.getInt(base.push(P_WEIGHT), null, 1);
        outputName = state.parameters.getString(base.push(P_TEST_NAME), null);

        resultHeight = 0;
        resultWidth = 0;

        testGeneralization = state.parameters.getBoolean(base.push(P_GENERALIZE),null,false);

        String currentDirectory = System.getProperty("user.dir");
        state.output.message(currentDirectory);

        try {
            info = new PixelInfo(true);

            trainingInput = new double[trainSize][26];
            trainingAnswers = new int[trainSize];
            String dataLines[] = new String[trainSize];
            String testingLines[] = new String[testSize];
            Scanner scanTrain = new Scanner(train),
                    scanTest = new Scanner(test);

            //Scanning and storing all our training sets
            for (int x = 0; x < trainSize; x++) {

                String trainingLine = scanTrain.nextLine();
                dataLines[x]        = trainingLine;
            }

            for (int i = 0; i < dataLines.length; i++) {
                trainingAnswers[i] = Integer.parseInt(dataLines[i].substring(dataLines[i].length() - 4, dataLines[i].length() - 3));
                totalHits = (trainingAnswers[i] == 1) ? totalHits + 1 : totalHits;

                String[] parameterValues = dataLines[i].substring(0, dataLines[i].length() - 4).split(" ");

                for (int j = 0 ; j < parameterValues.length ; j ++) {
                    trainingInput[i][j] = Double.parseDouble(parameterValues[j]);
                }
            }

            if (testGeneralization) {
                resultPixelData = new int[testSize][3];
                testingAnswers = new int[testSize];
                testingInput = new double[testSize][26];
                //Scanning and storing all our testing sets
                for (int x = 0; x < testSize; x++) {
                    String testingLine  = scanTest.nextLine();
                    testingLines[x]     = testingLine;
                }

                for (int i = 0; i < testingLines.length; i++) {
                    testingAnswers[i] = Integer.parseInt(testingLines[i].substring(testingLines[i].length() - 4, testingLines[i].length() - 3));
                    if (testingAnswers[i] == 1) {
                        testingPositive ++;
                        testingAnswers[i] = 1;
                    }
                    else {
                        testingNegative ++;
                        testingAnswers[i] = 0;
                    }

                    String[] parameterValues = testingLines[i].substring(0, testingLines[i].length() - 4).split(" ");

                    for (int j = 0 ; j < parameterValues.length ; j ++) {
                        if (j == 0) {
                            if (resultWidth == 0)
                                resultWidth = (int)Double.parseDouble(parameterValues[j]);
                            else
                                resultWidth = (int)Math.max(resultWidth, Double.parseDouble(parameterValues[j]));
                        }
                        if (j == 1) {
                            if (resultHeight == 0)
                                resultHeight = (int)Double.parseDouble(parameterValues[j]);
                            else
                                resultHeight = (int)Math.max(resultHeight, Double.parseDouble(parameterValues[j]));
                        }

                        testingInput[i][j] = Double.parseDouble(parameterValues[j]);
                    }
                }

//                state.output.fatal("pos" + testingPositive + " neg" + testingNegative);
            }
        }
        catch (IOException e) {
            state.output.fatal("The file could not be read due to an IOException:\n" + e);
        }

    } //setup

    int positiveCount, negativeCount;
    int positiveWeight;
    double sum;
    int falsePositive, falseNegative;
    boolean trainingCheck = false;
    int hits = 0;

    public void testIndividual(final EvolutionState state,
                               final Individual ind,
                               final int subpopulation,
                               final int threadnum,
                               int size,
                               double[][] dataInput,
                               int[] answer) {
        CVProjectData input = (CVProjectData) (this.input);
        double result = 0.0;

        for (int y = 0; y < size; y++) {
            currentValue = dataInput[y];

            ((GPIndividual) ind).trees[0].child.eval(
                    state, threadnum, input, stack, ((GPIndividual) ind), this);

//            state.output.message(input.d + "");
//            result = currentValue[0] - input.d;
            if (size == testSize) {
                if (answer[y] == 1 && input.d >= 0.0) {
                    setResultPixelData(y, (int)dataInput[y][0], (int)dataInput[y][1], 3);
                } //true positive
                else if (answer[y] == 0 && input.d >= 0.0) {
                    setResultPixelData(y, (int)dataInput[y][0], (int)dataInput[y][1], 1);
                } //false positive
                if (answer[y] == 1 && input.d < 0.0) {
                    setResultPixelData(y, (int)dataInput[y][0], (int)dataInput[y][1], 2);
                }
                else if (answer[y] == 0 && input.d < 0.0) {
                    setResultPixelData(y, (int)dataInput[y][0], (int)dataInput[y][1], 4);
                } //true negative
            }

            if (answer[y] == 1 && input.d >= 0.0) {
                positiveWeight += weight;
                positiveCount ++;
                hits += weight;
            }
            else if (answer[y] == 0 && input.d >= 0.0) {
                falsePositive ++;
            }
            if (answer[y] == 1 && input.d < 0.0) {
                falseNegative ++;
            }
            else if (answer[y] == 0 && input.d < 0.0) {
                negativeCount ++;
                hits ++;
            } //true negative
            sum += 1.0 - (hits/((double)size + positiveWeight));
        } //training
    }

    private void setResultPixelData(int index, int x, int y, int colour) {
        resultPixelData[index][0] = x;
        resultPixelData[index][1] = y;
        resultPixelData[index][2] = colour;
    }

    /**
     *
     * @param state
     * @param ind
     * @param subpopulation
     * @param threadnum
     */
    public void evaluate(final EvolutionState state,
                         final Individual ind,
                         final int subpopulation,
                         final int threadnum) {
        if (!ind.evaluated)  // don't bother reevaluating
        {
            positiveWeight = 0;
            sum = 0.0;
            positiveCount = 0;
            negativeCount = 0;
            hits = 0;

            testIndividual(state, ind, subpopulation, threadnum, trainSize,
                    trainingInput, trainingAnswers);

            // the fitness better be KozaFitness!
            KozaFitness f = ((KozaFitness) ind.fitness);
            f.setStandardizedFitness(state, sum);
            f.hits = hits;
            f.hitPercent = (((((double)positiveCount)/testingPositive) +
                    (((double)negativeCount)/testingNegative))/2);
            ind.evaluated = true;


        }
    }
//
    public void describe(final EvolutionState state,
                         final Individual ind,
                         final int subpopulation,
                         final int threadnum,
                         final int log) {

        sum = 0.0;
        positiveWeight = 0;
        positiveCount = 0;
        negativeCount = 0;
        falsePositive = 0;
        falseNegative = 0;
        Individual bestIndividual = null;

        if (testGeneralization) {
            if (state.statistics != null &&
                    (state.statistics instanceof RegressionStatisticsA ||
                            state.statistics instanceof SimpleStatistics)) {
                if (state.statistics instanceof RegressionStatisticsA)
                    bestIndividual = ((RegressionStatisticsA)(state.statistics)).getBestIndividuals()[subpopulation];
                if (bestIndividual == null)
                    state.output.fatal("Failed to obtain best individual.");
            }
            if (bestIndividual == null) {
                state.output.fatal("Failed to obtain best individual.");
            }
            else {
                state.output.message("Found best individual from state and using it.");

                testIndividual(state, bestIndividual, subpopulation, threadnum, testSize,
                        testingInput, testingAnswers);

                KozaFitness f = ((KozaFitness) ind.fitness);
                f.setStandardizedFitness(state, sum);
                f.hits = hits;
            }
        }

        if (testGeneralization) {
            state.output.println("\n\nBest Individual Subpop: " + subpopulation + "\n", log);
            bestIndividual.printIndividualForHumans(state, log);

            state.output.println("\n\nTesting set size: " + testSize + "\n\nBest Individual's Generalization Score...\n" +
                            "\nTrue Positive: " + positiveCount +
                            "\nFalse Positive: " + falsePositive +
                            "\nFalse Negative: " + falseNegative +
                            "\nTrue Negative: " + negativeCount +
                            "\nTest Set Total Hits: " + (positiveCount + negativeCount) +
                            "\nTrue Positive percent: " + (double)positiveCount/(double)testingPositive +
                            "\nFalse Positive percent: " + (double)falsePositive/(double)testingPositive +
                            "\nTrue Negative percent: " + (double)negativeCount/(double)testingNegative +
                            "\nFalse Negative percent: " + (double)falseNegative/(double)testingNegative +
                            "\n(positive + negative)/(total positive + total negative):     " +

                            (((double)(positiveCount+negativeCount))/(testingPositive+testingNegative)) +
                            "\n((positive/total positive)+(negative/total negative))/2: " +

                            (((((double)positiveCount)/testingPositive) +
                                    (((double)negativeCount)/testingNegative))/2) +
                            "\nMax(positive/total positive,negative/total negative):    " +
                            Math.max((((double)positiveCount)/testingPositive),
                                    (((double)negativeCount)/testingNegative)),
                    log);
            info.setNewImage(resultPixelData, resultWidth, resultHeight, seed, outputName, "_subpop" + subpopulation);
        }
        endtime = System.currentTimeMillis();

        state.output.println("Total elapsed time:\nseconds: "
                + (endtime - startTime)/1000 + " milliseconds: " + (endtime - startTime), log);
    }
}