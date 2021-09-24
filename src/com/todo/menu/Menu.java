package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("== ToDoList 관리 명령어 목록 ==");
        System.out.println("add <- 새로운 항목 추가");
        System.out.println("del <- 기존 항목 선택 삭제");
        System.out.println("edit <- 기존 항목 선택 수정");
        System.out.println("ls <- 전체 목록 출력");
        System.out.println("ls_name_asc <- 목록을 제목순으로 정렬");
        System.out.println("ls_name_desc <- 목록을 제목역순으로 정렬");
        System.out.println("ls_date <- 목록을 날짜순으로 정렬");
        System.out.println("ls_date_desc <- 목록을 날짜순으로 정렬");
        System.out.println("save <- 목록을 .txt 파일로 저장합니다");
        System.out.println("load <- 저장된 .txt 파일의 내용을 불러옵니다");
        System.out.println("search <- 키워드와 일치하는 제목 또는 내용을 가진 목록을 표시합니다.");
        System.out.println("search_category <- 키워드와 일치하는 카테고리를 가진 목록을 표시합니다.");
        System.out.println("exit <- 프로그램 종료");
    }
    public static void prompt()
    {
    	System.out.print("\nCommend : ");
    }
}
