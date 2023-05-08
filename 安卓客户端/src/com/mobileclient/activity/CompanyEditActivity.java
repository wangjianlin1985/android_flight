package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Company;
import com.mobileclient.service.CompanyService;
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

public class CompanyEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明公司idTextView
	private TextView TV_companyId;
	// 声明航空公司输入框
	private EditText ET_companyName;
	// 声明法人代表输入框
	private EditText ET_personName;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 出版成立日期控件
	private DatePicker dp_bornDate;
	protected String carmera_path;
	/*要保存的航空公司信息*/
	Company company = new Company();
	/*航空公司管理业务逻辑层*/
	private CompanyService companyService = new CompanyService();

	private int companyId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.company_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑航空公司信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_companyId = (TextView) findViewById(R.id.TV_companyId);
		ET_companyName = (EditText) findViewById(R.id.ET_companyName);
		ET_personName = (EditText) findViewById(R.id.ET_personName);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		dp_bornDate = (DatePicker)this.findViewById(R.id.dp_bornDate);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		companyId = extras.getInt("companyId");
		/*单击修改航空公司按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取航空公司*/ 
					if(ET_companyName.getText().toString().equals("")) {
						Toast.makeText(CompanyEditActivity.this, "航空公司输入不能为空!", Toast.LENGTH_LONG).show();
						ET_companyName.setFocusable(true);
						ET_companyName.requestFocus();
						return;	
					}
					company.setCompanyName(ET_companyName.getText().toString());
					/*验证获取法人代表*/ 
					if(ET_personName.getText().toString().equals("")) {
						Toast.makeText(CompanyEditActivity.this, "法人代表输入不能为空!", Toast.LENGTH_LONG).show();
						ET_personName.setFocusable(true);
						ET_personName.requestFocus();
						return;	
					}
					company.setPersonName(ET_personName.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(CompanyEditActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					company.setTelephone(ET_telephone.getText().toString());
					/*获取出版日期*/
					Date bornDate = new Date(dp_bornDate.getYear()-1900,dp_bornDate.getMonth(),dp_bornDate.getDayOfMonth());
					company.setBornDate(new Timestamp(bornDate.getTime()));
					/*调用业务逻辑层上传航空公司信息*/
					CompanyEditActivity.this.setTitle("正在更新航空公司信息，稍等...");
					String result = companyService.UpdateCompany(company);
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
	    company = companyService.GetCompany(companyId);
		this.TV_companyId.setText(companyId+"");
		this.ET_companyName.setText(company.getCompanyName());
		this.ET_personName.setText(company.getPersonName());
		this.ET_telephone.setText(company.getTelephone());
		Date bornDate = new Date(company.getBornDate().getTime());
		this.dp_bornDate.init(bornDate.getYear() + 1900,bornDate.getMonth(), bornDate.getDate(), null);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
