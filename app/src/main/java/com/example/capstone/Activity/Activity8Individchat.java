package com.example.capstone.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.capstone.MessageItem;
import com.example.capstone.R;
import com.example.capstone.adapter.chatAdapter;
import com.example.capstone.code;
import com.example.capstone.item3;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Activity8Individchat extends AppCompatActivity {
    private String myUid;
    private ArrayList<item3> iList3;
    Button btnSend, btnOut;
    EditText edtChat;
    FirebaseDatabase fireDB;
    DatabaseReference chatRef;
    ArrayList<MessageItem> msgArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity8_individchat);

        this.initializeData();

        Intent intent = new Intent();

        RecyclerView recyclerView = findViewById(R.id.ichatrecyclerView);
        btnSend = findViewById(R.id.btn_Send);
        btnOut = findViewById(R.id.btn_Out);
        edtChat = findViewById(R.id.edt_Chat);

        fireDB = FirebaseDatabase.getInstance();
        chatRef = fireDB.getReference();

        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        LinearLayoutManager manager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        recyclerView.setAdapter(new chatAdapter(iList3));  // Adapter 등록

        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MessageItem msgItem = snapshot.getValue(MessageItem.class);
                msgArray.add(msgItem);

                if(myUid == msgItem.getUid()) {
                    iList3.add(new item3(msgItem.getMsg(), code.item3.RIGHT_CONTENT));
                    chatRef.removeValue();
                }
                else {
                    iList3.add(new item3(msgItem.getMsg(), code.item3.LEFT_CONTENT));
                    chatRef.removeValue();
                }

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSend(view);
            }
        });

        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Activity8Individchat.this, Activity5Main.class);
                startActivity(mIntent);
            }
        });
    }

    public void clickSend(View view) {
        String msg = edtChat.getText().toString();

        MessageItem msgItem = new MessageItem(msg, FirebaseAuth.getInstance().getCurrentUser().getUid());
        chatRef.push().setValue(msgItem);
        edtChat.setText("");

        //소프트키패드를 안보이도록
        //키패드가 자동으로 켜지는 것을 방지
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }

    public void initializeData()
    {
        iList3 = new ArrayList<>();

        iList3.add(new item3("매칭에 성공하였습니다.", code.item3.CENTER_CONTENT));
        iList3.add(new item3("익명1님이 입장하셨습니다.", code.item3.CENTER_CONTENT));
        iList3.add(new item3("익명2님이 입장하셨습니다.", code.item3.CENTER_CONTENT));
    }
}