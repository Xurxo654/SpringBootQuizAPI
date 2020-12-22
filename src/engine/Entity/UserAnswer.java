package engine.Entity;

public class UserAnswer {
    private int[] answer;

    public UserAnswer() {}

    public UserAnswer(int[] answer) {
        this.answer = answer;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}
