package what.util;

/**
 * Store information about the current version, Major.Minor.Patch
 */
public class VersionNumber {
	private int major = 0, minor = 0, patch = 0;
	private boolean preview = false;

	/**
	 * Create the version number, the string should contain the version information
	 * such as "1.2.1"
	 * Will also handle cases of incomplete version numbers, ie. 1.2 will be read to 1.2.0
	 * A version number with a 'b' at the end indicates a preview(beta) build: 1.2.1.b -> 1.2.1 beta release
	 * @param s string containg the version information
	 */
	public VersionNumber(String s){
		String[] vals = s.split("\\.");
		if (vals.length > 0)
			major = Integer.parseInt(vals[0]);
		if (vals.length > 1)
			minor = Integer.parseInt(vals[1]);
		if (vals.length > 2)
			patch = Integer.parseInt(vals[2]);
		if (s.charAt(s.length() - 1) == 'b')
			preview = true;
	}

	/**
	 * Check if this version number is higher than some other version
	 * @param other the version number to compare with
	 * @return true if this version is a higher version
	 */
	public boolean isHigher(VersionNumber other){
		if (major < other.major)
			return false;
		if (minor < other.minor)
			return false;
		if (patch < other.patch)
			return false;
		//If the version numbers are equal but we aren't a preview and the other build is, we're higher
		return !(major == other.major && minor == other.minor && patch == other.patch)
			|| (!preview && other.preview);
	}

	public int getMajor(){
		return major;
	}

	public int getMinor(){
		return minor;
	}

	public int getPatch(){
		return patch;
	}

	public boolean isPreview(){
		return preview;
	}

	@Override
	public String toString(){
		return major + "." + minor + "." + patch + (preview ? " preview" : "");
	}
}
