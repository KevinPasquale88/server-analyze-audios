package parkinson.audio.server.analyzer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class IndexValue {
    private DoubleProperty time;
    private DoubleProperty f1;
    private DoubleProperty f2;
    private DoubleProperty intensity;

    public IndexValue() {}

    public IndexValue (double time, double f1, double f2, double intensity) {
        this.time = new SimpleDoubleProperty(time);
        this.f1 = new SimpleDoubleProperty(f1);
        this.f2 = new SimpleDoubleProperty(f2);
        this.intensity = new SimpleDoubleProperty(intensity);
    }

    public DoubleProperty timeProperty() {
        return time;
    }

    public DoubleProperty f1Property() {
        return f1;
    }

    public DoubleProperty f2Property() {
        return f2;
    }

    public DoubleProperty intensityProperty() {
        return intensity;
    }

    public void setTime(double time) {
        this.time.set(time);
    }

    public void setF1(double f1) {
        this.f1.set(f1);
    }

    public void setF2(double f2) {
        this.f2.set(f2);
    }

    public void setIntensity(double intensity) {
        this.intensity.set(intensity);
    }

    public double getTime() {
        return time.get();
    }

    public double getF1() {
        return f1.get();
    }

    public double getF2() {
        return f2.get();
    }

    public double getIntensity() {
        return intensity.get();
    }
}
