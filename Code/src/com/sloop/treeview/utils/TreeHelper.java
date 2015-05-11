/**
 * @Title: TreeHelper.java
 * @Package com.sloop.treeview.utils
 * @Description: TODO
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015年2月21日 上午3:19:27
 * @version V1.0
 */

package com.sloop.treeview.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.sloop.treeview.R;
import com.sloop.treeview.utils.annotation.TreeNodeId;
import com.sloop.treeview.utils.annotation.TreeNodeLabel;
import com.sloop.treeview.utils.annotation.TreeNodePid;

/**
 * 树形结构的帮助类 将元数据转换为节点
 * @ClassName: TreeHelper
 * @Description: 
 * @author sloop
 * @date 2015年2月21日 上午3:19:27
 *
 */

public class TreeHelper {

	/**
	 * 将用户数据转化为树形数据
	 * @Title: convertDatas2Nodes
	 * @param datas
	 * @return List<Node>	Node数据集
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static <T> List<Node> convertDatas2Nodes(List<T> datas) throws IllegalAccessException, IllegalArgumentException {
		
		List<Node> nodes = new ArrayList<Node>();
		Node node = null;
		for (T t : datas) {		//循环遍历所有数据
			int id = -1;
			int pId = -1;
			String label = null;
			
			Class clazz = t.getClass();	//使用反射+注解获取属性和方法
			
			//遍历所有字段(属性)并根据注解判断方法
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if(field.getAnnotation(TreeNodeId.class) != null){
					//扩展-通过用户指定类型来强制转换到该类型
			/*		TreeNodeId annotation = field.getAnnotation(TreeNodeId.class);
					Class type = annotation.type();
					if (type==String.class) {
						//强转为String
					}else if (type == Integer.class) {
						//强转为int
					}*/
					
					field.setAccessible(true);	//设置访问权限 强制更改为可以访问
					id = field.getInt(t);
				}
				if(field.getAnnotation(TreeNodePid.class) != null){
					field.setAccessible(true);	
					pId = field.getInt(t);
				}
				if(field.getAnnotation(TreeNodeLabel.class) != null){
					field.setAccessible(true);	
					label = (String) field.get(t);
				}
			}
			node = new Node(id, pId, label);
			nodes.add(node);
		}
		//为node设置关联关系
	/*	for (Node n : nodes){
			for (Node m : nodes) {
				if (m.getpId() == n.getId()) {			//m是n的子节点
					n.getChildren().add(m);
					m.setParent(n);
				}else if (m.getId() == n.getpId()) {	//m是n的父节点
					m.getChildren().add(n);
					n.setParent(m);
				}
			}
		}*/
		for (int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);
			for (int j = i+1; j < nodes.size(); j++) {
				Node m = nodes.get(j);
				if (m.getpId() == n.getId()) {			//m是n的子节点
					n.getChildren().add(m);
					m.setParent(n);
				}else if (m.getId() == n.getpId()) {	//m是n的父节点
					m.getChildren().add(n);
					n.setParent(m);
				}
			}
		}
		
		for (Node n : nodes) {
			setNodeIcon(n);
		}
		
		return nodes;
	}

	/**
	 * 获取排序后的节点数据
	 * @Title: getSortedNodes
	 * @param datas
	 * @return List<Node>
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static <T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel) throws IllegalAccessException, IllegalArgumentException {
		
		List<Node> result = new ArrayList<Node>();		//排序完成的节点
		List<Node> nodes = convertDatas2Nodes(datas);	//转化后的所有节点
		List<Node> rootNodes = getRootNodes(nodes);
		Log.e("TAG", "转化后的所有节点个数"+nodes.size());
		Log.e("TAG", "根节点个数"+rootNodes.size());
		for (Node node : rootNodes) {
	//		Log.e("TAG", "根节点--"+node.getName());
			addNode(result, node, defaultExpandLevel, 1);
		}
		Log.e("TAG", "排序完成的节点个数"+result.size());
		for (Node node : result) {
			
		//	Log.e("TAG", "排序----"+node.getName());
		}
		return result;
	}
	
	/**
	 * 把一个节点的所有孩子节点都放入result(递归)
	 * @Title: addNode
	 * @param result					添加进哪个父节点
	 * @param node						需要添加进去的node
	 * @param defaultExpandLevel		默认展开层级
	 * @param currentLevel				当前层级
	 */
	private static void addNode(List<Node> result, Node node, int defaultExpandLevel, int currentLevel) {
		
		result.add(node);
		if (node.isLeaf()){							//如果是叶子节点说明该分支添加结束 返回
			return;
		}
		if (defaultExpandLevel >= currentLevel) {	//当前层级小于默认展开层级就展开当前
			node.setExpend(true);
		}

		for (int i = 0; i < node.getChildren().size(); i++) {
			addNode(result, node.getChildren().get(i), defaultExpandLevel, currentLevel+1);
		}
	}
	
	/**
	 * 过滤出需要显示的node集合
	 * @Title: fliterVisibleNodes
	 * @return List<Node>
	 */
	public static List<Node> fliterVisibleNodes(List<Node> nodes) {
		
		List<Node> result = new ArrayList<Node>();
		
		for (Node node : nodes) {
			if (node.isRoot() || node.isParentExpend()) {	//如果当前节点是根节点或者他的父节点处于展开状态就显示
				setNodeIcon(node);	//刷新图标
				result.add(node);
			}
		}
		for (Node node : result) {
	//		Log.e("TAG", "显示--"+node.getName());
		}
		return result;
	}

	/**
	 * 从所有节点中获取根节点集合
	 * @Title: getRootNodes
	 * @param nodes
	 * @return List<Node> 
	 */
	private static List<Node> getRootNodes(List<Node> nodes) {
		
		List<Node> root = new ArrayList<Node>();
		
		for (Node node : nodes) {
			if (node.isRoot()) {
				root.add(node);
			}
		}
		
		return root;
	}

	/**
	 * 给node设置图片
	 * @Title: setNodeIcon
	 * @param n void 
	 */
	private static void setNodeIcon(Node n) {
		if (n.getChildren().size()>0 && n.isExpend()) {			//有子节点并且是展开的
			n.setIcon(R.drawable.item_open);
		}else if (n.getChildren().size()>0 && !n.isExpend()) {	//有子节点但是未展开
			n.setIcon(R.drawable.item_close);
		}
	}
}
