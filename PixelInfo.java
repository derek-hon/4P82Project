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
 * Order:
 * x
 * y
 * 55x55meanfilter
 * 65x65meanfilter
 * 75x75meanfilter
 * 55x55min
 * 65x65min
 * 75x75min
 * 55x55max
 * 65x65max
 * 75x75max
 * 55x55standarddeviation
 * 65x65standarddeviation
 * 75x75standarddeviation
 * hit
 *
 * 105x105
 * 95x95
 * 85x85
 * 75x75
 * 65x65
 * 55x55
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
    private BufferedImage imageTwo, testresult, truthTwo, imageFour, truthFour, trainThree, truthThree, result;
    private ArrayList<Integer> xPoints, testXPoints;
    private ArrayList<Integer> yPoints, testYPoints;
    private ArrayList<Double> mean55x55;
    private ArrayList<Double> mean65x65;
    private ArrayList<Double> mean75x75;
    private ArrayList<Double> mean85x85;
    private ArrayList<Double> mean95x95;
    private ArrayList<Double> mean105x105;

    private ArrayList<Double> min55x55;
    private ArrayList<Double> min65x65;
    private ArrayList<Double> min75x75;
    private ArrayList<Double> min85x85;
    private ArrayList<Double> min95x95;
    private ArrayList<Double> min105x105;


    private ArrayList<Double> max55x55;
    private ArrayList<Double> max65x65;
    private ArrayList<Double> max75x75;
    private ArrayList<Double> max85x85;
    private ArrayList<Double> max95x95;
    private ArrayList<Double> max105x105;

    private ArrayList<Double> standardDeviation55x55;
    private ArrayList<Double> standardDeviation65x65;
    private ArrayList<Double> standardDeviation75x75;
    private ArrayList<Double> standardDeviation85x85;
    private ArrayList<Double> standardDeviation95x95;
    private ArrayList<Double> standardDeviation105x105;

    private ArrayList<Integer> hits;
    BufferedWriter bufferedWriter = null;
    public static void main(String[] args) throws IOException {
        PixelInfo p = new PixelInfo();
    }

    public PixelInfo(boolean result) {

    }


    public PixelInfo() throws IOException {

        try {

            imageTwo = ImageIO.read(new File("images/img2test.png"));
            truthTwo = ImageIO.read(new File("images/img2testtruth.png"));
            imageFour = ImageIO.read(new File("images/img4train.png"));
            truthFour = ImageIO.read(new File("images/img4truth.png"));
//            trainThree = ImageIO.read(new File("images/biggertrain3.png"));
//            truthThree = ImageIO.read(new File("images/biggertruth3.png"));

            int widthTwo = imageTwo.getWidth(),
                    widthFour = imageFour.getWidth(),
//                    widthThree = trainThree.getWidth(),
                    heightTwo = imageTwo.getHeight(),
                    heightFour = imageFour.getHeight();
//                    heightThree = trainThree.getHeight();

            double[][] imgInfoTwo, imgInfoFour;

            imgInfoTwo = pixelInformation(widthTwo, heightTwo, imageTwo, truthTwo);
            imgInfoFour = pixelInformation(widthFour, heightFour, imageFour, truthFour);
//            imageThree = pixelInformation(widthThree, heightThree, trainThree, truthThree);

            new StringBuilder();
            StringBuilder fileOne;
            StringBuilder fileTwo = new StringBuilder();
            StringBuilder fileThree = new StringBuilder();

            File myFile = new File("imageOne.txt");
            Writer writer = new FileWriter(myFile);
            bufferedWriter = new BufferedWriter(writer, imgInfoTwo.length);

            for (double[] doubles : imgInfoTwo) {
                fileOne = new StringBuilder();
                for (int j = 0; j < 27; j++) {
                    fileOne.append(doubles[j]).append(" ");
                }
                fileOne.append("\n");

                bufferedWriter.write(fileOne.toString());
            }
            bufferedWriter.flush();

            File myFileTwo = new File("imageTwo.txt");
            Writer writerTwo = new FileWriter(myFileTwo);
            bufferedWriter = new BufferedWriter(writerTwo, imgInfoFour.length);
            bufferedWriter.write(fileTwo.toString());

            for (double[] doubles : imgInfoFour) {
                fileTwo = new StringBuilder();
                for (int j = 0; j < 27; j++) {
                    fileTwo.append(doubles[j]).append(" ");
                }
                fileTwo.append("\n");

                bufferedWriter.write(fileTwo.toString());
            }
            bufferedWriter.flush();

            if(bufferedWriter != null){
                writer.flush();
                writerTwo.flush();
                bufferedWriter.close();
            }



        } catch (IOException e) {
            System.out.println(e);
        }
    }


    private double[][] pixelInformation(int width, int height, BufferedImage image, BufferedImage truth) {
        double[][] info = new double[width*height][27];
        double[] filterInformation;

        xPoints = new ArrayList<>();
        yPoints = new ArrayList<>();

        mean55x55 = new ArrayList<>();
        mean65x65 = new ArrayList<>();
        mean75x75 = new ArrayList<>();
        mean85x85 = new ArrayList<>();
        mean95x95 = new ArrayList<>();
        mean105x105 = new ArrayList<>();

        min55x55 = new ArrayList<>();
        min65x65 = new ArrayList<>();
        min75x75 = new ArrayList<>();
        min85x85 = new ArrayList<>();
        min95x95 = new ArrayList<>();
        min105x105 = new ArrayList<>();

        max55x55 = new ArrayList<>();
        max65x65 = new ArrayList<>();
        max75x75 = new ArrayList<>();
        max85x85 = new ArrayList<>();
        max95x95 = new ArrayList<>();
        max105x105 = new ArrayList<>();

        standardDeviation55x55 = new ArrayList<>();
        standardDeviation65x65 = new ArrayList<>();
        standardDeviation75x75 = new ArrayList<>();
        standardDeviation85x85 = new ArrayList<>();
        standardDeviation95x95 = new ArrayList<>();
        standardDeviation105x105 = new ArrayList<>();

        hits = new ArrayList<>();

        DecimalFormat df = new DecimalFormat("#.####");

        for (int x = 0 ; x < width ; x ++) {
            for (int y = 0 ; y < height ; y ++) {
                xPoints.add(x);
                yPoints.add(y);

                //55x55
                filterInformation = filters(55, 55/2, x, y, width, height, image);

                filterInformation[1]/= filterInformation[0];

                filterInformation[4] = Math.sqrt(filterInformation[4]/filterInformation[0]);
                standardDeviation55x55.add(Double.parseDouble(df.format(filterInformation[4])));
                mean55x55.add(Double.parseDouble(df.format(filterInformation[1])));
                min55x55.add(filterInformation[2]);
                max55x55.add(filterInformation[3]);

                //65x65
                filterInformation = filters(65, 65/2, x, y, width, height, image);

                filterInformation[1]/= filterInformation[0];

                filterInformation[4] = Math.sqrt(filterInformation[4]/filterInformation[0]);
                standardDeviation65x65.add(Double.parseDouble(df.format(filterInformation[4])));
                mean65x65.add(Double.parseDouble(df.format(filterInformation[1])));
                min65x65.add(filterInformation[2]);
                max65x65.add(filterInformation[3]);

                //75x75
                filterInformation = filters(75, 75/2, x, y, width, height, image);

                filterInformation[1]/= filterInformation[0];

                filterInformation[4] = Math.sqrt(filterInformation[4]/filterInformation[0]);
                standardDeviation75x75.add(Double.parseDouble(df.format(filterInformation[4])));
                mean75x75.add(Double.parseDouble(df.format(filterInformation[1])));
                min75x75.add(filterInformation[2]);
                max75x75.add(filterInformation[3]);

                //85x85
                filterInformation = filters(85, 85/2, x, y, width, height, image);

                filterInformation[1]/= filterInformation[0];

                filterInformation[4] = Math.sqrt(filterInformation[4]/filterInformation[0]);
                standardDeviation85x85.add(Double.parseDouble(df.format(filterInformation[4])));
                mean85x85.add(Double.parseDouble(df.format(filterInformation[1])));
                min85x85.add(filterInformation[2]);
                max85x85.add(filterInformation[3]);

                //95x95
                filterInformation = filters(95, 95/2, x, y, width, height, image);

                filterInformation[1]/= filterInformation[0];

                filterInformation[4] = Math.sqrt(filterInformation[4]/filterInformation[0]);
                standardDeviation95x95.add(Double.parseDouble(df.format(filterInformation[4])));
                mean95x95.add(Double.parseDouble(df.format(filterInformation[1])));
                min95x95.add(filterInformation[2]);
                max95x95.add(filterInformation[3]);

                //105x105
                filterInformation = filters(105, 105/2, x, y, width, height, image);

                filterInformation[1]/= filterInformation[0];

                filterInformation[4] = Math.sqrt(filterInformation[4]/filterInformation[0]);
                standardDeviation105x105.add(Double.parseDouble(df.format(filterInformation[4])));
                mean105x105.add(Double.parseDouble(df.format(filterInformation[1])));
                min105x105.add(filterInformation[2]);
                max105x105.add(filterInformation[3]);

                int pixelValue = truth.getRGB(x,y);
                if (((pixelValue>>16) & 0xff) == 255 && ((pixelValue>>8) & 0xff) == 0 && ((pixelValue & 0xff)) == 0)
                    hits.add(1);
                else
                    hits.add(0);
            }
        }

        for (int i = 0; i < max55x55.size() ; i ++) {
            info[i] = setArrayData(i);
        }

        return info;
    }

    private double[] setArrayData (int index) {
        double[] arrayToReturn = new double[27];
        arrayToReturn[0] = xPoints.get(index);
        arrayToReturn[1] = yPoints.get(index);

        arrayToReturn[2] = mean55x55.get(index);
        arrayToReturn[3] = mean65x65.get(index);
        arrayToReturn[4] = mean75x75.get(index);
        arrayToReturn[5] = mean85x85.get(index);
        arrayToReturn[6] = mean95x95.get(index);
        arrayToReturn[7] = mean105x105.get(index);

        arrayToReturn[8] = min55x55.get(index);
        arrayToReturn[9] = min65x65.get(index);
        arrayToReturn[10] = min75x75.get(index);
        arrayToReturn[11] = min85x85.get(index);
        arrayToReturn[12] = min95x95.get(index);
        arrayToReturn[13] = min105x105.get(index);

        arrayToReturn[14] = max55x55.get(index);
        arrayToReturn[15] = max65x65.get(index);
        arrayToReturn[16] = max75x75.get(index);
        arrayToReturn[17] = max85x85.get(index);
        arrayToReturn[18] = max95x95.get(index);
        arrayToReturn[19] = max105x105.get(index);

        arrayToReturn[20] = standardDeviation55x55.get(index);
        arrayToReturn[21] = standardDeviation65x65.get(index);
        arrayToReturn[22] = standardDeviation75x75.get(index);
        arrayToReturn[23] = standardDeviation85x85.get(index);
        arrayToReturn[24] = standardDeviation95x95.get(index);
        arrayToReturn[25] = standardDeviation105x105.get(index);

        arrayToReturn[26] = hits.get(index);

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

    protected  void setNewImage(int[][] newPixelData, int width, int height, int imgNumber, String name, String imgName) {
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
            String file = "ec/Project4P82/" + name + "Images/"+ name + "_" + imgName + "_" + number +".png";
            File temp = new File(file);
            while (temp.exists()) {
                number ++;
                file = "ec/Project4P82/" + name + "Images/"+ name + "_" + imgName +  "_" + number +".png";
                temp = new File(file);
            }

            ImageIO.write(result, "png", new File(file));
        }
        catch(IOException e) {
            System.out.println("exception: " + e);
        }
    }
}