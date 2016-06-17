package de.lmu.dbs.ifi.jfeaturelib.examples;

import de.lmu.ifi.dbs.jfeaturelib.features.SURF;
import de.lmu.ifi.dbs.utilities.Arrays2;
import ij.process.ColorProcessor;
import java.io.InputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import javax.imageio.ImageIO;


public class SurfDemo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        // load the image
        InputStream stream = SurfDemo.class.getClassLoader().getResourceAsStream("test.jpg");
        ColorProcessor image = new ColorProcessor(ImageIO.read(stream));

        // initialize the descriptor
        SURF descriptor = new SURF();

        // run the descriptor and extract the features
        descriptor.run(image);

        // obtain the features
        List<double[]> features = descriptor.getFeatures();

        // print the features to system out
        for (double[] feature : features) {
            System.out.println(Arrays2.join(feature, ", ", "%.5f"));
        }
    }
}
