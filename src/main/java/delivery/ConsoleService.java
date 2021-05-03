package delivery;

import delivery.io.ConsoleIO;
import entity.SlangWord;
import entity.Quiz;
import service.SlangWordQuiz;
import service.SlangWordService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ConsoleService {
    private final ConsoleIO io;
    private final SlangWordService service;
    private final List<String> history;

    public ConsoleService(String path) throws IOException {
        this.service = new SlangWordService(path);
        this.history = new ArrayList<>();
        this.io = new ConsoleIO();
    }

    public void HandleSearchSlang() {
        var slang = io.enterString("-> Enter slang: ");
        List<String> definitions = service.searchByWord(slang);
        io.displaySlangDefinitions(definitions);
        this.history.add(slang);
    }

    public void HandleSearchDefinition() {
        var word = this.io.enterString("-> Enter definition: ");
        var result = this.service.searchByDefinition(word);
        if (null == result || result.isEmpty()) {
            io.display("\nNo result!!!\n\n");
            return;
        }

        io.display("\n* Related words:\n");
        for (var value : result) {
            io.display("\t# " + value + ":\n");
            service.searchByWord(value).forEach(e -> io.display("\t\t- " + e + "\n"));
            io.display("------------------------------------------------------------\n");
        }
    }

    public void HandleShowHistory() {
        System.out.println("\nSearching history: ");
        this.history.forEach(w -> io.display("- " + w + "\n"));
        io.display("\n\n");
    }

    public void HandleInsert() throws IOException {
        String slang = this.io.enterString("-> Enter slang: ");
        String meaning = this.io.enterString("-> Enter definition: ");
        var word = new SlangWord(slang, new ArrayList<String>() {{add(meaning);}});

        this.io.display("=> Success!\n\n", "=> Fail!\n\n",
                service.exists(slang) && HandleExists(word) ||
                this.service.insert(word));

        this.service.addDefinitionToTrie(meaning, slang);
    }

    private boolean HandleExists(SlangWord word) throws IOException {
        String userInput = this.io.enterString(
                "-> this word has been existed!\n" +
                "-> Overwrite(o) or duplicate(d) or no(n):");

        if (userInput.equalsIgnoreCase("n")) {
            return true;
        }

        if (userInput.equalsIgnoreCase("o")) {
            return service.update(word);
        }

        return (userInput.equalsIgnoreCase("d") && service.addDefinition(word));
    }

    public void HandleEdit() throws IOException {
        String slang = this.io.enterString("-> Enter slang: ");
        if (!this.service.exists(slang)) {
            this.io.display("\nNo Result!\n\n");
            return;
        }

        String definition = this.io.enterString("-> definition: ");
        var updated = service.update(new SlangWord(slang,
                Collections.singletonList(definition)));
        if (updated) {
            this.io.display("\n=> Updated!\n\n");
            service.reloadTrie();
            return;
        }
        this.io.display("\n=> Fail!\n");
    }

    public void HandleDelete() throws IOException {
        var slang = this.io.enterString("-> Enter slang: ");
        if (!this.service.exists(slang)) {
            System.out.println("\n* No Result!\n");
            return;
        }

        System.out.println(this.service.delete(new SlangWord(slang, null)) ? "=> Deleted!\n" : "=> Fail!\n");
        this.service.reloadTrie();
    }

    public void HandleReset() throws IOException {
        this.history.clear();
        this.service.reset();
        io.display("\n=> Success!\n\n");
    }

    public void HandleRandomSlangWord() {
        var word = this.service.randomWord();
        io.display("* Word: " + word.getWord());
        io.displaySlangDefinitions(word.getDefinitions());
    }

    public void HandleQuizBySlang() {
        SlangWordQuiz challenge = new SlangWordQuiz(4, this.service);
        HandleQuiz(challenge.quizByWord());
    }

    public void HandleQuizByDefinition() {
        SlangWordQuiz challenge = new SlangWordQuiz(4, this.service);
        HandleQuiz(challenge.quizByDefinition());
    }

    private void HandleQuiz(Quiz quiz) {
        io.display("What is '" + quiz.getQuestion() + "'?\n");
        io.displayQuiz(quiz);
        int selection = io.enterInt("-> Your answer: ");
        io.display("=> Right! Congratulate!\n\n",
                "=> Wrong! Result: " + quiz.getRightAnswer() + "\n\n",
                quiz.isRightSelection(selection - 1));
    }
}
