package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Teacher.
 */
@Entity
@Table(name = "teacher")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "teacher_no", nullable = false)
    private String teacherNo;

    @NotNull
    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    @NotNull
    @Column(name = "subject", nullable = false)
    private String subject;

    @NotNull
    @Column(name = "time_table", nullable = false)
    private String timeTable;

    @Column(name = "teacher_tel")
    private String teacherTel;

    @OneToMany(mappedBy = "teacher")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TimeTable> timeTables = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    @OneToMany(mappedBy = "teacher")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Course> subjects = new HashSet<>();
    @ManyToMany(mappedBy = "teachers")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Grade> grades = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacherNo() {
        return teacherNo;
    }

    public Teacher teacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
        return this;
    }

    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public Teacher teacherName(String teacherName) {
        this.teacherName = teacherName;
        return this;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubject() {
        return subject;
    }

    public Teacher subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTimeTable() {
        return timeTable;
    }

    public Teacher timeTable(String timeTable) {
        this.timeTable = timeTable;
        return this;
    }

    public void setTimeTable(String timeTable) {
        this.timeTable = timeTable;
    }

    public String getTeacherTel() {
        return teacherTel;
    }

    public Teacher teacherTel(String teacherTel) {
        this.teacherTel = teacherTel;
        return this;
    }

    public void setTeacherTel(String teacherTel) {
        this.teacherTel = teacherTel;
    }

    public Set<TimeTable> getTimeTables() {
        return timeTables;
    }

    public Teacher timeTables(Set<TimeTable> timeTables) {
        this.timeTables = timeTables;
        return this;
    }

    public Teacher addTimeTable(TimeTable timeTable) {
        this.timeTables.add(timeTable);
        timeTable.setTeacher(this);
        return this;
    }

    public Teacher removeTimeTable(TimeTable timeTable) {
        this.timeTables.remove(timeTable);
        timeTable.setTeacher(null);
        return this;
    }

    public void setTimeTables(Set<TimeTable> timeTables) {
        this.timeTables = timeTables;
    }

    public User getUser() {
        return user;
    }

    public Teacher user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Course> getSubjects() {
        return subjects;
    }

    public Teacher subjects(Set<Course> courses) {
        this.subjects = courses;
        return this;
    }

    public Teacher addSubject(Course course) {
        this.subjects.add(course);
        course.setTeacher(this);
        return this;
    }

    public Teacher removeSubject(Course course) {
        this.subjects.remove(course);
        course.setTeacher(null);
        return this;
    }

    public void setSubjects(Set<Course> courses) {
        this.subjects = courses;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public Teacher grades(Set<Grade> grades) {
        this.grades = grades;
        return this;
    }

    public Teacher addGrade(Grade grade) {
        this.grades.add(grade);
        grade.getTeachers().add(this);
        return this;
    }

    public Teacher removeGrade(Grade grade) {
        this.grades.remove(grade);
        grade.getTeachers().remove(this);
        return this;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Teacher teacher = (Teacher) o;
        if (teacher.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teacher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + getId() +
            ", teacherNo='" + getTeacherNo() + "'" +
            ", teacherName='" + getTeacherName() + "'" +
            ", subject='" + getSubject() + "'" +
            ", timeTable='" + getTimeTable() + "'" +
            ", teacherTel='" + getTeacherTel() + "'" +
            "}";
    }
}
