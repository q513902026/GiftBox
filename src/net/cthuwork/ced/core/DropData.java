package net.cthuwork.ced.core;

import org.bukkit.entity.LivingEntity;

public abstract class DropData {
	private DropType type;

	protected DropData(DropType type) {
		this.type = type;
	}

	public DropType getDropType() {
		return type;
	}

	public abstract void processDrop(LivingEntity entity);

	public static enum DropType {
		ITEM_STACK, EXP,MONSTER
	}
}
