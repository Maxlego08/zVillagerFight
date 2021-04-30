package fr.maxlego08.mobfighter.path;

import org.bukkit.Location;

import fr.maxlego08.mobfighter.api.Fighter;
import fr.maxlego08.mobfighter.api.enums.EnumVersion;
import fr.maxlego08.mobfighter.api.path.Path;
import fr.maxlego08.mobfighter.api.path.PathManager;
import fr.maxlego08.mobfighter.path.nms.Path16R1;
import fr.maxlego08.mobfighter.path.nms.Path16R2;
import fr.maxlego08.mobfighter.path.nms.Path16R3;
import fr.maxlego08.mobfighter.path.nms.Path18R3;
import fr.maxlego08.mobfighter.zcore.utils.ItemDecoder;

public class ZPathManager implements PathManager {

	private final Path path;

	public ZPathManager() {
		double nms = ItemDecoder.getNMSVersion();
		if (nms == 1.8)
			this.path = new Path18R3();
		else if (nms == 1.16) {
			EnumVersion nmsVersion = ItemDecoder.getVersion();
			switch (nmsVersion) {
			case V_16_R1:
				this.path = new Path16R1();
				break;
			case V_16_R2:
				this.path = new Path16R2();
				break;
			case V_16_R3:
				this.path = new Path16R3();
				break;
			default:
				this.path = new Path16R3();
				break;
			}
		} else
			this.path = new Path16R3();
	}

	@Override
	public void setPathGoal(Fighter fighter, Location location) {
		this.path.setPath(fighter, location, fighter.getConfiguration().getSpeed());
	}

}
