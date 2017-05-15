package com.example.mido.fileiosharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private  static final String TAG=MainActivity.class.getSimpleName();

EditText editText;
    Button read_btn,write_btn;
    TextView data_tv;
    SharedPreferences sharedPreferences;


    UserSettingsChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    editText= (EditText) findViewById(R.id.user_input_et);
        read_btn= (Button) findViewById(R.id.read_btn);
        write_btn= (Button) findViewById(R.id.write_btn);
        data_tv= (TextView) findViewById(R.id.data_tv);

        sharedPreferences=getSharedPreferences(getPackageName()+"."+TAG,MODE_PRIVATE);
        
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             SharedPreferences.Editor editor=sharedPreferences.edit();
                if(!editText.getText().toString().isEmpty())
                 editor.putString("SAMPLE_KEY",editText.getText().toString()).commit();
            }
        });

        read_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String s= sharedPreferences.getString("SAMPLE_KEY","string not found");
                data_tv.setText(s);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_setting_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        listener=new UserSettingsChangeListener(getApplicationContext());
        if(item.getItemId()==R.id.action_setting)
        {
            startActivity(new Intent(MainActivity.this,SettingActivity.class));
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).registerOnSharedPreferenceChangeListener(listener);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).unregisterOnSharedPreferenceChangeListener(listener);
    }
}
