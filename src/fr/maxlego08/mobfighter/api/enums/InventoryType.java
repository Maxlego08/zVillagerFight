package fr.maxlego08.mobfighter.api.enums;

public enum InventoryType {

	DEFAULT,
	BET,
	BET_CREATE,

	;

	/**
	 * 
	 * @param string
	 * @return
	 */
	public static InventoryType form(String string) {
		if (string == null)
			return DEFAULT;
		try {
			InventoryType type = valueOf(string.toUpperCase());
			return type == null ? DEFAULT : type;
		} catch (Exception e) {
			return DEFAULT;
		}
	}

	public boolean isDefault() {
		return true;
	}

}
