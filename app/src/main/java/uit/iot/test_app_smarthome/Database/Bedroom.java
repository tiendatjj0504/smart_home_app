package uit.iot.test_app_smarthome.Database;

public class Bedroom {
    private boolean windowState, lightState;

    public Bedroom() {
    }

    public Bedroom(boolean windowState, boolean lightState) {
        this.windowState = windowState;
        this.lightState = lightState;
    }

    public boolean isWindowState() {
        return windowState;
    }

    public boolean isLightState() {
        return lightState;
    }

    public void setWindowState(boolean windowState) {
        this.windowState = windowState;
    }

    public void setLightState(boolean lightState) {
        this.lightState = lightState;
    }
}
