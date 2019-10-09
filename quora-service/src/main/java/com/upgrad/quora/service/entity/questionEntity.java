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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="question")
public class questionEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private  String uuid;

    @Column(name = "content")
    @Size(max = 255)
    @NotNull
    private String content;

    @Column(name="date")
    @NotNull
    private ZonedDateTime date;

    @ManyToOne
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private userEntity user;

    @OneToMany(mappedBy = "question" ,fetch=FetchType.EAGER)
    List<answerEntity> answers = new ArrayList<>();

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public void setUser(userEntity user) {
        this.user = user;
    }

    public void setAnswers(List<answerEntity> answers) {
        this.answers = answers;
    }

    public Integer getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getContent() {
        return content;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public userEntity getUser() {
        return user;
    }

    public List<answerEntity> getAnswers() {
        return answers;
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
