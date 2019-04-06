package com.nitesh.dubey.helloiiitg;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {

    public String email;
    public String phone;
    public String name;
    public String rollNo;
    public String department;
    public String semester;
    ArrayList<Subject> subjects = new ArrayList<>();
    ArrayList<Subject> electives = new ArrayList<>();
    ArrayList<Subject>backlogs = new ArrayList<>();

    public Student(){

    }

    public Student (String email, String phone, String name, String rollNo, String department, String semester, ArrayList<String>course, String elective1,
                    String elective2, String elective3, String HSSelective, String backlog1, String backlog2, String backlog3) {

        this.email = email;
        this.phone = phone;
        this.name = name;
        this.rollNo = rollNo;
        this.department = department;
        this.semester = semester;
        for(String studentCourses : course) {
            this.subjects.add(new Subject(studentCourses, "0", "0"));
        }
        electives.add(new Subject(elective1, "0", "0"));
        electives.add(new Subject(elective2, "0", "0"));
        electives.add(new Subject(elective3, "0", "0"));
        electives.add(new Subject(HSSelective, "0","0"));
        backlogs.add(new Subject(backlog1, "0","0"));
        backlogs.add(new Subject(backlog2,"0","0"));
        backlogs.add(new Subject(backlog3, "0", "0"));

    }
}
