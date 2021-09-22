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
        System.out.println("saveList <- 목록을 날짜순으로 정렬");

        System.out.println("exit <- 프로그램 종료");
    }
    public static void prompt()
    {
    	System.out.print("\nCommend : ");
    }
}
