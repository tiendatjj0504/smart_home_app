package uit.iot.test_app_smarthome.Database;

public class MainDoor {
    private boolean doorState;
    private String lightState;
    private String warningState;

    public MainDoor() {}

    public MainDoor(boolean doorState, String lightState, String warningState) {
        this.doorState = doorState;
        this.lightState = lightState;
        this.warningState = warningState;
    }

    public boolean isDoorState() {
        return doorState;
    }

    public String getLightState() {
        return lightState;
    }

    public String getWarningState() {
        return warningState;
    }

    public void setDoorState(boolean doorState) {
        this.doorState = doorState;
    }

    public void setLightState(String lightState) {
        this.lightState = lightState;
    }

    public void setWarningState(String warningState) {
        this.warningState = warningState;
    }
}
