package com.herbfamily;

public class Member {
	
	private int id;
	private String name;
	private String nickname;
	
	public Member(int id, String name, String nickname) {
		this.id = id; 
		this.name = name;
		this.nickname = nickname;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNickname() {
		return nickname;
	}
}
