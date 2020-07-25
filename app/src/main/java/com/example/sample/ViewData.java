package com.example.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewData extends AppCompatActivity
{
    private List<User> userList = new ArrayList<>();
    private List<User> encrypted_userList = new ArrayList<>();
    private DatabaseHelper db;

    private TextView tv_userid;
    private EditText et_fullname, et_weight, et_height, et_age, et_bmi;
    private Button btn_next, btn_prev, btn_save, btn_delete;


    int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        tv_userid = (TextView) findViewById(R.id.tv_userid);

        et_fullname = (EditText) findViewById(R.id.et_fullname);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_height = (EditText) findViewById(R.id.et_height);
        et_age = (EditText) findViewById(R.id.et_age);
        et_bmi = (EditText) findViewById(R.id.et_bmi);

        btn_next = (Button) findViewById(R.id.btn_nextt);
        btn_prev = (Button) findViewById(R.id.btn_prev);
        btn_save = (Button) findViewById(R.id.btn_save);
//        btn_delete = (Button) findViewById(R.id.btn_delete);


        btn_save.setEnabled(false);


        db = new DatabaseHelper(this);




        encrypted_userList.addAll(db.getAllUsers());

        if(encrypted_userList!=null && !encrypted_userList.isEmpty())
        {
            userList = decrypt_Users_List(encrypted_userList);

            tv_userid.setText("User ID: " + Integer.toString(userList.get(index).getId()));
            et_fullname.setText(userList.get(index).getName());
            et_weight.setText(userList.get(index).getWeight());
            et_height.setText(userList.get(index).getHeight());
            et_age.setText(userList.get(index).getAge());
            et_bmi.setText(userList.get(index).getBmi());

        }


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(userList!= null &&!userList.isEmpty())
                {
                    index+=1;

                    if(index == userList.size() || index > userList.size())
                    {

                    }
                    else
                    {

                        tv_userid.setText("User ID: " + Integer.toString(userList.get(index).getId()));
                        et_fullname.setText(userList.get(index).getName());
                        et_weight.setText(userList.get(index).getWeight());
                        et_height.setText(userList.get(index).getHeight());
                        et_age.setText(userList.get(index).getAge());
                        et_bmi.setText(userList.get(index).getBmi());
                    }


                    btn_save.setEnabled(false);
                    btn_save.setBackgroundResource(R.drawable.disbaled_button_bg);

                }



            }
        });


        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(userList!= null &&!userList.isEmpty())
                {
                    if(index == 0)
                    {

                    }
                    else
                    {
                        index-=1;
                    }

                    tv_userid.setText("User ID: " + Integer.toString(userList.get(index).getId()));
                    et_fullname.setText(userList.get(index).getName());
                    et_weight.setText(userList.get(index).getWeight());
                    et_height.setText(userList.get(index).getHeight());
                    et_age.setText(userList.get(index).getAge());
                    et_bmi.setText(userList.get(index).getBmi());

                }

                btn_save.setEnabled(false);
                btn_save.setBackgroundResource(R.drawable.disbaled_button_bg);


            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(userList!= null &&!userList.isEmpty())
                {
                    int current_id = userList.get(index).getId();

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

                        String enc_name = encrypt(name);
                        String enc_height = encrypt(height);
                        String enc_weight = encrypt(weight);
                        String enc_age = encrypt(age);
                        String enc_bmi = encrypt(Integer.toString(bmi));

                        User user = new User(current_id,enc_name,enc_weight,enc_height,enc_age,enc_bmi);

                        db.updateUser(user);

                        User u = db.getUser(current_id);

                        if (u != null)
                        {
                            List<User> enc_list = new ArrayList<>();
                            enc_list.addAll(db.getAllUsers());
                            userList.clear();
                            userList =decrypt_Users_List(enc_list);

                            et_bmi.setText(userList.get(index).getBmi());
                            et_weight.setText(userList.get(index).getWeight());
                            Toast.makeText(getApplicationContext(), decrypt(u.getName()) + " updated successfully", Toast.LENGTH_LONG).show();

                        }
                    }

                }




            }
        });



        et_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btn_save.setEnabled(true);
                btn_save.setBackgroundResource(R.drawable.gradient_button_bg);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //
//        btn_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                int current_id = userList.get(index).getId();
//
//                String name = et_fullname.getText().toString().trim();
//                String height = et_height.getText().toString().trim();
//                String weight = et_weight.getText().toString().trim();
//                String age = et_age.getText().toString().trim();
//
//                int height_int = (Integer. valueOf(height) / 100);
//                int height_sq = (height_int^2);
//                int bmi =((Integer. valueOf(weight))/height_sq);
//
//                User user = new User(current_id,name,weight,height,age,Integer.toString(bmi));
//
//                db.deleteUser(user);
//
//            }
//        });



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

    private List<User> decrypt_Users_List(List<User> users)
    {
        List<User> decrypted_list =  new ArrayList<>();

        for(int i = 0; i<users.size(); i++)
        {
            int id = users.get(i).getId();
            String name = decrypt(users.get(i).getName());
            String weight = decrypt(users.get(i).getWeight());
            String height = decrypt(users.get(i).getHeight());
            String age = decrypt(users.get(i).getAge());
            String bmi = decrypt(users.get(i).getBmi());

            User user = new User(id,name,weight,height,age,bmi);

            decrypted_list.add(user);
        }

        return decrypted_list;
    }



}
