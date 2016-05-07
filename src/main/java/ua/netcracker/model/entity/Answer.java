package ua.netcracker.model.entity;

/**
 * Created by Alyona on 06.05.2016.
 */
public abstract class Answer <T> {
    private Integer questionId;
    private T value;

    public Answer() {};

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public abstract String toString();

    public abstract String getValueDBFormat();
}