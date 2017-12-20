package itp341.naruka.shatrujeet.finalprojectnarukashatrujeet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class CreateEvent extends AppCompatActivity {

    DatabaseReference fb;
    EditText code, name, location;
    DatePicker datePicker;
    TimePicker timePicker;
    Button create;
    int sysYear,sysMonth,sysDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);


        //step1:set up firebase
        fb=FirebaseDatabase.getInstance().getReference("Events");


        code = (EditText) findViewById(R.id.editTextUnique);
        name = (EditText) findViewById(R.id.editTextName);
        location = (EditText) findViewById(R.id.editTextLocation);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        sysDay=datePicker.getDayOfMonth();
        sysMonth=datePicker.getMonth();
        sysYear=datePicker.getYear();

        create = (Button) findViewById(R.id.button);





        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (code.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error1), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error2), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (location.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error3), Toast.LENGTH_SHORT).show();
                    return;
                }


                int month, year, day,hour,min;
                month = datePicker.getMonth();
                day = datePicker.getDayOfMonth();
                year = datePicker.getYear();
                hour=timePicker.getHour();
                min=timePicker.getMinute();

                if (compareDate(year, month, day) ) {
                    String time = (hour + ":" + min);
                    String date = ((month + 1) + "/" + day + "/" + year);

                    final Event event = new Event(name.getText().toString(), code.getText().toString(), location.getText().toString(), date, time, 0);


                    if((month==sysMonth && day==sysDay && year==sysYear )) {

                        if(compareTime(hour,min)) {
                            fb.child(event.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        // TODO: handle the case where the data already exists
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error6), Toast.LENGTH_SHORT).show();
                                    } else {
                                        // TODO: handle the case where the data does not yet exist
                                        fb.child(event.getId()).setValue(event);
                                        Toast.makeText(getApplicationContext(), "Sucessful creation", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError firebaseError) {
                                }
                            });
                        }
                    }
                    else
                    {
                        fb.child(event.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    // TODO: handle the case where the data already exists
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error6), Toast.LENGTH_SHORT).show();
                                } else {
                                    // TODO: handle the case where the data does not yet exist
                                    fb.child(event.getId()).setValue(event);
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg1), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError firebaseError) {
                            }
                        });
                    }



                }


            }
        });

    }

    public boolean compareTime(int hour,int minute)
    {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");
        String strTime = simpleDateFormatTime.format(currentTime.getTime());
        Log.d("Time",strTime+"  "+hour+":"+minute);
        String[] time= strTime.split(":");
        int currentHour=Integer.parseInt(time[0]);
        int currentMin=Integer.parseInt(time[1]);
        if(hour>currentHour )
        {
            return true;
        }
        else if(hour ==currentHour && minute>currentMin)
        {
            return true;
        }
        else
        {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.error5),Toast.LENGTH_LONG).show();
            return false;
        }


    }

    public boolean compareDate(int year,int month,int dayOfMonth) {
        String s =  (month + 1) + "-" + dayOfMonth + "-" + year;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date strDate = null;
        try {
            strDate = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (System.currentTimeMillis() > strDate.getTime()) {
           if(sysMonth==month && sysDay==dayOfMonth && year==sysYear)
           {
               return true;
           }

            Toast.makeText(getApplicationContext(),getResources().getString(R.string.error4),Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            return true;
        }

    }



}
