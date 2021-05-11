package fr.maxlego08.mobfighter.zcore.utils.players;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/*
*  Util class for creating actionbars.
*  Compatibility 1.7 - 1.16
*
* Forked from: https://github.com/ConnorLinfoot/ActionBarAPI/blob/master/src/main/java/com/connorlinfoot/actionbarapi/ActionBarAPI.java
*
*/
public class ActionBar {

	private static String serverPackageVersion;
	private static boolean useOldMethods = false;
	private static boolean isSuperior1_16Ver = false;

	static {
		serverPackageVersion = Bukkit.getServer().getClass().getPackage().getName();
		serverPackageVersion = serverPackageVersion.substring(serverPackageVersion.lastIndexOf(".") + 1);

		if (serverPackageVersion.equalsIgnoreCase("v1_8_R1") || serverPackageVersion.startsWith("v1_7_")) {
			useOldMethods = true;
		}

		int ver = Integer.parseInt(serverPackageVersion.replace("v1_", "").replaceAll("_R\\d", ""));
		if (ver >= 16) {
			isSuperior1_16Ver = true;
		}
	}

	public static void sendActionBar(Player player, String message) {
		if (!player.isOnline()) {
			return;
		}
		try {
			Class<?> craftPlayerClass = Class
					.forName("org.bukkit.craftbukkit." + serverPackageVersion + ".entity.CraftPlayer");
			Object craftPlayer = craftPlayerClass.cast(player);
			Object packet;
			Class<?> packetPlayOutChatClass = Class
					.forName("net.minecraft.server." + serverPackageVersion + ".PacketPlayOutChat");
			Class<?> packetClass = Class.forName("net.minecraft.server." + serverPackageVersion + ".Packet");
			if (useOldMethods) {
				Class<?> chatSerializerClass = Class
						.forName("net.minecraft.server." + serverPackageVersion + ".ChatSerializer");
				Class<?> iChatBaseComponentClass = Class
						.forName("net.minecraft.server." + serverPackageVersion + ".IChatBaseComponent");
				Method m3 = chatSerializerClass.getDeclaredMethod("a", String.class);
				Object cbc = iChatBaseComponentClass
						.cast(m3.invoke(chatSerializerClass, "{\"text\": \"" + message + "\"}"));
				packet = packetPlayOutChatClass.getConstructor(new Class<?>[] { iChatBaseComponentClass, byte.class })
						.newInstance(cbc, (byte) 2);
			} else {
				Class<?> chatComponentTextClass = Class
						.forName("net.minecraft.server." + serverPackageVersion + ".ChatComponentText");
				Class<?> iChatBaseComponentClass = Class
						.forName("net.minecraft.server." + serverPackageVersion + ".IChatBaseComponent");
				try {
					Class<?> chatMessageTypeClass = Class
							.forName("net.minecraft.server." + serverPackageVersion + ".ChatMessageType");
					Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
					Object chatMessageType = null;
					for (Object obj : chatMessageTypes) {
						if (obj.toString().equals("GAME_INFO")) {
							chatMessageType = obj;
						}
					}
					Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[] { String.class })
							.newInstance(message);
					if (isSuperior1_16Ver) {
						packet = packetPlayOutChatClass
								.getConstructor(
										new Class<?>[] { iChatBaseComponentClass, chatMessageTypeClass, UUID.class })
								.newInstance(chatCompontentText, chatMessageType, player.getUniqueId());
					} else {
						packet = packetPlayOutChatClass
								.getConstructor(new Class<?>[] { iChatBaseComponentClass, chatMessageTypeClass })
								.newInstance(chatCompontentText, chatMessageType);
					}
				} catch (ClassNotFoundException cnfe) {
					Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[] { String.class })
							.newInstance(message);
					packet = packetPlayOutChatClass
							.getConstructor(new Class<?>[] { iChatBaseComponentClass, byte.class })
							.newInstance(chatCompontentText, (byte) 2);
				}
			}
			Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
			Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
			Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
			Object playerConnection = playerConnectionField.get(craftPlayerHandle);
			Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
			sendPacketMethod.invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendActionBar(final Player player, final String message, int duration) {
		sendActionBar(player, message);
	}

	public static void sendActionBarToAllPlayers(String message) {
		sendActionBarToAllPlayers(message, -1);
	}

	public static void sendActionBarToAllPlayers(String message, int duration) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			sendActionBar(p, message, duration);
		}
	}
}