package fr.maxlego08.mobfighter.path;

import org.bukkit.Location;

import fr.maxlego08.mobfighter.api.Fighter;
import fr.maxlego08.mobfighter.api.enums.EnumVersion;
import fr.maxlego08.mobfighter.api.path.Path;
import fr.maxlego08.mobfighter.api.path.PathManager;
import fr.maxlego08.mobfighter.path.nms.Path12R1;
import fr.maxlego08.mobfighter.path.nms.Path13R1;
import fr.maxlego08.mobfighter.path.nms.Path13R2;
import fr.maxlego08.mobfighter.path.nms.Path14R1;
import fr.maxlego08.mobfighter.path.nms.Path15R1;
import fr.maxlego08.mobfighter.path.nms.Path16R1;
import fr.maxlego08.mobfighter.path.nms.Path16R2;
import fr.maxlego08.mobfighter.path.nms.Path16R3;
import fr.maxlego08.mobfighter.path.nms.Path18R3;
import fr.maxlego08.mobfighter.zcore.logger.Logger;
import fr.maxlego08.mobfighter.zcore.logger.Logger.LogType;
import fr.maxlego08.mobfighter.zcore.utils.ItemDecoder;

public class ZPathManager implements PathManager {

	private final Path path;

	public ZPathManager() {
		double nms = ItemDecoder.getNMSVersion();
		if (nms == 1.8)
			this.path = new Path18R3();
		else if (nms == 1.15)
			this.path = new Path15R1();
		else if (nms == 1.12)
			this.path = new Path12R1();
		else if (nms == 1.13) {
			EnumVersion nmsVersion = ItemDecoder.getVersion();
			if (nmsVersion.equals(EnumVersion.V_13_R1))
				this.path = new Path13R1();
			else
				this.path = new Path13R2();
		} else if (nms == 1.14)
			this.path = new Path14R1();
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
		} else {
			Logger.info("PLEASE CONTECT DEVELOPPER FOR ADDED YOUR VERSION OF SPIGOT", LogType.ERROR);
			this.path = null;
		}
	}

	@Override
	public void setPathGoal(Fighter fighter, Location location) {
		if (this.path != null)
			this.path.setPath(fighter, location, fighter.getConfiguration().getSpeed());
		else
			Logger.info("PLEASE CONTECT DEVELOPPER FOR ADDED YOUR VERSION OF SPIGOT", LogType.ERROR);
	}

}
