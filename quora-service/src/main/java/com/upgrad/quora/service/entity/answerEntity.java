package com.upgrad.quora.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name="answer")
@NamedQueries({
        @NamedQuery(name = "answerById", query = "select u from answerEntity u where u.uuid = :uuid"),
})
public class answerEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private  String uuid;

    @Column(name = "ans")
    @Size(max = 255)
    @NotNull
    private String answer;

    @Column(name="date")
    @NotNull
    private ZonedDateTime date;

    @ManyToOne
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private userEntity user;

    @ManyToOne
    @JoinColumn(name="question_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private questionEntity question;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public void setUser(userEntity user) {
        this.user = user;
    }

    public void setQuestion(questionEntity question) {
        this.question = question;
    }

    public Integer getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getAnswer() {
        return answer;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public userEntity getUser() {
        return user;
    }

    public questionEntity getQuestion() {
        return question;
    }

    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
