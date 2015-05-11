/**
 * @Title: SimpleTreeListViewAdapter.java
 * @Package com.sloop.treeview.adapter
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015年2月22日 上午2:01:06
 * @version V1.0
 */

package com.sloop.treeview.adapter;

import java.util.List;

import android.R.integer;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sloop.treeview.R;
import com.sloop.treeview.utils.Node;
import com.sloop.treeview.utils.TreeHelper;
import com.sloop.treeview.utils.adapter.TreeListViewAdapter;

/**
 * @ClassName: SimpleTreeListViewAdapter
 * @author sloop
 * @date 2015年2月22日 上午2:01:06
 */

public class SimpleTreeListViewAdapter<T> extends TreeListViewAdapter<T> {

	/**
	 * 创建一个新的实例 SimpleTreeListViewAdapter. 
	 * @param context
	 * @param tree
	 * @param datas
	 * @param defaultExpandLevel
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	
	public SimpleTreeListViewAdapter(Context context, ListView tree, List<T> datas, int defaultExpandLevel) throws IllegalAccessException, IllegalArgumentException {
		super(context, tree, datas, defaultExpandLevel);
	}

	/**
	 * @Override
	 * Title: getConvertView
	 * @param node
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return 
	 */
	@Override
	public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if (convertView==null) {
			//设置false返回的是单个listview true返回的是包含父view的viewgroup
			convertView = mInflater.inflate(R.layout.list_item, parent, false);
			holder = new ViewHolder();
			holder.mIcon = (ImageView) convertView.findViewById(R.id.item_icon);
			holder.mText = (TextView) convertView.findViewById(R.id.item_text);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (node.getIcon() == -1) {
			holder.mIcon.setVisibility(View.INVISIBLE);
		}else {
			holder.mIcon.setVisibility(View.VISIBLE);
			holder.mIcon.setImageResource(node.getIcon());
		}
		holder.mText.setText(node.getName());
		return convertView;
	}

	private class ViewHolder{
		ImageView mIcon;
		TextView mText;
	}

	/**
	 * 动态插入节点
	 * @Title: addExtraNode
	 * @param position
	 * @param trim void 
	 */
	public void addExtraNode(int position, String name) {
		Node node = mVisibleNodes.get(position);
		int index = mAllNodes.indexOf(node);
		Node exteaNode = new Node(-1, node.getId(), name);
		exteaNode.setParent(node);
		node.getChildren().add(exteaNode);
		mAllNodes.add(index+1, exteaNode);
		//注意数据的存储
		mVisibleNodes = TreeHelper.fliterVisibleNodes(mAllNodes);
		notifyDataSetChanged();
	}
}
