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
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�׸��� �߰��մϴ�]\n"
				+ "������ �Է����ּ��� : ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("�ߺ��� ������ �����մϴ�.");
			return;
		}
		
		System.out.print("������ �Է����ּ��� : ");
		desc = sc.next();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("�׸��� �߰��Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "[�׸��� �����մϴ�]\n"
				+ "������ �׸��� ������ �Է����ּ��� : ");
		String title = sc.next();
		
		
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("�׸��� �����Ǿ����ϴ�.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�׸��� �����մϴ�]\n"
				+ "������ �׸��� ������ �Է����ּ��� : ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("�Է��� ������ �������� �ʽ��ϴ�.");
			return;
		}

		System.out.print("���ο� ������ �Է����ּ��� : ");
		
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("�ߺ��Ǵ� ������ �����մϴ�.");
			return;
		}
		
		System.out.println("���ο� ������ �Է����ּ��� : ");
		String new_description = sc.next().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("�����Ǿ����ϴ�.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[��ü ����� ����մϴ�]");
		for (TodoItem item : l.getList()) {
			
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String now = transFormat.format(item.getCurrent_date());
			System.out.println("����: " + item.getTitle() + "  ����:  " + item.getDesc() + " - " + now);
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
				String title = st.nextToken();
			    String desc = st.nextToken();
			    String from = st.nextToken();
			    SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			    Date current_date = fm.parse(from);
			    
				TodoItem t = new TodoItem(title, desc);
				t.setCurrent_date(current_date);
				l.addItem(t);
				
			}
			System.out.println("������ ������ �׸� �߰��Ǿ����ϴ�.");
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
