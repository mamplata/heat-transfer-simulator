package com.kyle.heattransfer.science;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LayerTemperatureCalculatorTest {

    @Test
    void calculatesTemperatureAtLayerEnd() {
        var calculator = new LayerTemperatureCalculator();

        double endTemperature = calculator.calculateEndTemperature(
                20.0,
                8.7805,
                0.2 / 0.72);

        assertEquals(17.561, endTemperature, 0.001);
    }
}