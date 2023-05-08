package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.CompanyService;
import com.mobileclient.service.CityService;
import com.mobileclient.service.CityService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class FlightSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public FlightSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.flight_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_flightNo = (TextView)convertView.findViewById(R.id.tv_flightNo);
	  holder.tv_comparyObj = (TextView)convertView.findViewById(R.id.tv_comparyObj);
	  holder.tv_startCity = (TextView)convertView.findViewById(R.id.tv_startCity);
	  holder.tv_arriveCity = (TextView)convertView.findViewById(R.id.tv_arriveCity);
	  holder.tv_flightDate = (TextView)convertView.findViewById(R.id.tv_flightDate);
	  holder.tv_flyTime = (TextView)convertView.findViewById(R.id.tv_flyTime);
	  /*设置各个控件的展示内容*/
	  holder.tv_flightNo.setText("航班号：" + mData.get(position).get("flightNo").toString());
	  holder.tv_comparyObj.setText("航空公司：" + (new CompanyService()).GetCompany(Integer.parseInt(mData.get(position).get("comparyObj").toString())).getCompanyName());
	  holder.tv_startCity.setText("出发城市：" + (new CityService()).GetCity(mData.get(position).get("startCity").toString()).getCityName());
	  holder.tv_arriveCity.setText("到达城市：" + (new CityService()).GetCity(mData.get(position).get("arriveCity").toString()).getCityName());
	  try {holder.tv_flightDate.setText("航班日期：" + mData.get(position).get("flightDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_flyTime.setText("起飞时间：" + mData.get(position).get("flyTime").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_flightNo;
    	TextView tv_comparyObj;
    	TextView tv_startCity;
    	TextView tv_arriveCity;
    	TextView tv_flightDate;
    	TextView tv_flyTime;
    }
} 
