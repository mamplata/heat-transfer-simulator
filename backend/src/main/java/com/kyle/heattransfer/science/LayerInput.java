package com.kyle.heattransfer.science;

public record LayerInput(
        String name,
        double thicknessMeters,
        double conductivityWPerMeterKelvin) {

    public LayerInput {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Layer name is required.");
        }
        if (!Double.isFinite(thicknessMeters) || thicknessMeters <= 0) {
            throw new IllegalArgumentException(
                    "Layer thickness must be greater than zero.");
        }
        if (!Double.isFinite(conductivityWPerMeterKelvin)
                || conductivityWPerMeterKelvin <= 0) {
            throw new IllegalArgumentException(
                    "Thermal conductivity must be greater than zero.");
        }
    }
}