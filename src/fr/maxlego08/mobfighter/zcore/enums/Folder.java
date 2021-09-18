package fr.maxlego08.mobfighter.zcore.enums;

public enum Folder {

	UTILS,
	ADDONS,
	
	;
	

	public String toFolder(){
		return name().toLowerCase();
	}
	
}
