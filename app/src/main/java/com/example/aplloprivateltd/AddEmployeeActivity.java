package com.example.aplloprivateltd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class AddEmployeeActivity extends AppCompatActivity {
    private EditText name,department,email,eurl;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        //for add dialogplus
         name=findViewById(R.id.textAddname);
         department=findViewById(R.id.textAddDepartment);
         email=findViewById(R.id.textAddEmail);
         eurl=findViewById(R.id.textAddImage);
         Button addEmployeeBtn = findViewById(R.id.addEmployeeBTn);
        Button backbtn = findViewById(R.id.addActvityBackBTn);

        addEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              if (name.length()==0){
                  Toast.makeText(AddEmployeeActivity.this, "error", Toast.LENGTH_SHORT).show();
                return;
              }
              else {
                  insertData();
                  clearAll();
              }

            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





    }


    //function for inserting data
    private void insertData()
    {

        Map<String,Object> map=new HashMap<>();

        map.put("name",name.getText().toString());
        map.put("department",department.getText().toString());
        map.put("email",email.getText().toString());
        map.put("eurl",eurl.getText().toString());


        FirebaseDatabase.getInstance().getReference().child("employees").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddEmployeeActivity.this, "data inserted successfully", Toast.LENGTH_SHORT).show();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddEmployeeActivity.this, "failed due to some reasone", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    //function for clearing date after insertion
    private void clearAll(){
        name.setText("");
        department.setText("");
        email.setText("");
        eurl.setText("");
    }
}