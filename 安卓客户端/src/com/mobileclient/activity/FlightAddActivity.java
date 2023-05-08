package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Flight;
import com.mobileclient.service.FlightService;
import com.mobileclient.domain.Company;
import com.mobileclient.service.CompanyService;
import com.mobileclient.domain.City;
import com.mobileclient.service.CityService;
import com.mobileclient.domain.City;
import com.mobileclient.service.CityService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class FlightAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明航班号输入框
	private EditText ET_flightNo;
	// 声明航空公司下拉框
	private Spinner spinner_comparyObj;
	private ArrayAdapter<String> comparyObj_adapter;
	private static  String[] comparyObj_ShowText  = null;
	private List<Company> companyList = null;
	/*航空公司管理业务逻辑层*/
	private CompanyService companyService = new CompanyService();
	// 声明出发城市下拉框
	private Spinner spinner_startCity;
	private ArrayAdapter<String> startCity_adapter;
	private static  String[] startCity_ShowText  = null;
	private List<City> cityList = null;
	/*出发城市管理业务逻辑层*/
	private CityService cityService = new CityService();
	// 声明到达城市下拉框
	private Spinner spinner_arriveCity;
	private ArrayAdapter<String> arriveCity_adapter;
	private static  String[] arriveCity_ShowText  = null;
	// 出版航班日期控件
	private DatePicker dp_flightDate;
	// 声明起飞时间输入框
	private EditText ET_flyTime;
	// 声明候机楼输入框
	private EditText ET_waitFloor;
	// 声明接机楼输入框
	private EditText ET_meetFloor;
	protected String carmera_path;
	/*要保存的航班信息*/
	Flight flight = new Flight();
	/*航班管理业务逻辑层*/
	private FlightService flightService = new FlightService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.flight_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加航班");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_flightNo = (EditText) findViewById(R.id.ET_flightNo);
		spinner_comparyObj = (Spinner) findViewById(R.id.Spinner_comparyObj);
		// 获取所有的航空公司
		try {
			companyList = companyService.QueryCompany(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int companyCount = companyList.size();
		comparyObj_ShowText = new String[companyCount];
		for(int i=0;i<companyCount;i++) { 
			comparyObj_ShowText[i] = companyList.get(i).getCompanyName();
		}
		// 将可选内容与ArrayAdapter连接起来
		comparyObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, comparyObj_ShowText);
		// 设置下拉列表的风格
		comparyObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_comparyObj.setAdapter(comparyObj_adapter);
		// 添加事件Spinner事件监听
		spinner_comparyObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				flight.setComparyObj(companyList.get(arg2).getCompanyId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_comparyObj.setVisibility(View.VISIBLE);
		spinner_startCity = (Spinner) findViewById(R.id.Spinner_startCity);
		// 获取所有的出发城市
		try {
			cityList = cityService.QueryCity(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int cityCount = cityList.size();
		startCity_ShowText = new String[cityCount];
		for(int i=0;i<cityCount;i++) { 
			startCity_ShowText[i] = cityList.get(i).getCityName();
		}
		// 将可选内容与ArrayAdapter连接起来
		startCity_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, startCity_ShowText);
		// 设置下拉列表的风格
		startCity_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_startCity.setAdapter(startCity_adapter);
		// 添加事件Spinner事件监听
		spinner_startCity.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				flight.setStartCity(cityList.get(arg2).getCityNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_startCity.setVisibility(View.VISIBLE);
		spinner_arriveCity = (Spinner) findViewById(R.id.Spinner_arriveCity);
		arriveCity_ShowText = new String[cityCount];
		for(int i=0;i<cityCount;i++) { 
			arriveCity_ShowText[i] = cityList.get(i).getCityName();
		}
		// 将可选内容与ArrayAdapter连接起来
		arriveCity_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arriveCity_ShowText);
		// 设置下拉列表的风格
		arriveCity_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_arriveCity.setAdapter(arriveCity_adapter);
		// 添加事件Spinner事件监听
		spinner_arriveCity.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				flight.setArriveCity(cityList.get(arg2).getCityNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_arriveCity.setVisibility(View.VISIBLE);
		dp_flightDate = (DatePicker)this.findViewById(R.id.dp_flightDate);
		ET_flyTime = (EditText) findViewById(R.id.ET_flyTime);
		ET_waitFloor = (EditText) findViewById(R.id.ET_waitFloor);
		ET_meetFloor = (EditText) findViewById(R.id.ET_meetFloor);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加航班按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取航班号*/ 
					if(ET_flightNo.getText().toString().equals("")) {
						Toast.makeText(FlightAddActivity.this, "航班号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_flightNo.setFocusable(true);
						ET_flightNo.requestFocus();
						return;	
					}
					flight.setFlightNo(ET_flightNo.getText().toString());
					/*获取航班日期*/
					Date flightDate = new Date(dp_flightDate.getYear()-1900,dp_flightDate.getMonth(),dp_flightDate.getDayOfMonth());
					flight.setFlightDate(new Timestamp(flightDate.getTime()));
					/*验证获取起飞时间*/ 
					if(ET_flyTime.getText().toString().equals("")) {
						Toast.makeText(FlightAddActivity.this, "起飞时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_flyTime.setFocusable(true);
						ET_flyTime.requestFocus();
						return;	
					}
					flight.setFlyTime(ET_flyTime.getText().toString());
					/*验证获取候机楼*/ 
					if(ET_waitFloor.getText().toString().equals("")) {
						Toast.makeText(FlightAddActivity.this, "候机楼输入不能为空!", Toast.LENGTH_LONG).show();
						ET_waitFloor.setFocusable(true);
						ET_waitFloor.requestFocus();
						return;	
					}
					flight.setWaitFloor(ET_waitFloor.getText().toString());
					/*验证获取接机楼*/ 
					if(ET_meetFloor.getText().toString().equals("")) {
						Toast.makeText(FlightAddActivity.this, "接机楼输入不能为空!", Toast.LENGTH_LONG).show();
						ET_meetFloor.setFocusable(true);
						ET_meetFloor.requestFocus();
						return;	
					}
					flight.setMeetFloor(ET_meetFloor.getText().toString());
					/*调用业务逻辑层上传航班信息*/
					FlightAddActivity.this.setTitle("正在上传航班信息，稍等...");
					String result = flightService.AddFlight(flight);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
