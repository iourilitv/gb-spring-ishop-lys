package less4hw.bridge;

import less4hw.adapter.IKelvinThermometer;

public class SingeKelvinThermometer extends AbstractKelvinThermometer {
    public SingeKelvinThermometer(IKelvinThermometer implementor) {
        super(implementor);
    }

    public double getValue() {
        return implementor.getKelvinValue();
    }
}
