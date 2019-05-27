package com.example.desenvolvimento.exemplocloudfirestorecompleto;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setSubtitle("Firestore");

        fm = getSupportFragmentManager();
        addEventFrgmt();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_event_m:
                addEventFrgmt();
                return true;
            case R.id.view_events_m:
                viewEventsFrgmt();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void addEventFrgmt(){
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.events_content, new AddEventFragment());
        ft.commit();
    }
    public void viewEventsFrgmt(){
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.events_content, new ViewEventsFragment());
        ft.commit();
    }
}
