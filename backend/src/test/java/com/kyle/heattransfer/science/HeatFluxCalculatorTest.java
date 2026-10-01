package com.kyle.heattransfer.science;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeatFluxCalculatorTest {

    @Test
    void calculatesHeatFluxFromTemperatureDifferenceAndResistance() {
        var calculator = new HeatFluxCalculator();

        double heatFlux = calculator.calculate(20.0, 0.0, 2.2777778);

        assertEquals(8.7805, heatFlux, 0.001);
    }
}