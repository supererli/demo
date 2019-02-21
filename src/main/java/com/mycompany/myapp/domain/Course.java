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
 * A Course.
 */
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @NotNull
    @Column(name = "course_type", nullable = false)
    private String courseType;

    @ManyToOne
    @JsonIgnoreProperties("courses")
    private Teacher teacher;

    @ManyToMany(mappedBy = "courses")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Lab> labs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public Course courseName(String courseName) {
        this.courseName = courseName;
        return this;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public Course courseType(String courseType) {
        this.courseType = courseType;
        return this;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Course teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<Lab> getLabs() {
        return labs;
    }

    public Course labs(Set<Lab> labs) {
        this.labs = labs;
        return this;
    }

    public Course addLab(Lab lab) {
        this.labs.add(lab);
        lab.getCourses().add(this);
        return this;
    }

    public Course removeLab(Lab lab) {
        this.labs.remove(lab);
        lab.getCourses().remove(this);
        return this;
    }

    public void setLabs(Set<Lab> labs) {
        this.labs = labs;
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
        Course course = (Course) o;
        if (course.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), course.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", courseName='" + getCourseName() + "'" +
            ", courseType='" + getCourseType() + "'" +
            "}";
    }
}
