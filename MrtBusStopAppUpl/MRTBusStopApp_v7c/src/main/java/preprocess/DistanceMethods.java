package preprocess;

import java.util.ArrayList;
import java.util.List;

import org.geotools.geometry.jts.JTSFactoryFinder;

import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import com.vividsolutions.jts.geom.GeometryFactory;

import models.BusStop;
import models.ShpMrtStation;
import models.StraightLineObject;

public class DistanceMethods {
	static GeometryFactory gf = JTSFactoryFinder.getGeometryFactory();

	public static List<StraightLineObject> createSLObjList(List<BusStop> distinctBusStopList,
			List<ShpMrtStation> mrtStationList) {

		double distanceCalculated;
		List<StraightLineObject> nearbyMRTStationList = new ArrayList<>();

		for (BusStop busStop : distinctBusStopList) {

			for (ShpMrtStation a : mrtStationList) {
				Point aTemp = Point.at(Coordinate.fromDegrees(a.getGeomObj().getCoordinate().y),
						Coordinate.fromDegrees(a.getGeomObj().getCoordinate().x));
				
				Point busPointTemp = Point.at(Coordinate.fromDegrees(busStop.getGeomObj().getCoordinate().y),
						Coordinate.fromDegrees(busStop.getGeomObj().getCoordinate().x));

				distanceCalculated = EarthCalc.gcdDistance(busPointTemp, aTemp);

				if (distanceCalculated < 500) {
					
					StraightLineObject b = new StraightLineObject(busStop.getPOINT_NO(), a.getName(),
							distanceCalculated, busStop.getGeomObj(), a.getGeomObj());
					
					nearbyMRTStationList.add(b);
				}
			}
		}
		return nearbyMRTStationList;

	}

}
