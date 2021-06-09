package com.example.sqlitecrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText taskname, timeRequired, venue, date, Time;
    Button sbmt;
    String DatePicker;
    DrawerLayout drawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        taskname =findViewById(R.id.TaskName);
        timeRequired = findViewById(R.id.TimeRequired);
        venue = findViewById(R.id.Venue);
        date = findViewById(R.id.Date);
        Time = findViewById(R.id.time);


        sbmt = (Button) findViewById(R.id.sbmt_add);


            sbmt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    processinsert(taskname.getText().toString(),timeRequired.getText().toString(),venue.getText().toString(),date.getText().toString(),Time.getText().toString());
                }
            });


        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        // Handle the camera import action (for now display a toast).
                        drawer.closeDrawer(GravityCompat.START);
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_today:
                        // Handle the gallery action (for now display a toast).
                        drawer.closeDrawer(GravityCompat.START);
                        Intent intent1 = new Intent(getApplicationContext(),fetchdata.class);
                        startActivity(intent1);
                        return true;
                    case R.id.nav_contact:
                        drawer.closeDrawer(GravityCompat.START);
                        Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:17755316"));
                        startActivity(intent2);
                        return true;
                    case R.id.nav_about:
                        drawer.closeDrawer(GravityCompat.START);
                        Intent intent3 = new Intent(getApplicationContext(),About.class);
                        startActivity(intent3);
                        return true;
                    case R.id.nav_exit:
                        drawer.closeDrawer(GravityCompat.START);
                        finish();
                        System.exit(0);
                        return true;
                    default:
                }
                return false;
            }
        });
    }
    public void setUpToolbar() {
        drawer = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));
        actionBarDrawerToggle.syncState();

    }
    private void processinsert(String n, String r, String v,String d,String t)
    {
       String res=new dbmanager(this).addrecord(n,r,v,d,t);
       taskname.setText("");
       timeRequired.setText("");
       venue.setText("");
       date.setText("");
       Time.setText("");
       Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    public void time(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Initialize hour and minute
                int t2Hour = hourOfDay;
                int t2Minute = minute;

                //Store hour and minute in string
                String time = t2Hour + ":" + t2Minute;
                //Initialize 24 hours time format
                SimpleDateFormat f24format = new SimpleDateFormat(
                        "HH:mm"
                );
                try {
                    Date date = f24format.parse(time);
                    //Initialize 12 hours time format
                    SimpleDateFormat f12format = new SimpleDateFormat(
                            "hh:mm aa"
                    );
                    //Set selected time on text view
                    Time.setText(f12format.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, 12, 0, false

        );
        //Set transparent background
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.show();
    }

    public void date(View view) {
        DialogFragment newFragment = new datePicker();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }
    public void processDatePickerResult(int year, int month, int dayOfMonth) {
        String month_string = Integer.toString(month +1);
        String day_string = Integer.toString(dayOfMonth);
        String year_string = Integer.toString(year);

        DatePicker = (month_string+"/"+day_string+"/"+year_string);
        date.setText(DatePicker);

        Toast.makeText(this,"Date:"+DatePicker,Toast.LENGTH_SHORT).show();

    }
}