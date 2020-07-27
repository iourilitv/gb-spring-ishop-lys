package less4hw.adapter;

public class KelvinThermometer extends AbstractThermometer implements IKelvinThermometer {

    public KelvinThermometer() {
    }

    public KelvinThermometer(double temperature) {
        super(temperature);
    }

    @Override
    public double getKelvinValue() {
        return getValue();
    }
}
