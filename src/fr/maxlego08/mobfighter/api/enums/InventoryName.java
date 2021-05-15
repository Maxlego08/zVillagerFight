package fr.maxlego08.mobfighter.api.enums;

public enum InventoryName {

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
