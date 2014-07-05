package server.world.content.transport;



public abstract class Modern extends SpellBook {
	
	@Override
	public int getMask() {
		return 100;
	}
	
	@Override
	public int getStartAnimation() {
		return 714;
	}

	@Override
	public int getEndAnimation() {
		return 715;
	}

	@Override
	public int getGfx() {
		return 308;
	}

	@Override
	public int getDelay() {
		return 3;
	}	

}
