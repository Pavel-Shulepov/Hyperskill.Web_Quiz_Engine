package engine.domain;

import javax.persistence.*;

@Entity
@Table(name = "option")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "text")
    private String text;

    public String getText() {
        return text;
    }

    public Option() {
    }

    public Option(String text) {
        this.text = text;
    }
}
