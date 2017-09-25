package samples.springbootApp.entity;

import java.util.Date;

public class Task {
	
	private Integer id;
	private String name;
	private String description;
	private Date date;
	private String time;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public void copyContentsFrom(Task from){
		this.setId(from.getId());
		this.setDate(from.getDate());
		this.setName(from.getName());
		this.setDescription(from.getDescription());
		this.setTime(from.getTime());
	}
	

}
