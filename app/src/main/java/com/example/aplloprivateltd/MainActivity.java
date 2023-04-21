package com.example.aplloprivateltd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
  RecyclerView emprecyclerview;
  EmployeeAdapter employeeAdapter;
  FloatingActionButton addBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emprecyclerview=findViewById(R.id.empRecyecler);
        emprecyclerview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println( "Fetching FCM registration token failed");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();


                        // Log and toast
                        //System.out.println(token);

                        Toast.makeText(MainActivity.this, "your divice ragistation is"+token, Toast.LENGTH_SHORT).show();

                    }
                });


        //finding id
     addBtn=findViewById(R.id.addBtn);

     addBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent iaddact=new Intent(MainActivity.this,AddEmployeeActivity.class);
             startActivity(iaddact);
         }
     });




        FirebaseRecyclerOptions<MainModal> options =
                new FirebaseRecyclerOptions.Builder<MainModal>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("employees"), MainModal.class)
                        .build();
  employeeAdapter =new EmployeeAdapter(options);
  emprecyclerview.setAdapter(employeeAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        employeeAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        employeeAdapter.stopListening();
    }


    //to set search funtionality
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
           getMenuInflater().inflate(R.menu.search,menu);
        MenuItem  item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                txtSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                txtSearch(s);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
    private void txtSearch(String str)
    {
        FirebaseRecyclerOptions<MainModal> options =
                new FirebaseRecyclerOptions.Builder<MainModal>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("employees").orderByChild("name").startAt(str).endAt(str+"~"), MainModal.class)
                        .build();
        employeeAdapter =new EmployeeAdapter(options);
        employeeAdapter.startListening();
        emprecyclerview.setAdapter(employeeAdapter);
    }


}