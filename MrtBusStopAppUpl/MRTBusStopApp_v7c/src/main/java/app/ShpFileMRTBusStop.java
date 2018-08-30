package app;

import java.io.IOException;
import java.util.List;

import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import models.BusStop;
import models.ShpMrtStation;
import models.StraightLineObject;
import preprocess.DistanceMethods;
import preprocess.ExtractBusStop;
import preprocess.ExtractMrtStation;
import preprocess.FeatureMethods;

public class ShpFileMRTBusStop {

	static long _startTime = -1;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		_startTime = System.currentTimeMillis();

		try {

			List<ShpMrtStation> stationNameList = null;
			List<BusStop> distinctBusStopList = null;
			List<StraightLineObject> lineFeatureList = null;

			writeMessage("before ExtractMrtStation.getMrtList()");
			// create list of mrt station points
			stationNameList = ExtractMrtStation.getMrtList();
			writeMessage("Completed ExtractMrtStation.getMrtList()");

			if (stationNameList == null) {
				return;
			}

			// extract list of bus stops, keep only distinct bus stops
			try {
				distinctBusStopList = ExtractBusStop.getBusStopList();
			} catch (Exception e) {
				System.out.println("error with ExtractBusStop method");
				return;
			}
			writeMessage("Completed ExtractBusStop.getBusStopList()");

			if (distinctBusStopList == null) {
				return;
			}

			// create list of StraightLineObject for MRT and BusStop that are close enough
			lineFeatureList = DistanceMethods.createSLObjList(distinctBusStopList, stationNameList);
			System.out.println(lineFeatureList.size());
			writeMessage("Completed DistanceMethods.createSLObjList()");

			// necessary input for ShapefileDataStore and ListFeatureCollection
			// in FeatureMethods.createListSF()
			SimpleFeatureType sftinMain = FeatureMethods.getSFType();

			// write out the shapefile
			List<SimpleFeature> listSFOutput = FeatureMethods.createListSF(lineFeatureList);
			FeatureMethods.writeOutShapeFile(sftinMain, listSFOutput);
			writeMessage("Completed FeatureMethods.writeOutShapeFile()");

		} catch (IOException e) {
			System.err.println("IOException has occurred in main.");
			e.printStackTrace();
			return;
		} catch (Exception e) {
			System.err.println("an error has occurred with a method in main");
			e.printStackTrace();
			return;
		}

	}

	static void writeMessage(String message) {
		double elapsedTime = (System.currentTimeMillis() - _startTime) / 1000.0;
		System.out.println(elapsedTime + ": " + message);
	}
}
