package fr.maxlego08.mobfighter.api.button.buttons;

import java.util.List;

import fr.maxlego08.mobfighter.api.enums.BetSecletType;

public interface BetSelectButton extends PlaceholderButton {

	/**
	 * 
	 * @return
	 */
	boolean isSelected();
	
	/**
	 * 
	 * @param value
	 */
	void setSelect(boolean value);
	
	/**
	 * 
	 * @return bet type
	 */
	BetSecletType getBetType();
	
	/**
	 * 
	 * @return lore
	 */
	List<String> getSelectedLore();
	
}
