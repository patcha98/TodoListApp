package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�׸��� �߰��մϴ�]\n"
				+ "������ �Է����ּ��� : ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("�ߺ��� ������ �����մϴ�.");
			return;
		}
		System.out.print("ī�װ��� �Է����ּ��� : ");
		category = sc.next();
		
		System.out.print("������ �Է����ּ��� : ");
		desc = sc.next();
		
		System.out.print("���� ��¥�� �Է����ּ��� (ex:2021/09/30) : ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		if(list.addItem(t)>0)
			System.out.println("�߰��Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�׸��� �����մϴ�]\n"
				+ "������ �׸��� ��ȣ�� �Է����ּ��� : ");
		int index = sc.nextInt();

		if(l.deleteItem(index)>0)
			System.out.println("�����Ǿ����ϴ�.");
	}


	public static void updateItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "[�׸��� �����մϴ�]\n"
				+ "������ �׸��� ��ȣ�� �Է����ּ��� : ");
		int index = sc.nextInt();
		System.out.print("���ο� ī�װ��� �Է����ּ��� : ");
		String new_category = sc.next().trim();
		System.out.print("���ο� ������ �Է����ּ��� : ");
		String new_title = sc.next().trim();
		System.out.println("���ο� ������ �Է����ּ��� : ");
		String new_desc = sc.next().trim();
		System.out.print("���ο� ������¥�� �Է����ּ��� (ex:2021/09/30) : ");
		String new_due_date = sc.next().trim();
		
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date);
		t.setId(index);
		if(l.addItem(t) > 0)
			System.out.println("�����Ǿ����ϴ�.");
		
	}
	

	public static void listAll(TodoList l) {
		System.out.printf("[��ü ����� ����մϴ�]" + "�� %d ��", l.getCount());
		for (TodoItem item : l.getList()) {
			item.toTextString();
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		System.out.println("[��ü ����� �����մϴ�]");
		try {
			Writer w = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.println("���� �Ϸ�!");
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		System.out.println("[���� ������ �ҷ��ɴϴ�]");
		try {
			BufferedReader br = new BufferedReader(new FileReader("todolist.txt"));
			String oneline;
			while((oneline = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline, "##");
				String category = st.nextToken();
				String title = st.nextToken();
			    String desc = st.nextToken();
			    String due_date = st.nextToken();
			    String current_date = st.nextToken();
			    
				TodoItem t = new TodoItem(title, desc, category, due_date);
				t.setCurrent_date(current_date);
				l.addItem(t);
				
			}
			System.out.println("������ ������ �׸� �߰��Ǿ����ϴ�.");
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void search(TodoList l, String keyword) {
		int count = 0;
		for(TodoItem item : l.getList()) {
			if (item.getDesc().contains(keyword) || item.getTitle().contains(keyword)) {
				item.toTextString();
				count++;
			}
		}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.println(item + " ");
			count++;
			
		}
		System.out.println("�� " + count + "���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.");
		
	}
	
	public static void search_category(TodoList l, String cate) {
		int count = 0;
		for(TodoItem item : l.getList()) {
			if (item.getCategory().contains(cate)){
				item.toTextString();
				count++;
			}
		}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}

	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.println("[��ü ���, �� "+ l.getCount() + "��]");
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			item.toTextString();
		}
			
	}
}