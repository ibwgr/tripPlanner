package view.travel;

import com.teamdev.jxmaps.MapViewOptions;
import controller.common.MainController;

import javax.swing.*;
import java.awt.*;

/**
 * Diese View zeigt dieGesamtreise im Grossformat als Google Maps dar.
 * (Es wird keine Liste angezeigt)
 *
  * @author  Dieter Biedermann
 */
public class BigMapView extends JPanel {

    MainController mainController;
    MapPolylineSingle mapView;

    public BigMapView(MainController mainController) {
        this.mainController = mainController;

        this.setLayout(new BorderLayout());

        MapViewOptions options = new MapViewOptions();
        options.importPlaces();
        mapView = new MapPolylineSingle(options, mainController);

        this.add(mapView, BorderLayout.CENTER);

    }

}
