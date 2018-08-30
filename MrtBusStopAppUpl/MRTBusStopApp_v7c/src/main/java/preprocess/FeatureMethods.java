package preprocess;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;

import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.GeometryBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;

import models.StraightLineObject;

public class FeatureMethods {
	static GeometryFactory gf = JTSFactoryFinder.getGeometryFactory();
	static GeometryBuilder gb = new GeometryBuilder(gf);
	static SimpleFeatureType TYPE;
	static DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmm");
	static Calendar cal = Calendar.getInstance();
	static String todayDateTime = df.format(cal.getTime());
	static final String OUTPUT_SHAPEFILE = "BusStopsNearMrt_" + todayDateTime + ".shp";

	public static SimpleFeatureType getSFType() throws SchemaException, NoSuchAuthorityCodeException, FactoryException {

		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();

		// throws NoSuchAuthorityCodeException, FactoryException
		CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:4326");

		builder.setName("BusStopMrtAssoc");
		builder.setCRS(sourceCRS);

		builder.add("the_geom", MultiLineString.class);
		builder.add("nameStn", String.class);
		builder.add("stopNum", String.class);
		builder.add("distance", java.math.BigDecimal.class);

		TYPE = builder.buildFeatureType();

		return TYPE;

		// http://docs.geotools.org/latest/userguide/tutorial/feature/csv2shp.html
		// under "Another way to build a SimpleFeatureType"
		// SimpleFeatureTypeBuilder should be used over DataUtilities.createType
		// from org.geotools.data.DataUtilities
		// DataUtilities.createType throws Schema Exception
		/*
		 * TYPE = DataUtilities.createType("test", "line",
		 * "the_geom:MultiLineString:srid=4326," + "nameStn:String," + "stopNum:String,"
		 * + "distance:java.math.BigDecimal");
		 * 
		 * return TYPE;
		 */
		// more examples when using DataUtilities
		/*
		 * TYPE = DataUtilities.createType("test", "line",
		 * "the_geom:MultiLineString:srid=4326," + "nameStn:String," +
		 * "stopNum:Integer," + "distance:java.math.BigDecimal," + "svcNum:String," +
		 * "direction:Integer");
		 */
		/*
		 * TYPE = DataUtilities.createType("test", "line",
		 * "the_geom:MultiLineString:srid=4326," + "nameStn:String," +
		 * "stopNum:Integer," + "stopName:String," + "distance:Double," +
		 * "svcNum:String," + "direction:Integer");
		 */
	}

	public static List<SimpleFeature> createListSF(List<StraightLineObject> lineFeatureList) {

		List<SimpleFeature> lineCollection = new ArrayList<>();

		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder((SimpleFeatureType) TYPE);

		BigDecimal bdLoop;

		for (StraightLineObject sLoop : lineFeatureList) {
			com.vividsolutions.jts.geom.Coordinate[] coordBusArray = sLoop.getBusStopCoord().getCoordinates();
			com.vividsolutions.jts.geom.Coordinate[] coordMrtArray = sLoop.getMrtStationCoord().getCoordinates();

			// array of xy points to be used by GeometryBuilder.lineString
			double[] interimLineString = { coordBusArray[0].x, coordBusArray[0].y, coordMrtArray[0].x,
					coordMrtArray[0].y };

			LineString lineLoop = gb.lineString(interimLineString);
			MultiLineString mlsLoop = gb.multiLineString(lineLoop);

			bdLoop = new BigDecimal(sLoop.getDistance()).setScale(1, RoundingMode.HALF_UP);

			featureBuilder.add(mlsLoop);
			featureBuilder.add(sLoop.getStationName());
			featureBuilder.add(sLoop.getPOINT_NO());
			featureBuilder.add(bdLoop);

			SimpleFeature feature = featureBuilder.buildFeature(null);

			lineCollection.add(feature);
		}
		return lineCollection;
	}

	public static void writeOutShapeFile(SimpleFeatureType simpleFeatureType, List<SimpleFeature> input)
			throws MalformedURLException, IOException {

		// select a location to save the shape file
		JFileDataStoreChooser chooser = new JFileDataStoreChooser("shp");
		chooser.setDialogTitle("Save shapefile");
		chooser.setCurrentDirectory(new File("."));
		chooser.setSelectedFile(new File(OUTPUT_SHAPEFILE));
		int saveDialogOptionUsed = chooser.showSaveDialog(null);
		File newFile = chooser.getSelectedFile();

		if (saveDialogOptionUsed == JFileChooser.CANCEL_OPTION) {
			System.out.println("Cancel was clicked. Shapefile will not be saved.");
			return;
		}
		if (saveDialogOptionUsed == JFileChooser.ERROR_OPTION) {
			System.out
					.println("Error occurred or \"Save shapefile\" window was dismissed. Shapefile will not be saved.");
			return;
		}

		if (newFile == null) {
			System.out.println("No file or no file location was chosen to save the shapefile.");
			return;
		}

		ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

		Map<String, Serializable> params = new HashMap<>();
		params.put("url", newFile.toURI().toURL());
		params.put("create spatial index", Boolean.TRUE);

		ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);

		newDataStore.createSchema(simpleFeatureType);

		String typeName = newDataStore.getTypeNames()[0];
		SimpleFeatureSource testSource = newDataStore.getFeatureSource(typeName);

		SimpleFeatureStore sfStore = (SimpleFeatureStore) testSource;

		SimpleFeatureCollection collection = new ListFeatureCollection(simpleFeatureType, input);

		// starts the transaction to write out the shapefile
		Transaction transaction = new DefaultTransaction("create");
		sfStore.setTransaction(transaction);
		try {
			sfStore.addFeatures(collection);
			transaction.commit();
		} catch (Exception problem) {
			problem.printStackTrace();
			transaction.rollback();
		} finally {
			transaction.close();
		}
	}

}
