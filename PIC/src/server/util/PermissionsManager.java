package server.util;

public interface PermissionsManager {
	
	/**
	 * is it members only?
	 * 
	 * @return
	 */
	public boolean isMembers();
	
	/**
	 * returns the rights required to interact
	 * 
	 * @return
	 */
	public int getRights();

}
