package com.thepracticaldeveloper.reactiveweb.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.Objects;

@Document(collection = "InterestOptions")
public final class InterestOptions {

    private String id;
    private String interestQuestionName;
    private String interestQuestion;
    private Integer displayOrder;
    private AnswerOptions[] answerOptions;
    private String selectedOptions;

    // Empty constructor is needed for Jackson to deserialize JSON
    public InterestOptions() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterestQuestionName() {
        return interestQuestionName;
    }

    public void setInterestQuestionName(String interestQuestionName) {
        this.interestQuestionName = interestQuestionName;
    }

    public String getInterestQuestion() {
        return interestQuestion;
    }

    public void setInterestQuestion(String interestQuestion) {
        this.interestQuestion = interestQuestion;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public AnswerOptions[] getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(AnswerOptions[] answerOptions) {
        this.answerOptions = answerOptions;
    }

    public String getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(String selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestOptions that = (InterestOptions) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getInterestQuestionName(), that.getInterestQuestionName()) &&
                Objects.equals(getInterestQuestion(), that.getInterestQuestion()) &&
                Objects.equals(getDisplayOrder(), that.getDisplayOrder()) &&
                Arrays.equals(getAnswerOptions(), that.getAnswerOptions()) &&
                getSelectedOptions().equals(that.getSelectedOptions());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getInterestQuestionName(), getInterestQuestion(), getDisplayOrder(), getSelectedOptions());
        result = 31 * result + Arrays.hashCode(getAnswerOptions());
        return result;
    }

    @Override
    public String toString() {
        return "InterestOptions{" +
                "id='" + id + '\'' +
                ", interestQuestionName='" + interestQuestionName + '\'' +
                ", interestQuestion='" + interestQuestion + '\'' +
                ", displayOrder=" + displayOrder +
                ", answerOptions=" + Arrays.toString(answerOptions) +
                ", selectedOptions='" + selectedOptions + '\'' +
                '}';
    }
}
