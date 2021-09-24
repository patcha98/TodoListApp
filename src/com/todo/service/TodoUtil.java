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
				+ "[항목을 추가합니다]\n"
				+ "제목을 입력해주세요 : ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("중복된 제목이 존재합니다.");
			return;
		}
		System.out.print("카테고리을 입력해주세요 : ");
		category = sc.next();
		
		System.out.print("내용을 입력해주세요 : ");
		desc = sc.next();
		
		System.out.print("마감 날짜을 입력해주세요 (ex:2021/09/30) : ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		list.addItem(t);
		System.out.println("항목이 추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		int i = 0;
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "[항목을 삭제합니다]\n"
				+ "삭제할 항목의 번호를 입력해주세요 : ");
		int index = sc.nextInt();

		for (TodoItem item : l.getList()) {
			i ++;
			if (i == index) {
				l.deleteItem(item);
				System.out.println("항목이 삭제되었습니다.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		int i = 0;
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "[항목을 수정합니다]\n"
				+ "수정할 항목의 번호를 입력해주세요 : ");
		int index = sc.nextInt();
		
		for (TodoItem item : l.getList()) {
			i ++;
			if (i == index) {
				
				System.out.print("새로운 카테고리를 입력해주세요 : ");
				String new_category = sc.next().trim();

				System.out.print("새로운 제목을 입력해주세요 : ");
				String new_title = sc.next().trim();
					if (l.isDuplicate(new_title)) {
						System.out.println("중복되는 제목이 존재합니다.");
						return;
				}
				
				System.out.println("새로운 내용을 입력해주세요 : ");
				String new_description = sc.next().trim();
				
				System.out.print("새로운 마감날짜를 입력해주세요 (ex:2021/09/30) : ");
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
		System.out.println("[전체 목록을 출력합니다]" + "총 " + i + "개");
		for (TodoItem item : l.getList()) {
			count ++;
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String now = transFormat.format(item.getCurrent_date());
			System.out.println(count + ". [" + item.getCategory() + "] " + "제목: " + item.getTitle() + " - " + "내용 : " + item.getDesc() + " - " + "마감날짜 : " + item.getDue_date() + " - " + "생성날짜 : " + now);
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		System.out.println("[전체 목록을 저장합니다]");
		try {
			Writer w = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.println("저장 완료!");
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		System.out.println("[파일 내용을 불러옵니다]");
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
			System.out.println("파일의 내용이 항목에 추가되었습니다.");
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
				+ "[키워드로 제목 또는 내용을 검색합니다.]\n"
				+ "검색할 키워드를 입력해주세요 : ");
		String search = sc.next();

		for (TodoItem item : l.getList()) {
			
			if (item.getTitle().contains(search) || item.getDesc().contains(search)) {
				count ++;
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String now = transFormat.format(item.getCurrent_date());
				System.out.println(count + ". [" + item.getCategory() + "] " + "제목: " + item.getTitle() + " - " + "내용 : " + item.getDesc() + " - " + "마감날짜 : " + item.getDue_date() + " - " + "생성날짜 : " + now);
			}
		}
		System.out.println("총 " + count + " 개의 결과가 존재합니다.");
	}
	
	public static void search_category(TodoList l) {
		int count = 0;
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "[키워드로 카테고리를 검색합니다.]\n"
				+ "검색할 키워드를 입력해주세요 : ");
		String search = sc.next();

		for (TodoItem item : l.getList()) {
			if (item.getCategory().contains(search)) {
				count ++;
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String now = transFormat.format(item.getCurrent_date());
				System.out.println(count + ". [" + item.getCategory() + "] " + "제목: " + item.getTitle() + " - " + "내용 : " + item.getDesc() + " - " + "마감날짜 : " + item.getDue_date() + " - " + "생성날짜 : " + now);
			}
		}
		System.out.println("총 " + count + " 개의 결과가 존재합니다.");
	}
}
