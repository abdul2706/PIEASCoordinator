package com.example.pieascoordinator;

import java.io.Serializable;

public class Subject implements Serializable {

    private String subjectName, subjectInstructor, courseCode;

    public Subject(String subjectName, String subjectInstructor, String courseCode) {
        this.subjectName = subjectName;
        this.subjectInstructor = subjectInstructor;
        this.courseCode = courseCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectInstructor() {
        return subjectInstructor;
    }

    public void setSubjectInstructor(String subjectInstructor) {
        this.subjectInstructor = subjectInstructor;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectName='" + subjectName + '\'' +
                ", subjectInstructor='" + subjectInstructor + '\'' +
                ", courseCode='" + courseCode + '\'' +
                '}';
    }

}
