package com.example.jeongyeonkim.funfuncar;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Sign_UpActivity extends Activity {

    private EditText user_car_num ;
    private EditText user_pwd;
    private EditText user_name;
    private EditText user_phone;
    private EditText user_date;
    private EditText user_emergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        user_car_num = (EditText)findViewById(R.id.user_car_num);
        user_pwd = (EditText)findViewById(R.id.user_pwd);
        user_name = (EditText)findViewById(R.id.user_name);
        user_phone = (EditText)findViewById(R.id.user_phone);
        user_date = (EditText)findViewById(R.id.user_date);
        user_emergency = (EditText)findViewById(R.id.user_emergency);


        Button main_go = (Button)findViewById(R.id.btn_sign_in);
        Button btn_sign_db = (Button)findViewById(R.id.btn_sign_db);

        main_go.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);


            }
        }) ;

        btn_sign_db.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent1 = new Intent(Sign_UpActivity.this, MainActivity.class);
                startActivity(intent1);


            }
        }) ;


    }



    public void insert (View view) {
        String car_num = user_car_num.getText().toString();
        String pwd = user_pwd.getText().toString();
        String name = user_name.getText().toString();
        String phone = user_phone.getText().toString();
        String date = user_date.getText().toString();
        String emergency = user_emergency.getText().toString();

        insertToDatabase(car_num, pwd, name, phone, date, emergency);
    }

    private void insertToDatabase(String car_num, String pwd, String name, String phone, String date, String emergency) {

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Sign_UpActivity.this, "Please Wait", null, true, true);
                //loading = ProgressDialog.show(getApplicationContext(), "Please Wait", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(String... params) {

                try {
                    String car_num =  (String) params[0];
                    String pwd = (String) params[1];
                    String name = (String) params[2];
                    String phone = (String)params[3];
                    String date = (String) params[4];
                    String emergency = (String)params[5];

                    String link = "http://10.0.2.2/insert.php";
                    String data = URLEncoder.encode("car_num", "UTF8") + "=" + URLEncoder.encode(car_num, "UTF8");
                    data += "&" + URLEncoder.encode("pwd", "UTF8") + "=" + URLEncoder.encode(pwd, "UTF8");
                    data += "&" + URLEncoder.encode("name", "UTF8") + "=" + URLEncoder.encode(name, "UTF8");
                    data += "&" + URLEncoder.encode("phone", "UTF8") + "=" + URLEncoder.encode(phone, "UTF8");
                    data += "&" + URLEncoder.encode("date", "UTF8") + "=" + URLEncoder.encode(date, "UTF8");
                    data += "&" + URLEncoder.encode("emergency", "UTF8") + "=" + URLEncoder.encode(date, "UTF8");

                    URL url = new URL(link);
                    //URLConnection conn = url.openConnection();
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    wr.close();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line;
                    //String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());

                }
            }
        }
        InsertData task = new InsertData();
        task.execute(car_num, pwd, name, phone, date, emergency);
    }

}


