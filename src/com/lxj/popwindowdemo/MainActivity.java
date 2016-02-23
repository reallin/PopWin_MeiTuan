package com.lxj.popwindowdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

public class MainActivity extends Activity implements
OnClickListener {
	private ListView listView, popListView;
	private ProgressBar progressBar;
	private List<Map<String, String>> menuData1, menuData2, menuData3;
	private PopupWindow popMenu;
	private SimpleAdapter menuAdapter1, menuAdapter2, menuAdapter3;

	private LinearLayout product, sort, activity;
	private ImageView cartIv;
	private TextView productTv, sortTv, activityTv, titleTv;
	private int green, grey;

	private String currentProduct, currentSort, currentActivity;
	private int menuIndex = 0;

	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supplier_list);
		findView();
		initMenuData();
		initPopMenu();
		
	}
	private void initMenuData() {
		menuData1 = new ArrayList<Map<String, String>>();
		String[] menuStr1 = new String[] { "全部", "粮油", "衣服", "图书", "电子产品",
				 "酒水饮料", "水果" };
		Map<String, String> map1;
		for (int i = 0, len = menuStr1.length; i < len; ++i) {
			map1 = new HashMap<String, String>();
			map1.put("name", menuStr1[i]);
			menuData1.add(map1);
		}

		menuData2 = new ArrayList<Map<String, String>>();
		String[] menuStr2 = new String[] { "综合排序", "配送费最低" };
		Map<String, String> map2;
		for (int i = 0, len = menuStr2.length; i < len; ++i) {
			map2 = new HashMap<String, String>();
			map2.put("name", menuStr2[i]);
			menuData2.add(map2);
		}

		menuData3 = new ArrayList<Map<String, String>>();
		String[] menuStr3 = new String[] { "优惠活动", "特价活动", "免配送费",
				"可在线支付" };
		Map<String, String> map3;
		for (int i = 0, len = menuStr3.length; i < len; ++i) {
			map3 = new HashMap<String, String>();
			map3.put("name", menuStr3[i]);
			menuData3.add(map3);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.supplier_list_product:
			productTv.setTextColor(Color.parseColor("#39ac69"));
			popListView.setAdapter(menuAdapter1);
			popMenu.showAsDropDown(product, 0, 2);
			menuIndex = 0;
			break;
		case R.id.supplier_list_sort:
			sortTv.setTextColor(Color.parseColor("#39ac69"));
			popListView.setAdapter(menuAdapter2);
			popMenu.showAsDropDown(product, 0, 2);
			menuIndex = 1;
			break;
		case R.id.supplier_list_activity:
			activityTv.setTextColor(Color.parseColor("#39ac69"));
			popListView.setAdapter(menuAdapter3);
			popMenu.showAsDropDown(product, 0, 2);
			menuIndex = 2;
			break;

		}
	}
	protected void findView() {
		listView = (ListView) findViewById(R.id.supplier_list_lv);
		product = (LinearLayout) findViewById(R.id.supplier_list_product);
		sort = (LinearLayout) findViewById(R.id.supplier_list_sort);
		activity = (LinearLayout) findViewById(R.id.supplier_list_activity);
		productTv = (TextView) findViewById(R.id.supplier_list_product_tv);
		sortTv = (TextView) findViewById(R.id.supplier_list_sort_tv);
		activityTv = (TextView) findViewById(R.id.supplier_list_activity_tv);
		titleTv = (TextView) findViewById(R.id.supplier_list_title_tv);
		cartIv = (ImageView) findViewById(R.id.supplier_list_cart_iv);
		progressBar = (ProgressBar) findViewById(R.id.progress);

		product.setOnClickListener(this);
		sort.setOnClickListener(this);
		activity.setOnClickListener(this);
		cartIv.setOnClickListener(this);
	}
	private void initPopMenu() {
		initMenuData();
		View contentView = View.inflate(this, R.layout.popwin_supplier_list,
				null);
		popMenu = new PopupWindow(contentView,
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		popMenu.setOutsideTouchable(true);
		popMenu.setBackgroundDrawable(new BitmapDrawable());
		popMenu.setFocusable(true);
		popMenu.setAnimationStyle(R.style.popwin_anim_style);
		popMenu.setOnDismissListener(new OnDismissListener() {
			public void onDismiss() {
				productTv.setTextColor(Color.parseColor("#5a5959"));
				sortTv.setTextColor(Color.parseColor("#5a5959"));
				activityTv.setTextColor(Color.parseColor("#5a5959"));
			}
		});

		popListView = (ListView) contentView
				.findViewById(R.id.popwin_supplier_list_lv);
		contentView.findViewById(R.id.popwin_supplier_list_bottom)
				.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						popMenu.dismiss();
					}
				});
		menuAdapter1 = new SimpleAdapter(this, menuData1,
				R.layout.item_listview_popwin, new String[] { "name" },
				new int[] { R.id.listview_popwind_tv });
		menuAdapter2 = new SimpleAdapter(this, menuData2,
				R.layout.item_listview_popwin, new String[] { "name" },
				new int[] { R.id.listview_popwind_tv });
		menuAdapter3 = new SimpleAdapter(this, menuData3,
				R.layout.item_listview_popwin, new String[] { "name" },
				new int[] { R.id.listview_popwind_tv });

		popListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				popMenu.dismiss();
				if (menuIndex == 0) {
					currentProduct = menuData1.get(pos).get("name");
					titleTv.setText(currentProduct);
					productTv.setText(currentProduct);
					Toast.makeText(MainActivity.this, currentProduct, Toast.LENGTH_SHORT).show();
				} else if (menuIndex == 1) {
					currentSort = menuData2.get(pos).get("name");
					titleTv.setText(currentSort);
					sortTv.setText(currentSort);
					Toast.makeText(MainActivity.this, currentSort, Toast.LENGTH_SHORT).show();
				} else {
					currentActivity = menuData3.get(pos).get("name");
					titleTv.setText(currentActivity);
					activityTv.setText(currentActivity);
					Toast.makeText(MainActivity.this, currentActivity, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
