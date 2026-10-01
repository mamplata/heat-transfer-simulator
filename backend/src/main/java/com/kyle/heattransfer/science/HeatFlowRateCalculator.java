package com.kyle.heattransfer.science;

public class HeatFlowRateCalculator {

    public double calculate(double heatFluxWPerM2, double wallAreaM2) {
        return heatFluxWPerM2 * wallAreaM2;
    }
}