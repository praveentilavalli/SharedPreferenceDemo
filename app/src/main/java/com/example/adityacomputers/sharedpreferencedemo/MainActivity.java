package com.example.adityacomputers.sharedpreferencedemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtpassword;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtpassword=(TextView)findViewById(R.id.password);
        //password the clciked save into shared preference
        txtpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //create the alert dialog to save the password
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Set the password");
                builder.setMessage("Set password");
                final View view1=getLayoutInflater().inflate(R.layout.passwordset,null);
                builder.setView(view1);
               final EditText etpasswd=(EditText)view1.findViewById(R.id.etPassword);
                //if shared preference contains the password already populate it
                final SharedPreferences sharedPreferences=getSharedPreferences("settingpref", Context.MODE_PRIVATE);
                if(sharedPreferences.contains("password"))
                    etpasswd.setText(sharedPreferences.getString("password",null));
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //after save button clicked save into password
                        String passwd=etpasswd.getText().toString();
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("password",passwd);
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"Password has been set",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        TextView txtremainder=(TextView)findViewById(R.id.remainder);
        txtremainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Set the remainder");
                builder.setMessage("Set remainder");
                final View view1=getLayoutInflater().inflate(R.layout.remainder,null);
                builder.setView(view1);
                final RadioGroup radioGroup=(RadioGroup)view1.findViewById(R.id.rg);
                final RadioButton rbdaily=(RadioButton)view1.findViewById(R.id.rbdaiy);
                final RadioButton rbweekly=(RadioButton)view1.findViewById(R.id.rbweekly);
                final RadioButton rbmonthly=(RadioButton)view1.findViewById(R.id.rbmonthly);
                //if the shared prefrence already contains populate with existing value
                final SharedPreferences sharedPreferences=getSharedPreferences("settingpref", Context.MODE_PRIVATE);
                if(sharedPreferences.contains("remainder"))
                {
                    String ch=sharedPreferences.getString("remainder",null);
                    if(ch.equals("daily"))
                        rbdaily.setChecked(true);
                    else if(ch.equals("weekly"))
                        rbweekly.setChecked(true);
                    else if(ch.equals("monthly"))
                        rbmonthly.setChecked(true);
                }
                //when radio button is changes save it into sharedprefernece
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        String ch="";
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                         if(i==R.id.rbdaiy)
                             ch="daily";
                        else if(i==R.id.rbweekly)
                             ch="weekly";
                        else if(i==R.id.rbmonthly)
                             ch="monthly";
                        editor.putString("remainder",ch);
                        editor.commit();

                        Toast.makeText(getApplicationContext(),"Remainder has been saved",Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        final CheckBox chk=(CheckBox)findViewById(R.id.chkslock);
        //if setting of screen lock wirh password is on show check marks
        final SharedPreferences sharedPreferences=getSharedPreferences("settingpref", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("lock")) {
            String f=sharedPreferences.getString("lock",null);
            if(f.equals("lock"))
                chk.setChecked(true);
            else
                chk.setChecked(false);
        }
        //when clicked changes save into shared preference
            chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                if(chk.isChecked())
                editor.putString("lock","lock");
                else
                editor.putString("lock","no");
                editor.commit();
                Toast.makeText(getApplicationContext(),"Screen lock with password set",Toast.LENGTH_SHORT).show();
            }
        });
        }
}
