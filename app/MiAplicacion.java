import android.app.Application;

public class MiAplicacion extends Application {

    private String variableGolbal;

    public String getSomeVariable() {
        return variableGolbal;
    }

    public void setSomeVariable(String variableGolbal) {
        this.variableGolbal = variableGolbal;
    }
}
