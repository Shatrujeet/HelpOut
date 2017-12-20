package itp341.naruka.shatrujeet.finalprojectnarukashatrujeet;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class FindEvents extends AppCompatActivity {

    ListView listVeiwEvents;
    List<Event> eventList;
    int sysYear,sysMonth,sysDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_events);

        eventList=new ArrayList<>();
        listVeiwEvents=(ListView) findViewById(R.id.listView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        update();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        update();
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    public void update()
    {
        DatabaseReference db= FirebaseDatabase.getInstance().getReference("Events");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventList.clear();
                for(DataSnapshot eventSnapShot:dataSnapshot.getChildren())
                {
                    Event event=eventSnapShot.getValue(Event.class);
                    int year,month,day,hour ,minute;

                    String time[] =event.getTime().toString().split(":");

                    String date[] =event.getDate().toString().split("/");

                    hour =Integer.parseInt(time[0]);
                    minute=Integer.parseInt(time[1]);

                    month=Integer.parseInt(date[0]);
                    day=Integer.parseInt(date[1]);
                    year = Integer.parseInt(date[2]);

                    if(compareDate(year,month,day))
                    {
                        if(sysMonth==month && sysDay==day && year==sysYear)
                        {
                            if(compareTime(hour,minute))
                                eventList.add(event);

                        }
                        else
                        eventList.add(event);

                    }

                }

                EventsList adapter=new EventsList(FindEvents.this,eventList);
                listVeiwEvents.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
            return false;
        }


    }

    public boolean compareDate(int year,int month,int dayOfMonth) {
        String s =  (month ) + "-" + dayOfMonth + "-" + year;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date strDate = null;
        try {
            strDate = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (System.currentTimeMillis() > strDate.getTime()) {


            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            sysYear=calendar.get(Calendar.YEAR);
            sysMonth=calendar.get(calendar.MONTH)+1;
            sysDay=calendar.get(calendar.DAY_OF_MONTH);
            Log.d("Dates1",sysMonth+"/"+sysDay+"/"+sysYear);
            Log.d("Dates2",month+"/"+dayOfMonth+"/"+year);

            if(sysMonth==month && sysDay==dayOfMonth && year==sysYear)
            {

                return true;
            }

            return false;
        }
        else
        {
            return true;
        }

    }
}
