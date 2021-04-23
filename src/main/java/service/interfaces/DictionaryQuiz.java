package service.interfaces;

import service.Quiz;

public interface DictionaryQuiz {
    Quiz quizByWord();
    Quiz quizByDefinition();
}
