package com.todo.service;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
		String title = sc.next();
		
		System.out.print("\n"
				+ "[�׸��� �����մϴ�]\n"
				+ "������ �׸��� ������ �Է����ּ��� : ");
		
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
			System.out.println("�������� �ʴ� �����Դϴ�.");
			return;
		}
		
		System.out.println("�ߺ��Ǵ� ������ �����մϴ�.");
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
		try {
			Writer w = new FileWriter("output2.txt");
			w.write(l.toString());
			w.close();
			System.out.println("���� �Ϸ�!");
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
