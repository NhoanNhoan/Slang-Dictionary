package delivery;

import java.io.IOException;
import java.util.HashMap;

class InputController {
    private HashMap<Integer, Execution> executions;

    public InputController(HashMap<Integer, Execution> executions) {
        this.executions = executions;
    }

    public void executeSelection(int selection) throws IOException {
        this.executions.get(selection).execute();
    }
}