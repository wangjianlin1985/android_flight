package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Dot;
import com.mobileclient.service.DotService;
import com.mobileclient.domain.Company;
import com.mobileclient.service.CompanyService;
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
public class DotDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明网点id控件
	private TextView TV_dotId;
	// 声明航空公司控件
	private TextView TV_companyObj;
	// 声明网点名称控件
	private TextView TV_title;
	// 声明城市控件
	private TextView TV_cityObj;
	// 声明电话控件
	private TextView TV_telephone;
	// 声明传真控件
	private TextView TV_fax;
	// 声明地址控件
	private TextView TV_address;
	/* 要保存的网点信息 */
	Dot dot = new Dot(); 
	/* 网点管理业务逻辑层 */
	private DotService dotService = new DotService();
	private CompanyService companyService = new CompanyService();
	private CityService cityService = new CityService();
	private int dotId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.dot_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看网点详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_dotId = (TextView) findViewById(R.id.TV_dotId);
		TV_companyObj = (TextView) findViewById(R.id.TV_companyObj);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_cityObj = (TextView) findViewById(R.id.TV_cityObj);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_fax = (TextView) findViewById(R.id.TV_fax);
		TV_address = (TextView) findViewById(R.id.TV_address);
		Bundle extras = this.getIntent().getExtras();
		dotId = extras.getInt("dotId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DotDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    dot = dotService.GetDot(dotId); 
		this.TV_dotId.setText(dot.getDotId() + "");
		Company companyObj = companyService.GetCompany(dot.getCompanyObj());
		this.TV_companyObj.setText(companyObj.getCompanyName());
		this.TV_title.setText(dot.getTitle());
		City cityObj = cityService.GetCity(dot.getCityObj());
		this.TV_cityObj.setText(cityObj.getCityName());
		this.TV_telephone.setText(dot.getTelephone());
		this.TV_fax.setText(dot.getFax());
		this.TV_address.setText(dot.getAddress());
	} 
}
