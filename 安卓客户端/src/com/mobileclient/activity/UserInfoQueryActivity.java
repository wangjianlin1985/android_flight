package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.UserInfo;
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
public class UserInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明用户名输入框
	private EditText ET_user_name;
	// 声明姓名输入框
	private EditText ET_name;
	// 出生日期控件
	private DatePicker dp_birthday;
	private CheckBox cb_birthday;
	// 声明所在城市下拉框
	private Spinner spinner_cityObj;
	private ArrayAdapter<String> cityObj_adapter;
	private static  String[] cityObj_ShowText  = null;
	private List<City> cityList = null; 
	/*城市管理业务逻辑层*/
	private CityService cityService = new CityService();
	/*查询过滤条件保存到这个对象中*/
	private UserInfo queryConditionUserInfo = new UserInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.userinfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置用户查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_user_name = (EditText) findViewById(R.id.ET_user_name);
		ET_name = (EditText) findViewById(R.id.ET_name);
		dp_birthday = (DatePicker) findViewById(R.id.dp_birthday);
		cb_birthday = (CheckBox) findViewById(R.id.cb_birthday);
		spinner_cityObj = (Spinner) findViewById(R.id.Spinner_cityObj);
		// 获取所有的城市
		try {
			cityList = cityService.QueryCity(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int cityCount = cityList.size();
		cityObj_ShowText = new String[cityCount+1];
		cityObj_ShowText[0] = "不限制";
		for(int i=1;i<=cityCount;i++) { 
			cityObj_ShowText[i] = cityList.get(i-1).getCityName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		cityObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cityObj_ShowText);
		// 设置所在城市下拉列表的风格
		cityObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_cityObj.setAdapter(cityObj_adapter);
		// 添加事件Spinner事件监听
		spinner_cityObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionUserInfo.setCityObj(cityList.get(arg2-1).getCityNo()); 
				else
					queryConditionUserInfo.setCityObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_cityObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionUserInfo.setUser_name(ET_user_name.getText().toString());
					queryConditionUserInfo.setName(ET_name.getText().toString());
					if(cb_birthday.isChecked()) {
						/*获取出生日期*/
						Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
						queryConditionUserInfo.setBirthday(new Timestamp(birthday.getTime()));
					} else {
						queryConditionUserInfo.setBirthday(null);
					} 
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionUserInfo", queryConditionUserInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
