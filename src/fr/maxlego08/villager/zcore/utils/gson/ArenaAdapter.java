package fr.maxlego08.villager.zcore.utils.gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import fr.maxlego08.villager.ZArena;
import fr.maxlego08.villager.api.Arena;
import fr.maxlego08.villager.zcore.ZPlugin;

public class ArenaAdapter extends TypeAdapter<Arena> {

	private static Type seriType = new TypeToken<Map<String, Object>>() {
	}.getType();

	private static String NAME = "name";
	private static String CENTER = "center";
	private static String FIRST = "first";
	private static String SECOND = "second";

	public ArenaAdapter() {
	}

	@Override
	public void write(JsonWriter jsonWriter, Arena arena) throws IOException {
		if (arena == null) {
			jsonWriter.nullValue();
			return;
		}
		jsonWriter.value(getRaw(arena));
	}

	@Override
	public Arena read(JsonReader jsonReader) throws IOException {
		if (jsonReader.peek() == JsonToken.NULL) {
			jsonReader.nextNull();
			return null;
		}
		return fromRaw(jsonReader.nextString());
	}

	private String getRaw(Arena arena) {
		Map<String, Object> serial = new HashMap<String, Object>();
		serial.put(NAME, arena.getName());
		serial.put(FIRST, changeLocationToStringEye(arena.getFirstLocation()));
		serial.put(SECOND, changeLocationToStringEye(arena.getSecondLocation()));
		serial.put(CENTER, changeLocationToStringEye(arena.getCenterLocation()));
		return ZPlugin.z().getGson().toJson(serial);
	}

	private Arena fromRaw(String raw) {
		Map<String, Object> keys = ZPlugin.z().getGson().fromJson(raw, seriType);
		String name = (String) keys.get(NAME);
		String center = (String) keys.get(CENTER);
		String first = (String) keys.get(FIRST);
		String second = (String) keys.get(SECOND);
		return new ZArena(name, changeStringLocationToLocationEye(first), changeStringLocationToLocationEye(second),
				changeStringLocationToLocationEye(center));
	}

	/**
	 * @param location
	 *            as string
	 * @return string as locaiton
	 */
	protected Location changeStringLocationToLocationEye(String s) {
		String[] a = s.split(",");
		World w = Bukkit.getServer().getWorld(a[0]);
		float x = Float.parseFloat(a[1]);
		float y = Float.parseFloat(a[2]);
		float z = Float.parseFloat(a[3]);
		if (a.length == 6) {
			float yaw = Float.parseFloat(a[4]);
			float pitch = Float.parseFloat(a[5]);
			return new Location(w, x, y, z, yaw, pitch);
		}
		return new Location(w, x, y, z);
	}

	/**
	 * @param location
	 * @return location as String
	 */
	protected String changeLocationToStringEye(Location location) {
		String ret = location.getWorld().getName() + "," + location.getBlockX() + "," + location.getBlockY() + ","
				+ location.getBlockZ() + "," + location.getYaw() + "," + location.getPitch();
		return ret;
	}

}
