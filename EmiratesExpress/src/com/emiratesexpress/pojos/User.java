package com.emiratesexpress.pojos;

public class User {

	private String userId;
	private String name;
	private String nameArabic;
	private String username;
	private String password;
	private String email;
	private String status;
	private String userType;
	private String created;
	private String mobile;
	private String company;

	public User() {
		super();
	}

	public User(String userId, String name, String nameArabic, String username, String password, String email, String status, String userType, String created,
			String mobile, String company) {
		super();
		this.userId = userId;
		this.name = name;
		this.nameArabic = nameArabic;
		this.username = username;
		this.password = password;
		this.email = email;
		this.status = status;
		this.userType = userType;
		this.created = created;
		this.mobile = mobile;
		this.company = company;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameArabic() {
		return nameArabic;
	}

	public void setNameArabic(String nameArabic) {
		this.nameArabic = nameArabic;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
