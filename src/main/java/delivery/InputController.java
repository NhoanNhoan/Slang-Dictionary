package delivery;

import entity.Execution;

import java.io.IOException;
import java.util.HashMap;

class InputController {
    private final HashMap<Integer, Execution> executions;

    public InputController(HashMap<Integer, Execution> executions) {
        this.executions = executions;
    }

    public void executeSelection(int selection) throws IOException {
        this.executions.get(selection).execute();
    }
}