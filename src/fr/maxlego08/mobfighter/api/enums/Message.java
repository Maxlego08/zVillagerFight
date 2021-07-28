package fr.maxlego08.mobfighter.api.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

public enum Message {

	PREFIX("§8[§a§l!§8] "),
	
	TELEPORT_MOVE("§cVous ne devez pas bouger !", false),
	TELEPORT_MESSAGE("§7Téléportatio dans §3%s §7secondes !", false),
	TELEPORT_ERROR("§cVous avez déjà une téléportation en cours !", false),
	TELEPORT_SUCCESS("§7Téléportation effectué !", false),
	
	INVENTORY_NULL("§cImpossible de trouver l'inventaire avec l'id §6%s§c.", false),
	INVENTORY_CLONE_NULL("§cLe clone de l'inventaire est null !", false),
	INVENTORY_OPEN_ERROR("§cUne erreur est survenu avec l'ouverture de l'inventaire §6%s§c.", false),
	INVENTORY_BUTTON_PREVIOUS("§f» §7Page précédente", false),
	INVENTORY_BUTTON_NEXT("§f» §7Page suivante", false),
	
	TIME_DAY("%02d jour(s) %02d heure(s) %02d minute(s) %02d seconde(s)"),
	TIME_HOUR("%02d heure(s) %02d minute(s) %02d seconde(s)"),
	TIME_HOUR_SIMPLE("%02d:%02d:%02d"),
	TIME_MINUTE("%02d minute(s) %02d seconde(s)"),
	TIME_SECOND("%02d seconde(s)"),
	
	COMMAND_SYNTAXE_ERROR("§cYou must execute the command like this§7: §2%command%"),
	COMMAND_NO_PERMISSION("§cYou don't have permission !"),
	COMMAND_NO_CONSOLE("§cOnly player can use this command."),
	COMMAND_NO_ARG("§cThis argument does not exist !"),
	COMMAND_SYNTAXE_HELP("§b» §f%syntaxe% §8- §7%description%"),
	
	ARENA_ALREADY_EXIST("§cThis arena already exists."),
	ARENA_DOESNT_EXIST("§cThis arena doesn't exists."),
	ARENA_CREATE("§7You have just created the §f%name% §farena."), 
	ARENA_DELETE("§7You have just deleted the §f%name% §farena."),
	ARENA_DELETE_ERROR_ACTIVE("§cYou cannot delete the arena at this time."),
	
	ARENA_LOCATION_FIRST("§7You just put the §ofirst§7 location for the §f%name%§7 arena."),
	ARENA_LOCATION_SECOND("§7You just put the §osecond§7 location for the §f%name%§7 arena."),
	ARENA_LOCATION_CENTER("§7You just put the §ocenter§7 location for the §f%name%§7 arena."), 
	ARENA_INVALID("§cYou cannot start a duel, the arena is invalid."), 
	
	ARENA_EMPTY("§cThere is no arena created."),
	ARENA_SHOW_HEADER("§7List of arenas:"),
	ARENA_SHOW_CONTENT("§f- §7%name% §8(%is_valid%§8)"),
	ARENA_SHOW_VALID("§avalid"),
	ARENA_SHOW_INVALID("§cinvalid"),
	
	COMMAND_DESCRIPTION_ARENA("Show arena commands"),
	COMMAND_DESCRIPTION_ARENA_CREATE("Create an arena"),
	COMMAND_DESCRIPTION_ARENA_DELETE("Delete an arena"),
	COMMAND_DESCRIPTION_ARENA_SHOW("Show arena list"),
	COMMAND_DESCRIPTION_ARENA_FIRST("Set arena first location"),
	COMMAND_DESCRIPTION_ARENA_SECOND("Set arena second location"),
	COMMAND_DESCRIPTION_ARENA_CENTER("Set arena center location"), 
	COMMAND_DESCRIPTION_VERSION("Show plugin version"), 
	COMMAND_DESCRIPTION_RELOAD("Reload configuration"), 
	COMMAND_DESCRIPTION_START("Start a duel"), 
	COMMAND_DESCRIPTION_STOP("Stop a duel"), 
	COMMAND_DESCRIPTION_BET("Show your bet"), 
	COMMAND_RELOAD("§aReload !!"),
	
	DUEL_START("§fStart of the duel between §a%first% §fand §2%second%§f. May the best man win!"),
	DUEL_WIN("§a%winner% §fjust won the fight against §b%looser%§f."),
	DUEL_START_ERROR_ALIVE("§cYou cannot have this entity used."),
	DUEL_START_ERROR_DUEL("§cArena is already use."),
	DUEL_STOP_ERROR_DUEL("§cArena is not use."),
	DUEL_STOP_SUCCESS("§aYou have just stop a duel."),
	DUEL_COOLDOWN("§fThe duel between §a%first% §fand §2%second% §fstart in §a%timer%§f."),
	
	BET_ALREADY_CREATE("§cYou have already created a bet."),
	BET_ALREADY_START("§cThe duel is already in progress, you can't create a bet now."),
	BET_NONE("§cYou cannot create a bet at this time."),
	BET_NOT_FOUND("§cImpossible to find the fighter §2%name%§c."),
	BET_NOT_ENOUGHT_MONEY("§cYou don't have enought money to do this."),
	BET_CREATE("§fYou have just bet §a%bet%%currency% §fon the fighter §2%fighter%."),
	BET_WIN("§fYou have just won §a%value%%currency% §fwith your bet §8(§7x%multiplicator%§8)"),
	BET_LOOSE("§fYou just lost your bet."),
	BET_HELP("§cYou did not create a bet.", "§7To create a bet you must do §f/zmf bet create <name> <bet>§7."),
	BET_SHOW("§fYour bet§8:", "§7Fighter§8: §a%fighter%", "§7Bet§8: §2%bet%", "§7Multiplicator§8: §a%multiplicator%"),
	BET_MIN("§cYou must put at least §f100$§c."),
	BET_MAX("§cYou can't put in more than §f$100,000§c."),
	BET_NOT_SELECTED("None"),
	BET_DUEL_ALREADY_START("§cThe duel has already begun."),
	BET_SELECT_EMPTY("§cYou must select a fighter."),
	BET_NAME_EMPTY("None"),
	BET_PRICE_EMPTY("0"),
	
	
	;

	private List<String> messages;
	private String message;
	private Map<String, Object> titles = new HashMap<>();
	private boolean use = true;
	private MessageType type = MessageType.TCHAT;

	private ItemStack itemStack;

	/**
	 * 
	 * @param message
	 */
	private Message(String message) {
		this.message = message;
		this.use = true;
	}

	/**
	 * 
	 * @param message
	 */
	private Message(MessageType type, String message) {
		this.message = message;
		this.use = true;
		this.type = type;
	}

	/**
	 * @param itemStack
	 */
	private Message(ItemStack itemStack) {
		this.itemStack = itemStack;
		this.type = MessageType.ITEMSTACK;
	}

	private Message(String title, String subTitle, int a, int b, int c) {
		this.use = true;
		this.titles.put("title", title);
		this.titles.put("subtitle", subTitle);
		this.titles.put("start", a);
		this.titles.put("time", b);
		this.titles.put("end", c);
		this.titles.put("isUse", true);
		this.type = MessageType.TITLE;
	}

	/**
	 * 
	 * @param message
	 */
	private Message(String... message) {
		this.messages = Arrays.asList(message);
		this.use = true;
	}

	/**
	 * 
	 * @param message
	 * @param use
	 */
	private Message(String message, boolean use) {
		this.message = message;
		this.use = use;
	}

	public String getMessage() {
		return message;
	}

	public String toMsg() {
		return message;
	}

	public String msg() {
		return message;
	}

	public boolean isUse() {
		return use;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getMessages() {
		return messages == null ? Arrays.asList(message) : messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public boolean isMessage() {
		return messages != null && messages.size() > 1;
	}

	public String getTitle() {
		return (String) titles.get("title");
	}

	public Map<String, Object> getTitles() {
		return titles;
	}

	public void setTitles(Map<String, Object> titles) {
		this.titles = titles;
	}

	public String getSubTitle() {
		return (String) titles.get("subtitle");
	}

	public boolean isTitle() {
		return titles.containsKey("title");
	}

	public int getStart() {
		return ((Number) titles.get("start")).intValue();
	}

	public int getEnd() {
		return ((Number) titles.get("end")).intValue();
	}

	public int getTime() {
		return ((Number) titles.get("time")).intValue();
	}

	public boolean isUseTitle() {
		return (boolean) titles.getOrDefault("isUse", "true");
	}

	public String replace(String a, String b) {
		return message.replace(a, b);
	}

	public MessageType getType() {
		return type;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public static Message form(String replace) {
		for (Message message : Message.values())
			if (message.name().equalsIgnoreCase(replace))
				return message;
		return null;
	}

}

