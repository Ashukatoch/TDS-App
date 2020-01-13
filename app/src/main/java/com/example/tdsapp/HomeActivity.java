package com.example.tdsapp;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tdsapp.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;
import java.util.zip.Inflater;

public class HomeActivity extends AppCompatActivity {
private Toolbar toolbar;
private FloatingActionButton fbtn;
private EditText name,description;
private Button save,cancel;
private RecyclerView recyclerView;
//Firebase
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    toolbar=findViewById(R.id.toolbar);
    fbtn=findViewById(R.id.fabadd);
    auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        String uid=user.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("All Data").child(uid);
        recyclerView=findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("TDS APP");
    fbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            AddData();
        }
    });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Data>options=new FirebaseRecyclerOptions.Builder<Data>().setQuery(databaseReference,Data.class).build();
        FirebaseRecyclerAdapter adapter=new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Data model)
            {
                       holder.SetDate(model.getDate());
                       holder.SetTitle(model.getName());
                       holder.SetDescription(model.getDescritption());
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return null;
            }
        };
        recyclerView.setAdapter(adapter);
    }
    
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        View myview;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
        myview=itemView;
        }
        public void SetTitle(String title)
        {
            TextView mtitle=myview.findViewById(R.id.title);
            mtitle.setText(title);
        }
        public void SetDescription(String desc)
        {
            TextView mdesc=myview.findViewById(R.id.description);
            mdesc.setText(desc);
        }
        public void SetDate(String date)
        {
            TextView mdate=myview.findViewById(R.id.date);
            mdate.setText(date);

        }
    }

    private void AddData()
    {
        AlertDialog.Builder mydialog=new AlertDialog.Builder(this);
        LayoutInflater inflator=LayoutInflater.from(getApplicationContext());
        View view=inflator.inflate(R.layout.inputlayout,null);
        name=view.findViewById(R.id.name);
        description=view.findViewById(R.id.description);
        save=view.findViewById(R.id.btn_save);
        cancel=view.findViewById(R.id.btn_cancel);
        final AlertDialog dialog=mydialog.create();
        dialog.setView(view);
        dialog.setCancelable(false);
        dialog.show();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String mname=name.getText().toString().trim();
                String mdesc=description.getText().toString().trim();
                if(TextUtils.isEmpty(mname))
                    name.setError("Required Field....");
                if(TextUtils.isEmpty(mdesc))
                    description.setError("Required Field....");
                String id=databaseReference.push().getKey();
                String date= DateFormat.getDateInstance().format(new Date());
                Data data=new Data(mname,mdesc,id,date);
                databaseReference.child(id).setValue(data);
                Toast.makeText(HomeActivity.this, "Data uploaded", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

}
