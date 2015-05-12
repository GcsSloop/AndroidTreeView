package com.sloop.treeview;

import java.util.ArrayList;
import java.util.List;

import com.sloop.treeview.adapter.SimpleTreeListViewAdapter;
import com.sloop.treeview.bean.FileBean;
import com.sloop.treeview.bean.OrgBean;
import com.sloop.treeview.utils.Node;
import com.sloop.treeview.utils.adapter.TreeListViewAdapter.OnTreeNodeClickListener;

import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private ListView mTree;
//	private SimpleTreeListViewAdapter<FileBean> mAdapter;
	private SimpleTreeListViewAdapter<OrgBean> mAdapter2;
//	private List<FileBean> mDatas;
	private List<OrgBean> mDatas2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTree = (ListView) findViewById(R.id.listView);
		
		initDatas();
		
	/*	try {
			mAdapter = new SimpleTreeListViewAdapter<FileBean>(this, mTree, mDatas, 1);
			mTree.setAdapter(mAdapter);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		try {
			mAdapter2 = new SimpleTreeListViewAdapter<OrgBean>(this, mTree, mDatas2, 1);
			mTree.setAdapter(mAdapter2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		initEvent();
	}

	
	/**
	 * 初始化响应事件
	 * @Title: initEvent void
	 */
	private void initEvent() {
		mAdapter2.setOnTreeNodeClickLitener(new OnTreeNodeClickListener() {

			@Override
			public void onClick(Node node, int position) {
				if (node.isLeaf()) {
					Toast.makeText(MainActivity.this, node.getName(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		mTree.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("添加节点");
				final EditText et = new EditText(MainActivity.this);
				builder.setView(et);
				builder.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String name = et.getText().toString();
						if (TextUtils.isEmpty(name)) {
							return;
						}
						mAdapter2.addExtraNode(position, name);
					}
				});
				builder.setNegativeButton("取消", null);
				builder.show();
				return true;	//返回值为tru表示长按事件触发后不会触发点击事件
			}
		});
	}
	
	/**
	 * 初始化数据
	 * @Title: initDatas void 
	 */
	private void initDatas() {
/*		mDatas = new ArrayList<FileBean>();
		
		FileBean bean = new FileBean(1, 0, "根目录1");
		mDatas.add(bean);
		bean = new FileBean(2, 0, "根目录2");
		mDatas.add(bean);
		bean = new FileBean(3, 0, "根目录3");
		mDatas.add(bean);
		
		bean = new FileBean(4, 1, "根目录1-1");
		mDatas.add(bean);
		bean = new FileBean(5, 1, "根目录1-2");
		mDatas.add(bean);

		bean = new FileBean(6, 5, "根目录1-2-1");
		mDatas.add(bean);
		
		bean = new FileBean(7, 3, "根目录3-1");
		mDatas.add(bean);
		bean = new FileBean(8, 3, "根目录3-2");
		mDatas.add(bean);*/
		
		mDatas2 = new ArrayList<OrgBean>();
		
		OrgBean bean = new OrgBean(1, 0, "根目录1");
		mDatas2.add(bean);
		bean = new OrgBean(2, 0, "根目录2");
		mDatas2.add(bean);
		bean = new OrgBean(3, 0, "根目录3");
		mDatas2.add(bean);
		
		bean = new OrgBean(4, 1, "根目录1-1");
		mDatas2.add(bean);
		bean = new OrgBean(5, 1, "根目录1-2");
		mDatas2.add(bean);

		bean = new OrgBean(6, 5, "根目录1-2-1");
		mDatas2.add(bean);
		
		bean = new OrgBean(7, 3, "根目录3-1");
		mDatas2.add(bean);
		bean = new OrgBean(8, 3, "根目录3-2");
		mDatas2.add(bean);
	}


}
