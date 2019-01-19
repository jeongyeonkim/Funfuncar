package com.example.jeongyeonkim.funfuncar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Newuser_info extends Activity {

    private EditText name;
    private EditText birthday;
    private EditText sex;
    private EditText phone_num;
    private EditText blood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);

        name = (EditText)findViewById(R.id.name);
        birthday = (EditText)findViewById(R.id.birthday);
        sex = (EditText)findViewById(R.id.sex);
        phone_num = (EditText)findViewById(R.id.phone_num);
        blood = (EditText)findViewById(R.id.blood);
    }


    public void insert (View view) {
        String new_name = name.getText().toString();
        String new_birthday = birthday.getText().toString();
        String new_sex = sex.getText().toString();
        String new_phone_num = phone_num.getText().toString();
        String new_blood = blood.getText().toString();

        insertToDatabase(new_name, new_birthday, new_sex, new_phone_num, new_blood);
        Intent intent = new Intent(Newuser_info.this, HistoryActivity.class);
        startActivity(intent);

    }

    private void insertToDatabase(String new_name, String new_birthday, String new_sex, String new_phone_num, String new_blood) {

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Newuser_info.this, "Please Wait", null, true, true);
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
                    String new_name =  (String) params[0];
                    String new_birthday = (String) params[1];
                    String new_sex = (String) params[2];
                    String new_phone_num = (String)params[3];
                    String new_blood = (String)params[4];

                    String link = "http://10.0.2.2/insert_user.php";
                    String data = URLEncoder.encode("new_name", "UTF8") + "=" + URLEncoder.encode(new_name, "UTF8");
                    data += "&" + URLEncoder.encode("new_birthday", "UTF8") + "=" + URLEncoder.encode(new_birthday, "UTF8");
                    data += "&" + URLEncoder.encode("new_sex", "UTF8") + "=" + URLEncoder.encode(new_sex, "UTF8");
                    data += "&" + URLEncoder.encode("new_phone_num", "UTF8") + "=" + URLEncoder.encode(new_phone_num, "UTF8");
                    data += "&" + URLEncoder.encode("new_blood", "UTF8") + "=" + URLEncoder.encode(new_phone_num, "UTF8");

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
        task.execute(new_name,new_birthday,new_sex,new_phone_num,new_blood);
    }
}
