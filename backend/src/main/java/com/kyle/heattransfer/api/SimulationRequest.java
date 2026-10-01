package com.kyle.heattransfer.api;

import com.kyle.heattransfer.science.LayerInput;

import java.util.List;

public record SimulationRequest(
        double indoorTemperatureC,
        double outdoorTemperatureC,
        double wallAreaM2,
        List<LayerInput> layers) {
}