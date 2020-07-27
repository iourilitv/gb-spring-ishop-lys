package less4hw.adapter;

public class KelvinableFahrenheitThermometer extends CelsiusThermometer implements IKelvinThermometer {

    public KelvinableFahrenheitThermometer() {
    }

    public KelvinableFahrenheitThermometer(double temperature) {
        super(temperature);
    }

    @Override
    public double getKelvinValue() {
        return (getValue() - 32) * 5 / 9 - KELVIN_ABSOLUTE_NULL;
    }
}
