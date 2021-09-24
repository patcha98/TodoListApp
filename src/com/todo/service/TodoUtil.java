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
		list.addItem(t);
		System.out.println("�׸��� �߰��Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		int i = 0;
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "[�׸��� �����մϴ�]\n"
				+ "������ �׸��� ��ȣ�� �Է����ּ��� : ");
		int index = sc.nextInt();

		for (TodoItem item : l.getList()) {
			i ++;
			if (i == index) {
				l.deleteItem(item);
				System.out.println("�׸��� �����Ǿ����ϴ�.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		int i = 0;
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "[�׸��� �����մϴ�]\n"
				+ "������ �׸��� ��ȣ�� �Է����ּ��� : ");
		int index = sc.nextInt();
		
		for (TodoItem item : l.getList()) {
			i ++;
			if (i == index) {
				
				System.out.print("���ο� ī�װ��� �Է����ּ��� : ");
				String new_category = sc.next().trim();

				System.out.print("���ο� ������ �Է����ּ��� : ");
				String new_title = sc.next().trim();
					if (l.isDuplicate(new_title)) {
						System.out.println("�ߺ��Ǵ� ������ �����մϴ�.");
						return;
				}
				
				System.out.println("���ο� ������ �Է����ּ��� : ");
				String new_description = sc.next().trim();
				
				System.out.print("���ο� ������¥�� �Է����ּ��� (ex:2021/09/30) : ");
				String new_due_date = sc.next().trim();

				item.setTitle(new_title);
				item.setDesc(new_description);
				item.setCategory(new_category);
				item.setDue_date(new_due_date);

				break;
			}

				
		}
	}
	

	public static void listAll(TodoList l) {
		int i = 0;
		int count = 0;
		for (TodoItem item : l.getList()) {
			i ++;
		}
		System.out.println("[��ü ����� ����մϴ�]" + "�� " + i + "��");
		for (TodoItem item : l.getList()) {
			count ++;
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String now = transFormat.format(item.getCurrent_date());
			System.out.println(count + ". [" + item.getCategory() + "] " + "����: " + item.getTitle() + " - " + "���� : " + item.getDesc() + " - " + "������¥ : " + item.getDue_date() + " - " + "������¥ : " + now);
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
			    String from = st.nextToken();
			    SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			    Date current_date = fm.parse(from);
			    
				TodoItem t = new TodoItem(title, desc, category, due_date);
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
	
	public static void search(TodoList l) {
		int count = 0;
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "[Ű����� ���� �Ǵ� ������ �˻��մϴ�.]\n"
				+ "�˻��� Ű���带 �Է����ּ��� : ");
		String search = sc.next();

		for (TodoItem item : l.getList()) {
			
			if (item.getTitle().contains(search) || item.getDesc().contains(search)) {
				count ++;
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String now = transFormat.format(item.getCurrent_date());
				System.out.println(count + ". [" + item.getCategory() + "] " + "����: " + item.getTitle() + " - " + "���� : " + item.getDesc() + " - " + "������¥ : " + item.getDue_date() + " - " + "������¥ : " + now);
			}
		}
		System.out.println("�� " + count + " ���� ����� �����մϴ�.");
	}
	
	public static void search_category(TodoList l) {
		int count = 0;
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "[Ű����� ī�װ��� �˻��մϴ�.]\n"
				+ "�˻��� Ű���带 �Է����ּ��� : ");
		String search = sc.next();

		for (TodoItem item : l.getList()) {
			if (item.getCategory().contains(search)) {
				count ++;
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String now = transFormat.format(item.getCurrent_date());
				System.out.println(count + ". [" + item.getCategory() + "] " + "����: " + item.getTitle() + " - " + "���� : " + item.getDesc() + " - " + "������¥ : " + item.getDue_date() + " - " + "������¥ : " + now);
			}
		}
		System.out.println("�� " + count + " ���� ����� �����մϴ�.");
	}
}
