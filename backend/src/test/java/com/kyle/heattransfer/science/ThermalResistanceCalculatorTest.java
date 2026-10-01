package com.kyle.heattransfer.science;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThermalResistanceCalculatorTest {

    @Test
    void calculatesResistanceFromThicknessAndConductivity() {
        var layer = new LayerInput("Brick", 0.2, 0.72);
        var calculator = new ThermalResistanceCalculator();

        double resistance = calculator.calculate(layer);

        assertEquals(0.2778, resistance, 0.001);
    }

    @Test
    void addsResistanceAcrossMultipleLayers() {
        var layers = List.of(
                new LayerInput("Brick", 0.2, 0.72),
                new LayerInput("Insulation", 0.08, 0.04)
        );
        var calculator = new ThermalResistanceCalculator();

        double total = calculator.calculateTotal(layers);

        assertEquals(2.2778, total, 0.001);
    }
}