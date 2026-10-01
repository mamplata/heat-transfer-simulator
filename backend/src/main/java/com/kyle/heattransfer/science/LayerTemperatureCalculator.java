package com.kyle.heattransfer.science;

public class LayerTemperatureCalculator {

    public double calculateEndTemperature(
            double startTemperatureC,
            double heatFluxWPerM2,
            double layerResistanceM2KPerW) {

        return startTemperatureC - heatFluxWPerM2 * layerResistanceM2KPerW;
    }
}