package com.example.mido.fileiosharedpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private  static final String TAG=MainActivity.class.getSimpleName();

EditText editText;
    Button read_btn,write_btn;
    TextView data_tv;
    SharedPreferences sharedPreferences;


    UserSettingsChangeListener listener;
   public static final String FILE_NAME="mina.txt";

    File mdirectory;
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
           /*  SharedPreferences.Editor editor=sharedPreferences.edit();
                if(!editText.getText().toString().isEmpty())
                 editor.putString("SAMPLE_KEY",editText.getText().toString()).commit();*/

                if(!editText.getText().toString().isEmpty())
                {
                    try {
                        writeToFile(FILE_NAME,editText.getText().toString(), Context.MODE_PRIVATE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        read_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /* String s= sharedPreferences.getString("SAMPLE_KEY","string not found");
                data_tv.setText(s);*/

                try {
                    String readString=readFromFile(FILE_NAME);
                    data_tv.setText(readString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
    protected void onResume() {
        super.onResume();
        try {
            data_tv.setText(readFromFile(FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).unregisterOnSharedPreferenceChangeListener(listener);
    }

    private void writeToFile(String fileName,String text,int MODE) throws FileNotFoundException,IOException
    {    //MODE is a file creation mode (private - world_readable - world_writable)
        //File mFolder = new File(getFilesDir() + "/sample");
      mdirectory=new File(Environment.getExternalStorageDirectory(),"MINA");
        mdirectory.mkdir();

        FileOutputStream fileOutputStream=openFileOutput(fileName,MODE);      //new FileOutputStream(fileName)
        OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fileOutputStream);
        outputStreamWriter.write(text);
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }
    private String readFromFile(String fileName)throws FileNotFoundException,IOException
    {
        String readString="";
        FileInputStream fileInputStream=openFileInput(fileName);
        InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder=new StringBuilder(readString);
        while((readString=bufferedReader.readLine())!=null)
        {
            stringBuilder.append(readString);
        }
        inputStreamReader.close(); //so important to close the file
        return stringBuilder.toString();
    }


}
