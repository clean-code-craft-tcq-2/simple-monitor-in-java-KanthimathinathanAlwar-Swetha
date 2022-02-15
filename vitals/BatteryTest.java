package vitals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

public class BatteryTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  float maxTemp;
  float minTemp;
  float maxSoc;
  float minSoc;
  float maxCR;
  Random random = new Random();

  public void setUpStreams() {
    System.setOut(new PrintStream(this.outContent));
  }

  public void restoreStreams() {
    System.setOut(this.originalOut);
  }

  public void tempFailureHighLimit() {
    assert (Battery.batteryIsOk(this.maxTemp + 1, this.maxSoc, this.maxCR) == false);
  }

  public void tempFailureLowLimit() {
    assert (Battery.batteryIsOk(this.minTemp - 1, this.maxSoc, this.maxCR) == false);
  }

  public void tempSuccess() {
    float randomTempWithinRange =this.random.nextFloat() * (this.maxTemp - this.minTemp);
    assert (Battery.batteryIsOk(randomTempWithinRange, 40, 0.5f) == true);
  }

  public void socFailureHighLimit() {
    assert (Battery.batteryIsOk(this.maxTemp, this.maxSoc + 1, this.maxCR) == false);
  }

  public void socFailureLowLimit() {
    assert (Battery.batteryIsOk(this.maxTemp, this.minSoc - 1, this.maxCR) == false);
  }

  public void socSuccess() {
    assert (Battery.batteryIsOk(20, 40, 0.5f) == true);
  }

  public void crFailure() {
    assert (Battery.batteryIsOk(this.maxTemp, this.maxSoc, this.maxCR + 1) == false);
  }

  public void crSuccess() {
    float randomCRWithinRange = this.random.nextFloat() * (this.maxCR);
    assert (Battery.batteryIsOk(20, 40, randomCRWithinRange) == true);
  }

  public void allParamsFailure() {
    assert (Battery.batteryIsOk(this.maxTemp + 1, this.minSoc - 1, this.maxCR + 1) == false);
  }

  public void tempWarning() {
    setUpStreams();
    Battery.batteryIsOk(45, 58, 0.5f);
    assert ("Temperatur Warnung : Entlade-/Ladespitze nahert sich".equals(this.outContent.toString()));
    restoreStreams();
  }

  public void socWarning() {
    setUpStreams();
    Battery.batteryIsOk(40, 78, 0.5f);
    assert ("Ladezustand Warnung : Entlade-/Ladespitze nahert sich".equals(this.outContent.toString()));
    restoreStreams();
  }

  public void crWarning() {
    setUpStreams();
    Battery.batteryIsOk(40, 58, 0.8f);
    assert ("Ladestrom Warnung : Entlade-/Ladespitze nahert sich".equals(this.outContent.toString()));
    restoreStreams();
  }

  public void deLangContents() {
    setUpStreams();
    Battery.batteryIsOk(46, 80, 0.8f);
    assert ("Temperatur Warnung : Entlade-/Ladespitze nahert sich".equals(this.outContent.toString()));
    restoreStreams();
  }

  public void enLangContents() {
    setUpStreams();
    Battery.getResourceBundle(Battery.enlocale);
    Battery.batteryIsOk(46, 80, 0.8f);
    assert ("Temperature Warning : Approaching discharge/charge-peak".equals(this.outContent.toString()));
    restoreStreams();
  }
}
