import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {
    List<Station> route;
    StationIndex si = new StationIndex();
    RouteCalculator rc = new RouteCalculator(si);


    @Override
    protected void setUp() throws Exception {
        route = new ArrayList<>();

        Line fruit = new Line(1, "Фруктовая");
        Line color = new Line(2, "Цветная");
        Line zoo = new Line(3, "Зоопарковая");

        si.addLine(fruit);
        si.addLine(color);
        si.addLine(zoo);

        fruit.addStation(new Station("Яблочная", fruit));
        fruit.addStation(new Station("Грушевая", fruit));
        fruit.addStation(new Station("Персиковая", fruit));
        fruit.addStation(new Station("Лимонная", fruit));

        color.addStation(new Station("Зеленая", color));
        color.addStation(new Station("Синяя", color));
        color.addStation(new Station("Желтая", color));
        color.addStation(new Station("Красная", color));

        zoo.addStation(new Station("Ботанический сад", zoo));
        zoo.addStation(new Station("Террариум", zoo));
        zoo.addStation(new Station("Дельфинарий", zoo));
        zoo.addStation(new Station("Кошачий ряд", zoo));

        fruit.getStations().stream().forEach(station -> si.addStation(station));
        color.getStations().stream().forEach(station -> si.addStation(station));
        zoo.getStations().stream().forEach(station -> si.addStation(station));

        List<Station> connect1 = new ArrayList<>();
        connect1.add(si.getStation("Персиковая"));
        connect1.add(si.getStation("Синяя"));
        si.addConnection(connect1);

        List<Station> connect2 = new ArrayList<>();
        connect2.add(si.getStation("Красная"));
        connect2.add(si.getStation("Ботанический сад"));
        si.addConnection(connect2);

        route.addAll(fruit.getStations());
        route.addAll(color.getStations());
        route.addAll(zoo.getStations());
    }

    public void testCalculateDuration() {
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 29.5;
        assertEquals(expected, actual);
    }

    public void testGetShortestRoute() {
        List<Station> actual = rc.getShortestRoute(route.get(0), route.get(3));
        List<Station> expected = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            expected.add(route.get(i));
        }
        assertEquals(expected, actual);
    }

    public void testGetRouteOnTheLine() {
        List<Station> actual = rc.getShortestRoute(route.get(0), route.get(2));
        List<Station> expected = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            expected.add(route.get(i));
        }
        assertEquals(expected, actual);
    }

    public void testGetRouteWithOneConnection() {
        List<Station> actual = rc.getShortestRoute(route.get(0), route.get(5));
        List<Station> expected = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (i == 4 || i == 3) {
                continue;
            }
            expected.add(route.get(i));
        }
        assertEquals(expected, actual);
    }

    public void testGetRouteWithTwoConnections() {
        List<Station> actual = rc.getShortestRoute(route.get(0), route.get(10));
        List<Station> expected = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            if (i == 4 || i == 3) {
                continue;
            }
            expected.add(route.get(i));
        }
        assertEquals(expected, actual);
    }
}
