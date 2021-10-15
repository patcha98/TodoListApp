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
				+ "[항목을 추가합니다]\n"
				+ "제목을 입력해주세요 : ");
		
		title = sc.next();
		
		if (list.isDuplicate(title)) {
			System.out.printf("중복된 제목이 존재합니다.");
			return;
		}
		
		System.out.print("작성자 명을 입력해주세요 : ");
		name = sc.next();
		
		System.out.print("내용을 입력해주세요 : ");
		desc = sc.next();
		
		System.out.print("카테고리을 입력해주세요 : ");
		category = sc.next();
		
		System.out.print("마감 날짜을 입력해주세요 (ex:2021/09/30) : ");
		due_date = sc.next();
		
		System.out.print("이 일을 완료 하였나요?(yes : 1 , no : 0) : ");
		check = sc.nextInt();
		
		System.out.print("이 일은 필수적으로 완료해야하나요?(yes : 1 , no : 0) : ");
		manda = sc.nextInt();
		
		TodoItem t = new TodoItem(title, desc, category, due_date, check, manda, name);
		
		if(list.addItem(t)>0)
			System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[항목을 삭제합니다]\n"
				+ "삭제할 항목의 번호를 입력해주세요 : ");
		int index = sc.nextInt();

		if(l.deleteItem(index)>0)
			System.out.println("삭제되었습니다.");
	}


	public static void updateItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "[항목을 수정합니다]\n"
				+ "수정할 항목의 번호를 입력해주세요 : ");
		int index = sc.nextInt();
		System.out.print("새로운 작성자 명을 입력해주세요 : ");
		String new_name = sc.next().trim();
		System.out.print("새로운 카테고리를 입력해주세요 : ");
		String new_category = sc.next().trim();
		System.out.print("새로운 제목을 입력해주세요 : ");
		String new_title = sc.next().trim();
		System.out.println("새로운 내용을 입력해주세요 : ");
		String new_desc = sc.next().trim();
		System.out.print("새로운 마감날짜를 입력해주세요 (ex:2021/09/30) : ");
		String new_due_date = sc.next().trim();
		System.out.print("이 일을 완료 하였나요?(yes : 1 , no : 0) : ");
		int new_check = sc.nextInt();
		System.out.print("이 일을 필수적으로 완료해야하나요?(yes : 1 , no : 0) : ");
		int new_manda = sc.nextInt();
		
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date, new_check, new_manda, new_name);
		t.setId(index);
		if(l.addItem(t) > 0)
			System.out.println("수정되었습니다.");
		
	}
	

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록을 출력합니다]" + "총 %d 개" + "\n", l.getCount());
		for (TodoItem item : l.getList()) {
			item.toTextString();
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
			System.out.println("파일의 내용이 항목에 추가되었습니다.");
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
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.println(item + " ");
			count++;
			
		}
		System.out.println("총 " + count + "개의 카테고리가 등록되어 있습니다.");
		
	}
	
	public static void search_category(TodoList l, String cate) {
		int count = 0;
		for(TodoItem item : l.getList()) {
			if (item.getCategory().contains(cate)){
				item.toTextString();
				count++;
			}
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}

	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.println("[전체 목록, 총 "+ l.getCount() + "개]");
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			item.toTextString();
		}
			
	}

	public static void multiDeleteItem(TodoList l, String[] ddel) {
		// TODO Auto-generated method stub
		

		if(l.Multi_deleteItem(ddel)>0)
			System.out.println("삭제되었습니다.");
	}
	
	public static void multiAddItem(TodoList list) {
		// TODO Auto-generated method stub
		String title, desc, category, due_date, name;
		int check, manda;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[항목을 추가합니다]\n"
				+ "제목을 입력해주세요 : ");
		
		title = sc.next();
		
		if (list.isDuplicate(title)) {
			System.out.printf("중복된 제목이 존재합니다.");
			return;
		}
		
		System.out.print("작성자 명을 입력해주세요 : ");
		name = sc.next();
		
		System.out.print("내용을 입력해주세요 : ");
		desc = sc.next();
		
		System.out.print("카테고리을 입력해주세요 : ");
		category = sc.next();
		
		System.out.print("첫 주차 마감 날짜을 입력해주세요 (ex:2021/09/30) : ");
		due_date = sc.next();
		
		System.out.print("몇 주간 반복할지 입력해주세요 (ex:3) : ");
		int weeks = sc.nextInt();
		System.out.print("이 일을 완료 하였나요?(yes : 1 , no : 0) : ");
		check = sc.nextInt();
		
		System.out.print("이 일은 필수적으로 완료해야하나요?(yes : 1 , no : 0) : ");
		manda = sc.nextInt();
		int i = 1;
		for(int j = weeks; j > 0; j = j - 1) {
			
			
			TodoItem t = new TodoItem(title, desc, category, due_date, check, manda, name);
			
			if(list.addItem(t)>0)
				System.out.println(i+"주차 아이템이 추가되었습니다.");
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