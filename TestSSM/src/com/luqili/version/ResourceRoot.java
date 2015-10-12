package com.luqili.version;

public class ResourceRoot   {
	private String version;
	private String resourceRoot;
	public ResourceRoot(String version){
		this.version=version;
		this.resourceRoot="?v="+version;
	}
	public void init(){
		resourceRoot="?v="+version;
	}
	public String getResourceRoot() {
		return resourceRoot;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	

}
