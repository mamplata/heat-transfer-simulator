package com.kyle.heattransfer.science;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class HeatTransferSimulator {

    private final ThermalResistanceCalculator resistanceCalculator =
            new ThermalResistanceCalculator();
    private final HeatFluxCalculator heatFluxCalculator =
            new HeatFluxCalculator();
    private final HeatFlowRateCalculator heatFlowRateCalculator =
            new HeatFlowRateCalculator();
    private final LayerTemperatureCalculator temperatureCalculator =
            new LayerTemperatureCalculator();

    public HeatTransferResult simulate(
            double indoorTemperatureC,
            double outdoorTemperatureC,
            double wallAreaM2,
            List<LayerInput> layers) {

        if (layers == null || layers.isEmpty()) {
            throw new IllegalArgumentException("Add at least one wall layer.");
        }

        if (!Double.isFinite(wallAreaM2) || wallAreaM2 <= 0) {
            throw new IllegalArgumentException(
                    "Wall area must be a finite number greater than zero.");
        }

        if (!Double.isFinite(indoorTemperatureC)
                || !Double.isFinite(outdoorTemperatureC)) {
            throw new IllegalArgumentException(
                    "Temperatures must be finite numbers.");
        }

        for (LayerInput layer : layers) {
            if (layer == null) {
                throw new IllegalArgumentException(
                        "Wall layers cannot be null.");
            }
        }

        double totalResistance = resistanceCalculator.calculateTotal(layers);

        if (!Double.isFinite(totalResistance) || totalResistance <= 0) {
            throw new IllegalArgumentException(
                    "Total wall resistance must be a finite number greater than zero.");
        }

        double heatFlux = heatFluxCalculator.calculate(
                indoorTemperatureC,
                outdoorTemperatureC,
                totalResistance);

        if (!Double.isFinite(heatFlux)) {
            throw new IllegalArgumentException(
                    "Calculated heat flux is outside the supported numeric range.");
        }

        double heatFlowRate = heatFlowRateCalculator.calculate(
                heatFlux,
                wallAreaM2);

        if (!Double.isFinite(heatFlowRate)) {
            throw new IllegalArgumentException(
                    "Calculated heat flow rate is outside the supported numeric range.");
        }

        List<LayerTemperatureResult> layerResults = new ArrayList<>();
        double currentTemperature = indoorTemperatureC;

        for (LayerInput layer : layers) {
            double resistance = resistanceCalculator.calculate(layer);
            double endTemperature = temperatureCalculator.calculateEndTemperature(
                    currentTemperature,
                    heatFlux,
                    resistance);

            if (!Double.isFinite(endTemperature)) {
                throw new IllegalArgumentException(
                        "Calculated layer temperature is outside the supported numeric range.");
            }

            layerResults.add(new LayerTemperatureResult(
                    layer.name(),
                    resistance,
                    currentTemperature,
                    endTemperature));

            currentTemperature = endTemperature;
        }

        return new HeatTransferResult(
                totalResistance,
                heatFlux,
                heatFlowRate,
                List.copyOf(layerResults));
    }
}