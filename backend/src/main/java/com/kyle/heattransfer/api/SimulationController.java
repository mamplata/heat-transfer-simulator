package com.kyle.heattransfer.api;

import com.kyle.heattransfer.science.HeatTransferResult;
import com.kyle.heattransfer.science.HeatTransferSimulator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/simulations")
public class SimulationController {

    private final HeatTransferSimulator simulator;

    public SimulationController(HeatTransferSimulator simulator) {
        this.simulator = simulator;
    }

    @PostMapping
    public HeatTransferResult simulate(@RequestBody SimulationRequest request) {
        return simulator.simulate(
                request.indoorTemperatureC(),
                request.outdoorTemperatureC(),
                request.wallAreaM2(),
                request.layers());
    }
}