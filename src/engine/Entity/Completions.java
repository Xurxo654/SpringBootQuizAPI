package engine.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Completions {

    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long completionId;

    private int id;
    private Date completedAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int userId;

    public Completions() {

    }

    public int getId() {
        return id;
    }

    public void setId(int quizId) {
        this.id = quizId;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user) {
        this.userId = user;
    }
}
