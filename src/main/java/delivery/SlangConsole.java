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
        this.functions = new (path);
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
            public void execute() {
                functions.HandleRandomSlangWord();
            }
        });

        executions.put(8, new Execution() {
            @Override
            public void execute() {
                functions.HandleQuizByValue();
            }
        });

        executions.put(9, new Execution() {
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

interface Execution {
    void execute() throws IOException;
}

class Menu {
    private String[] selections;

    public Menu(String ...selections) {
        this.selections = selections;
    }

    int receiveSelection() {
        showSelections();
        System.out.print("-> Your selection: ");
        return new Scanner(System.in).nextInt();
    }

    void showSelections() {
        AtomicInteger index = new AtomicInteger(1);
        Arrays.stream(this.selections).forEach(e -> System.out.println((index.getAndIncrement()) + ". " + e));
    }
}

class InputController {
    private HashMap<Integer, Execution> executions;

    public InputController(HashMap<Integer, Execution> executions) {
        this.executions = executions;
    }

    public void executeSelection(int selection) throws IOException {
        this.executions.get(selection).execute();
    }
}

class ConsoleService {
    private SlangWordService service;
    private List<String> historySearch;
    private Scanner input;

    public ConsoleService(String path) throws IOException {
        this.service = new SlangWordService(path);
        this.input = new Scanner(System.in);
        this.historySearch = new ArrayList<>();
    }

    public void HandleSearchSlang() {
        System.out.print("-> Slang: ");
        String word = this.input.nextLine();
        List<String> definitions = this.service.search(word);
        showList(definitions);
        this.historySearch.add(word);
    }

    private void showList(List<String> definitions) {
        if (null == definitions) {
            System.out.println("Not found");
            return;
        }

        definitions.forEach(d -> System.out.println("-> " + d));
    }

    public void HandleSearchDefinition() {
        System.out.print("-> definition: ");
        String definition = this.input.nextLine();
        var result = this.service.searchByDefinition(definition);
        showList(result);
    }

    public void HandleShowHistory() {
        System.out.println("Searching history: ");
        this.historySearch.forEach(w -> System.out.println("-> " + w));
    }

    public void HandleInsert() throws IOException {
        System.out.println("Enter:");
        System.out.print("-> Slang: ");
        String slang = this.input.nextLine();

        System.out.println("-> Definition: ");
        String definition = this.input.nextLine();

        var word = new SlangWord(slang, Collections.singletonList(definition));
        var msg = this.service.insert(word) ? "Success" : "Fail";
        this.service.addDefinitionToTrie(definition, slang);
        System.out.println(msg);
    }

    public void HandleEdit() throws IOException {
        System.out.println("Enter:");
        System.out.print("-> slang: ");
        String slang = this.input.nextLine();
        if (!this.service.exists(slang)) {
            System.out.println("Not found");
            return;
        }

        System.out.println("-> definition: ");
        String definition = this.input.nextLine();
        var listDefinition = new ArrayList<String>(){{add(definition);}};

        System.out.println(this.service.update(new SlangWord(slang, listDefinition)) ? "Success" : "Fail");
    }

    public void HandleDelete() throws IOException {
        System.out.println("Enter: ");
        System.out.print("-> slang: ");
        String slang = this.input.nextLine();
        if (!this.service.exists(slang)) {
            System.out.println("Not found");
            return;
        }

        System.out.println(this.service.delete(new SlangWord(slang, null)) ? "Success" : "Fail");
    }

    public void HandleReset() {
        this.historySearch.clear();
    }

    public void HandleRandomSlangWord() {
        var word = this.service.randomWord();
        System.out.println("Word: " + word.getWord() + "Definitions: " + word.getDefinitions());
    }

    public void HandleQuizByValue() {
        SlangWordQuiz challenge = new SlangWordQuiz(4, this.service);
        var quiz = challenge.quizByWord();
        HandleQuiz(quiz);
    }

    public void HandleQuizByDefinition() {
        SlangWordQuiz challenge = new SlangWordQuiz(4, this.service);
        var quiz = challenge.quizByDefinition();
        HandleQuiz(quiz);
    }

    private void HandleQuiz(Quiz quiz) {
        System.out.println(quiz.getQuestion());
        System.out.println("Selection");

        for (var i = 0; i < quiz.getSelections().size(); i++) {
            System.out.println(i + 1 + quiz.getSelections().get(i));
        }

        int userSelection = this.input.nextInt();
        if (!quiz.isRightSelection(userSelection)) {
            System.out.println("Wrong");
        } else {
            System.out.println("Right! Congratulate!");
        }
    }
}