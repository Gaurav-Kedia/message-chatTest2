package com.gaurav.chattest2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ListView texts;
    Button btn;
    EditText edt;
    int i = 0, a;
    String[] array = new String[99999];
    Firebase mfire;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.send);
        edt = (EditText) findViewById(R.id.edittext);
        Firebase.setAndroidContext(this);

        texts = (ListView) findViewById(R.id.textv);
        btn = (Button) findViewById(R.id.send);
        edt = (EditText) findViewById(R.id.edittext);
        mfire = new Firebase("https://chattest2-63ed0.firebaseio.com/messagess");
        DatabaseReference myRef = database.getReference("messagess");

        myRef.setValue("Hello World");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase mchild = mfire.child("message" + Integer.toString(i));
                String str = edt.getText().toString();
                mchild.setValue(str);
                i++;
                edt.setText("");
            }
        });
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = (String) dataSnapshot.getValue(Boolean.parseBoolean("messagess"));
                Log.d("Value", "Value is: " + dataSnapshot.getValue(Boolean.parseBoolean("messagess")));
                showData(dataSnapshot.getValue(Boolean.parseBoolean("messagess")));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Value", "Failed to read value.", error.toException());
            }
        });
    }

    private void showData(Object messagess) {
        String str1 = String.valueOf(messagess);
       /* int a = 1, b = 0, c = 0;
       for (int j = 0; j < str1.length(); j++) {
            if (str1.charAt(j) == '=') {
                b = j + 1;
                       try{ for (int l = j + 1; (str1.charAt(l) != ','); l++) {
                                c = l;
                            j = l + 1;
                        }} catch (Exception e){
                           System.out.print(e);
                       }
            } else {
                        if (b < c) {
                    array[a] = str1.substring(b, c + 1);
                    c = 0;
                    a++;
                }
            }
        }
        */

        array = new String[]{"Gaurav", "Kedia", "is", "good"};

            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);

            texts.setAdapter(arrayAdapter);
    }
}
