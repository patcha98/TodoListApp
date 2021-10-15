package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		Menu.displaymenu();
		
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
			
			case "save":
				TodoUtil.saveList(l, "todolist.txt");
				break;
				
			case "load":
				TodoUtil.loadList(l, "todolist.txt");
				break;

			case "ls_name_asc":
				System.out.println("��������� �����Ͽ����ϴ�.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("���񿪼����� �����Ͽ����ϴ�.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("��¥������ �����Ͽ����ϴ�.");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("��¥�������� �����Ͽ����ϴ�.");
				TodoUtil.listAll(l, "due_date", 0);
				break;
			
			case "search":
				System.out.print("�˻��� Ű���带 �Է����ּ��� : ");
				Scanner sc1 = new Scanner(System.in);
				String keyword = sc1.nextLine();
				TodoUtil.search(l,keyword);
				break;
			
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
			
			case "search_category":
				System.out.print("�˻��� Ű���带 �Է����ּ��� : ");
				Scanner sc2 = new Scanner(System.in);
				String cate = sc2.nextLine();
				TodoUtil.search_category(l, cate);
				break;
			
			case "multi_del":
				System.out.print("������ �������� ��ȣ�� �Է����ּ��� (ex:1,4,6,7) : ");
				Scanner sc3 = new Scanner(System.in);
				String Mdel = sc3.nextLine();
				String[] Ddel = Mdel.split(",");
				TodoUtil.multiDeleteItem(l, Ddel);

				break;
			
			case "multi_add":
	
				TodoUtil.multiAddItem(l);

				break;
				
				
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				quit = true;
				break;

			default:
				System.out.println("�Է��� ��ɾ Ʋ�Ƚ��ϴ� �ٽ� �Է����ּ���. (����-help)");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
	}
}