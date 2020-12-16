# MRTBusStopApp - Summary

Java application to associate nearby bus stops(up to 500m) to a MRT station and output the associations as **lines** to shapefile. 

In QGIS 2.18, the Vector/Research Tools/Select by location highlights the points of the 2 compared layers of points. However, when there are MRT stations near to each other, it becomes difficult to ascertain if a bus stop is near to only one or more than one MRT station.

## Main notes

- the Extract*.java classes are for extracting data from shapefiles. Examples are in ShapeFileResource folder
- DistanceMethods class prepares data for being output as shapefiles by FeatureMethods class. Examples in OutputShapefile folder
- StnNameBusSvcNo and BusStop_v1 are unused in this version
