import java.util.*;

public record ClassFileVersion(int majorVersion, int minorVersion, String javaSEVersion, String releaseDate) implements Comparable<ClassFileVersion> {
	public static final Map<Integer, ClassFileVersion> MAJOR_VERSIONS = Map.ofEntries(
		//  Map.entry(45, new ClassFileVersion(45, -1, "1.0.2", "May 1996"       )),
			Map.entry(45, new ClassFileVersion(45, -1, "1.1",   "February 1997"  )),
			Map.entry(46, new ClassFileVersion(46, -1, "1.2",   "December 1998"  )),
			Map.entry(47, new ClassFileVersion(47, -1, "1.3",   "May 2000"       )),
			Map.entry(48, new ClassFileVersion(48, -1, "1.4",   "February 2002"  )),
			Map.entry(49, new ClassFileVersion(49, -1, "5.0",   "September 2004" )),
			Map.entry(50, new ClassFileVersion(50, -1, "6",     "December 2006"  )),
			Map.entry(51, new ClassFileVersion(51, -1, "7",     "July 2011"      )),
			Map.entry(52, new ClassFileVersion(52, -1, "8",     "March 2014"     )),
			Map.entry(53, new ClassFileVersion(53, -1, "9",     "September 2017" )),
			Map.entry(54, new ClassFileVersion(54, -1, "10",    "March 2018"     )),
			Map.entry(55, new ClassFileVersion(55, -1, "11",    "September 2018" )),
			Map.entry(56, new ClassFileVersion(56, -1, "12",    "March 2019"     )),
			Map.entry(57, new ClassFileVersion(57, -1, "13",    "September 2019" )),
			Map.entry(58, new ClassFileVersion(58, -1, "14",    "March 2020"     )),
			Map.entry(59, new ClassFileVersion(59, -1, "15",    "September 2020" )),
			Map.entry(60, new ClassFileVersion(60, -1, "16",    "March 2021"     )),
			Map.entry(61, new ClassFileVersion(61, -1, "17",    "September 2021" )),
			Map.entry(62, new ClassFileVersion(62, -1, "18",    "March 2022"     )),
			Map.entry(63, new ClassFileVersion(63, -1, "19",    "September 2022" )),
			Map.entry(64, new ClassFileVersion(64, -1, "20",    "March 2023"     )),
			Map.entry(65, new ClassFileVersion(65, -1, "21",    "September 2023" )),
			Map.entry(66, new ClassFileVersion(66, -1, "22",    "March 2024"     )),
			Map.entry(67, new ClassFileVersion(67, -1, "23",    "September 2024" ))
	);

	@SuppressWarnings("preview")
	public ClassFileVersion(int majorVersion, int minorVersion) throws IllegalArgumentException {
		var version = MAJOR_VERSIONS.get(majorVersion);
		if (version == null)
			throw new IllegalArgumentException(String.format("Unsupported class file version %d.%d", majorVersion, minorVersion));
		if (majorVersion >= 56 && !(minorVersion == 0x0000 || minorVersion == 0xFFFF))
			throw new IllegalArgumentException(String.format("Illegal class file version: %d.%d", majorVersion, minorVersion));
		this(majorVersion, minorVersion, version.javaSEVersion(), version.releaseDate());
	}

	public int compareTo(ClassFileVersion version) {
		var majorComparison = Integer.compare(majorVersion(), version.majorVersion());
		if (majorComparison != 0)
			return majorComparison;
		return Integer.compare(minorVersion(), version.minorVersion());
	}
}
