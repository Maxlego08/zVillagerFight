package fr.maxlego08.mobfighter.button.buttons;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.maxlego08.mobfighter.api.button.Button;
import fr.maxlego08.mobfighter.api.button.buttons.BetSelectButton;
import fr.maxlego08.mobfighter.api.enums.BetSecletType;
import fr.maxlego08.mobfighter.api.enums.ButtonType;
import fr.maxlego08.mobfighter.api.enums.PlaceholderAction;
import fr.maxlego08.mobfighter.api.sound.SoundOption;

public class ZBetSelectButton extends ZPlaceholderButton implements BetSelectButton {

	private final BetSecletType type;
	private final List<String> selectedLore;
	private boolean selected;

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
	 * @param type2
	 */
	public ZBetSelectButton(ButtonType type, ItemStack itemStack, int slot, String permission, String message,
			Button elseButton, boolean isPermanent, PlaceholderAction action, String placeholder, String value,
			boolean needGlow, SoundOption sound, BetSecletType type2, List<String> selected) {
		super(type, itemStack, slot, permission, message, elseButton, isPermanent, action, placeholder, value, needGlow,
				sound);
		this.selectedLore = selected;
		this.type = type2;
	}

	@Override
	public boolean isSelected() {
		return this.selected;
	}

	@Override
	public void setSelect(boolean value) {
		this.selected = value;
	}

	@Override
	public BetSecletType getBetType() {
		return this.type;
	}

	@Override
	public List<String> getSelectedLore() {
		return this.selectedLore;
	}

	@Override
	public ItemStack getCustomItemStack(Player player) {
		ItemStack itemStack = super.getCustomItemStack(player);
		if (this.isSelected()) {
			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setLore(this.selectedLore);
			itemStack.setItemMeta(itemMeta);
			glow(itemStack);
		}
		return this.papi(itemStack, player);
	}

}
