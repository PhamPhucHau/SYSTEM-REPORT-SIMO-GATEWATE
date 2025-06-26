package com.org.shbvn.svbsimo.configure;

//@Component
//@ConfigurationProperties(prefix = "nfs.client.oms")
public class NFsSourceProperties {

	private String host;
	
	private String username;

	private String password;

	private String remotedirectory;

	private String remotebackupdirectory;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
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

	public String getRemotedirectory() {
		return remotedirectory;
	}

	public void setRemotedirectory(String remotedirectory) {
		this.remotedirectory = remotedirectory;
	}

	public String getRemotebackupdirectory() {
		return remotebackupdirectory;
	}

	public void setRemotebackupdirectory(String remotebackupdirectory) {
		this.remotebackupdirectory = remotebackupdirectory;
	}
	
	
}
