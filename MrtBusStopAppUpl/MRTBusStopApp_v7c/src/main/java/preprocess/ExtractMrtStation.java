package preprocess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import models.ShpMrtStation;

public class ExtractMrtStation {

	static Map<String, String> mapStationNameConvert = new HashMap<String, String>();

	public static List<ShpMrtStation> getMrtList() throws IOException {

		SimpleFeatureIterator sfi1;
		List<ShpMrtStation> stationNameList = new ArrayList<ShpMrtStation>();
		String isNameNull;
		String isAttrStn;

		// display a data store file chooser dialog for shapefiles
		JFileDataStoreChooser jFileChooser = new JFileDataStoreChooser("shp");
		jFileChooser.setDialogTitle("Choose shapefile with MRT/LRT points");
		// File file = JFileDataStoreChooser.showOpenFile("shp", null);
		// File file = jFileChooser.showOpenFile("shp", null);
		jFileChooser.setCurrentDirectory(new File("."));
		int shpSelectBool = jFileChooser.showDialog(null, "Choose");
		File file = jFileChooser.getSelectedFile();
		if (shpSelectBool == JFileChooser.CANCEL_OPTION) {
			System.out.println("Cancel was clicked. No shapefile loaded.");
			return null;
		}
		if (shpSelectBool == JFileChooser.ERROR_OPTION) {
			System.out.println(
					"Error occurred or \"Choose shapefile with MRT/LRT points\" window was dismissed. No shapefile loaded.");
			return null;
		}
		if (file == null) {
			System.out.println("No MRT/LRT shapefile was chosen.");
			return null;
		}

		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		SimpleFeatureSource featureSource = store.getFeatureSource();

		Style style = SLD.createSimpleStyle(featureSource.getSchema());
		Layer layer = new FeatureLayer(featureSource, style);

		FeatureLayer layer2 = (FeatureLayer) layer;

		SimpleFeatureCollection fCollection2 = layer2.getSimpleFeatureSource().getFeatures();

		// SimpleFeatureIterator has to be closed once use of it is complete
		sfi1 = fCollection2.features();

		// copies out records in shp file where "railway" field contains the word
		// "station"
		// needed as input to create List of StraightLineObject containing MRT station
		// name & bus stop id
		try {
			while (sfi1.hasNext()) {
				SimpleFeature sf = sfi1.next();
				isNameNull = (String) sf.getAttribute("name");
				isAttrStn = (String) sf.getAttribute("railway");
				Geometry gLoop = (Geometry) sf.getDefaultGeometry();

				if (isNameNull != null && !isNameNull.isEmpty() && isAttrStn.contains("station")) {

					ShpMrtStation shpTemp = new ShpMrtStation(isNameNull, gLoop);

					stationNameList.add(shpTemp);

					isNameNull = null;
				}
			}
			ShpMrtStation woodlandsTrainChkPt = new ShpMrtStation("WOODLANDS TRAIN CHECKPT");
			stationNameList.remove(woodlandsTrainChkPt);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("ExtractMrtStation.getMrtList() has encountered error.");
			e.printStackTrace();
			throw e;
		} finally {
			sfi1.close();
		}
		return stationNameList;
	}
}
