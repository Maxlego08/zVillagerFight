package fr.maxlego08.mobfighter.api;

public interface Duel {

	/**
	 * 
	 * @return
	 */
	Arena getArena();

	/**
	 * 
	 * @return
	 */
	Fighter getFirstFighter();
	
	/**
	 * 
	 * @return
	 */
	Fighter getSecondFighter();
	
	/**
	 * 
	 */
	void start();
	
	/**
	 * 
	 */
	void update();
	
	/**
	 * Check if duel is valid
	 * 
	 * @return true if is valid
	 */
	boolean isValid();
	
	boolean isFinish();
	
}
