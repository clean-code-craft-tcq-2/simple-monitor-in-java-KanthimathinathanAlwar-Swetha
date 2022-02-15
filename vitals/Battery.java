package vitals;

import static vitals.BatteryParams.CR;
import static vitals.BatteryParams.OUT_OF_RANGE;
import static vitals.BatteryParams.SOC;
import static vitals.BatteryParams.TEMP;
import static vitals.BatteryParams.WARNING;

import java.util.Locale;
import java.util.ResourceBundle;

public class Battery {

  static Locale delocale = new Locale("de", "DE");
  static Locale enlocale = new Locale("en", "EN");
  static ResourceBundle bundle = getResourceBundle(delocale);
  private static float tempUpperLimit = 45;
  private static float tempLowerLimit = 0;
  private static float socUpperLimit = 80;
  private static float socLowerLimit = 20;
  private static float chargeRateLowerLimit = 0.0f;
  private static float chargeRateUpperLimit = 0.8f;

  public static ResourceBundle getResourceBundle(final Locale locale) {
    return ResourceBundle.getBundle("vitals.BatteryParams", locale);
  }

  public static float getTempUpperLimit() {
    return Battery.tempUpperLimit;
  }

  public static float getTempLowerLimit() {
    return Battery.tempLowerLimit;
  }

  public static float getSocUpperLimit() {
    return Battery.socUpperLimit;
  }

  public static float getSocLowerLimit() {
    return Battery.socLowerLimit;
  }

  public static float getChargeRateLowerLimit() {
    return Battery.chargeRateLowerLimit;
  }

  public static float getChargeRateUpperLimit() {
    return Battery.chargeRateUpperLimit;
  }

  public static float getWarningLimit(final float limit) {
    return limit * (5.0f / 100.0f);
  }

  static void printBatteryState(final String state, final BatteryParams range) {
    System.out.println(bundle.getString(state) + " " + bundle.getString(range.toString()));
  }

  static boolean checkInRange(final BatteryParams state, final float value, final float low, final float high,
      final float warn) {
    checkWanrings(state, value, low, high, warn);
    if ((value < low) || (value > high)) {
      printBatteryState(state.toString(), OUT_OF_RANGE);
      return false;
    }
    return true;
  }


  static void checkWanrings(final BatteryParams state, final float value, final float low, final float high,
      final float warn) {
    if ((value < (low + warn)) || (value > (high - warn))) {
      printBatteryState(state.toString(), WARNING);
    }
  }


  static boolean batteryIsOk(final float temperature, final float soc, final float chargeRate) {
    Temperature temp = temper -> checkInRange(TEMP, temper, getTempLowerLimit(), getTempUpperLimit(),
        getWarningLimit(getTempUpperLimit()));
    StateOfCharge sc = stateOfCharge -> checkInRange(SOC, stateOfCharge, getSocLowerLimit(), getSocUpperLimit(),
        getWarningLimit(getSocUpperLimit()));
    ChargeRate cr = rate -> checkInRange(CR, rate, getChargeRateLowerLimit(), getChargeRateUpperLimit(),
        getWarningLimit(getChargeRateUpperLimit()));
    return temp.checkTemp(temperature) && sc.checkSOC(soc) && cr.checkChargeRate(chargeRate);
  }

  public static void main(final String[] args) {
    BatteryTest test = new BatteryTest();
    test.tempFailureHighLimit();
    test.tempFailureLowLimit();
    test.tempSuccess();
    test.socFailureHighLimit();
    test.socFailureLowLimit();
    test.socSuccess();
    test.crFailure();
    test.crSuccess();
    test.allParamsFailure();
    test.tempWarning();
    test.socWarning();
    test.crWarning();
    test.deLangContents();
    test.enLangContents();
  }
}

interface Temperature {

  boolean checkTemp(final float temp);
}

interface StateOfCharge {

  boolean checkSOC(final float soc);
}

interface ChargeRate {

  boolean checkChargeRate(final float cr);
}
