package delivery;

import entity.Execution;

import java.io.IOException;
import java.util.*;

public class SlangConsole {
    private GUI menu;
    private InputController controller;
    private ConsoleService functions;

    public SlangConsole(String path) throws IOException {
        this.menu = new GUI("Find slang word.",
                "Find by definition.",
                "Show history.",
                "Insert.",
                "Edit.",
                "Delete.",
                "Reset",
                "Random.",
                "Quiz slang word.",
                "Quiz by definition.",
                "Stop.");
        this.functions = new ConsoleService(path);
        var executions = new HashMap<Integer, Execution>();

        executions.put(1, new Execution() {
            @Override
            public void execute() {
                functions.HandleSearchSlang();
            }
        });

        executions.put(2, new Execution() {
            @Override
            public void execute() {
                functions.HandleSearchDefinition();
            }
        });

        executions.put(3, new Execution() {
            @Override
            public void execute() {
                functions.HandleShowHistory();
            }
        });

        executions.put(4, new Execution() {
            @Override
            public void execute() throws IOException {
                functions.HandleInsert();
            }
        });

        executions.put(5, new Execution() {
            @Override
            public void execute() throws IOException {
                functions.HandleEdit();
            }
        });

        executions.put(6, new Execution() {
            @Override
            public void execute() throws IOException {
                functions.HandleDelete();
            }
        });

        executions.put(7, new Execution() {
            @Override
            public void execute() throws IOException {
                functions.HandleReset();
            }
        });

        executions.put(8, new Execution() {
            @Override
            public void execute() {
                functions.HandleRandomSlangWord();
            }
        });

        executions.put(9, new Execution() {
            @Override
            public void execute() {
                functions.HandleQuizBySlang();
            }
        });

        executions.put(10, new Execution() {
            @Override
            public void execute() {
                functions.HandleQuizByDefinition();
            }
        });

        this.controller = new InputController(executions);
    }

    public void Run() throws IOException {
        int selection = 1;
        while (selection > 0 && selection < 11) {
            selection = menu.receiveSelection();
            controller.executeSelection(selection);
        }
    }


}

