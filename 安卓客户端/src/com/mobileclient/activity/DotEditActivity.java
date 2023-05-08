package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Dot;
import com.mobileclient.service.DotService;
import com.mobileclient.domain.Company;
import com.mobileclient.service.CompanyService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class DotEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明网点idTextView
	private TextView TV_dotId;
	// 声明航空公司下拉框
	private Spinner spinner_companyObj;
	private ArrayAdapter<String> companyObj_adapter;
	private static  String[] companyObj_ShowText  = null;
	private List<Company> companyList = null;
	/*航空公司管理业务逻辑层*/
	private CompanyService companyService = new CompanyService();
	// 声明网点名称输入框
	private EditText ET_title;
	// 声明城市下拉框
	private Spinner spinner_cityObj;
	private ArrayAdapter<String> cityObj_adapter;
	private static  String[] cityObj_ShowText  = null;
	private List<City> cityList = null;
	/*城市管理业务逻辑层*/
	private CityService cityService = new CityService();
	// 声明电话输入框
	private EditText ET_telephone;
	// 声明传真输入框
	private EditText ET_fax;
	// 声明地址输入框
	private EditText ET_address;
	protected String carmera_path;
	/*要保存的网点信息*/
	Dot dot = new Dot();
	/*网点管理业务逻辑层*/
	private DotService dotService = new DotService();

	private int dotId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.dot_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑网点信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_dotId = (TextView) findViewById(R.id.TV_dotId);
		spinner_companyObj = (Spinner) findViewById(R.id.Spinner_companyObj);
		// 获取所有的航空公司
		try {
			companyList = companyService.QueryCompany(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int companyCount = companyList.size();
		companyObj_ShowText = new String[companyCount];
		for(int i=0;i<companyCount;i++) { 
			companyObj_ShowText[i] = companyList.get(i).getCompanyName();
		}
		// 将可选内容与ArrayAdapter连接起来
		companyObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, companyObj_ShowText);
		// 设置图书类别下拉列表的风格
		companyObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_companyObj.setAdapter(companyObj_adapter);
		// 添加事件Spinner事件监听
		spinner_companyObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				dot.setCompanyObj(companyList.get(arg2).getCompanyId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_companyObj.setVisibility(View.VISIBLE);
		ET_title = (EditText) findViewById(R.id.ET_title);
		spinner_cityObj = (Spinner) findViewById(R.id.Spinner_cityObj);
		// 获取所有的城市
		try {
			cityList = cityService.QueryCity(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int cityCount = cityList.size();
		cityObj_ShowText = new String[cityCount];
		for(int i=0;i<cityCount;i++) { 
			cityObj_ShowText[i] = cityList.get(i).getCityName();
		}
		// 将可选内容与ArrayAdapter连接起来
		cityObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cityObj_ShowText);
		// 设置图书类别下拉列表的风格
		cityObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_cityObj.setAdapter(cityObj_adapter);
		// 添加事件Spinner事件监听
		spinner_cityObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				dot.setCityObj(cityList.get(arg2).getCityNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_cityObj.setVisibility(View.VISIBLE);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_fax = (EditText) findViewById(R.id.ET_fax);
		ET_address = (EditText) findViewById(R.id.ET_address);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		dotId = extras.getInt("dotId");
		/*单击修改网点按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取网点名称*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(DotEditActivity.this, "网点名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					dot.setTitle(ET_title.getText().toString());
					/*验证获取电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(DotEditActivity.this, "电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					dot.setTelephone(ET_telephone.getText().toString());
					/*验证获取传真*/ 
					if(ET_fax.getText().toString().equals("")) {
						Toast.makeText(DotEditActivity.this, "传真输入不能为空!", Toast.LENGTH_LONG).show();
						ET_fax.setFocusable(true);
						ET_fax.requestFocus();
						return;	
					}
					dot.setFax(ET_fax.getText().toString());
					/*验证获取地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(DotEditActivity.this, "地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					dot.setAddress(ET_address.getText().toString());
					/*调用业务逻辑层上传网点信息*/
					DotEditActivity.this.setTitle("正在更新网点信息，稍等...");
					String result = dotService.UpdateDot(dot);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    dot = dotService.GetDot(dotId);
		this.TV_dotId.setText(dotId+"");
		for (int i = 0; i < companyList.size(); i++) {
			if (dot.getCompanyObj() == companyList.get(i).getCompanyId()) {
				this.spinner_companyObj.setSelection(i);
				break;
			}
		}
		this.ET_title.setText(dot.getTitle());
		for (int i = 0; i < cityList.size(); i++) {
			if (dot.getCityObj().equals(cityList.get(i).getCityNo())) {
				this.spinner_cityObj.setSelection(i);
				break;
			}
		}
		this.ET_telephone.setText(dot.getTelephone());
		this.ET_fax.setText(dot.getFax());
		this.ET_address.setText(dot.getAddress());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
