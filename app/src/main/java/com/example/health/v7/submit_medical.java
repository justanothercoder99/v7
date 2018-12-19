package com.example.health.v7;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class submit_medical extends AppCompatActivity {

    Button button;
    CheckBox ch1,ch2,ch3,ch4,ch5,ch6,ch7;
    RadioGroup rg1,rg2,rg3,rg4,rg5;
    RadioButton rb1,rb2,rb3,rb4,rb5;
    EditText et1,et2;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_medical);

        mRef = FirebaseDatabase.getInstance().getReference("Patient");

        button = findViewById(R.id.button5);

        rg1 = findViewById(R.id.radionewTB);
        int selectedId1 = rg1.getCheckedRadioButtonId();
        rb1 = findViewById(selectedId1);

        rg2 = findViewById(R.id.radioPrevTB);
        int selectedId2 = rg2.getCheckedRadioButtonId();
        rb2 = findViewById(selectedId2);

        et1 = findViewById(R.id.editText);

        rg3 = findViewById(R.id.radioHIV);
        int selectedId3 = rg3.getCheckedRadioButtonId();
        rb3 = findViewById(selectedId3);

        ch1 = findViewById(R.id.checkBox5);
        ch2 = findViewById(R.id.checkBox6);
        ch3 = findViewById(R.id.checkBox7);

        et2 = findViewById(R.id.editText3);

        ch4 = findViewById(R.id.checkBox);
        ch5 = findViewById(R.id.checkBox2);
        ch6 = findViewById(R.id.checkBox3);
        ch7 = findViewById(R.id.checkBox4);

        rg4 = findViewById(R.id.radioTreat);
        int selectedId4 = rg4.getCheckedRadioButtonId();
        rb4 = findViewById(selectedId4);

        rg5 = findViewById(R.id.radioPyra);
        int selectedId5 = rg5.getCheckedRadioButtonId();
        rb5 = findViewById(selectedId5);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        getValues();
                        Toast.makeText(submit_medical.this, "Patient Details Entered Successfully", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(submit_medical.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }


    public void getValues() {

        String New_TB = rb1.getText().toString();
        String Prev_TB = rb2.getText().toString();
        String Prev_Exposure = et1.getText().toString();
        String HIV = rb3.getText().toString();

        StringBuffer result1 = new StringBuffer();
        result1.append("TB Skin Test:").append(ch1.isChecked());
        result1.append("TB Blood Test:").append(ch3.isChecked());
        result1.append("None:").append(ch2.isChecked());

        String Patient_under_Doctor = et2.getText().toString();

        StringBuffer result2 = new StringBuffer();
        result2.append("Isonizid: ").append(ch4.isChecked());
        result2.append("Pyrazinamide: ").append(ch6.isChecked());
        result2.append("Rifampicin").append(ch5.isChecked());
        result2.append("Ethambutol").append(ch7.isChecked());

        String anti_tb = rb4.getText().toString();
        String resistance = rb5.getText().toString();

        String rs1 = result1.toString();
        String rs2 = result2.toString();

        Medical medical = new Medical(New_TB,Prev_TB,Prev_Exposure,HIV,rs1,Patient_under_Doctor,rs2,anti_tb,resistance);
        mRef.child("new_Medical").setValue(medical);
    }
}
