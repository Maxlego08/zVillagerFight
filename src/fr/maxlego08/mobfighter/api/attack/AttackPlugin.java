package fr.maxlego08.mobfighter.api.attack;

public interface AttackPlugin {

	AttackPluginVersion getVersion();	
	
	boolean isEnable();
	
	void onEnable();
	
	void onDisable();
	
}
