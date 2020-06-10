package engine.domain;

import javax.persistence.*;

@Entity
@Table(name = "answer")
public class AnswerEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "answer_index")
    private int answerIndex;

    public int getAnswerIndex() {
        return answerIndex;
    }

    public AnswerEntity() {
    }

    public AnswerEntity(int answerIndex) {
        this.answerIndex = answerIndex;
    }
}
