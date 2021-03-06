package fr.maxlego08.mobfighter.api.enums;

public enum EnumVersion {

	V_1_7_10,

	V_16_R1, V_16_R2, V_16_R3,

	V_13_R1, V_13_R2,

	UNKOWN

	;

	public static EnumVersion getVersion(double version, String subVersion) {

		if (version == 1.16) {
			if (subVersion.equalsIgnoreCase("R1"))
				return V_16_R1;
			else if (subVersion.equalsIgnoreCase("R2"))
				return V_16_R2;
			else if (subVersion.equalsIgnoreCase("R3"))
				return V_16_R3;
		} else if (version == 1.13) {
			if (subVersion.equalsIgnoreCase("R1"))
				return V_13_R1;
			if (subVersion.equalsIgnoreCase("R2"))
				return V_13_R2;
		}

		return EnumVersion.UNKOWN;
	}

}
