package com.kyle.heattransfer.science;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HeatTransferSimulatorTest {

    @Test
    void simulatesHeatTransferAcrossBrickAndInsulation() {
        var simulator = new HeatTransferSimulator();

        var result = simulator.simulate(
                20.0,
                0.0,
                10.0,
                List.of(
                        new LayerInput("Brick", 0.20, 0.72),
                        new LayerInput("Insulation", 0.08, 0.04)
                ));

        assertEquals(2.2778, result.totalResistanceM2KPerW(), 0.001);
        assertEquals(8.7805, result.heatFluxWPerM2(), 0.001);
        assertEquals(87.805, result.heatFlowRateW(), 0.01);

        assertEquals(2, result.layers().size());
        assertEquals(20.0, result.layers().get(0).startTemperatureC(), 0.001);
        assertEquals(17.561, result.layers().get(0).endTemperatureC(), 0.001);
        assertEquals(0.0, result.layers().get(1).endTemperatureC(), 0.001);
    }

    @Test
    void rejectsAnEmptyLayerList() {
        var simulator = new HeatTransferSimulator();

        assertThrows(IllegalArgumentException.class, () ->
                simulator.simulate(20.0, 0.0, 10.0, List.of()));
    }

    @Test
    void rejectsZeroWallArea() {
        var simulator = new HeatTransferSimulator();
        var layers = List.of(new LayerInput("Brick", 0.2, 0.72));

        assertThrows(IllegalArgumentException.class, () ->
                simulator.simulate(20.0, 0.0, 0.0, layers));
    }

    @Test
    void rejectsInfiniteWallArea() {
        var simulator = new HeatTransferSimulator();
        var layers = List.of(new LayerInput("Brick", 0.2, 0.72));

        assertThrows(IllegalArgumentException.class, () ->
                simulator.simulate(20.0, 0.0, Double.POSITIVE_INFINITY, layers));
    }

    @Test
    void rejectsNonFiniteIndoorTemperature() {
        var simulator = new HeatTransferSimulator();
        var layers = List.of(new LayerInput("Brick", 0.2, 0.72));

        assertThrows(IllegalArgumentException.class, () ->
                simulator.simulate(Double.NaN, 0.0, 10.0, layers));
    }

    @Test
    void rejectsNonFiniteOutdoorTemperature() {
        var simulator = new HeatTransferSimulator();
        var layers = List.of(new LayerInput("Brick", 0.2, 0.72));

        assertThrows(IllegalArgumentException.class, () ->
                simulator.simulate(20.0, Double.POSITIVE_INFINITY, 10.0, layers));
    }

    @Test
    void rejectsNullLayer() {
        var simulator = new HeatTransferSimulator();
        List<LayerInput> layers = Arrays.asList((LayerInput) null);

        assertThrows(IllegalArgumentException.class, () ->
                simulator.simulate(20.0, 0.0, 10.0, layers));
    }
}