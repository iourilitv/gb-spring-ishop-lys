package less4hw;

import less4hw.adapter.*;
import less4hw.bridge.SingeKelvinThermometer;

public class KelvinScaleBridgeApp {
    public static void main(String[] args) {
        KelvinThermometer kelvinThermometer = new KelvinThermometer(281.14);
        KelvinableCelsiusThermometer kelvinableCelsiusThermometer =
                new KelvinableCelsiusThermometer(12.2);

        KelvinableFahrenheitThermometer kelvinableFahrenheitThermometer =
                new KelvinableFahrenheitThermometer(52.4);

        System.out.println("********** Kelvin Thermometer *********");
        measure(kelvinThermometer);

        System.out.println("********** Celsius Thermometer *********");
        measure(kelvinableCelsiusThermometer);

        System.out.println("********** Fahrenheit Thermometer *********");
        measure(kelvinableFahrenheitThermometer);
    }

    private static void measure(IKelvinThermometer thermometer) {
        SingeKelvinThermometer singeKelvinThermometer = new SingeKelvinThermometer(thermometer);

        System.out.println(singeKelvinThermometer.getImplementorName() + ".value= " + singeKelvinThermometer.getValue() + "K.");

    }

}
