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
				+ "[항목을 추가합니다]\n"
				+ "제목을 입력해주세요 : ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("중복된 제목이 존재합니다.");
			return;
		}
		
		System.out.print("내용을 입력해주세요 : ");
		desc = sc.next();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("항목이 추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title = sc.next();
		
		System.out.print("\n"
				+ "[항목을 삭제합니다]\n"
				+ "삭제할 항목의 제목을 입력해주세요 : ");
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("항목이 삭제되었습니다.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[항목을 수정합니다]\n"
				+ "수정할 항목의 제목을 입력해주세요 : ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("입력한 제목이 존재하지 않습니다.");
			return;
		}

		System.out.print("새로운 제목을 입력해주세요 : ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("존재하지 않는 제목입니다.");
			return;
		}
		
		System.out.println("중복되는 제목이 존재합니다.");
		String new_description = sc.next().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록을 출력합니다]");
		for (TodoItem item : l.getList()) {
			
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String now = transFormat.format(item.getCurrent_date());
			System.out.println("제목: " + item.getTitle() + "  내용:  " + item.getDesc() + " - " + now);
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter("output2.txt");
			w.write(l.toString());
			w.close();
			System.out.println("저장 완료!");
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
