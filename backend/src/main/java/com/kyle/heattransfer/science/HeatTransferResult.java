package com.kyle.heattransfer.science;

import java.util.List;

public record HeatTransferResult(
        double totalResistanceM2KPerW,
        double heatFluxWPerM2,
        double heatFlowRateW,
        List<LayerTemperatureResult> layers) {
}