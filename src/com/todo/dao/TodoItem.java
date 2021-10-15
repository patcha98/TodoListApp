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
    private int check;
    private int manda;
    private String name;

    public TodoItem(String title, String desc, String categoty, String due_date, int check, int manda, String name){
        this.title=title;
        this.desc=desc;
        this.category = categoty;
        this.due_date=due_date;
        this.check=check;
        this.manda = manda;
        this.name = name;
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

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    public String toSaveString() {
    	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String now = transFormat.format(current_date);
    	return  category + "##" +title + "##" + desc + "##" + due_date + "##" + now + "##" + check + "\n";
    }
    public void toTextString() {
    	if(check == 0) {
			System.out.print(id + "." +"완료[X]");
		}
		else {
			System.out.print(id + "." +"완료[O]");
		}
    	if(manda == 0) {
			System.out.print(" 필수여부[X]");
		}
		else {
			System.out.print(" 필수여부[O]");
		}
    	System.out.println(" 생성자 : " + name + " [" + category + "] " + "제목: " + title + " - " + "내용 : " + desc + " - " + "마감날짜 : " + due_date + " - " + "생성날짜 : " + current_date);
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

	public int getCheck() {
		return check;
	}

	public void setCheck(int check) {
		this.check = check;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public int getManda() {
		return manda;
	}

	public void setManda(int manda) {
		this.manda = manda;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}