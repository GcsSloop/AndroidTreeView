/**
 * @Title: Node.java
 * @Package com.sloop.treeview.utils
 * @Description:
 * Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015年2月21日 上午3:40:42
 * @version V1.0
 */

package com.sloop.treeview.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构的节点
 * 
 * @ClassName: Node
 * @author sloop
 * @date 2015年2月21日 上午3:42:26
 */

public class Node {

	/**
	 * 当前id
	 */
	private int id;
	/**
	 * 父节点id 根节点的pid=0
	 */
	private int pId;
	/**
	 * 显示名称
	 */
	private String name;
	/**
	 * 树的层级
	 */
	private int level;
	/**
	 * 是否展开
	 */
	private boolean isExpend = false;
	/**
	 * 图标
	 */
	private int icon;
	/**
	 * 父节点
	 */
	private Node parent;
	/**
	 * 子节点
	 */
	private List<Node> children = new ArrayList<Node>();

	/**
	 * 
	 * 创建一个新的实例 Node.
	 * 
	 * @param id 当前节点id
	 * @param pId 父节点id
	 * @param name 显示名称
	 */
	public Node(int id, int pId, String name) {
		this.id = id;
		this.pId = pId;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 得到当前节点的层级
	 * 
	 * @Title: getLevel
	 * @return int
	 */
	public int getLevel() {
		return parent == null ? 0 : parent.getLevel() + 1;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isExpend() {
		return isExpend;
	}

	/**
	 * 改变展开状态
	 * 
	 * @Title: setExpend
	 * @param isExpend void
	 */
	public void setExpend(boolean isExpend) {
		this.isExpend = isExpend;
		if (!isExpend) { // 收缩子节点
			for (Node node : children) {
				node.setExpend(false);
			}
		}
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	/**
	 * 判断是否是根节点
	 * 
	 * @Title: isRoot
	 * @return boolean
	 */
	public boolean isRoot() {
		return parent == null;
	}

	/**
	 * 判断父节点是否处于展开状态
	 * 
	 * @Title: isParentExpend
	 * @return boolean
	 */
	public boolean isParentExpend() {
		if (parent == null)
			return false;
		return parent.isExpend();
	}

	/**
	 * 判断是否是叶子节点
	 * 
	 * @Title: isLeaf
	 * @return boolean
	 */
	public boolean isLeaf() {
		return children.size() == 0;
	}
}
