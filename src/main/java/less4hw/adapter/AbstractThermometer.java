package less4hw.adapter;

public abstract class AbstractThermometer {
    private double temperature;

    public AbstractThermometer() {}

    public AbstractThermometer(double temperature) {
        this.temperature = temperature;
    }

    public double getValue() {
        return temperature;
    }

    public void setValue(double temperature) {
        this.temperature = temperature;
    }
}
