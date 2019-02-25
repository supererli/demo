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
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "stu_no", nullable = false)
    private String stuNo;

    @OneToMany(mappedBy = "studentName")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Grade> classNames = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("studentNames")
    private Grade className;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Student name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStuNo() {
        return stuNo;
    }

    public Student stuNo(String stuNo) {
        this.stuNo = stuNo;
        return this;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public Set<Grade> getClassNames() {
        return classNames;
    }

    public Student classNames(Set<Grade> grades) {
        this.classNames = grades;
        return this;
    }

    public Student addClassName(Grade grade) {
        this.classNames.add(grade);
        grade.setStudentName(this);
        return this;
    }

    public Student removeClassName(Grade grade) {
        this.classNames.remove(grade);
        grade.setStudentName(null);
        return this;
    }

    public void setClassNames(Set<Grade> grades) {
        this.classNames = grades;
    }

    public Grade getClassName() {
        return className;
    }

    public Student className(Grade grade) {
        this.className = grade;
        return this;
    }

    public void setClassName(Grade grade) {
        this.className = grade;
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
        Student student = (Student) o;
        if (student.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", stuNo='" + getStuNo() + "'" +
            "}";
    }
}
