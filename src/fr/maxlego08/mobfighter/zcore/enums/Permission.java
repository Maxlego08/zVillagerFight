package fr.maxlego08.mobfighter.zcore.enums;

public enum Permission {

	ZMOBFIGHTER_USE,
	ZMOBFIGHTER_BET,
	ZMOBFIGHTER_BET_CREATE,
	ZMOBFIGHTER_ARENA,
	ZMOBFIGHTER_ARENA_CREATE,
	ZMOBFIGHTER_ARENA_DELETE,
	ZMOBFIGHTER_ARENA_SHOW,
	ZMOBFIGHTER_ARENA_LOCATION_FIRST,
	ZMOBFIGHTER_ARENA_LOCATION_SECOND,
	ZMOBFIGHTER_ARENA_LOCATION_CENTER, 
	
	ZMOBFIGHTER_START,
	ZMOBFIGHTER_STOP, ZMOBFIGHTER_RELOAD,
	;

	private String permission;

	private Permission() {
		this.permission = this.name().toLowerCase().replace("_", ".");
	}

	public String getPermission() {
		return permission;
	}

}
