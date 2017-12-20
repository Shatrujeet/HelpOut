package itp341.naruka.shatrujeet.finalprojectnarukashatrujeet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrganizeEvent extends AppCompatActivity {

    Button createEvent,checkAttendees,deleteEvent;
    EditText eventCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organize_event);


        createEvent=(Button)findViewById(R.id.buttonCreate);
        checkAttendees=(Button)findViewById(R.id.buttonCheck);
        eventCode=(EditText)findViewById(R.id.editTextCode);
        deleteEvent=(Button)findViewById(R.id.buttonRemoveEvent);

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CreateEvent.class));
            }
        });

        checkAttendees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!eventCode.getText().toString().isEmpty())
               {
                   final String event=eventCode.getText().toString();

                   DatabaseReference fb= FirebaseDatabase.getInstance().getReference("Events");
                   fb.child(event).child("attendees").addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot snapshot) {
                           try {
                               if (snapshot.getValue() != null) {
                                   try {
                                       Toast.makeText(getApplicationContext(), getResources().getString(R.string.numberAttendeed)+":"+snapshot.getValue(), Toast.LENGTH_SHORT).show();
                                   } catch (Exception e) {
                                       e.printStackTrace();
                                   }
                               } else {
                                 //  Toast.makeText(getApplicationContext(), getResources().getString(R.string.error1), Toast.LENGTH_SHORT).show();
                               }
                           } catch (Exception e) {
                               e.printStackTrace();
                           }
                       }

                       @Override
                       public void onCancelled(DatabaseError firebaseError) {
                           Log.e("onCancelled", " cancelled");
                       }
                   });
               }
               else
               {
                   Toast.makeText(getApplicationContext(),getResources().getString(R.string.error1),Toast.LENGTH_SHORT).show();
               }
            }
        });



        deleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!eventCode.getText().toString().isEmpty())
                {
                    final String event=eventCode.getText().toString();

                    final DatabaseReference fb= FirebaseDatabase.getInstance().getReference("Events");

                    fb.child(event).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // TODO: handle the case where the data already exists

                                fb.child(event).removeValue();
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg3), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else {
                                // TODO: handle the case where the data does not yet exist
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error1), Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError firebaseError) {
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.error1).toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
