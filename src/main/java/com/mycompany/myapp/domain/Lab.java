package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Lab.
 */
@Entity
@Table(name = "lab")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lab implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "lab_name", nullable = false)
    private String labName;

    @NotNull
    @Column(name = "lab_type", nullable = false)
    private String labType;

    @Column(name = "lab_volume")
    private Integer labVolume;

    @NotNull
    @Column(name = "lab_time", nullable = false)
    private Instant labTime;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "lab_course",
               joinColumns = @JoinColumn(name = "labs_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "courses_id", referencedColumnName = "id"))
    private Set<Course> courses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabName() {
        return labName;
    }

    public Lab labName(String labName) {
        this.labName = labName;
        return this;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getLabType() {
        return labType;
    }

    public Lab labType(String labType) {
        this.labType = labType;
        return this;
    }

    public void setLabType(String labType) {
        this.labType = labType;
    }

    public Integer getLabVolume() {
        return labVolume;
    }

    public Lab labVolume(Integer labVolume) {
        this.labVolume = labVolume;
        return this;
    }

    public void setLabVolume(Integer labVolume) {
        this.labVolume = labVolume;
    }

    public Instant getLabTime() {
        return labTime;
    }

    public Lab labTime(Instant labTime) {
        this.labTime = labTime;
        return this;
    }

    public void setLabTime(Instant labTime) {
        this.labTime = labTime;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public Lab courses(Set<Course> courses) {
        this.courses = courses;
        return this;
    }

    public Lab addCourse(Course course) {
        this.courses.add(course);
        course.getLabs().add(this);
        return this;
    }

    public Lab removeCourse(Course course) {
        this.courses.remove(course);
        course.getLabs().remove(this);
        return this;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
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
        Lab lab = (Lab) o;
        if (lab.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lab.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lab{" +
            "id=" + getId() +
            ", labName='" + getLabName() + "'" +
            ", labType='" + getLabType() + "'" +
            ", labVolume=" + getLabVolume() +
            ", labTime='" + getLabTime() + "'" +
            "}";
    }
}
