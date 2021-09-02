package com.cos.busanbus;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main";
    private static final String LOG_TAG = "MainActivity";

    private MainActivity mContext = this;
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private BottomNavigationView bottomNavigationView;
    private EditText edit;
    private TextView text;
    private Button search;
    taskDataBustop td;
    String key = "http://61.43.246.153/openapi-data/service/busanBIMS2/busStop?arsno=13123&pageNo=1&numOfRows=10&ServiceKey=FDNF6JMfFFTFBHH2nJKqLNKoJMl1VumD3J1LC7xcc%2BuB8I2uLgYWh83d%2FNZ%2FaUHGk%2BgT0BChBBcuds7YYcvXqw%3D%3D";
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = findViewById(R.id.editSearch);
        text = findViewById(R.id.result);
        search = findViewById(R.id.searchButton);

        mapView = new MapView(mContext);
        mapViewContainer = findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        // 중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.15601700819226, 129.0594790151887), true);

        // 줌 레벨 변경
        mapView.setZoomLevel(3, true);

        // 중심점 변경 + 줌 레벨 변경
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(35.15601700819226, 129.0594790151887), 2, true);

        // 줌 인
        mapView.zoomIn(true);

        // 줌 아웃
        mapView.zoomOut(true);

        //마커 1
        MapPOIItem marker = new MapPOIItem();
        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(35.15601700819226,129.0594790151887);
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

//      mapView.addPOIItem(marker);

        //마커 2
        MapPOIItem marker2 = new MapPOIItem();
        MapPoint MARKER_POINT2 = MapPoint.mapPointWithGeoCoord(35.15601700819226,129.0594790151887);
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT2);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker2);

        MapPOIItem [] markers = new MapPOIItem[2];
        markers[0]=marker;
        markers[1]=marker2;


        for(int i=0;i<markers.length;i++){
            mapView.addPOIItem(markers[i]);
        }


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                td = new taskDataBustop();
                td.execute(edit.getText().toString());
                edit.setText("");
            }
        });
        init();
        initLr();
        /*getHashKey(); // 해시키 받는 함수*/
    }


    public class taskDataBustop extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... search) {
            String str = edit.getText().toString();
            String url = "http://61.43.246.153/openapi-data/service/busanBIMS2/busStop?resultCode=00&pageNo=1&numOfRows=10&ServiceKey=FDNF6JMfFFTFBHH2nJKqLNKoJMl1VumD3J1LC7xcc%2BuB8I2uLgYWh83d%2FNZ%2FaUHGk%2BgT0BChBBcuds7YYcvXqw%3D%3D";
            XmlPullParserFactory factory;
            XmlPullParser parser;
            URL xmlUrl;
            String returnResult = "a";
            String gpsX = "";
            String gpsY = "";
            String searchData = "";
            try {
                boolean flag1 = false;
                boolean flagGPSX = false;
                boolean flagGPSY = false;

                xmlUrl = new URL(url);
                xmlUrl.openConnection().getInputStream();
                factory = XmlPullParserFactory.newInstance();
                parser = factory.newPullParser();
                parser.setInput(xmlUrl.openStream(), "utf-8");
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            if (parser.getName().equals("bstopArsno")) {
                                flag1 = true;
                            } else if(parser.getName().equals("bstopNm")){
                                flag1 = true;
                            } else if(parser.getName().equals("gpsX")){
                                flagGPSX = true;
                            } else if(parser.getName().equals("gpsY")){
                                flagGPSY = true;

                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                        case XmlPullParser.TEXT:
                            if (flag1 == true) {
                                returnResult += parser.getText() + "\n";
                                flag1 = false;

                            } else if(flagGPSX == true){
                                gpsX = parser.getText() + "/n";
                                System.out.println("X좌표: " + gpsX);
                                flagGPSX = false;

                            } else if(flagGPSY == true){
                                gpsY = parser.getText() + "/n";
                                System.out.println("Y좌표: " + gpsY);
                                flagGPSY = false;
                            }
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {

            }

            System.out.println("totalResult : " + returnResult);
            return returnResult;

            /*for(int i=0; i<returnResult.length(); i++){
                if(returnResult.equals(edit.getText())){
                    searchData += edit.getText() + "/n";
                }
            }

            System.out.println("searchData : " + searchData);*/

        }

        @Override
        protected void onPostExecute(String result) {
            text.setText(result);
        }
    }

    /*private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }*/

private void init(){
bottomNavigationView = findViewById(R.id.bottomNavigation);
}
private void initLr(){
        search.setOnClickListener(v -> {
            MyData.data="넘어가냐?";
            Intent intent = new Intent(

                    mContext,
                    ListActivity2.class
            );
            startActivity(intent);
        });
    bottomNavigationView.setOnItemSelectedListener((item -> {
        switch (item.getItemId()){
            case R.id.search_icon:
                Log.d(TAG, "initLr: search 클릭됨" );
                Intent intent =new Intent(
                        MainActivity.this,
                        ListActivity2.class
                );
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.map_icon:
                Log.d(TAG, "initLr: map 클릭됨" );
                Intent intent1 =new Intent(
                        MainActivity.this,
                        MainActivity.class
                );
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
                break;
        }
        return true;
    }));
}
}