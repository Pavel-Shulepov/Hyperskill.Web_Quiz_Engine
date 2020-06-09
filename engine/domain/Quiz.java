package engine.domain;


public final class Quiz {
    private final String title;
    private final String text;
    private final String[] options;
    private final int answerIndex;

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public Quiz(String title,
                String text,
                String[] options,
                int answerIndex) {
        this.answerIndex = answerIndex;
        this.title = title;
        this.text = text;
        this.options = options;
    }
}
