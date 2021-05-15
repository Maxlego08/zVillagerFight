package fr.maxlego08.mobfighter.button.buttons;

import org.bukkit.inventory.ItemStack;

import fr.maxlego08.mobfighter.api.button.Button;
import fr.maxlego08.mobfighter.api.button.buttons.BetNumberButton;
import fr.maxlego08.mobfighter.api.enums.ButtonType;
import fr.maxlego08.mobfighter.api.enums.PlaceholderAction;
import fr.maxlego08.mobfighter.api.sound.SoundOption;

public class ZBetNumberButton extends ZPlaceholderButton implements BetNumberButton {

	private final long modifier;

	/**
	 * @param type
	 * @param itemStack
	 * @param slot
	 * @param permission
	 * @param message
	 * @param elseButton
	 * @param isPermanent
	 * @param action
	 * @param placeholder
	 * @param value
	 * @param needGlow
	 * @param sound
	 * @param modifier
	 */
	public ZBetNumberButton(ButtonType type, ItemStack itemStack, int slot, String permission, String message,
			Button elseButton, boolean isPermanent, PlaceholderAction action, String placeholder, String value,
			boolean needGlow, SoundOption sound, long modifier) {
		super(type, itemStack, slot, permission, message, elseButton, isPermanent, action, placeholder, value, needGlow,
				sound);
		this.modifier = modifier;
	}

	@Override
	public long getModifier() {
		return this.modifier;
	}
	
}
