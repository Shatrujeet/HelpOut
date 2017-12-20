package itp341.naruka.shatrujeet.finalprojectnarukashatrujeet;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import itp341.naruka.shatrujeet.finalprojectnarukashatrujeet.BuildConfig;
import itp341.naruka.shatrujeet.finalprojectnarukashatrujeet.Event;
import itp341.naruka.shatrujeet.finalprojectnarukashatrujeet.R;

/**
 * Created by shatrujeet lawa on 12/5/2017.
 */

public class EventsList extends ArrayAdapter<Event> {

    private Activity context;
    private List<Event> eventsList;

    public EventsList(Activity cont,List<Event> eList)
    {

        super(cont, R.layout.list_layout,eList);
        context=cont;
        eventsList=eList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        final View listViewItem=inflater.inflate(R.layout.list_layout,null,true);
        TextView eventName=(TextView)listViewItem.findViewById(R.id.textViewEventName);
        TextView eventTime=(TextView)listViewItem.findViewById(R.id.textViewEventTime);
        TextView eventAddress=(TextView)listViewItem.findViewById(R.id.textViewAddress);
        Button rsvp=(Button)listViewItem.findViewById(R.id.buttonRSVP);
        Button maps=(Button)listViewItem.findViewById(R.id.buttonMaps);

        final Event event=eventsList.get(position);

        eventName.setText(event.getName());
        eventTime.setText(event.getTime());
        eventAddress.setText(event.getLocation()+"  Date:"+event.getDate());

        rsvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db=FirebaseDatabase.getInstance().getReference("Events");
                db.child(event.getId()).setValue(new Event(event.getName(),event.getId(),event.getLocation(),event.getDate(),event.getTime(),event.getAttendees()+1));
                Toast.makeText(view.getContext(),view.getResources().getString(R.string.msg2),Toast.LENGTH_SHORT).show();
            }
        });

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q="+event.getLocation()));
                view.getContext().startActivity(intent);
            }
        });


        return listViewItem;
    }
}
