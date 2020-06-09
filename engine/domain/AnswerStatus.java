package engine.domain;

public final class AnswerStatus {
    private final boolean success;
    private final String feedback;

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }

    public AnswerStatus(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }
}
