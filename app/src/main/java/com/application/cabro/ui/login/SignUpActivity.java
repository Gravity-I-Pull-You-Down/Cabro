package com.application.cabro.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.Touch;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.application.cabro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;

import static java.lang.System.*;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String StateName, CityName, Name, Email, phonenumber,PasswordText,TimeStamp;
    private ArrayAdapter<CharSequence> CityAdapter;
    private ArrayAdapter<CharSequence> StateAdapter;
    private Spinner StateSpinner;
    private Spinner CitySpinner;
    private Button SubmitButton;
    private EditText EditTextName, EditTextEmail, EditTextPhoneNumber,EditTextPassword;
    private FirebaseDatabase SignUpDataBase ;
    private DatabaseReference SignUpRefernce;
    private FirebaseAuth SignUpaAuthinticate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditTextName = findViewById(R.id.username);
        EditTextEmail = findViewById(R.id.email);
        EditTextPhoneNumber = findViewById(R.id.phoneNumber);
        EditTextPassword = findViewById(R.id.password);

        StateSpinner = findViewById(R.id.StateSpinner);
        CitySpinner = findViewById(R.id.CitySpinner);
        SubmitButton = findViewById(R.id.Submit);
//        CityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        StateAdapter = ArrayAdapter.createFromResource(this, R.array.india_states, android.R.layout.simple_spinner_item);
        StateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CitySpinner.setAdapter(CityAdapter);
        StateSpinner.setAdapter(StateAdapter);
        StateSpinner.setOnItemSelectedListener(this);
        /*Firebase.setAndroidContext(this);
        NameInDatabase = new Firebase("https://carbro-722b6.firebaseio.com/");
        EmailInDataBase = new Firebase("https://carbro-722b6.firebaseio.com/");
        PhoneInDataBase = new Firebase("https://carbro-722b6.firebaseio.com/");
        StateInDataBase = new Firebase("https://carbro-722b6.firebaseio.com/");
        CityInDataBase = new Firebase("https://carbro-722b6.firebaseio.com/");*/

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitButtonPress();

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (position == 34) {
            CityAdapter = ArrayAdapter.createFromResource(this, R.array.CityUttarpradesh, android.R.layout.simple_spinner_item);
        }
        CitySpinner.setAdapter(CityAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

    public void SubmitButtonPress() {
        SignUpDataBase = FirebaseDatabase.getInstance();
        SignUpRefernce = SignUpDataBase.getReference("Users");
        StateName = StateSpinner.getSelectedItem().toString();
        CityName = CitySpinner.getSelectedItem().toString();
        Name = EditTextName.getText().toString();
        Email = EditTextEmail.getText().toString();
        phonenumber = EditTextPhoneNumber.getText().toString();
        PasswordText = EditTextPassword.getText().toString();

       // Toast.makeText(SignUpActivity.this, StateName + " \t" + CityName, Toast.LENGTH_SHORT).show();
        //SignUpRefernce.setValue(Name);



        TimeStamp= SignUpRefernce.push().getKey();
        //SignUpRefernce NameCreater = SignUpDataBase.getReference();
        SignUpRefernce.child(TimeStamp).child("Name").setValue(Name);

        ///DatabaseReference EmailCreater = SignUpDataBase.getReference();
        SignUpRefernce.child(TimeStamp).child("Email").setValue(Email);
        //DatabaseReference PhonenumberCreater = SignUpDataBase.getReference();
        SignUpRefernce.child(TimeStamp).child("Phone Number").setValue(phonenumber);
       // DatabaseReference StateCreater = SignUpDataBase.getReference();
        SignUpRefernce.child(TimeStamp).child("State Name").setValue(StateName);
        //DatabaseReference CityCreater = SignUpDataBase.getReference();
        SignUpRefernce.child(TimeStamp).child("City Name").setValue(CityName);
        //DatabaseReference PasswordField = SignUpDataBase.getReference();
        SignUpRefernce.child(TimeStamp).child("Password").setValue(PasswordText).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "SignUp Failure", Toast.LENGTH_LONG).show();
                    //finish();
                    startActivity(getIntent());
                }
            }
        });


//===================================Auth=====================================//
        SignUpaAuthinticate = FirebaseAuth.getInstance();
        SignUpaAuthinticate.createUserWithEmailAndPassword(Email,PasswordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "User Authentication Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(SignUpActivity.this, "User Authentication Failure", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
}
