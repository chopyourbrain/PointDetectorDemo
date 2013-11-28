package de.lmu.dbs.ifi.jfeaturelib.examples;

import de.lmu.ifi.dbs.jfeaturelib.Progress;
import de.lmu.ifi.dbs.jfeaturelib.features.Haralick;
import ij.process.ColorProcessor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;

/**
 * This example demonstrates the use of a progress listener
 *
 * @author Franz
 */
public class StatusListenerDemo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        // load the image
        File f = new File(StatusListenerDemo.class.getResource("/test.jpg").toURI());
        ColorProcessor image = new ColorProcessor(ImageIO.read(f));

        // initialize the descriptor, attach the listener and run
        Haralick descriptor = new Haralick();
        descriptor.addPropertyChangeListener(new Listener());
        descriptor.run(image);
    }

    static class Listener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            // Avoid anything that uses a lot of CPU-cycles as this is called from the same thread as the feature extraction.
            // Especially avoid updating any GUI elements from here directly and use SwingUtilities.invokeLater(...)
            // http://docs.oracle.com/javase/7/docs/api/javax/swing/SwingUtilities.html#invokeLater(java.lang.Runnable)
            //
            // Otherwise: Blame neither me nor Java for slow feature extraction as this might turn out to be really 
            // embarassing for you
            if (evt.getNewValue() instanceof Progress) {
                Progress p = (Progress) evt.getNewValue();
                System.out.println(p.getProgress() + "%: " + p.getMessage());
            }
        }
    }
}
