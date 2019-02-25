package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TimeTable.
 */
@Entity
@Table(name = "time_table")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TimeTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "weekday", nullable = false)
    private String weekday;

    @NotNull
    @Column(name = "jhi_number", nullable = false)
    private Integer number;

    @ManyToOne
    @JsonIgnoreProperties("timeTables")
    private Teacher teacher;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWeekday() {
        return weekday;
    }

    public TimeTable weekday(String weekday) {
        this.weekday = weekday;
        return this;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public Integer getNumber() {
        return number;
    }

    public TimeTable number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public TimeTable teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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
        TimeTable timeTable = (TimeTable) o;
        if (timeTable.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timeTable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimeTable{" +
            "id=" + getId() +
            ", weekday='" + getWeekday() + "'" +
            ", number=" + getNumber() +
            "}";
    }
}
