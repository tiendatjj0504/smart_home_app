package uit.iot.test_app_smarthome.Database;

public class Kitchen {
    private boolean doorState;
    private String gas, temp, humi, warningState;

    public Kitchen() {
    }

    public Kitchen(boolean doorState, String gas, String temp, String humi, String warningState) {
        this.doorState = doorState;
        this.gas = gas;
        this.temp = temp;
        this.humi = humi;
        this.warningState = warningState;
    }

    public boolean isDoorState() {
        return doorState;
    }

    public String getGas() {
        return gas;
    }

    public String getTemp() {
        return temp;
    }

    public String getHumi() {
        return humi;
    }

    public String getWarningState() {
        return warningState;
    }

    public void setDoorState(boolean doorState) {
        this.doorState = doorState;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setHumi(String humi) {
        this.humi = humi;
    }

    public void setWarningState(String warningState) {
        this.warningState = warningState;
    }
}

