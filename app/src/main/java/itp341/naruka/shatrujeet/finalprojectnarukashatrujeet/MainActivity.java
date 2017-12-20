package itp341.naruka.shatrujeet.finalprojectnarukashatrujeet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button organizeEvent,findEvent;
        organizeEvent=(Button)findViewById(R.id.buttonOrganize);
        findEvent=(Button)findViewById(R.id.buttonFind);

        organizeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                      startActivity(new Intent(getApplicationContext(),OrganizeEvent.class));
            }
        });

        findEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),FindEvents.class));
            }
        });
    }
}
