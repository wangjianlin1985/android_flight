package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Flight;
import com.mobileclient.domain.Company;
import com.mobileclient.service.CompanyService;
import com.mobileclient.domain.City;
import com.mobileclient.service.CityService;
import com.mobileclient.domain.City;
import com.mobileclient.service.CityService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class FlightQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
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
	/*城市管理业务逻辑层*/
	private CityService cityService = new CityService();
	// 声明到达城市下拉框
	private Spinner spinner_arriveCity;
	private ArrayAdapter<String> arriveCity_adapter;
	private static  String[] arriveCity_ShowText  = null;
	// 航班日期控件
	private DatePicker dp_flightDate;
	private CheckBox cb_flightDate;
	/*查询过滤条件保存到这个对象中*/
	private Flight queryConditionFlight = new Flight();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.flight_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置航班查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_flightNo = (EditText) findViewById(R.id.ET_flightNo);
		spinner_comparyObj = (Spinner) findViewById(R.id.Spinner_comparyObj);
		// 获取所有的航空公司
		try {
			companyList = companyService.QueryCompany(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int companyCount = companyList.size();
		comparyObj_ShowText = new String[companyCount+1];
		comparyObj_ShowText[0] = "不限制";
		for(int i=1;i<=companyCount;i++) { 
			comparyObj_ShowText[i] = companyList.get(i-1).getCompanyName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		comparyObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, comparyObj_ShowText);
		// 设置航空公司下拉列表的风格
		comparyObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_comparyObj.setAdapter(comparyObj_adapter);
		// 添加事件Spinner事件监听
		spinner_comparyObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionFlight.setComparyObj(companyList.get(arg2-1).getCompanyId()); 
				else
					queryConditionFlight.setComparyObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_comparyObj.setVisibility(View.VISIBLE);
		spinner_startCity = (Spinner) findViewById(R.id.Spinner_startCity);
		// 获取所有的城市
		try {
			cityList = cityService.QueryCity(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int cityCount = cityList.size();
		startCity_ShowText = new String[cityCount+1];
		startCity_ShowText[0] = "不限制";
		for(int i=1;i<=cityCount;i++) { 
			startCity_ShowText[i] = cityList.get(i-1).getCityName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		startCity_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, startCity_ShowText);
		// 设置出发城市下拉列表的风格
		startCity_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_startCity.setAdapter(startCity_adapter);
		// 添加事件Spinner事件监听
		spinner_startCity.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionFlight.setStartCity(cityList.get(arg2-1).getCityNo()); 
				else
					queryConditionFlight.setStartCity("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_startCity.setVisibility(View.VISIBLE);
		spinner_arriveCity = (Spinner) findViewById(R.id.Spinner_arriveCity);
		arriveCity_ShowText = new String[cityCount+1];
		arriveCity_ShowText[0] = "不限制";
		for(int i=1;i<=cityCount;i++) { 
			arriveCity_ShowText[i] = cityList.get(i-1).getCityName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		arriveCity_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arriveCity_ShowText);
		// 设置到达城市下拉列表的风格
		arriveCity_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_arriveCity.setAdapter(arriveCity_adapter);
		// 添加事件Spinner事件监听
		spinner_arriveCity.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionFlight.setArriveCity(cityList.get(arg2-1).getCityNo()); 
				else
					queryConditionFlight.setArriveCity("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_arriveCity.setVisibility(View.VISIBLE);
		dp_flightDate = (DatePicker) findViewById(R.id.dp_flightDate);
		cb_flightDate = (CheckBox) findViewById(R.id.cb_flightDate);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionFlight.setFlightNo(ET_flightNo.getText().toString());
					if(cb_flightDate.isChecked()) {
						/*获取航班日期*/
						Date flightDate = new Date(dp_flightDate.getYear()-1900,dp_flightDate.getMonth(),dp_flightDate.getDayOfMonth());
						queryConditionFlight.setFlightDate(new Timestamp(flightDate.getTime()));
					} else {
						queryConditionFlight.setFlightDate(null);
					} 
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionFlight", queryConditionFlight);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
