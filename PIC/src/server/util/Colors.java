package server.util;

public enum Colors {
	BLUE("@blu@"), RED("@red@"), GREEN("@gre@"), YELLOW(
			"@yel@"), PINK("<col=16711935>"), PURPLE("<col=9830655>"), BLACK(
			"@bla@"), WHITE("<col=16777215>"), ORANGE("@dre@"), AQUA(
			"@cya@"), GOLD("<col=16757504>"), TEAL("<col=30023>"), LAVANDER(
			"<col=8021134>"), MAGENTA("@mag@"), RANDOM("<col="
			+ Misc.random(16776960) + ">");

	private String color;

	private Colors(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return color;
	}

	@Override
	public String toString() {
		return color;
	}
}
