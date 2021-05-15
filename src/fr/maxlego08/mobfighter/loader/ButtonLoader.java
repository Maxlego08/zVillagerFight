package fr.maxlego08.mobfighter.loader;

import java.util.List;
import java.util.Optional;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.IEconomy;
import fr.maxlego08.mobfighter.api.button.Button;
import fr.maxlego08.mobfighter.api.enums.BetSecletType;
import fr.maxlego08.mobfighter.api.enums.ButtonType;
import fr.maxlego08.mobfighter.api.enums.PlaceholderAction;
import fr.maxlego08.mobfighter.api.enums.XSound;
import fr.maxlego08.mobfighter.api.sound.SoundOption;
import fr.maxlego08.mobfighter.button.buttons.ZBackButton;
import fr.maxlego08.mobfighter.button.buttons.ZBetSelectButton;
import fr.maxlego08.mobfighter.button.buttons.ZHomeButton;
import fr.maxlego08.mobfighter.button.buttons.ZInventoryButton;
import fr.maxlego08.mobfighter.button.buttons.ZPerformButton;
import fr.maxlego08.mobfighter.button.buttons.ZPlaceholderButton;
import fr.maxlego08.mobfighter.button.buttons.ZSlotButton;
import fr.maxlego08.mobfighter.exceptions.ButtonCreateItemStackNullPointerException;
import fr.maxlego08.mobfighter.exceptions.ButtonTypeException;
import fr.maxlego08.mobfighter.sound.ZSoundOption;
import fr.maxlego08.mobfighter.zcore.utils.loader.ItemStackLoader;
import fr.maxlego08.mobfighter.zcore.utils.loader.Loader;

public class ButtonLoader implements Loader<Button> {

	private final ZMobPlugin plugin;
	// private final IEconomy economy;

	/**
	 * @param plugin
	 * @param economy
	 */
	public ButtonLoader(ZMobPlugin plugin, IEconomy economy) {
		super();
		this.plugin = plugin;
		// this.economy = economy;
	}

	@Override
	public Button load(YamlConfiguration configuration, String path, Object... args) {

		Loader<ItemStack> loaderItemStack = new ItemStackLoader();
		ButtonType type = null;
		try {
			type = ButtonType.from(configuration.getString(path + "type"), (String) args[0], path + "type");
		} catch (ButtonTypeException e1) {
			e1.printStackTrace();
		}

		int slot = configuration.getInt(path + "slot");
		boolean isPermanent = configuration.getBoolean(path + "isPermanent", false);
		boolean glowIfCheck = configuration.getBoolean(path + "glowIfCheck", false);
		slot = slot < 0 ? 0 : slot;

		String name = (String) args[0];

		ItemStack itemStack = loaderItemStack.load(configuration, path + "item.");

		if (itemStack == null && !type.isNeedItems())
			try {
				throw new ButtonCreateItemStackNullPointerException(
						"Cannot find the itemtack for the button " + path + "item in inventory " + name);
			} catch (ButtonCreateItemStackNullPointerException e) {
				e.printStackTrace();
			}

		// Permission
		String permission = configuration.getString(path + "permission", null);
		Button elseButton = null;
		String elseMessage = configuration.getString(path + "elseMessage", null);

		PlaceholderAction action = PlaceholderAction.from(configuration.getString(path + "action", null));
		String placeHolder = configuration.getString(path + "placeHolder", null);
		String value = configuration.getString(path + "value", "0.0");

		// Sound

		Optional<XSound> optional = XSound.matchXSound(configuration.getString(path + "sound", null));
		XSound xSound = optional.isPresent() ? optional.get() : null;

		SoundOption sound = null;
		if (optional.isPresent()) {
			float pitch = Float.valueOf(configuration.getString(path + "pitch", "1.0f"));
			float volume = Float.valueOf(configuration.getString(path + "volume", "1.0f"));
			sound = new ZSoundOption(xSound, pitch, volume);
		}

		if (configuration.contains(path + "else"))
			elseButton = load(configuration, path + "else.", (String) args[0], true);

		Button button = null;

		switch (type) {
		case BACK:
			return new ZBackButton(type, itemStack, slot, permission, elseMessage, elseButton, isPermanent, action,
					placeHolder, value, null, null, plugin, glowIfCheck, sound);
		case HOME:
			return new ZHomeButton(type, itemStack, slot, permission, elseMessage, elseButton, isPermanent, action,
					placeHolder, value, null, null, plugin, glowIfCheck, sound);
		case INVENTORY:
			String inventory = configuration.getString(path + "inventory");
			return new ZInventoryButton(type, itemStack, slot, permission, elseMessage, elseButton, isPermanent, action,
					placeHolder, value, inventory, null, plugin, glowIfCheck, sound);
		case PERFORM_COMMAND:
			List<String> commands = configuration.getStringList(path + "commands");
			List<String> consoleCommands = configuration.getStringList(path + "consoleCommands");
			boolean closeInventory = configuration.getBoolean(path + "closeInventory", false);
			return new ZPerformButton(type, itemStack, slot, permission, elseMessage, elseButton, isPermanent, action,
					placeHolder, value, commands, consoleCommands, closeInventory, glowIfCheck, sound);
		case NONE_SLOT:
			List<Integer> list = configuration.getIntegerList(path + "slots");
			return new ZSlotButton(type, itemStack, slot, permission, elseMessage, elseButton, isPermanent, action,
					placeHolder, value, list, glowIfCheck, sound);
		case BET_SELECT_TYPE: {
			BetSecletType betSecletType = BetSecletType
					.valueOf(configuration.getString(path + "betType").toUpperCase());
			List<String> selectedLore = configuration.getStringList(path + "selectedLore");
			return new ZBetSelectButton(type, itemStack, slot, permission, elseMessage, elseButton, isPermanent, action,
					placeHolder, value, glowIfCheck, sound, betSecletType, selectedLore);
		}
		case NONE:
		default:
			button = new ZPlaceholderButton(type, itemStack, slot, permission, elseMessage, elseButton, isPermanent,
					action, placeHolder, value, glowIfCheck, sound);
			break;
		}

		return button;

	}

	@Override
	public void save(Button object, YamlConfiguration configuration, String path) {
		// TODO Auto-generated method stub

	}

}
