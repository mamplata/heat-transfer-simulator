package com.kyle.heattransfer.science;
import java.util.List;

public class ThermalResistanceCalculator {

    public double calculate(LayerInput layer) {
        return layer.thicknessMeters()
                / layer.conductivityWPerMeterKelvin();
    }

    public double calculateTotal(List<LayerInput> layers) {
        return layers.stream()
                .mapToDouble(this::calculate)
                .sum();
    }
}