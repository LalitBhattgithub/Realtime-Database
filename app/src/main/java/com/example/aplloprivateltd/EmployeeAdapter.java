package com.example.aplloprivateltd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeAdapter extends FirebaseRecyclerAdapter<MainModal, EmployeeAdapter.myViewHolder> {

    // parameterize constructor
    public EmployeeAdapter(@NonNull FirebaseRecyclerOptions<MainModal> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull MainModal model) {
        holder.name.setText(model.getName());
        holder.department.setText(model.getDepartment());
        holder.email.setText(model.getEmail());
        Glide.with(holder.img.getContext())  //for getting image from database
                .load(model.getEurl())
                .into(holder.img);

        //for Edit button
   holder.editBtn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
     final  DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
             .setContentHolder(new ViewHolder(R.layout.update_popup))
             .setExpanded(true,770)
             .create();

           Log.e("api","this is done");     //for showing msg in logcat

           //getting view
     View view=dialogPlus.getHolderView();

     //taking variable and finding id foe them

           EditText name=view.findViewById(R.id.textUpdatename);
           EditText department=view.findViewById(R.id.textUpdateDepartment);
           EditText email=view.findViewById(R.id.textUpdateEmail);
           EditText eurl=view.findViewById(R.id.textUpdateImage);

           //intilizing  update button
           Button updateBtn=view.findViewById(R.id.updateBTn);


           //set the text on dialog
           name.setText(model.getName());
           department.setText(model.getDepartment());
           email.setText(model.getEmail());
           eurl.setText(model.getEurl());


           dialogPlus.show();

           updateBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   Map<String,Object> map =new HashMap<>();
                   map.put("name",name.getText().toString());
                   map.put("department",department.getText().toString());
                   map.put("email",email.getText().toString());
                   map.put("eurl",eurl.getText().toString());
                   
                   //ref of firebase database
                   FirebaseDatabase.getInstance().getReference().child("employees").child(getRef(position).getKey()).updateChildren(map)

                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void unused) {
                                   dialogPlus.dismiss();
                                   Toast.makeText(holder.name.getContext(), "data updated successfully", Toast.LENGTH_SHORT).show();
                               }
                           })


                           //for fail
                           .addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure( Exception e) {
                                   Toast.makeText(holder.name.getContext(), "somthing gone wrong", Toast.LENGTH_SHORT).show();
                               }
                           });





                   
               }
           }); //update button close

       }
   });


         holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                   builder.setTitle("Are you sure");
                   builder.setMessage("Deleted data can't be undo");

                   builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           FirebaseDatabase.getInstance().getReference().child("employees")
                                   .child(Objects.requireNonNull(getRef(position).getKey())).removeValue();
                       }
                   });

                   builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           Toast.makeText(holder.name.getContext(), "cancelled", Toast.LENGTH_SHORT).show();
                       }
                   });
                   builder.show();
               }
           }); //delete button close*/
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
     CircleImageView img;
     TextView name,department,email;
      Button editBtn,deleteBtn;

       public myViewHolder(@NonNull View itemView) {
           super(itemView);

           img=(CircleImageView) itemView.findViewById(R.id.img1);
           name=itemView.findViewById(R.id.textName);
           department=itemView.findViewById(R.id.departmentview);
           email=itemView.findViewById(R.id.empEmail);
           editBtn=(Button)itemView.findViewById(R.id.editBtn);
           deleteBtn=itemView.findViewById(R.id.deleteBtn);


       }
   }
}
