package less4hw.adapter;

public class KelvinableCelsiusThermometer extends CelsiusThermometer implements IKelvinThermometer {

    public KelvinableCelsiusThermometer() {
    }

    public KelvinableCelsiusThermometer(double temperature) {
        super(temperature);
    }

    @Override
    public double getKelvinValue() {
        return getValue() - KELVIN_ABSOLUTE_NULL;
    }
}
