package com.example.travelku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterAct extends AppCompatActivity {
Button next,back;
EditText username, password, email;

DatabaseReference reference;
String USERNAME_KEY=" usernamekey";
String username_key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);

        next =findViewById(R.id.next);
        back =findViewById(R.id.login);

        //berpindah Activity Lain ke activity registertwo
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotonextregister = new Intent(RegisterAct.this, RegisterTwoAct.class);
                startActivity(gotonextregister);
            }
        });
        //menyimpan data ke localstorage (handphone)
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(username_key, username.getText().toString());
        editor.apply();

        //Proses Simpan ke Database Firebase
        reference = FirebaseDatabase.getInstance().getReference().child("User").child(username.getText().toString());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                dataSnapshot.getRef().child("email").setValue(email.getText().toString());
                dataSnapshot.getRef().child("saldo").setValue(100000);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //berpindah Activity Lain ke activity Login
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotologin = new Intent(RegisterAct.this, LoginAct.class);
                startActivity(gotologin);
            }
        });
    }
}
//1711500001 Heri Anggara