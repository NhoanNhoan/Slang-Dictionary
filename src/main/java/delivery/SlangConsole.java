package delivery;

import entity.SlangWord;
import service.Quiz;
import service.SlangWordQuiz;
import service.SlangWordService;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SlangConsole {
    private Menu menu;
    private InputController controller;
    private ConsoleService functions;

    public SlangConsole(String path) throws IOException {
        this.menu = new Menu("Find slang word.",
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
        while (true) {
            int selection = menu.receiveSelection();
            controller.executeSelection(selection);
        }
    }


}

