package net.cthuwork.core;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public abstract class DropData {
	private DropType type;

	protected DropData(DropType type) {
		this.type = type;
	}

	public DropType getDropType() {
		return type;
	}

	public abstract void processDrop(Player player);

	public static enum DropType {
		ITEM_STACK, EXP
	}
}
