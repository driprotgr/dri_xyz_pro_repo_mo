package com.sachet.traveltracker.beans;



import com.sachet.traveltracker.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;


public class User {

	private String name;
	private String email;
	private String category;
	private Long id;
	private String mobile;
	private String password;
	private AccountType accountType;
	private String accountId;
	private boolean actFlag;
	private String otp;
	private String authToken;
	private String gcmIdentifierId;
	private String deviceId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public boolean getActFlag() {
		return actFlag;
	}
	public void setActFlag(boolean actFlag) {
		this.actFlag = actFlag;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getGcmIdentifierId() {
		return gcmIdentifierId;
	}
	public void setGcmIdentifierId(String gcmIdentifier) {
		this.gcmIdentifierId = gcmIdentifier;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}


    public static User fromJson(String json){

        if(json == null || json.isEmpty()){
            return null;
        }

        User user = new User();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);

            if(jsonObject.has("accountType"))
                user.setAccountType(AccountType.fromString(jsonObject.getString("accountType")));
            if(jsonObject.has("accountId"))
                user.setAccountId(jsonObject.getString("accountId"));
            if(jsonObject.has("actFlag"))
                user.setActFlag(jsonObject.getBoolean("actFlag"));
            if(jsonObject.has("authToken"))
                user.setAuthToken(jsonObject.getString("authToken"));
            if(jsonObject.has("category"))
                user.setCategory(jsonObject.getString("category"));
            if(jsonObject.has("deviceId"))
                user.setCategory(jsonObject.getString("deviceId"));
            if(jsonObject.has("name"))
                user.setName(jsonObject.getString("name"));
            if ( jsonObject.has("mobile"))
                user.setMobile(jsonObject.getString("mobile"));
            if(jsonObject.has("email"))
                user.setEmail(jsonObject.getString("email"));
            if(jsonObject.has("gcmIdentifierId"))
                user.setGcmIdentifierId(jsonObject.getString("gcmIdentifierId"));
            if(jsonObject.has("id"))
                user.setId(jsonObject.getLong("id"));
            if (jsonObject.has("password"))
                user.setPassword(jsonObject.getString("password"));
            if(jsonObject.has("otp"))
                user.setOtp(jsonObject.getString("otp"));

        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
        return user;
    }

	public JSONObject toJson(){
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("accountId", this.accountId);
			jsonObject.put("accountType", this.accountType.getValue());
			jsonObject.put("actFlag", this.actFlag);
			jsonObject.put("authToken", this.authToken);
			jsonObject.put("category", this.category);
			jsonObject.put("deviceId", Constants.DEVICE_ID);
			jsonObject.put("email", this.email);
			jsonObject.put("gcmIdentifierId", Constants.GCM_ID);//issue//temp fix
			jsonObject.put("id", this.id);
			jsonObject.put("name", this.name);
			jsonObject.put("otp", this.otp);
			jsonObject.put("password", this.password);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}

