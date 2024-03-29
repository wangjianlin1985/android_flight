package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.CompanyService;
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

public class DotSimpleAdapter extends SimpleAdapter { 
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

    public DotSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.dot_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_companyObj = (TextView)convertView.findViewById(R.id.tv_companyObj);
	  holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
	  holder.tv_cityObj = (TextView)convertView.findViewById(R.id.tv_cityObj);
	  holder.tv_telephone = (TextView)convertView.findViewById(R.id.tv_telephone);
	  holder.tv_fax = (TextView)convertView.findViewById(R.id.tv_fax);
	  /*设置各个控件的展示内容*/
	  holder.tv_companyObj.setText("航空公司：" + (new CompanyService()).GetCompany(Integer.parseInt(mData.get(position).get("companyObj").toString())).getCompanyName());
	  holder.tv_title.setText("网点名称：" + mData.get(position).get("title").toString());
	  holder.tv_cityObj.setText("城市：" + (new CityService()).GetCity(mData.get(position).get("cityObj").toString()).getCityName());
	  holder.tv_telephone.setText("电话：" + mData.get(position).get("telephone").toString());
	  holder.tv_fax.setText("传真：" + mData.get(position).get("fax").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_companyObj;
    	TextView tv_title;
    	TextView tv_cityObj;
    	TextView tv_telephone;
    	TextView tv_fax;
    }
} 
