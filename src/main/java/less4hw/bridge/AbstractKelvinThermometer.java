package less4hw.bridge;

import less4hw.adapter.IKelvinThermometer;

public abstract class AbstractKelvinThermometer {
    IKelvinThermometer implementor;

    public AbstractKelvinThermometer(IKelvinThermometer implementor) {
        this.implementor = implementor;
    }

    public String getImplementorName() {
        return implementor.getClass().getSimpleName();
    }
}
