package com.kyle.heattransfer.science;

public class HeatFluxCalculator {

    public double calculate(
            double indoorTemperatureC,
            double outdoorTemperatureC,
            double totalResistanceM2KPerW) {

        return (indoorTemperatureC - outdoorTemperatureC)
                / totalResistanceM2KPerW;
    }
}