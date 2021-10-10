package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private int id;

    public TodoItem(String title, String desc, String categoty, String due_date){
        this.title=title;
        this.desc=desc;
        this.category = categoty;
        this.due_date=due_date;
    }
    
    public void TodoSavedItem(String title, String desc, String current_date, String category, String due_date){
        this.title=title;
        this.desc=desc;
        this.current_date = current_date;
        this.category = category;
        this.due_date=due_date;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {

    	return current_date;
    }

    public void setCurrent_date(String current_date2) {
        this.current_date = current_date2;
    }
    
    public String toSaveString() {
    	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String now = transFormat.format(current_date);
    	return  category + "##" +title + "##" + desc + "##" + due_date + "##" + now + "\n";
    }
    public void toTextString() {
    	System.out.println(id + ". [" + category + "] " + "제목: " + title + " - " + "내용 : " + desc + " - " + "마감날짜 : " + due_date + " - " + "생성날짜 : " + current_date);
    }
    
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	public void setId(int id) {
		this.id = id;
	}
}