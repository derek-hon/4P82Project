package ec.Project4P82;


import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.Color;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;

/**
 * Order: x y 15x15meanfilter 19x19meanfilter 25x25
 *meanfilter 15x15min 19x19min 25x25
 *min 15x15max 19x19max 25x25
 *max 15x15standarddeviation 19x19standarddeviation 25x25
 *standarddeviation hit
 *
 * False Positive Red
 * False Negative Blue
 * True Positive Green
 * True Negative Black
 * Trained Area Purple
 */
public class PixelInfo {

    private double[][] trainInfo;
    private double[][] testInfo;
    private BufferedImage trainOne, testresult, truthOne, trainTwo, truthTwo, trainThree, truthThree, result;
    private ArrayList<Integer> xPoints, testXPoints;
    private ArrayList<Integer> yPoints, testYPoints;
    private ArrayList<Double> mean15x15, testMean15x15;
    private ArrayList<Double> mean19x19;
    private ArrayList<Double> mean25x25;
    private ArrayList<Double> min15x15;
    private ArrayList<Double> min19x19;
    private ArrayList<Double> min25x25;
    private ArrayList<Double> max15x15;
    private ArrayList<Double> max19x19;
    private ArrayList<Double> max25x25;
    private ArrayList<Double> standardDeviation15x15;
    private ArrayList<Double> standardDeviation19x19;
    private ArrayList<Double> standardDeviation25x25;
    private ArrayList<Integer> hits;
    BufferedWriter bufferedWriter = null;
    public static void main(String[] args) throws IOException {
        PixelInfo p = new PixelInfo();
    }

    public PixelInfo(boolean result) {

    }


    public PixelInfo() throws IOException {

        try {

            trainOne = ImageIO.read(new File("images/biggertrain1.png"));
            truthOne = ImageIO.read(new File("images/biggertruth1.png"));
            trainTwo = ImageIO.read(new File("images/biggertrain2.png"));
            truthTwo = ImageIO.read(new File("images/biggertruth2.png"));
            trainThree = ImageIO.read(new File("images/biggertrain3.png"));
            truthThree = ImageIO.read(new File("images/biggertruth3.png"));

            int widthOne = trainOne.getWidth(),
                    widthTwo = trainTwo.getWidth(),
                    widthThree = trainThree.getWidth(),
                    heightOne = trainOne.getHeight(),
                    heightTwo = trainTwo.getHeight(),
                    heightThree = trainThree.getHeight();

            double[][] imageOne, imageTwo, imageThree;

            imageOne = pixelInformation(widthOne, heightOne, trainOne, truthOne);
            imageTwo = pixelInformation(widthTwo, heightTwo, trainTwo, truthTwo);
            imageThree = pixelInformation(widthThree, heightThree, trainThree, truthThree);

            System.out.println(imageOne.length + " " + imageOne.length);
            System.out.println(imageTwo.length + " " + imageTwo.length);
            System.out.println(imageThree.length + " " + imageThree.length);

            new StringBuilder();
            StringBuilder fileOne;
            StringBuilder fileTwo = new StringBuilder();
            StringBuilder fileThree = new StringBuilder();

            File myFile = new File("imageOne.txt");
            Writer writer = new FileWriter(myFile);
            bufferedWriter = new BufferedWriter(writer, imageOne.length);

            for (double[] doubles : imageOne) {
                fileOne = new StringBuilder();
                for (int j = 0; j < 15; j++) {
                    fileOne.append(doubles[j]).append(" ");
                }
                fileOne.append("\n");

                bufferedWriter.write(fileOne.toString());
            }
            bufferedWriter.flush();

            File myFileTwo = new File("imageTwo.txt");
            Writer writerTwo = new FileWriter(myFileTwo);
            bufferedWriter = new BufferedWriter(writerTwo, 2*imageTwo.length);
            bufferedWriter.write(fileTwo.toString());

            for (double[] doubles : imageTwo) {
                fileTwo = new StringBuilder();
                for (int j = 0; j < 15; j++) {
                    fileTwo.append(doubles[j]).append(" ");
                }
                fileTwo.append("\n");

                bufferedWriter.write(fileTwo.toString());
            }
            bufferedWriter.flush();

            File myFileThree = new File("imageThree.txt");
            Writer writerThree = new FileWriter(myFileThree);
            bufferedWriter = new BufferedWriter(writerThree, imageThree.length);
            bufferedWriter.write(fileThree.toString());

            for (double[] doubles : imageThree) {
                fileThree = new StringBuilder();
                for (int j = 0; j < 15; j++) {
                    fileThree.append(doubles[j]).append(" ");
                }
                fileThree.append("\n");
                bufferedWriter.write(fileThree.toString());
            }

            bufferedWriter.flush();

//            bufferedWriter.close();

//            bufferedWriter.close();
            if(bufferedWriter != null){
                writer.flush();
                writerTwo.flush();
                writerThree.flush();
                bufferedWriter.close();
            }



        } catch (IOException e) {
            System.out.println(e);
        }
    }

//    private double[][] pixelInformation(int width, int height, BufferedImage image, BufferedImage truth) {
//        double[][] info = new double[width*height][15];
//        double[] filterInformation;
//
//        xPoints = new ArrayList<>();
//        yPoints = new ArrayList<>();
//        mean15x15 = new ArrayList<>();
//        mean17x17 = new ArrayList<>();
//        mean19x19 = new ArrayList<>();
//        min15x15 = new ArrayList<>();
//        min17x17 = new ArrayList<>();
//        min19x19 = new ArrayList<>();
//        max15x15 = new ArrayList<>();
//        max17x17 = new ArrayList<>();
//        max19x19 = new ArrayList<>();
//        standardDeviation15x15 = new ArrayList<>();
//        standardDeviation17x17 = new ArrayList<>();
//        standardDeviation19x19 = new ArrayList<>();
//        hits = new ArrayList<>();
//
//        DecimalFormat df = new DecimalFormat("#.####");
//
//        for (int x = 0 ; x < width ; x ++) {
//            for (int y = 0 ; y < height ; y ++) {
//                xPoints.add(x);
//                yPoints.add(y);
//
//                //15x15
//                filterInformation = filters(15, 15/2, x, y, width, height, image);
//
//                filterInformation[1]/= filterInformation[0];
//
//                filterInformation[4] = Math.sqrt(filterInformation[4]/filterInformation[0]);
//                standardDeviation15x15.add(Double.parseDouble(df.format(filterInformation[4])));
//                mean15x15.add(Double.parseDouble(df.format(filterInformation[1])));
//                min15x15.add(filterInformation[2]);
//                max15x15.add(filterInformation[3]);
//
//                //17x17
//                filterInformation = filters(17, 17/2, x, y, width, height, image);
//
//                filterInformation[1]/= filterInformation[0];
//
//                filterInformation[4] = Math.sqrt(filterInformation[4]/filterInformation[0]);
//                standardDeviation17x17.add(Double.parseDouble(df.format(filterInformation[4])));
//                mean17x17.add(Double.parseDouble(df.format(filterInformation[1])));
//                min17x17.add(filterInformation[2]);
//                max17x17.add(filterInformation[3]);
//
//                //19x19
//                filterInformation = filters(19, 19/2, x, y, width, height, image);
//
//                filterInformation[1]/= filterInformation[0];
//
//                filterInformation[4] = Math.sqrt(filterInformation[4]/filterInformation[0]);
//                standardDeviation19x19.add(Double.parseDouble(df.format(filterInformation[4])));
//                mean19x19.add(Double.parseDouble(df.format(filterInformation[1])));
//                min19x19.add(filterInformation[2]);
//                max19x19.add(filterInformation[3]);
//
//                int pixelValue = truth.getRGB(x,y);
//                if (((pixelValue>>16) & 0xff) == 255 && ((pixelValue>>8) & 0xff) == 0 && ((pixelValue & 0xff)) == 0)
//                    testHits.add(1);
//                else
//                    testHits.add(0);
//            }
//        }

//        for (int i = 0; i < max15x15.size() ; i ++) {
//            info[i] = setArrayData(i);
//        }
//
//        return info;
//    }


    private double[][] pixelInformation(int width, int height, BufferedImage image, BufferedImage truth) {
        double[][] info = new double[width*height][15];
        double[] filterInformation;

        xPoints = new ArrayList<>();
        yPoints = new ArrayList<>();
        mean15x15 = new ArrayList<>();
        mean19x19 = new ArrayList<>();
        mean25x25 = new ArrayList<>();
        min15x15 = new ArrayList<>();
        min19x19 = new ArrayList<>();
        min25x25 = new ArrayList<>();
        max15x15 = new ArrayList<>();
        max19x19 = new ArrayList<>();
        max25x25 = new ArrayList<>();
        standardDeviation15x15 = new ArrayList<>();
        standardDeviation19x19 = new ArrayList<>();
        standardDeviation25x25 = new ArrayList<>();
        hits = new ArrayList<>();

        DecimalFormat df = new DecimalFormat("#.####");

        for (int x = 0 ; x < width ; x ++) {
            for (int y = 0 ; y < height ; y ++) {
                xPoints.add(x);
                yPoints.add(y);

                //15x15
                filterInformation = filters(15, 15/2, x, y, width, height, image);

                filterInformation[1]/= filterInformation[0];

                filterInformation[4] = Math.sqrt(filterInformation[4]/filterInformation[0]);
                standardDeviation15x15.add(Double.parseDouble(df.format(filterInformation[4])));
                mean15x15.add(Double.parseDouble(df.format(filterInformation[1])));
                min15x15.add(filterInformation[2]);
                max15x15.add(filterInformation[3]);

                //19x19
                filterInformation = filters(17, 17/2, x, y, width, height, image);

                filterInformation[1]/= filterInformation[0];

                filterInformation[4] = Math.sqrt(filterInformation[4]/filterInformation[0]);
                standardDeviation19x19.add(Double.parseDouble(df.format(filterInformation[4])));
                mean19x19.add(Double.parseDouble(df.format(filterInformation[1])));
                min19x19.add(filterInformation[2]);
                max19x19.add(filterInformation[3]);

                //25x25
                filterInformation = filters(19, 19/2, x, y, width, height, image);

                filterInformation[1]/= filterInformation[0];

                filterInformation[4] = Math.sqrt(filterInformation[4]/filterInformation[0]);
                standardDeviation25x25.add(Double.parseDouble(df.format(filterInformation[4])));
                mean25x25.add(Double.parseDouble(df.format(filterInformation[1])));
                min25x25.add(filterInformation[2]);
                max25x25.add(filterInformation[3]);

                int pixelValue = truth.getRGB(x,y);
                if (((pixelValue>>16) & 0xff) == 255 && ((pixelValue>>8) & 0xff) == 0 && ((pixelValue & 0xff)) == 0)
                    hits.add(1);
                else
                    hits.add(0);
            }
        }

        for (int i = 0; i < max15x15.size() ; i ++) {
            info[i] = setArrayData(i);
        }

        return info;
    }

    private double[] setArrayData (int index) {
        double[] arrayToReturn = new double[15];
        arrayToReturn[0] = xPoints.get(index);
        arrayToReturn[1] = yPoints.get(index);
        arrayToReturn[2] = mean15x15.get(index);
        arrayToReturn[3] = mean19x19.get(index);
        arrayToReturn[4] = mean25x25.get(index);
        arrayToReturn[5] = min15x15.get(index);
        arrayToReturn[6] = min19x19.get(index);
        arrayToReturn[7] = min25x25.get(index);
        arrayToReturn[8] = max15x15.get(index);
        arrayToReturn[9] = max19x19.get(index);
        arrayToReturn[10] = max25x25.get(index);
        arrayToReturn[11] = standardDeviation15x15.get(index);
        arrayToReturn[12] = standardDeviation19x19.get(index);
        arrayToReturn[13] = standardDeviation25x25.get(index);
        arrayToReturn[14] = hits.get(index);

        return arrayToReturn;
    }


    protected double[][] getTrainInfo(){
        return trainInfo;
    }

    protected double[][] getTestInfo(){
        return testInfo;
    }

    private double[] filters(int filterSize, int distanceToLeft, int x, int y, int width, int height, BufferedImage image) {
        double avgMeanGrayscale = 0;
        int filterTopLeftX = x - distanceToLeft;
        int filterTopLeftY = y - distanceToLeft;
        int min = 0;
        int max = 0;
        ArrayList<Integer>standardDeviationMeanNumbers = new ArrayList<>();
        double standardDeviation = 0.0;
        int counter = 0;

        for (int i = 0 ; i < filterSize ; i ++) {
            if ((filterTopLeftX + i) < 0 || (filterTopLeftX + i) > (width - 1)) {
                continue;
            }
            for (int j = 0 ; j < filterSize ; j ++) {
                if ((filterTopLeftY + j) < 0 || (filterTopLeftY + j) > (height - 1))
                    continue;
                counter ++;
                int pixelValue = image.getRGB(filterTopLeftX + i, filterTopLeftY + j);
                int grayscale = (((pixelValue >> 16) & 0xff) + ((pixelValue >> 8) & 0xff) + (pixelValue & 0xff))/3;
                standardDeviationMeanNumbers.add(grayscale);
                avgMeanGrayscale += grayscale;
                if (min == 0)
                    min = grayscale;
                else
                    min = Math.min(min, grayscale);
                if (max == 0)
                    max = grayscale;
                else
                    max = Math.max(max, grayscale);
            }
        }

        for (Integer meanNumber : standardDeviationMeanNumbers) {
            standardDeviation += Math.pow((meanNumber - avgMeanGrayscale), 2);
        }

        return new double[] {counter, avgMeanGrayscale/(filterSize*filterSize), min, max, standardDeviation};
    }

    protected  void setNewImage(int[][] newPixelData, int width, int height, int imgNumber, String name) {
        result = new BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_RGB);

        for (int[] newPixelDatum : newPixelData) {
            switch (newPixelDatum[2]) {
                case 1:
                    result.setRGB(newPixelDatum[0], newPixelDatum[1], Color.RED.getRGB());
                    break;
                case 2:
                    result.setRGB(newPixelDatum[0], newPixelDatum[1], Color.BLUE.getRGB());
                    break;
                case 3:
                    result.setRGB(newPixelDatum[0], newPixelDatum[1], Color.GREEN.getRGB());
                    break;
                case 4:
                    result.setRGB(newPixelDatum[0], newPixelDatum[1], Color.BLACK.getRGB());
                    break;
                default:
                    System.out.println("error");
            }
        }

        try {
            int number = 0;
            String file = "ec/Project4P82/" + name + "Images/"+ name + "_" + number +".png";
            File temp = new File(file);
            while (temp.exists()) {
                number ++;
                file = "ec/Project4P82/" + name + "Images/"+ name + "_" + number +".png";
                temp = new File(file);
            }

            ImageIO.write(result, "png", new File(file));
        }
        catch(IOException e) {
            System.out.println("exception: " + e);
        }
    }
}