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
 * A Grade.
 */
@Entity
@Table(name = "grade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Grade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "grade_name", nullable = false)
    private String gradeName;

    @NotNull
    @Column(name = "grade_academy", nullable = false)
    private String gradeAcademy;

    @Column(name = "grade_department")
    private String gradeDepartment;

    @OneToOne    @JoinColumn(unique = true)
    private Teacher headTeacher;

    @OneToOne    @JoinColumn(unique = true)
    private TimeTable teacher;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "grade_teacher",
               joinColumns = @JoinColumn(name = "grades_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "teachers_id", referencedColumnName = "id"))
    private Set<Teacher> teachers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("classNames")
    private Student studentName;

    @OneToMany(mappedBy = "className")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Student> studentNames = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGradeName() {
        return gradeName;
    }

    public Grade gradeName(String gradeName) {
        this.gradeName = gradeName;
        return this;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeAcademy() {
        return gradeAcademy;
    }

    public Grade gradeAcademy(String gradeAcademy) {
        this.gradeAcademy = gradeAcademy;
        return this;
    }

    public void setGradeAcademy(String gradeAcademy) {
        this.gradeAcademy = gradeAcademy;
    }

    public String getGradeDepartment() {
        return gradeDepartment;
    }

    public Grade gradeDepartment(String gradeDepartment) {
        this.gradeDepartment = gradeDepartment;
        return this;
    }

    public void setGradeDepartment(String gradeDepartment) {
        this.gradeDepartment = gradeDepartment;
    }

    public Teacher getHeadTeacher() {
        return headTeacher;
    }

    public Grade headTeacher(Teacher teacher) {
        this.headTeacher = teacher;
        return this;
    }

    public void setHeadTeacher(Teacher teacher) {
        this.headTeacher = teacher;
    }

    public TimeTable getTeacher() {
        return teacher;
    }

    public Grade teacher(TimeTable timeTable) {
        this.teacher = timeTable;
        return this;
    }

    public void setTeacher(TimeTable timeTable) {
        this.teacher = timeTable;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public Grade teachers(Set<Teacher> teachers) {
        this.teachers = teachers;
        return this;
    }

    public Grade addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
        teacher.getGrades().add(this);
        return this;
    }

    public Grade removeTeacher(Teacher teacher) {
        this.teachers.remove(teacher);
        teacher.getGrades().remove(this);
        return this;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Student getStudentName() {
        return studentName;
    }

    public Grade studentName(Student student) {
        this.studentName = student;
        return this;
    }

    public void setStudentName(Student student) {
        this.studentName = student;
    }

    public Set<Student> getStudentNames() {
        return studentNames;
    }

    public Grade studentNames(Set<Student> students) {
        this.studentNames = students;
        return this;
    }

    public Grade addStudentName(Student student) {
        this.studentNames.add(student);
        student.setClassName(this);
        return this;
    }

    public Grade removeStudentName(Student student) {
        this.studentNames.remove(student);
        student.setClassName(null);
        return this;
    }

    public void setStudentNames(Set<Student> students) {
        this.studentNames = students;
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
        Grade grade = (Grade) o;
        if (grade.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grade.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Grade{" +
            "id=" + getId() +
            ", gradeName='" + getGradeName() + "'" +
            ", gradeAcademy='" + getGradeAcademy() + "'" +
            ", gradeDepartment='" + getGradeDepartment() + "'" +
            "}";
    }
}
