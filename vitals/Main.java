package vitals;

import static vitals.BatteryParams.CR;
import static vitals.BatteryParams.OUT_OF_RANGE;
import static vitals.BatteryParams.SOC;
import static vitals.BatteryParams.TEMP;

public class Main {

  private static float tempUpperLimit = 45;
  private static float tempLowerLimit = 0;
  private static float socUpperLimit = 80;
  private static float socLowerLimit = 20;
  private static float chargeRateUpperLimit = 0.8f;

  public Main(final float tempUpperLimit, final float tempLowerLimit, final float socUpperLimit,
      final float socLowerLimit, final float chargeRateUpperLimit) {
    Main.tempUpperLimit = tempUpperLimit;
    Main.tempLowerLimit = tempLowerLimit;
    Main.socUpperLimit = socUpperLimit;
    Main.socLowerLimit = socLowerLimit;
    Main.chargeRateUpperLimit = chargeRateUpperLimit;
  }

  public static float getTempUpperLimit() {
    return Main.tempUpperLimit;
  }

  public static float getTempLowerLimit() {
    return Main.tempLowerLimit;
  }

  public static float getSocUpperLimit() {
    return Main.socUpperLimit;
  }

  public static float getSocLowerLimit() {
    return Main.socLowerLimit;
  }

  public static float getChargeRateUpperLimit() {
    return Main.chargeRateUpperLimit;
  }

  static boolean checkTemp(final float temp) {
    return checkInRange(TEMP, temp, getTempLowerLimit(), getTempUpperLimit());
  }

  static boolean checkSOC(final float soc) {
    return checkInRange(SOC, soc, getSocLowerLimit(), getSocUpperLimit());
  }

  static boolean checkChargeRate(final float cr) {
    return checkInRange(CR, cr, cr, getChargeRateUpperLimit());
  }

  static void printFailure(final String text) {
    System.out.println(text);
  }

  static boolean checkInRange(final BatteryParams state, final float value, final float low, final float high) {
    if ((value < low) || (value > high)) {
      printFailure(state.toString() + OUT_OF_RANGE.toString());
      return false;
    }
    return true;
  }

  static boolean batteryIsOk(final float temperature, final float soc, final float chargeRate) {
    return checkTemp(temperature) && checkSOC(soc) && checkChargeRate(chargeRate);
  }

  public static void main(final String[] args) {
    assert (batteryIsOk(25, 70, 0.7f) == true);
    assert (batteryIsOk(50, 85, 0.0f) == false);
    System.out.println("Some more tests needed");
  }
}