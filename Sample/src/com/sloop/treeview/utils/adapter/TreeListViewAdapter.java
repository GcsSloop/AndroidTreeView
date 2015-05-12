/**
 * @Title: TreeListViewAdapter.java
 * @Package com.sloop.treeview.utils.adapter
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015年2月22日 上午1:16:25
 * @version V1.0
 */

package com.sloop.treeview.utils.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.sloop.treeview.utils.Node;
import com.sloop.treeview.utils.TreeHelper;

/**
 * @ClassName: TreeListViewAdapter
 * @author sloop
 * @date 2015年2月22日 上午1:16:25
 */

public abstract class TreeListViewAdapter<T> extends BaseAdapter {

	protected Context mContext;				//上下文
	protected List<Node> mAllNodes;			//所有节点
	protected List<Node> mVisibleNodes;		//显示的节点
	protected LayoutInflater mInflater;		//页面填充器
	protected ListView mTree;					//展示用的ListView
	
	/**
	 * 设置用户node的点击回调
	 * 给用户提供的条目点击响应
	 * 用于弥补OnItemClickListener被占用的不足
	 * @ClassName: OnTreeNodeClickListener
	 * @author sloop
	 * @date 2015年2月22日 上午1:44:19
	 */
	public interface OnTreeNodeClickListener{
		void onClick(Node node, int position);
	}
	
	protected OnTreeNodeClickListener mListener;
	
	public void setOnTreeNodeClickLitener(OnTreeNodeClickListener mListener) {
		this.mListener = mListener;
	}
	
	/**
	 * 创建一个新的实例 TreeListViewAdapter. 
	 * @param context			上下文
	 * @param mTree				展示用的ListView
	 * @param datas				数据集
	 * @param defaultLevel		默认展开层级
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public TreeListViewAdapter(Context context,ListView tree, List<T> datas, int defaultExpandLevel) throws IllegalAccessException, IllegalArgumentException {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
		mVisibleNodes = TreeHelper.fliterVisibleNodes(mAllNodes);
		for (Node node : mVisibleNodes) {
			Log.e("TAG", "显示--"+node.getName());
		}
		mTree = tree;
		
		mTree.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				expandOrCollapse(position);
				
				if (mListener != null) {
					mListener.onClick(mVisibleNodes.get(position), position);
				}
			}
		});
	}
	
	/**
	 * 点击收缩或者展开
	 * @Title: expandOrCollapse
	 * @param position 
	 */
	protected void expandOrCollapse(int position) {
		Node node = mVisibleNodes.get(position);
		if (node!=null) {
			if (node.isLeaf()) {
				return;
			}
			node.setExpend(!node.isExpend());
			mVisibleNodes = TreeHelper.fliterVisibleNodes(mAllNodes);
			notifyDataSetChanged();	//刷新
		}
	}
	

	/**
	 * @Override
	 * Title: getCount
	 * @return 
	 */
	@Override
	public int getCount() {
		return mVisibleNodes.size();
	}

	/**
	 * @Override
	 * Title: getItem
	 * @param position
	 * @return 
	 */
	@Override
	public Object getItem(int position) {
		return mVisibleNodes.get(position);
	}

	/**
	 * @Override
	 * Title: getItemId
	 * @param position
	 * @return 
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * @Override
	 * Title: getView
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return 
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Node node = mVisibleNodes.get(position);
		convertView = getConvertView(node, position, convertView, parent);
		convertView.setPadding(node.getLevel()*30, 3, 3, 3);	//设置padding内边距
		return convertView;
	}

	/**
	 * 提供给用户的自定义条目的方式
	 * @Title: getConvertView
	 * @param node
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return View
	 */
	public abstract View getConvertView(Node node, int position, View convertView, ViewGroup parent);
}
