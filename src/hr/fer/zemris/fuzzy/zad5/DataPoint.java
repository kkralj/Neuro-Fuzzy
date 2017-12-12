package hr.fer.zemris.fuzzy.zad5;


import java.util.ArrayList;
import java.util.List;

public class DataPoint {

    private double x, y;

    public DataPoint() {
    }

    public DataPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    private double euclidean_dist(DataPoint point) {
        return Math.sqrt(Math.pow(this.x - point.x, 2) + Math.pow(this.y - point.y, 2));
    }

    public static List<DataPoint> transformPoints(List<DataPoint> points, int M) {
        DataPoint Tc = new DataPoint();
        for (DataPoint point : points) {
            Tc.x += point.x;
            Tc.y += point.y;
        }
        Tc.x /= points.size();
        Tc.y /= points.size();

        double mx = 0, my = 0;
        for (DataPoint point : points) {
            point.x -= Tc.x;
            point.y -= Tc.y;
            mx = Math.max(mx, Math.abs(point.x));
            my = Math.max(my, Math.abs(point.y));
        }

        double m = Math.max(mx, my);
        for (DataPoint point : points) {
            point.x /= m;
            point.y /= m;
        }

        double D = 0;
        for (int i = 1; i < points.size(); i++) {
            D += points.get(i).euclidean_dist(points.get(i - 1));
        }

        List<DataPoint> result = new ArrayList<>();
        result.add(points.get(0));

        double dist = 0;
        double diff = D / (M - 1);
        double cnt = 1;

        for (int i = 1; i < points.size() && cnt < M; ) {
            dist += points.get(i).euclidean_dist(points.get(i - 1));
            while (dist >= cnt * diff && cnt < M) {
                result.add(points.get(i));
                cnt++;
            }
            i++;
        }

        while (result.size() < M) {
            result.add(points.get(points.size() - 1));
        }

        return result;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
