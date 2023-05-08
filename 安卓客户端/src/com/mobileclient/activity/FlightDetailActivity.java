package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Flight;
import com.mobileclient.service.FlightService;
import com.mobileclient.domain.Company;
import com.mobileclient.service.CompanyService;
import com.mobileclient.domain.City;
import com.mobileclient.service.CityService;
import com.mobileclient.domain.City;
import com.mobileclient.service.CityService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class FlightDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录id控件
	private TextView TV_flightId;
	// 声明航班号控件
	private TextView TV_flightNo;
	// 声明航空公司控件
	private TextView TV_comparyObj;
	// 声明出发城市控件
	private TextView TV_startCity;
	// 声明到达城市控件
	private TextView TV_arriveCity;
	// 声明航班日期控件
	private TextView TV_flightDate;
	// 声明起飞时间控件
	private TextView TV_flyTime;
	// 声明候机楼控件
	private TextView TV_waitFloor;
	// 声明接机楼控件
	private TextView TV_meetFloor;
	/* 要保存的航班信息 */
	Flight flight = new Flight(); 
	/* 航班管理业务逻辑层 */
	private FlightService flightService = new FlightService();
	private CompanyService companyService = new CompanyService();
	private CityService cityService = new CityService();
	private int flightId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.flight_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看航班详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_flightId = (TextView) findViewById(R.id.TV_flightId);
		TV_flightNo = (TextView) findViewById(R.id.TV_flightNo);
		TV_comparyObj = (TextView) findViewById(R.id.TV_comparyObj);
		TV_startCity = (TextView) findViewById(R.id.TV_startCity);
		TV_arriveCity = (TextView) findViewById(R.id.TV_arriveCity);
		TV_flightDate = (TextView) findViewById(R.id.TV_flightDate);
		TV_flyTime = (TextView) findViewById(R.id.TV_flyTime);
		TV_waitFloor = (TextView) findViewById(R.id.TV_waitFloor);
		TV_meetFloor = (TextView) findViewById(R.id.TV_meetFloor);
		Bundle extras = this.getIntent().getExtras();
		flightId = extras.getInt("flightId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FlightDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    flight = flightService.GetFlight(flightId); 
		this.TV_flightId.setText(flight.getFlightId() + "");
		this.TV_flightNo.setText(flight.getFlightNo());
		Company comparyObj = companyService.GetCompany(flight.getComparyObj());
		this.TV_comparyObj.setText(comparyObj.getCompanyName());
		City startCity = cityService.GetCity(flight.getStartCity());
		this.TV_startCity.setText(startCity.getCityName());
		City arriveCity = cityService.GetCity(flight.getArriveCity());
		this.TV_arriveCity.setText(arriveCity.getCityName());
		Date flightDate = new Date(flight.getFlightDate().getTime());
		String flightDateStr = (flightDate.getYear() + 1900) + "-" + (flightDate.getMonth()+1) + "-" + flightDate.getDate();
		this.TV_flightDate.setText(flightDateStr);
		this.TV_flyTime.setText(flight.getFlyTime());
		this.TV_waitFloor.setText(flight.getWaitFloor());
		this.TV_meetFloor.setText(flight.getMeetFloor());
	} 
}
