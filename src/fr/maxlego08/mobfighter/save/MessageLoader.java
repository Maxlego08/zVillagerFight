package fr.maxlego08.mobfighter.save;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.api.enums.MessageType;
import fr.maxlego08.mobfighter.zcore.utils.loader.ItemStackLoader;
import fr.maxlego08.mobfighter.zcore.utils.loader.Loader;
import fr.maxlego08.mobfighter.zcore.utils.storage.Persist;
import fr.maxlego08.mobfighter.zcore.utils.storage.Saveable;
import fr.maxlego08.mobfighter.zcore.utils.yaml.YamlUtils;

public class MessageLoader extends YamlUtils implements Saveable {

	public MessageLoader(JavaPlugin plugin) {
		super(plugin);
	}

	@Override
	public void save(Persist persist) {

		if (persist != null)
			return;

		File file = new File(plugin.getDataFolder(), "messages.yml");
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		Loader<ItemStack> loader = new ItemStackLoader();

		YamlConfiguration configuration = getConfig(file);
		for (Message message : Message.values()) {

			if (!message.isUse())
				continue;

			String path = "messages." + message.name().toLowerCase().replace("_", ".");

			configuration.set(path + ".type", message.getType().name());

			if (message.getType().equals(MessageType.TCHAT) || message.getType().equals(MessageType.ACTION)) {
				
				if (message.isMessage()) {
					configuration.set(path + ".messages", colorReverse(message.getMessages()));
				} else {
					configuration.set(path + ".message", colorReverse(message.getMessage()));
				}

			} else if (message.getType().equals(MessageType.ITEMSTACK)) {

				loader.save(message.getItemStack(), configuration, path + ".itemstack.");

			} else if (message.getType().equals(MessageType.TITLE)) {

				configuration.set(path + ".title", colorReverse(message.getTitle()));
				configuration.set(path + ".subtitle", colorReverse(message.getSubTitle()));
				configuration.set(path + ".fadeInTime", message.getStart());
				configuration.set(path + ".showTime", message.getTime());
				configuration.set(path + ".fadeOutTime", message.getEnd());

			}

		}

		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void load(Persist persist) {

		File file = new File(plugin.getDataFolder(), "messages.yml");
		if (!file.exists()) {
			this.save(null);
			return;
		}

		YamlConfiguration configuration = getConfig(file);

		for (String key : configuration.getConfigurationSection("messages.").getKeys(false)) {

			loadMessage(configuration, "messages." + key);

		}

		// Pour avoir directs les news paramètres
		this.save(null);
	}

	/**
	 * 
	 * @param configuration
	 * @param key
	 */
	private void loadMessage(YamlConfiguration configuration, String key) {

		if (configuration.contains(key + ".type")) {

			MessageType messageType = MessageType.valueOf(configuration.getString(key + ".type").toUpperCase());

			String keys = key.substring("messages.".length(), key.length());

			Message enumMessage = Message.form(keys.toUpperCase().replace(".", "_"));

			if (enumMessage == null)
				return;

			enumMessage.setType(messageType);
			switch (messageType) {
			case ACTION: {
				String message = configuration.getString(key + ".message");
				enumMessage.setMessage(color(message));
				break;
			}
			case ITEMSTACK: {
				try {
					Loader<ItemStack> loader = new ItemStackLoader();
					ItemStack itemStack = loader.load(configuration, key + ".itemstack.");
					enumMessage.setItemStack(itemStack);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			case TCHAT: {
				if (configuration.contains(key + ".messages")) {
					List<String> messages = configuration.getStringList(key + ".messages");
					enumMessage.setMessages(color(messages));
				} else {
					String message = configuration.getString(key + ".message");
					enumMessage.setMessage(color(message));
				}
				break;
			}
			case TITLE: {
				String title = configuration.getString(key + ".title");
				String subtitle = configuration.getString(key + ".subtitle");
				int fadeInTime = configuration.getInt(key + ".fadeInTime");
				int showTime = configuration.getInt(key + ".showTime");
				int fadeOutTime = configuration.getInt(key + ".fadeOutTime");
				Map<String, Object> titles = new HashMap<String, Object>();
				titles.put("title", color(title));
				titles.put("subtitle", color(subtitle));
				titles.put("start", fadeInTime);
				titles.put("time", showTime);
				titles.put("end", fadeOutTime);
				titles.put("isUse", true);
				enumMessage.setTitles(titles);
				break;
			}
			default:
				break;
			}

			return;
		}

		for (String newKey : configuration.getConfigurationSection(key + ".").getKeys(false))
			loadMessage(configuration, key + "." + newKey);
	}

}
