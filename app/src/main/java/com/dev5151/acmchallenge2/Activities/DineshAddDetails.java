package com.dev5151.acmchallenge2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dev5151.acmchallenge2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DineshAddDetails extends AppCompatActivity {

    EditText name,username,email,pass;
    RadioButton male,female;
    //RadioGroup gender1;
    Button register;
    DatabaseReference databaseReference;
    String gender="";
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinesh_add_details);


        name=findViewById(R.id.name);
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        register=findViewById(R.id.register);
        //gender1=findViewById(R.id.gender1);

        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        firebaseAuth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("") || username.getText().toString().equals("") || email.getText().toString().equals("") || pass.getText().toString().equals("") || (!male.isChecked() && !female.isChecked())){
                    if(name.getText().toString().equals(""))
                        name.setError("Required");
                    if(username.getText().toString().equals(""))
                        username.setError("Required");
                    if(email.getText().toString().equals(""))
                        email.setError("Required");
                    if(pass.getText().toString().equals(""))
                        pass.setError("Required");
                    if(!male.isChecked() && !female.isChecked())
                    {
                        male.setError("Required");
                        female.setError("Required");
                    }
                }
                else
                {
                    //Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();

                    final String fullName=name.getText().toString();
                    final String userName=username.getText().toString();
                    final String userEmail=email.getText().toString();
                    final String userPass=pass.getText().toString();

                    if(male.isChecked()){
                        gender="Male";
                    }
                    if(female.isChecked()){
                        gender="Female";
                    }



                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPass)
                            .addOnCompleteListener(DineshAddDetails.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        user information=new user(
                                                fullName,
                                                userName,
                                                userEmail,
                                                gender,
                                                userPass
                                        );

                                        FirebaseDatabase.getInstance().getReference("User")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(DineshAddDetails.this, "Registration complete", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(),DineshDisplay.class));

                                            }
                                        });

                                    } else {

                                        Toast.makeText(DineshAddDetails.this, "Registration not successful", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                }
            }
        });

    }

}
