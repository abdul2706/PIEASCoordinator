package com.example.pieascoordinator;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String userName;
    private boolean isTeacher;
    private ArrayList<Subject> subjects;

    public User(String userName, ArrayList<Subject> subjects, boolean isTeacher) {
        this.userName = userName;
        this.isTeacher = isTeacher;
        this.subjects = subjects;
    }

    public String[] getSubjectNamesAsArray() {
        String[] toReturn = new String[getSubjects().size()];
        for (int i = 0; i < toReturn.length; i++) {
            toReturn[i] = getSubjects().get(i).getSubjectName();
        }
        return toReturn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    public void addSubject(Subject subject) {
        if (!getSubjects().contains(subject)) {
            getSubjects().add(subject);
        }
    }

    public String[] getAllSubjects() {
        String[] toReturn = new String[getSubjects().size()];
        for (int i = 0; i < toReturn.length; i++) {
            toReturn[i] = getSubjects().get(i).getSubjectName();
        }
        return toReturn;
    }

    public void removeSubject(Subject subject) {
        getSubjects().remove(subject);
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    public boolean isStudent() {
        return !isTeacher();
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public int getNumberOfSubjects() {
        return getSubjects().size();
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder("Name: " + getUserName() + "\nIS_TEACHER: " + isTeacher() + "\nisStudent: " + isStudent() + "\n");
        for (int i = 0; i < getNumberOfSubjects(); i++) {
            toReturn.append(getSubjects().get(i).getSubjectName()).append("\n");
        }
        return toReturn.toString();
    }

}
