import { TestBed } from '@angular/core/testing';
import { App } from './app';

describe('App', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should draw wall slices from simulation results', () => {
    const fixture = TestBed.createComponent(App);
    fixture.componentInstance.result.set({
      totalResistanceM2KPerW: 2, heatFluxWPerM2: 10, heatFlowRateW: 120,
      layers: [
        { name: 'Brick', resistanceM2KPerW: 0.5, startTemperatureC: 20, endTemperatureC: 15 },
        { name: 'Insulation', resistanceM2KPerW: 1.5, startTemperatureC: 15, endTemperatureC: 0 }
      ]
    });
    fixture.detectChanges();
    expect(fixture.nativeElement.querySelectorAll('.wall-slice').length).toBe(2);
    expect(fixture.nativeElement.querySelector('.heat-direction')?.textContent).toContain('10.0 W/m²');
  });

  it('should render title', async () => {
    const fixture = TestBed.createComponent(App);
    await fixture.whenStable();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Where does');
  });
});
