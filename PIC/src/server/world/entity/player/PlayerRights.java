package server.world.entity.player;

public enum PlayerRights {
	
	PLAYER(0, "Player", false),
	MEMBER(4, "Member", true),
	MODERATOR(1, "Moderator", false),
	ADMINISTRATOR(2, "Admin", true),
	OWNER(3, "Owner", true);
	
	private int rightsAsInteger;
	private String rightsName;
	private boolean isMembers;
	
	private PlayerRights(int rightsAsInteger, String rightsName, boolean isMembers) {
		this.rightsAsInteger = rightsAsInteger;
		this.rightsName = rightsName;
		this.isMembers = isMembers;
	}

	public int getRightsAsInteger() {
		return rightsAsInteger;
	}

	public String getRightsName() {
		return rightsName;
	}

	public boolean isMembers() {
		return isMembers;
	}
	
	public static PlayerRights getRights(int rank) {
		for (PlayerRights rights : PlayerRights.values()) {
			if(rights.getRightsAsInteger() == rank) {
				return rights;
			}
		}
		return null;
	}

}
