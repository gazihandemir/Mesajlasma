package com.example.mesajlasma;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    String userName, otherName;
    TextView tvOtherUser;
    ImageView imgBackbtn, imgSendbtn;
    EditText edChatSend;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    RecyclerView chatRecyclerView;
    ChatAdapter chatAdapter;
    List<MesajModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tanimla();
        loadMesaj();
    }

    public void tanimla() {
        list = new ArrayList<>();
        tvOtherUser = findViewById(R.id.tvotherUser);
        imgBackbtn = findViewById(R.id.imgBackbtn);
        imgSendbtn = findViewById(R.id.imgSendBtn);
        edChatSend = findViewById(R.id.edChatSend);
        userName = getIntent().getExtras().getString("userName");
        otherName = getIntent().getExtras().getString("otherName");
        Log.i("alınan degerler : ", userName + "--" + otherName);
        tvOtherUser.setText(otherName);
        imgBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("kAdi", userName);
                startActivity(intent);
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        imgSendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mesaj = edChatSend.getText().toString();
                edChatSend.setText("");
                mesajGonder(mesaj);
            }
        });
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ChatActivity.this, 1);
        chatRecyclerView.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(ChatActivity.this, list, ChatActivity.this, userName);
        chatRecyclerView.setAdapter(chatAdapter);

    }

    public void mesajGonder(String text) {

        final Map messageMap = new HashMap();
        messageMap.put("text", text);
        messageMap.put("from", userName);
        final String key = reference.child("Mesajlar").child(userName).child(otherName).push().getKey();
        reference.child("Mesajlar").child(userName).child(otherName).child(key).setValue(messageMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            reference.child("Mesajlar").child(otherName).child(userName).child(key).setValue(messageMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });
                        }
                    }
                });
    }

    public void loadMesaj() {

        reference.child("Mesajlar").child(userName).child(otherName)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        MesajModel mesajModel = dataSnapshot.getValue(MesajModel.class);
                        //  Log.i("mesajlar", mesajModel.toString());
                        list.add(mesajModel);
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
