package vitals;

public enum BatteryParams {

                           TEMP("Temperature"),
                           SOC("StateOfCharge"),
                           CR("ChargeRate"),
                           WARNING_RANGE("WarningRange"),
                           FULL_RANGE("FullRange"),
                           WARNING("Warning"),
                           OUT_OF_RANGE("OutOfRange");

  private String displayText;

  private BatteryParams(final String displayText) {
    this.displayText = displayText;
  }

  @Override
  public String toString() {
    return this.displayText;
  }
}