package ru.practicum.mainserver.location.model;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.springframework.stereotype.Component;
import ru.practicum.mainserver.location.model.Coordinates;

@Component
public class CoordinatesMapper {
    public static final int SRID = 4326;
    public static GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), SRID);

    public static Point coordinatesToPoint(Coordinates coordinates) {
        if (coordinates == null) return null;
        else return geometryFactory.createPoint(new Coordinate(coordinates.getLon(), coordinates.getLat()));
    }

    public static Coordinates pointToCoordinates(Point point) {
        if (point == null) return null;
        else return new Coordinates(point.getY(), point.getX());
    }
}
