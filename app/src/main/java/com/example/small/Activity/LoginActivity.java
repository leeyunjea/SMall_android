package com.example.small.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.small.LoginFailDialog;
import com.example.small.R;
import com.example.small.Server.HttpClient;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    String userid,password;
    String serverURL = "http://192.168.0.34:8080/Android_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void onButtonLogin(View v) {
        userid = ((EditText) findViewById(R.id.etId)).getText().toString();
        password = ((EditText) findViewById(R.id.etPassword)).getText().toString();

        Map<String,String> params = new HashMap<String,String>();
        params.put("userid",userid);
        params.put("password",password);

        loginDB IDB = new loginDB();
        IDB.execute(params);
    }

    public void onButtonSignUp(View v){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }


    LoginFailDialog loginFailDialog;

    class loginDB extends AsyncTask<Map<String, String>, Integer, String> {

        String data=null;
        String receiveMsg="";
        //id, pw, 나이, 성별, 보유쿠폰, 즐겨찾기정보
        String name,gender,myCoupon,myBookmark;
        int birth;

        @Override
        protected String doInBackground(Map<String, String>...maps) {

            /*String param = "userid=" + userid + "&password=" + password +"";
            Log.i("yunjae", param);*/

            HttpClient.Builder http = new HttpClient.Builder("POST",serverURL);
            http.addAllParameters(maps[0]);


            HttpClient post = http.create();
            post.request();

            int statusCode = post.getHttpStatusCode();

            Log.i("yunjae", "응답코드"+statusCode);

            String body = post.getBody();

            Log.i("yunjae", "body : "+body);

            return body;

            /*try {
                //String data;
                URL url = new URL(serverURL);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                Log.i("yunjae", "URL에 접속");

                //안드로이드 -> 서버 파라미터값 전달
                OutputStreamWriter ows = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                ows.write(param);
                ows.flush();
                //ows.close();

                //서버 -> 안드로이드 파라미터값 전달
                if(conn.getResponseCode() == conn.HTTP_OK){

                    InputStreamReader in = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(in);
                    StringBuffer buffer = new StringBuffer();

                    while((data = reader.readLine()) != null) {
                        buffer.append(data);
                    }
                    receiveMsg = buffer.toString();
                    Log.i("yunjae", "서버에서 안드로이드로 전달 됨");

                    JSONObject json = new JSONObject(receiveMsg);
                    JSONArray jArr = json.getJSONArray("datasend");

                    Log.i("yunjae", "MainActivity = " + jArr.length());

                    for(int i=0; i<jArr.length(); i++){
                        json = jArr.getJSONObject(i);
                        name = json.getString("name");
                        birth = json.getInt("birth");
                        gender = json.getString("gender");
                        *//*myCoupon = json.getString("name");
                        myBookmark = json.getString("name");*//*
                    }

                }else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

                if(receiveMsg.contains("0")){
                    Log.i("yunjae", "로그인에 실패." + receiveMsg);
                }
                else{
                    Log.i("yunjae", "로그인에 성공" + receiveMsg);
                }

            }catch(MalformedURLException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();

            }

            return receiveMsg;
            */

        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);

            Log.i("yunjae", aVoid);
            /*if(receiveMsg.contains("0")){
                Log.i("yunjae", "로그인 실패");
                loginFailDialog = new LoginFailDialog(getApplicationContext(), "로그인에 실패하였습니다.", leftListener);
                loginFailDialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
                loginFailDialog.show();
            }
            else if(receiveMsg != null){
                Log.i("yunjae", "로그인에 성공"+name);
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("birth",birth);
                intent.putExtra("gender",gender);
                startActivity(intent);
                finish();
            }
            else {
                Log.i("yunjae", "로그인에 실패하였습니다else"+receiveMsg);
            }*/
        }

    }
    public View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.i("yunjae", "왼쪽클릭");
            loginFailDialog.dismiss();
        }
    };
}
