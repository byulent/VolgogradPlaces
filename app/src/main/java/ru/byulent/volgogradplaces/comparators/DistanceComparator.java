package ru.byulent.volgogradplaces.comparators;

import java.util.Comparator;

import ru.byulent.volgogradplaces.entities.Photo;

public class DistanceComparator implements Comparator<Photo> {
    private double latitude;
    private double longitude;

    public DistanceComparator(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public int compare(Photo o1, Photo o2) {
        double x1 = Math.abs(latitude - o1.getLatitude());
        double y1 = Math.abs(longitude - o1.getLongitude());
        double x2 = Math.abs(latitude - o2.getLatitude());
        double y2 = Math.abs(longitude - o2.getLongitude());
        double d1 = Math.sqrt(x1*x1+y1*y1);
        double d2 = Math.sqrt(x2*x2+y2*y2);
        return Double.compare(d1, d2);
    }
}
