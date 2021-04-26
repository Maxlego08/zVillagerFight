package fr.maxlego08.mobfighter.zcore.enums;

public enum Permission {

	ZVILLAGER_USE,
	ZVILLAGER_ARENA,
	ZVILLAGER_ARENA_CREATE,
	ZVILLAGER_ARENA_DELETE,
	ZVILLAGER_ARENA_SHOW,
	ZVILLAGER_ARENA_LOCATION_FIRST,
	ZVILLAGER_ARENA_LOCATION_SECOND,
	ZVILLAGER_ARENA_LOCATION_CENTER, 
	ZVILLAGER_START,
	
	;

	private String permission;

	private Permission() {
		this.permission = this.name().toLowerCase().replace("_", ".");
	}

	public String getPermission() {
		return permission;
	}

}
