package fr.maxlego08.mobfighter.api.enums;

public enum InventoryName {

	BET("bet"),
	BET_CREATE("bet_create"),
	
	;

	private final String name;

	/**
	 * @param name
	 */
	private InventoryName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
