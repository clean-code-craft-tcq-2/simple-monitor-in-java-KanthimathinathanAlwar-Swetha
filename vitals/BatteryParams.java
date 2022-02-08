package vitals;

public enum BatteryParams {

                           TEMP("Temperature"),
                           SOC("State of Charge"),
                           CR("Charge Rate"),
                           OUT_OF_RANGE(" is out of range!");

  private String displayText;

  private BatteryParams(final String displayText) {
    this.displayText = displayText;
  }

  @Override
  public String toString() {
    return this.displayText;
  }
}