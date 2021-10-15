package com.todo.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;
import com.todo.service.DbConnect;

public class TodoList {
	Connection conn;
	private List<TodoItem> list;

	public TodoList() {
		this.conn = DbConnect.getConnection();
		this.list = new ArrayList<TodoItem>();
	}

	public int addItem(TodoItem t) {
		String sql = "insert into TodoList (title, desc, category, current_date, due_date, checking, manda, item_name)" + "values (?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pstmt;
		int count = 0;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			if(t.getCurrent_date() == null) {
				Date current_date2 = new Date();
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String now = transFormat.format(current_date2);
				pstmt.setString(4,now);
			}
			else
				pstmt.setString(4,t.getCurrent_date());
			pstmt.setString(5,t.getDue_date());
			pstmt.setInt(6,t.getCheck());
			pstmt.setInt(7,t.getManda());
			pstmt.setString(8,t.getName());
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from TodoList where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	int editItem(TodoItem t) {
		String sql = "update TodoList set title = ?, desc = ?, category = ?, current_date = ?, due_date = ?, checking = ?, manda = ?, item_name = ?" + " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getCurrent_date());
			pstmt.setString(5,t.getDue_date());
			pstmt.setInt(6,t.getCheck());
			pstmt.setInt(7,t.getManda());
			pstmt.setString(8,t.getName());
			pstmt.setInt(9,t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM TodoList";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("desc");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int check = rs.getInt("checking");
				int manda = rs.getInt("manda");
				String name = rs.getString("item_name");
				TodoItem t = new TodoItem(title, desc, category, due_date, check, manda, name);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";
		try {
			String sql = "SELECT * FROM TodoList WHERE title like ? or desc like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			pstmt.close();
			}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {

		for (TodoItem myitem : list) {
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String now = transFormat.format(myitem.getCurrent_date());
			if(myitem.getCheck() == 0) {
				System.out.print("[X]");
			}
			else {
				System.out.print("[O]");
			}
			
			
			System.out.println("[" + myitem.getCategory() + "] " + "����: " + myitem.getTitle() + " - " + "���� : " + myitem.getDesc() + " - " + "������¥ : " + myitem.getDue_date() + " - " + "������¥ : " + now);
		}
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}


	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}
	

	public Boolean isDuplicate(String title) {
		
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) {
				return true;
				
			}
				
		}
		return false;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM TodoList;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println(count);
		return count;
	}
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM TodoList";
			ResultSet rs = stmt.executeQuery(sql);
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM TodoList WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery(sql);
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		
		ArrayList<TodoItem> l = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM TodoList ORDER BY " + orderby;
			if(ordering == 0)
				sql += " desc";
			
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("desc");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int check = rs.getInt("checking");
				int manda = rs.getInt("manda");
				String name = rs.getString("item_name");
				TodoItem t = new TodoItem(title, desc, category, due_date, check, manda, name);
				t.setId(id);
				t.setCurrent_date(current_date);
				l.add(t);
			}
			
			
			stmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		

		return l;
	}

	public int Multi_deleteItem(String[] ddel) {
		// TODO Auto-generated method stub
		String sql = "delete from TodoList where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		for (String i : ddel) {
			try {
				int index = Integer.parseInt(i);
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, index);
				count = pstmt.executeUpdate();
				pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}	
		return count;
	}
	
	public int Multi_addItem(TodoItem t, int day, int weeks) {
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        cal.add(Calendar.DATE, + day);
        
        int count = 0;
        
		for(; weeks == 0; weeks--) {
			String sql = "insert into TodoList (title, desc, category, current_date, due_date, checking, manda, item_name)" + "values (?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pstmt;
			try{
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,t.getTitle());
				pstmt.setString(2,t.getDesc());
				pstmt.setString(3,t.getCategory());
				if(t.getCurrent_date() == null) {
					Date current_date2 = new Date();
					SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String now = transFormat.format(current_date2);
					pstmt.setString(4,now);
				}
				else
					pstmt.setString(4,t.getCurrent_date());
				String dw = df.format(cal.getTime());
				pstmt.setString(5,dw);
		        cal.add(Calendar.DATE, +7);
				pstmt.setInt(6,t.getCheck());
				pstmt.setInt(7,t.getManda());
				pstmt.setString(8,t.getName());
				count = pstmt.executeUpdate();
				pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return count;
		
	}

}