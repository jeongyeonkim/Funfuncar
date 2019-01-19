package com.example.jeongyeonkim.funfuncar;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;

public class HistoryActivity_2 extends Activity{

    private Button btn_Newuser, btn_Unregistered, btn_Accident, btn_Remain;
    SmsManager mSMSManager;
    //String name[], birthday[], sex[], phone_num[], blood[];
    String now_passenger;
    String result ;
    String sms;

    InputMethodManager imm;
    PendingIntent sentPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        btn_Newuser = (Button)findViewById(R.id.btn_Newuser);
        btn_Unregistered = (Button)findViewById(R.id.btn_Unregistered);
        btn_Accident = (Button)findViewById(R.id.btn_Accident);
        btn_Remain = (Button)findViewById(R.id.btn_Remain);

        mSMSManager = SmsManager.getDefault();

        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        Intent intent_2 = getIntent();
        sms = intent_2.getExtras().getString("sms");

        //새로운 탑승자
        btn_Newuser.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HistoryActivity_2.this);

                // 제목셋팅
                alertDialogBuilder.setTitle("새로운 탑승자");

                // AlertDialog 셋팅
                alertDialogBuilder.setMessage("새로운 탑승자가 있습니다. 등록하시겠습니까?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 새로운 탑승자 정보 추가
                                //Intent intent = new Intent(HistoryActivity.this, Newuser_info.class);
                                Intent intent_4 = new Intent(HistoryActivity_2.this, Newuser_2.class);
                                startActivity(intent_4);


                            }
                        });
                alertDialogBuilder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 다이얼로그를 취소한다
                                dialog.cancel();
                            }
                        });

                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();

                // 다이얼로그 보여주기
                alertDialog.show();
            }
        }) ;

        //등록되지 않은 운전자
        btn_Unregistered.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder_2 = new AlertDialog.Builder(HistoryActivity_2.this);

                // 제목셋팅
                alertDialogBuilder_2.setTitle("등록되지 않은 운전자");

                // AlertDialog 셋팅
                alertDialogBuilder_2.setMessage("등록되지 않은 운전자입니다. 신고하시겠습니까?");
                alertDialogBuilder_2.setCancelable(false);
                alertDialogBuilder_2.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 도난 신고 SMS
                                //송신 인텐트
                                //PendingIntent sentPI = PendingIntent.getBroadcast(HistoryActivity_2.this, 0, new Intent("SMS_SENT"), 0);
                                //수신 인텐트
                                //PendingIntent recvPI = PendingIntent.getBroadcast(HistoryActivity_2.this, 0, new Intent("SMS_DELIVERED"), 0);

                                //mSMSManager = SmsManager.getDefault();
                                //mSMSManager.sendTextMessage("010-7767-1067",null, "차량 도난", sentPI, recvPI);
                                //mSMSManager.sendTextMessage("010-7767-1067",null, "차량 도난", null, null);
                                int permissionCheck = ContextCompat.checkSelfPermission(HistoryActivity_2.this, Manifest.permission.SEND_SMS);
                                if(permissionCheck == PackageManager.PERMISSION_DENIED){
                                    ActivityCompat.requestPermissions(HistoryActivity_2.this, new String[] {Manifest.permission.SEND_SMS},1);
                                    Toast.makeText(HistoryActivity_2.this,"권한을 허용하고 재전송해주세요",Toast.LENGTH_LONG).show();
                                } else {
                                    SmsManager sms_5 = SmsManager.getDefault();
                                    // 아래 구문으로 지정된 핸드폰으로 문자 메시지를 보낸다
                                    sms_5.sendTextMessage("010-7767-1067", null, "차량 도난 접수\n차량 번호 : 12가3456", sentPI, null);
                                    Toast.makeText(HistoryActivity_2.this,"전송을 완료하였습니다",Toast.LENGTH_LONG).show();
                                }

                                registerReceiver(new BroadcastReceiver() {
                                    @Override
                                    public void onReceive(Context context, Intent intent) {
                                        switch (getResultCode()) {
                                            case Activity.RESULT_OK:
                                                Toast.makeText(getBaseContext(), "신고가 완료되었습니다.", Toast.LENGTH_LONG).show();
                                                break;
                                        }
                                    }
                                }, new IntentFilter("SMS_SENT"));

                            }
                        });

                alertDialogBuilder_2.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 다이얼로그를 취소한다
                                dialog.cancel();
                            }
                        });

                // 다이얼로그 생성
                AlertDialog alertDialog_2 = alertDialogBuilder_2.create();

                // 다이얼로그 보여주기
                alertDialog_2.show();
            }
        }) ;

        //차량 사고
        btn_Accident.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                //디비에서 가져오기
                //selectToDatabase();

                //이건 디비 연동 없을때
                String sms_Accident = "차량 사고 발생 위치\n위도(37.400423),경도(127.110835)\n차량 번호 :12가3456";
                String sms_mingi = "이름 : 한민지\n 생년월일 : 951011\n 성별 : 여\n 혈액형 : A";


                //디비 연동 있을때
                //String sms = "[차량 사고 발생]\n 차량 번호 : 12가3456\n"+now_passenger;

                // 도난 신고 SMS
                //송신 인텐트
                //PendingIntent sentPI = PendingIntent.getBroadcast(HistoryActivity_2.this, 0, new Intent("SMS_SENT"), 0);
                //수신 인텐트
                //PendingIntent recvPI = PendingIntent.getBroadcast(HistoryActivity_2.this, 0, new Intent("SMS_DELIVERED"), 0);

                int permissionCheck = ContextCompat.checkSelfPermission(HistoryActivity_2.this, Manifest.permission.SEND_SMS);
                if(permissionCheck == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(HistoryActivity_2.this, new String[] {Manifest.permission.SEND_SMS},1);
                    Toast.makeText(HistoryActivity_2.this,"권한을 허용하고 재전송해주세요",Toast.LENGTH_LONG).show();
                } else {
                    SmsManager sms_2 = SmsManager.getDefault();
                    // 아래 구문으로 지정된 핸드폰으로 문자 메시지를 보낸다
                    sms_2.sendTextMessage("010-7767-1067", null, sms_Accident, sentPI, null);
                    //sms_2.sendTextMessage("010-7767-1067", null, sms_mingi, sentPI, null);
                    sms_2.sendTextMessage("010-7767-1067", null, sms, sentPI, null);
                    Toast.makeText(HistoryActivity_2.this,"신고 접수 완료.",Toast.LENGTH_LONG).show();
                }

                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        switch (getResultCode()) {
                            case Activity.RESULT_OK:
                                Toast.makeText(getBaseContext(), "차량 사고 신고가 완료되었습니다.", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                }, new IntentFilter("SMS_SENT"));


            }
        }) ;

        btn_Remain.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder_4 = new AlertDialog.Builder(HistoryActivity_2.this);

                // 제목셋팅
                alertDialogBuilder_4.setTitle("차량 내 탑승자 존재");

                // AlertDialog 셋팅
                alertDialogBuilder_4.setMessage("차량 내 탑승자가 존재합니다. 확인해주세요.");
                alertDialogBuilder_4.setCancelable(false);
                alertDialogBuilder_4.setNegativeButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 다이얼로그를 취소한다
                                dialog.cancel();
                            }
                        });

                // 다이얼로그 생성
                AlertDialog alertDialog_4 = alertDialogBuilder_4.create();

                // 다이얼로그 보여주기
                alertDialog_4.show();
            }
        }) ;

    }

    //현재 탑승자 정보 불러오기
    private void selectToDatabase() {


        class SelectData extends AsyncTask<String, Void, String> {
            ProgressDialog progressDialog;
            String errorString = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(HistoryActivity_2.this, "Please Wait", null, true, true);
                //loading = ProgressDialog.show(getApplicationContext(), "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d(TAG, "response - " + result);
                progressDialog.dismiss();
                now_passenger = s;
                //txtView.setText(s);
                //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


            }

            @Override
            protected String doInBackground(String... params) {

                try {

                    String link = "http://10.0.2.2/now_passenger.php";

                    URL url = new URL(link);
                    //URLConnection conn = url.openConnection();
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
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
        //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        SelectData task = new SelectData();
        //task.execute();

        try {
            result = task.execute().get();
            Log.d("click ", "..................." + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

}

