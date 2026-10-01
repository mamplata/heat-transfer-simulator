import { Component, signal } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface LayerInput {
  name: string;
  thicknessMeters: number;
  conductivityWPerMeterKelvin: number;
}

interface SimulationResult {
  totalResistanceM2KPerW: number;
  heatFluxWPerM2: number;
  heatFlowRateW: number;
  layers: Array<{
    name: string;
    resistanceM2KPerW: number;
    startTemperatureC: number;
    endTemperatureC: number;
  }>;
}

@Component({
  selector: 'app-root',
  imports: [FormsModule, DecimalPipe],
  templateUrl: './app.html'
})
export class App {
  indoorTemperatureC = 21;
  outdoorTemperatureC = 0;
  wallAreaM2 = 12;
  layers: LayerInput[] = [
    { name: 'Brick', thicknessMeters: 0.2, conductivityWPerMeterKelvin: 0.72 },
    { name: 'Insulation', thicknessMeters: 0.08, conductivityWPerMeterKelvin: 0.04 }
  ];
  result = signal<SimulationResult | null>(null);
  error = signal('');
  loading = signal(false);

  addLayer() {
    this.layers.push({ name: '', thicknessMeters: 0.1, conductivityWPerMeterKelvin: 0.5 });
  }

  temperatureColor(temperature: number) {
    const low = Math.min(this.indoorTemperatureC, this.outdoorTemperatureC);
    const range = Math.abs(this.indoorTemperatureC - this.outdoorTemperatureC);
    const fraction = range === 0 ? 0.5 : (temperature - low) / range;
    return `hsl(${205 - 185 * fraction} 55% 75%)`;
  }

  async simulate() {
    this.error.set('');
    this.result.set(null);
    this.loading.set(true);
    try {
      const response = await fetch('http://localhost:5005/api/simulations', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          indoorTemperatureC: this.indoorTemperatureC,
          outdoorTemperatureC: this.outdoorTemperatureC,
          wallAreaM2: this.wallAreaM2,
          layers: this.layers
        })
      });
      if (!response.ok) throw new Error((await response.text()) || `Request failed (${response.status})`);
      this.result.set(await response.json() as SimulationResult);
    } catch (error) {
      this.error.set(error instanceof TypeError
        ? 'Could not reach the simulator. Check that the backend is running on port 5005.'
        : error instanceof Error ? error.message : 'Simulation failed.');
    } finally {
      this.loading.set(false);
    }
  }
}
