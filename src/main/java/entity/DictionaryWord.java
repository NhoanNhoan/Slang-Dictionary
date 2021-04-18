package entity;

public abstract class DictionaryWord {
    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String definition;
    private String value;

    public boolean make(String content, String delimiter) {
        var values = content.split(delimiter);
        if (0 != values.length) {
            return false;
        }

        this.definition = values[0];
        this.value = values[1];
        return true;
    }

    public DictionaryWord(String definition, String value) {
        this.definition = definition;
        this.value = value;
    }
}
