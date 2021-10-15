package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date, name;
		int check, manda;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�׸��� �߰��մϴ�]\n"
				+ "������ �Է����ּ��� : ");
		
		title = sc.next();
		
		if (list.isDuplicate(title)) {
			System.out.printf("�ߺ��� ������ �����մϴ�.");
			return;
		}
		
		System.out.print("�ۼ��� ���� �Է����ּ��� : ");
		name = sc.next();
		
		System.out.print("������ �Է����ּ��� : ");
		desc = sc.next();
		
		System.out.print("ī�װ��� �Է����ּ��� : ");
		category = sc.next();
		
		System.out.print("���� ��¥�� �Է����ּ��� (ex:2021/09/30) : ");
		due_date = sc.next();
		
		System.out.print("�� ���� �Ϸ� �Ͽ�����?(yes : 1 , no : 0) : ");
		check = sc.nextInt();
		
		System.out.print("�� ���� �ʼ������� �Ϸ��ؾ��ϳ���?(yes : 1 , no : 0) : ");
		manda = sc.nextInt();
		
		TodoItem t = new TodoItem(title, desc, category, due_date, check, manda, name);
		
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
		System.out.print("���ο� �ۼ��� ���� �Է����ּ��� : ");
		String new_name = sc.next().trim();
		System.out.print("���ο� ī�װ��� �Է����ּ��� : ");
		String new_category = sc.next().trim();
		System.out.print("���ο� ������ �Է����ּ��� : ");
		String new_title = sc.next().trim();
		System.out.println("���ο� ������ �Է����ּ��� : ");
		String new_desc = sc.next().trim();
		System.out.print("���ο� ������¥�� �Է����ּ��� (ex:2021/09/30) : ");
		String new_due_date = sc.next().trim();
		System.out.print("�� ���� �Ϸ� �Ͽ�����?(yes : 1 , no : 0) : ");
		int new_check = sc.nextInt();
		System.out.print("�� ���� �ʼ������� �Ϸ��ؾ��ϳ���?(yes : 1 , no : 0) : ");
		int new_manda = sc.nextInt();
		
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date, new_check, new_manda, new_name);
		t.setId(index);
		if(l.addItem(t) > 0)
			System.out.println("�����Ǿ����ϴ�.");
		
	}
	

	public static void listAll(TodoList l) {
		System.out.printf("[��ü ����� ����մϴ�]" + "�� %d ��" + "\n", l.getCount());
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
			    String check1 = st.nextToken();
			    int check2 = Integer.parseInt(check1);
			    String manda1 = st.nextToken();
			    int manda2 = Integer.parseInt(manda1);
			    String name = st.nextToken();
			    
			    System.out.println(current_date);
			    
				TodoItem t = new TodoItem(title, desc, category, due_date, check2, manda2, name);
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

	public static void multiDeleteItem(TodoList l, String[] ddel) {
		// TODO Auto-generated method stub
		

		if(l.Multi_deleteItem(ddel)>0)
			System.out.println("�����Ǿ����ϴ�.");
	}
	
	public static void multiAddItem(TodoList list) {
		// TODO Auto-generated method stub
		String title, desc, category, due_date, name;
		int check, manda;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�׸��� �߰��մϴ�]\n"
				+ "������ �Է����ּ��� : ");
		
		title = sc.next();
		
		if (list.isDuplicate(title)) {
			System.out.printf("�ߺ��� ������ �����մϴ�.");
			return;
		}
		
		System.out.print("�ۼ��� ���� �Է����ּ��� : ");
		name = sc.next();
		
		System.out.print("������ �Է����ּ��� : ");
		desc = sc.next();
		
		System.out.print("ī�װ��� �Է����ּ��� : ");
		category = sc.next();
		
		System.out.print("ù ���� ���� ��¥�� �Է����ּ��� (ex:2021/09/30) : ");
		due_date = sc.next();
		
		System.out.print("�� �ְ� �ݺ����� �Է����ּ��� (ex:3) : ");
		int weeks = sc.nextInt();
		System.out.print("�� ���� �Ϸ� �Ͽ�����?(yes : 1 , no : 0) : ");
		check = sc.nextInt();
		
		System.out.print("�� ���� �ʼ������� �Ϸ��ؾ��ϳ���?(yes : 1 , no : 0) : ");
		manda = sc.nextInt();
		int i = 1;
		for(int j = weeks; j > 0; j = j - 1) {
			
			
			TodoItem t = new TodoItem(title, desc, category, due_date, check, manda, name);
			
			if(list.addItem(t)>0)
				System.out.println(i+"���� �������� �߰��Ǿ����ϴ�.");
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date to = null;
			try {
				to = transFormat.parse(due_date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
	        cal.setTime(to);
	        cal.add(Calendar.DATE, + 7);
	        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	        due_date = df.format(cal.getTime());
	        i++;
	        
		}
		
		
		
		
		
		
	}
	
}