package engine.Entity;

public class Answer {
    private boolean success;
    private String feedback;

    public Answer(boolean success) {
        this.success = success;
        feedback = success ? "Congratulations, you're right!" : "Wrong answer! Please, try again.";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

