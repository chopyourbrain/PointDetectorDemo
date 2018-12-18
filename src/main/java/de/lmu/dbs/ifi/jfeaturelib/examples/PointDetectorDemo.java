package de.lmu.dbs.ifi.jfeaturelib.examples;

import de.lmu.ifi.dbs.jfeaturelib.ImagePoint;
import de.lmu.ifi.dbs.jfeaturelib.edgeDetector.Susan;
import de.lmu.ifi.dbs.jfeaturelib.pointDetector.FASTCornerDetector;
import de.lmu.ifi.dbs.jfeaturelib.pointDetector.Harris;
import de.lmu.ifi.dbs.jfeaturelib.pointDetector.Moravec;
import de.lmu.ifi.dbs.jfeaturelib.pointDetector.TrajkovicHedley4N;
import ij.process.ColorProcessor;
import ij.process.ByteProcessor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

public class PointDetectorDemo {
    public static void printPixels(BufferedImage img, int x, int y, int color) {
        try {
        img.setRGB(x, y, color);
        img.setRGB(x + 1, y, color);
        img.setRGB(x, y + 1, color);
        img.setRGB(x - 1, y, color);
        img.setRGB(x, y - 1, color);
        img.setRGB(x + 1, y + 1, color);
        img.setRGB(x - 1, y - 1, color);
        img.setRGB(x + 1, y - 1, color);
        img.setRGB(x - 1, y + 1, color);}
        catch (Exception e) {
            System.out.println("Ne pokrasilos' :(");
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {

        // load the image
        File file = new File("//home//mikhail//neural//sonya//JFeatureLib-Demo//src//main//resources//test2.jpg");
        InputStream stream = StatusListenerDemo.class.getClassLoader().getResourceAsStream("test2.jpg");
        ColorProcessor image = new ColorProcessor(ImageIO.read(stream));
        // harris
        BufferedImage imgHarris = ImageIO.read(file);
        Harris harris = new Harris();
        harris.run(image);
        List<ImagePoint> imagePoint = harris.getPoints();
        for (int i = 0; i < imagePoint.size(); i++) {
            System.out.println(imagePoint.get(i).getX());
            System.out.println(imagePoint.get(i).getY());
            if (( imagePoint.get(i).getX() != 0) && (imagePoint.get(i).getX() != imagePoint.size()) && ( imagePoint.get(i).getY() != 0) && (imagePoint.get(i).getY() != imagePoint.size()) )
            printPixels(imgHarris, (int) imagePoint.get(i).getX(), (int) imagePoint.get(i).getY(), Color.RED.getRGB());
        }
        ImageIO.write(imgHarris, "jpg", new File("harris.jpg"));
        // moravec
        BufferedImage imgMoravec = ImageIO.read(file);
        Moravec moravec = new Moravec();
        moravec.run(image);
        List<ImagePoint> imagePointMoravec = moravec.getPoints();
        for (int i = 0; i < imagePointMoravec.size(); i++) {
            System.out.println(imagePointMoravec.get(i).getX());
            System.out.println(imagePointMoravec.get(i).getY());
            printPixels(imgMoravec, (int) imagePointMoravec.get(i).getX(), (int) imagePointMoravec.get(i).getY(), Color.RED.getRGB());
        }
        ImageIO.write(imgMoravec, "jpg", new File("moravec.jpg"));
        // TrajkovicHedley4N
        BufferedImage imgTrajkov = ImageIO.read(file);
        TrajkovicHedley4N trajkov = new TrajkovicHedley4N();
        trajkov.run(image);
        List<ImagePoint> imagePointTrajkov = trajkov.getPoints();
        for (int i = 0; i < imagePointTrajkov.size(); i++) {
            System.out.println(imagePointTrajkov.get(i).getX());
            System.out.println(imagePointTrajkov.get(i).getY());
            printPixels(imgTrajkov, (int) imagePointTrajkov.get(i).getX(), (int) imagePointTrajkov.get(i).getY(), Color.RED.getRGB());
        }
        ImageIO.write(imgTrajkov, "jpg", new File("trajkov.jpg"));
        // FASTCornerDetector
        BufferedImage imgFAST = ImageIO.read(file);
        FASTCornerDetector fast = new FASTCornerDetector();
        fast.run(image);
        List<ImagePoint> imagePointFast = fast.getPoints();
        for (int i = 0; i < imagePointFast.size(); i++) {
            System.out.println(imagePointFast.get(i).getX());
            System.out.println(imagePointFast.get(i).getY());
            printPixels(imgFAST, (int) imagePointFast.get(i).getX(), (int) imagePointFast.get(i).getY(), Color.RED.getRGB());
        }
        ImageIO.write(imgFAST, "jpg", new File("fast.jpg"));
        // Susan
        Susan susan = new Susan();
        susan.addPropertyChangeListener(new StatusListenerDemo.Listener());
        susan.run(image);
        ByteProcessor imagePointSusan = susan.getEdgemask();
        ImageIO.write(imagePointSusan.getBufferedImage(), "jpg", new File("susan.jpg"));
    }

}
