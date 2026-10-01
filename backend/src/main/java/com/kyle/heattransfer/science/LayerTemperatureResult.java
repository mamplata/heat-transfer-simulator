package com.kyle.heattransfer.science;

public record LayerTemperatureResult(
        String name,
        double resistanceM2KPerW,
        double startTemperatureC,
        double endTemperatureC) {
}