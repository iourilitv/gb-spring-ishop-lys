package less4hw;

import less4hw.adapter.*;

public class KelvinScaleAdapterApp {
    public static void main(String[] args) {
//        KelvinThermometer kelvinThermometer = new KelvinThermometer(352.22);
        KelvinThermometer kelvinThermometer = new KelvinThermometer();
        kelvinThermometer.setValue(281.14);

        CelsiusThermometer celsiusThermometer = new CelsiusThermometer(12.2);
        KelvinableCelsiusThermometer kelvinableCelsiusThermometer =
                new KelvinableCelsiusThermometer(12.2);

        FahrenheitThermometer fahrenheitThermometer = new FahrenheitThermometer(52.4);
        KelvinableFahrenheitThermometer kelvinableFahrenheitThermometer =
                new KelvinableFahrenheitThermometer(52.4);

        System.out.println("********** Kelvin Thermometer *********");
        System.out.println("kelvinThermometer: " + kelvinThermometer.getValue() + "K.");
        System.out.println("kelvinThermometer: " + kelvinThermometer.getKelvinValue() + "K.");

        System.out.println("********** Celsius Thermometer *********");
        System.out.println("celsiusThermometer: " + celsiusThermometer.getValue() + "C.");
        System.out.println("kelvinableCelsiusThermometer: " + kelvinableCelsiusThermometer.getKelvinValue() + "K.");

        System.out.println("********** Fahrenheit Thermometer *********");
        System.out.println("fahrenheitThermometer: " + fahrenheitThermometer.getValue() + "F.");
        System.out.println("kelvinableFahrenheitThermometer: " + kelvinableFahrenheitThermometer.getKelvinValue() + "K.");

    }
}
