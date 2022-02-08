package vitals;

import java.util.Random;

public class MainTest {

  private final float maxTemp;
  private final float minTemp;
  private final float maxSoc;
  private final float minSoc;
  private final float maxCR;
  private final Random random = new Random();

  public MainTest() {
    this.maxTemp = Main.getTempUpperLimit();
    this.minTemp = Main.getTempLowerLimit();
    this.maxSoc = Main.getSocUpperLimit();
    this.minSoc = Main.getSocLowerLimit();
    this.maxCR = Main.getChargeRateUpperLimit();
  }

  public void tempFailureHighLimit() {
    assert (Main.batteryIsOk(this.maxTemp + 1, this.maxSoc, this.maxCR) == false);
  }

  public void tempFailureLowLimit() {
    assert (Main.batteryIsOk(this.minTemp - 1, this.maxSoc, this.maxCR) == false);
  }

  public void tempSuccess() {
    float randomTempWithinRange = this.minTemp + (this.random.nextFloat() * (this.maxTemp - this.minTemp));
    assert (Main.batteryIsOk(randomTempWithinRange, this.maxSoc, this.maxCR) == true);
  }

  public void socFailureHighLimit() {
    assert (Main.batteryIsOk(this.maxTemp, this.maxSoc + 1, this.maxCR) == false);
  }

  public void socFailureLowLimit() {
    assert (Main.batteryIsOk(this.maxTemp, this.minSoc - 1, this.maxCR) == false);
  }

  public void socSuccess() {
    float randomSocWithinRange = this.minSoc + (this.random.nextFloat() * (this.maxSoc - this.minSoc));
    assert (Main.batteryIsOk(this.maxTemp, randomSocWithinRange, this.maxCR) == true);
  }

  public void crFailure() {
    assert (Main.batteryIsOk(this.maxTemp, this.maxSoc, this.maxCR + 1) == false);
  }

  public void crSuccess() {
    float randomCRWithinRange = this.random.nextFloat() * (this.maxCR);
    assert (Main.batteryIsOk(this.maxTemp, this.maxSoc, randomCRWithinRange) == true);
  }

  public void allParamsFailure() {
    assert (Main.batteryIsOk(this.maxTemp + 1, this.minSoc - 1, this.maxCR + 1) == false);
  }
}
