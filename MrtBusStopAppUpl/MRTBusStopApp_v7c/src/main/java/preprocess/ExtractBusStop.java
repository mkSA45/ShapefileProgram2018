package preprocess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.feature.simple.SimpleFeature;

import com.vividsolutions.jts.geom.Geometry;

import models.BusStop;

public class ExtractBusStop {

	public static List<BusStop> getBusStopList() throws IOException {

		SimpleFeatureIterator sfi1;
		String isNameNull;
		String isAttrHighway;

		// JFileChooser csvSelection = new JFileChooser();
		// Display a data store file chooser dialog for shapefiles
		JFileDataStoreChooser csvSelection = new JFileDataStoreChooser("shp");
		csvSelection.setDialogTitle("Choose the bus file");
		csvSelection.setCurrentDirectory(new File("."));
		int csvSelectBool = csvSelection.showDialog(null, "Select");
		csvSelection.setVisible(true);
		File interimFile = csvSelection.getSelectedFile();
		if (csvSelectBool == JFileChooser.CANCEL_OPTION) {
			System.out.println("Cancel was clicked. No bus file loaded.");
			return null;
		}
		if (csvSelectBool == JFileChooser.ERROR_OPTION) {
			System.out.println("Error occurred or \"Choose the bus file\" window was dismissed. No bus file loaded.");
			return null;
		}
		if (interimFile == null) {
			System.out.println("No bus pattern file was chosen.");
			return null;
		}

		List<BusStop> busStops = new ArrayList<BusStop>();

		FileDataStore store = FileDataStoreFinder.getDataStore(interimFile);
		SimpleFeatureSource featureSource = store.getFeatureSource();

		Style style = SLD.createSimpleStyle(featureSource.getSchema());
		Layer layer = new FeatureLayer(featureSource, style);

		FeatureLayer layer2 = (FeatureLayer) layer;

		SimpleFeatureCollection fCollection2 = layer2.getSimpleFeatureSource().getFeatures();

		// SimpleFeatureIterator has to be closed once use of it is complete
		sfi1 = fCollection2.features();

		// copies out records in shp file where "highway" column contains the word
		// "bus_stop"
		// needed as input to create List of StraightLineObject containing MRT station
		// name & bus stop id
		try {
			while (sfi1.hasNext()) {
				SimpleFeature sf = sfi1.next();
				isAttrHighway = (String) sf.getAttribute("highway");
				isNameNull = (String) sf.getAttribute("asset_ref");
				Geometry gLoop = (Geometry) sf.getDefaultGeometry();

				if (isNameNull != null && !isNameNull.isEmpty() && isAttrHighway.contains("bus_stop")) {

					BusStop busTemp = new BusStop(isNameNull, gLoop);

					busStops.add(busTemp);

					isNameNull = null;
				}
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("ExtractBusStop.getBusStopList() has encountered error.");
			e.printStackTrace();
			throw e;
		} finally {
			sfi1.close();
		}

		return busStops;
	}

}
