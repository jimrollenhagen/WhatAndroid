package what.gui;

/**
 * @author Gwindow
 * @since May 25, 2012 9:15:15 AM
 */
public enum ActivityNames {
	HOME("Home"), FORUM("Forum");

	private final String name;

	private ActivityNames(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
