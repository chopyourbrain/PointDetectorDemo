package de.lmu.dbs.ifi.jfeaturelib.examples;

import de.lmu.ifi.dbs.jfeaturelib.LibProperties;
import de.lmu.ifi.dbs.jfeaturelib.features.Haralick;
import de.lmu.ifi.dbs.jfeaturelib.features.Histogram;
import de.lmu.ifi.dbs.utilities.Arrays2;
import ij.process.ColorProcessor;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * This is is a very basic class that demonstrates the usage of a descriptor with plain Java and a user defined
 * configuration
 *
 * @author Franz
 */
public class HistogramConfigDemo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        // load the image
        File f = new File(HistogramConfigDemo.class.getResource("/test.jpg").toURI());
        ColorProcessor image = new ColorProcessor(ImageIO.read(f));

        // load the properties from the default properties file
        // change the histogram to span just 2 bins
        // and let's just extract the histogram for the RED channel
        LibProperties prop = LibProperties.get();
        prop.setProperty(LibProperties.HISTOGRAMS_BINS, 2);
        prop.setProperty(LibProperties.HISTOGRAMS_TYPE, "Red");
        // after v 1.0.1 you will be able to use this:
        // prop.setProperty(LibProperties.HISTOGRAMS_TYPE, Histogram.TYPE.Red.name());

        // initialize the descriptor, set the properties and run it
        Histogram descriptor = new Histogram();
        descriptor.setProperties(prop);
        descriptor.run(image);

        // obtain the features
        List<double[]> features = descriptor.getFeatures();

        // print the features to system out
        for (double[] feature : features) {
            System.out.println(Arrays2.join(feature, ", ", "%.5f"));
        }
    }
}
