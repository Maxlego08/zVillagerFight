package fr.maxlego08.mobfighter.api.attack;

public abstract class AttackJavaPlugin implements AttackPlugin {

	private AttackPluginVersion version;
	private boolean isEnable;

	public void init(AttackPluginVersion version) {
		this.version = version;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	@Override
	public AttackPluginVersion getVersion() {
		return version;
	}

	@Override
	public boolean isEnable() {
		return this.isEnable;
	}

}
