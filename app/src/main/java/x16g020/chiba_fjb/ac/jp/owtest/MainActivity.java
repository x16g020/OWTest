package x16g020.chiba_fjb.ac.jp.owtest;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements WeatherReader.OnWeatherListener, View.OnClickListener {

    final Double KELVIN = 273.15;

    int     num;
    int     cnt;

    String  datetext;
    String  year;
    String  month;
    String  day;
    String  hour;
    int     hour_int;

    Double  temperature;
    Double  h_temperature;
    Double  l_temperature;

    ImageView weatherImage;
    FrameLayout f_layout;

    TextView dateText;
    TextView temp;
    TextView max_min;

    Button day_before;
    Button hour_before;
    Button hour_after;
    Button day_after;

    private ViewPager pager;
    private FragmentPagerAdapter adapter;
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherImage    = findViewById(R.id.WeatherImage);
        f_layout        = findViewById(R.id.framelayout);
        dateText    = findViewById(R.id.dateT);
        temp        = findViewById(R.id.temperature);

        day_before = (Button)findViewById(R.id.before_day);
        hour_before = (Button)findViewById(R.id.before_hour);
        hour_after = (Button)findViewById(R.id.after_hour);
        day_after = (Button)findViewById(R.id.after_day);
        pager = (ViewPager) findViewById(R.id.pager);

        WeatherReader.getWeather("http://api.openweathermap.org/data/2.5/forecast?id=1850147&APPID=d21a1076e3577e18ffe577b79bef2496&mode=xml", this);

        adapter = new UserInfoViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        currentPage = 0;


        day_before.setOnClickListener(this);
        hour_before.setOnClickListener(this);
        hour_after.setOnClickListener(this);
        day_after.setOnClickListener(this);

    }

    @Override
    public void onWeather(List<Map> weather) {
        if (weather != null) {
            Map map = weather.get(cnt);
            num = Integer.parseInt(map.get("symbol_number").toString());

            if (num >= 800) {
                weatherImage.setImageResource(R.drawable.sunny);
//                f_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.sunny_sky));
            } else if (num >= 600 && num < 800) {
                weatherImage.setImageResource(R.drawable.rainny);
//                f_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.rainny_sky));
            } else if (num >= 400 && num < 600) {
                weatherImage.setImageResource(R.drawable.cloudy);
//                f_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.cloudy_sky));
            } else {
            }

            datetext = map.get("time_from").toString();
            year = datetext.substring(0, 4);
            month = datetext.substring(5, 7);
            day = datetext.substring(8, 10);
            hour = datetext.substring(11, 13);
            hour_int = Integer.parseInt(hour);
            dateText.setText(year + "年" + month + "月" + day + "日 " + hour_int + "時");

            temperature = Double.parseDouble(map.get("temperature_value").toString());
            temp.setText(String.valueOf(temperature));
        }else{

            dateText.setText("データなし");
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.before_day){
            if(cnt < 8){
                cnt = 0;
            }else {
                cnt = cnt - 8;
            }
        }else if(v.getId() == R.id.before_hour){
            cnt = cnt - 1;
        }else if(v.getId() == R.id.after_hour){
            cnt = cnt + 1;
        }else if(v.getId() == R.id.after_day){
            if(cnt > 32){
                cnt = 39;
            }else {
                cnt = cnt + 8;
            }
        }
    }

    public void onClickNext(View view) {
        currentPage ++;
        pager.setCurrentItem(currentPage);
    }

    public void onClickGoToTop(View view) {
        currentPage = 0;
        pager.setCurrentItem(currentPage);
    }

}
