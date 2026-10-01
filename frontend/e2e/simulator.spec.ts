import { expect, test } from '@playwright/test';

test('calculates wall heat flow and shows the wall layers', async ({ page }) => {
  await page.goto('/');
  await expect(page.getByRole('heading', { name: /where does the heat go/i })).toBeVisible();

  await expect.poll(async () => {
    try {
      return (await page.request.get('http://localhost:5005/api/simulations')).status();
    } catch {
      return 0;
    }
  }, { timeout: 60_000 }).toBe(405);

  await page.getByRole('button', { name: /calculate heat loss/i }).click();
  await expect(page.locator('.primary-result strong')).toContainText('110.6');
  await expect(page.locator('.wall-slice')).toHaveCount(2);
  await expect(page.locator('.wall-visual')).toContainText('Brick');
  await expect(page.locator('.wall-visual')).toContainText('Insulation');
});
