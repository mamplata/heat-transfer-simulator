package com.kyle.heattransfer.science;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeatFlowRateCalculatorTest {

    @Test
    void calculatesHeatFlowRateFromFluxAndArea() {
        var calculator = new HeatFlowRateCalculator();

        double heatFlowRate = calculator.calculate(8.7805, 10.0);

        assertEquals(87.805, heatFlowRate, 0.001);
    }
}