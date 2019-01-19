package com.example.jeongyeonkim.funfuncar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Newuser_3 extends Activity {

    private EditText name;
    private EditText birthday;
    private EditText sex;
    private EditText phone_num;
    private EditText blood;
    private Button btn_save;
    private Button btn_cancel;
    String new_name;
    String new_birthday;
    String new_sex;
    String new_phone_num;
    String new_blood;
    String sms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser_2);


        name = (EditText)findViewById(R.id.name_2);
        birthday = (EditText)findViewById(R.id.birthday_2);
        sex = (EditText)findViewById(R.id.sex_2);
        phone_num = (EditText)findViewById(R.id.phone_num_2);
        blood = (EditText)findViewById(R.id.blood_2);
        btn_save = (Button)findViewById(R.id.btn_submit_2);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_name = name.getText().toString();
                new_birthday = birthday.getText().toString();
                new_sex = sex.getText().toString();
                new_phone_num = phone_num.getText().toString();
                new_blood = blood.getText().toString();

                sms = "이름 :" +new_name+"\n 생년월일 :"+new_birthday+"\n 성별 :"+new_sex+"\n 혈액형 :"+ new_blood;

                Intent intent = new Intent(Newuser_3.this, HistoryActivity_2.class);
                intent.putExtra("sms",sms);
                startActivity(intent);
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Newuser_3.this, HistoryActivity.class);
                startActivity(intent);
            }
        });



    }
}
