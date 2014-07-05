package com.plug.world.content.transport;


public abstract class Ancient extends SpellBook {

	@Override
	public int getMask() {
		return 0;
	}
	
	@Override
	public int getStartAnimation() {
		return 1979;
	}

	@Override
	public int getEndAnimation() {
		return 0;
	}

	@Override
	public int getGfx() {
		return 392;
	}

	@Override
	public int getDelay() {
		return 1;
	}

}
