package com.nitesh.dubey.helloiiitg;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class StudentRegister extends AppCompatActivity {

    Spinner spinner;
    ListView listView;
    ArrayAdapter<String> adapter1, adapter2,adapter3;
    Spinner departmentStudent,selectelective1, selectelective2, selectelective3, selecthsselective ;
    String elective1 = "null", elective2 = "null", elective3 = "null", HSSelective = "null";
    EditText emailStudent, passwordStudent, phoneStudent, nameStudent, rollNoStudent, backlog1, backlog2, backlog3, creditbacklog1, creditbacklog2, creditbacklog3, register;

    String department = "xyz";
    ArrayList<String> courses;
    String selectedSemester = "pqr";
    int semesterCredits;

    ArrayAdapter<String> adapter;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        listView = findViewById(R.id.courseList);

        mAuth = FirebaseAuth.getInstance();    //Firebase Authentication

        emailStudent = findViewById(R.id.emailStudent);
        passwordStudent = findViewById(R.id.passwordStudent);
        phoneStudent = findViewById(R.id.phoneStudent);
        nameStudent = findViewById(R.id.nameStudent);
        rollNoStudent = findViewById(R.id.rollNoStudent);
        backlog1 = findViewById(R.id.backlog1);
        backlog2 = findViewById(R.id.backlog2);
        backlog3 = findViewById(R.id.backlog3);
        creditbacklog1 = findViewById(R.id.creditbacklog1);
        creditbacklog2 = findViewById(R.id.creditbacklog2);
        creditbacklog3 = findViewById(R.id.creditbacklog3);


        departmentStudent = (Spinner)findViewById(R.id.departmentStudent);
        selectelective1 = findViewById(R.id.selectelective1);
        selectelective2 = findViewById(R.id.selecetelective2);
        selectelective3 = findViewById(R.id.selectelective3);
        selecthsselective = findViewById(R.id.hsselective);

        courses = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(StudentRegister.this,android.R.layout.simple_list_item_1,courses);
        listView.setAdapter(adapter);

        spinner = findViewById(R.id.semesterStudent);

        //Choosing The department
        departmentStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courses.clear();
                if(position == 0) return;
                department = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Selecting Semester, Fetching Courses from firebase and getting the semester credits
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    courses.clear();
                    if (position == 0) return;
                    listView.setVisibility(View.VISIBLE);
                    selectedSemester = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(StudentRegister.this, selectedSemester,Toast.LENGTH_LONG).show();
                    if(department != null && selectedSemester != null)
                    showCoursesInList();


                    //Making Elective spinners visible/Invisible
                    if(selectedSemester.matches("sem7")) selectelective1.setVisibility(View.VISIBLE);
                    else if(selectedSemester.matches("sem8")) {
                        selectelective1.setVisibility(View.VISIBLE);
                        selectelective2.setVisibility(View.VISIBLE);
                        selectelective3.setVisibility(View.VISIBLE);
                    }
                    else{
                        selectelective1.setVisibility(View.GONE);
                        selectelective2.setVisibility(View.GONE);
                        selectelective3.setVisibility(View.GONE);
                    }

                    if(selectedSemester.matches("sem7") || selectedSemester.matches("sem8")) {
                        if (department.matches("CSE")) {
                            adapter1 = new ArrayAdapter<>(StudentRegister.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.CSE_Electives));
                        } else if (department.matches("ECE")) {
                            adapter1 = new ArrayAdapter<>(StudentRegister.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ECE_Electives));
                        }
                        selectelective1.setAdapter(adapter1);

                    }

                    if(selectedSemester.matches("sem8")) {
                        if (department.matches("CSE") && selectedSemester.matches("sem8")) {
                            adapter2 = new ArrayAdapter<>(StudentRegister.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.CSE_Electives));
                            adapter3 = new ArrayAdapter<>(StudentRegister.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.CSE_Electives));
                        } else {
                            adapter2 = new ArrayAdapter<>(StudentRegister.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ECE_Electives));
                            adapter3 = new ArrayAdapter<>(StudentRegister.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ECE_Electives));
                        }

                        selectelective2.setAdapter(adapter2);
                        selectelective3.setAdapter(adapter3);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            //if(selectedSemester.matches("sem7") || selectedSemester.matches("sem8")) {


                selectelective1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) return;
                        elective1 = parent.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            //}


            //if(selectedSemester.matches("sem8")) {

                selectelective2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position == 0) return;
                        elective2 = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                selectelective3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position == 0) return;
                        elective3 = parent.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            //}

        selecthsselective.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) return;
                HSSelective = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


   //Function For Fetching Courses in listview from firebase
    private void showCoursesInList () {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(department).child(selectedSemester);
        ref.addValueEventListener(new ValueEventListener() {
            String credits;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    courses.add(ds.getValue(String.class));
                    credits = ds.getValue(String.class);
                }
                adapter.notifyDataSetChanged();
                semesterCredits = Integer.parseInt(credits);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Register to Hello IIITG
    public void register (View view) {
        String bklg1 = backlog1.getText().toString().trim();
        String bklg2 = backlog2.getText().toString().trim();
        String bklg3 = backlog3.getText().toString().trim();
        String bklg1credit = creditbacklog1.getText().toString().trim();
        String bklg2credit = creditbacklog2.getText().toString().trim();
        String bklg3credit = creditbacklog3.getText().toString().trim();
        if(bklg1.isEmpty()) {
            backlog1.setError("Enter null if no Backlog");
            backlog1.requestFocus();
            return;
        }
        if(bklg2.isEmpty()) {
            backlog2.setError("Enter null if no Backlog");
            backlog2.requestFocus();
            return;
        }
        if(bklg3.isEmpty()) {
            backlog3.setError("Enter null if no Backlog");
            backlog3.requestFocus();
            return;
        }
        if(bklg1credit.isEmpty()) {
            creditbacklog1.setError("Enter 0 if no backlog");
            creditbacklog1.requestFocus();
            return;
        }
        if(bklg2credit.isEmpty()) {
            creditbacklog2.setError("Enter 0 if no backlog");
            creditbacklog2.requestFocus();
            return;
        }
        if(bklg3credit.isEmpty()) {
            creditbacklog3.setError("Enter 0 if no backlog");
            creditbacklog3.requestFocus();
            return;
        }

        int c1 = Integer.parseInt(bklg1credit);
        int c2 = Integer.parseInt(bklg2credit);
        int c3 = Integer.parseInt(bklg3credit);

        if((semesterCredits + c1 + c2 + c3) > 1.25 * semesterCredits) {
            Toast.makeText(StudentRegister.this, "You Cant Take Courses with credits more than 125% of actual credits of this semester",Toast.LENGTH_LONG).show();
            return;
        }

        String email = emailStudent.getText().toString().trim();
        String password = passwordStudent.getText().toString().trim();
        String phone = phoneStudent.getText().toString().trim();
        String name = nameStudent.getText().toString().trim();
        String roll = rollNoStudent.getText().toString().trim();

        if(email.isEmpty()) {
            emailStudent.setError("Email is Required");
            emailStudent.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailStudent.setError("Please Enter Valid Email Address");
            emailStudent.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            passwordStudent.setError("Password is Required");
            passwordStudent.requestFocus();
            return;
        }
        if(phone.isEmpty()) {
            phoneStudent.setError("Phone Number is Required");
            phoneStudent.requestFocus();
            return;
        }
        if(name.isEmpty()) {
            nameStudent.setError("Name is Required");
            nameStudent.requestFocus();
            return;
        }
        if(roll.isEmpty()) {
            rollNoStudent.setError("Roll Number is Required");
            rollNoStudent.requestFocus();
            return;
        }

        if(password.length() < 6) {
            passwordStudent.setError("Minimum length of password is 6");
            passwordStudent.requestFocus();
            return;
        }

        final Student student = new Student(email,phone,name,roll, department, selectedSemester, courses, elective1, elective2, elective3, HSSelective, bklg1, bklg2, bklg3);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_LONG).show();
                    signIn(student);
                } else if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText(getApplicationContext(), "Email already registered", Toast.LENGTH_LONG).show();
                }
            }
        });





    }

    private void signIn (final Student student) {
        mAuth.signInWithEmailAndPassword(emailStudent.getText().toString().trim(), passwordStudent.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser usr = FirebaseAuth.getInstance().getCurrentUser();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                    ref.child(usr.getUid()).setValue(student);

                }
            }
        });
    }



}