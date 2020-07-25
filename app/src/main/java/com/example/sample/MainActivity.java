package com.example.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity
{

    private Button btn_view_data, btn_add;

    private EditText et_fullname, et_height, et_weight, et_age;

    private DatabaseHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_view_data = (Button) findViewById(R.id.btn_view_data);

        et_fullname = (EditText) findViewById(R.id.et_fullname);
        et_height = (EditText) findViewById(R.id.et_height);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_age = (EditText) findViewById(R.id.et_age);

        btn_add = (Button) findViewById(R.id.btn_add);

        db = new DatabaseHelper(this);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et_fullname.getText().toString().trim();
                String height = et_height.getText().toString().trim();
                String weight = et_weight.getText().toString().trim();
                String age = et_age.getText().toString().trim();

                if(name.isEmpty() || height.isEmpty() || weight.isEmpty() || age.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please fill all the details", Toast.LENGTH_LONG).show();

                }
                else
                {

                    int height_int = (Integer. valueOf(height) / 100);
                    int height_sq = (height_int^2);
                    int bmi =((Integer. valueOf(weight))/height_sq);
                    createNote(name,height,weight,age,Integer.toString(bmi));

                }


            }
        });



        btn_view_data.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, ViewData.class);
                startActivity(i);


            }
        });
    }

    private void createNote(String name, String height, String weight, String age, String bmi )
    {
        String enc_name = encrypt(name);
        String enc_height = encrypt(height);
        String enc_weight = encrypt(weight);
        String enc_age = encrypt(age);
        String enc_bmi = encrypt(bmi);

        // newly inserted note id
        long id = db.insertUser(enc_name, enc_height, enc_weight, enc_age, enc_bmi);

        User u = db.getUser(id);

        if (u != null)
        {
            String user_name = decrypt(u.getName());
            Toast.makeText(getApplicationContext(), user_name + " added successfully", Toast.LENGTH_LONG).show();
            clearEditTexts();

        }
    }

    private void clearEditTexts()
    {
        et_fullname.getText().clear();
        et_height.getText().clear();
        et_weight.getText().clear();
        et_age.getText().clear();
    }



    private String encrypt(String source)
    {
        String encrypted = "";
        String sourceStr = source;
        try {
            encrypted = AESUtils.encrypt(sourceStr);
            Log.d("TEST", "encrypted:" + encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encrypted;

    }

    private String decrypt(String enc_string)
    {
        String encrypted = enc_string;
        String decrypted = "";
        try {
            decrypted = AESUtils.decrypt(encrypted);
            Log.d("TEST", "decrypted:" + decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decrypted;
    }

}
