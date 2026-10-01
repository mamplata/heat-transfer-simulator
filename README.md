# Thermo/Lab — Heat Transfer Simulator

Thermo/Lab estimates steady heat conduction through a wall made of flat material layers. Enter the indoor and outdoor temperatures, wall area, and each layer's thickness and thermal conductivity. The app returns the total thermal resistance, heat flux, heat flow rate, and temperature at each layer boundary.

## Model

The simulator treats the wall as one-dimensional, with steady heat flow through layers in series:

- Layer resistance: `Rᵢ = thicknessᵢ / conductivityᵢ` (m²·K/W)
- Total resistance: `Rtotal = Σ Rᵢ`
- Heat flux: `q = (Tinside − Toutside) / Rtotal` (W/m²)
- Heat flow rate: `Q = q × wall area` (W)
- Temperature after each layer: `Tnext = Tcurrent − q × Rᵢ` (°C)

Conductivity is in W/(m·K), thickness in meters, temperatures in °C, and area in m². A positive heat flow means heat moves from indoors to outdoors; a negative value means the reverse.

This is an idealized conduction model. It does not include inside/outside surface resistance, air gaps, thermal bridges, moisture, radiation, convection, or changing conditions. Results are estimates, not a full building-energy analysis.

## Run locally

Requirements: Java 21 and Node.js/npm.

From the repository root, run both services with one command:

```bash
./dev.sh
```

The script installs frontend dependencies on the first run if needed, starts the backend on `http://localhost:5005` and frontend on `http://localhost:4200`, and stops both when you press Ctrl+C.

## API

Send a `POST` request to `/api/simulations` with JSON like:

```json
{
  "indoorTemperatureC": 21,
  "outdoorTemperatureC": 0,
  "wallAreaM2": 12,
  "layers": [
    {
      "name": "Brick",
      "thicknessMeters": 0.2,
      "conductivityWPerMeterKelvin": 0.72
    },
    {
      "name": "Insulation",
      "thicknessMeters": 0.08,
      "conductivityWPerMeterKelvin": 0.04
    }
  ]
}
```

The response contains `totalResistanceM2KPerW`, `heatFluxWPerM2`, `heatFlowRateW`, and a `layers` array with each layer's resistance and start/end temperatures.

## Verify

Backend tests:

```bash
cd backend
./mvnw test
```

Frontend tests and production build:

```bash
cd frontend
npm test -- --watch=false
npm run build
```
