package engine.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Arrays;

@Entity
public class Quiz {


    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "Title may not be blank")
    private String title;

    @NotBlank(message = "Text may not be blank")
    private String text;

    @Size(min = 2)
    @NotEmpty
    private String[] options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String creator;

    public Quiz() {}

    public Quiz(String title, String text, String[] options, int[] answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
        Arrays.sort(this.answer);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {

        this.answer = answer;
        Arrays.sort(this.answer);
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Answer solve(int[] answer) {
        boolean success;
        Arrays.sort(answer);
        if (this.answer != null) {
            Arrays.sort(this.answer);
            success = Arrays.equals(this.answer, answer);
        } else {
            success = answer.length == 0;
        }
        return new Answer(success);
    }
}
