package service.interfaces;

import entity.Quiz;

public interface DictionaryQuiz {
    Quiz quizByWord();
    Quiz quizByDefinition();
}
