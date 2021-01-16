package eu.hoefel.unit.history.roman;

import static eu.hoefel.unit.history.roman.RomanUnits.ROMAN_FOOT;
import static eu.hoefel.unit.history.roman.RomanUnits.ROMAN_LIBRA;

import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.Units;
import eu.hoefel.unit.si.SiBaseUnit;

/**
 * Undocumented. Needs to be checked by a historian before commenting most of them in.
 * 
 * @author Udo Hoefel
 */
public enum RomanUnit implements Unit {
//	// length units
//	DIGITUS(ROMAN_FOOT / 16, "digitus"),

	/**
	 * The uncia was one twelfth of a {@link RomanUnits#ROMAN_FOOT Roman foot}. It
	 * is the origin of the {@link eu.hoefel.unit.imperial.ImperialUnit#INCH inch}.
	 */
	UNCIA(ROMAN_FOOT / 12, "uncia", "pollex"),
//	PALMUS(0.25 * ROMAN_FOOT, "palmus"),
//	PALMUS_MAIOR(0.75 * ROMAN_FOOT, "palmus-maior"),
//
//	/**
//	 * Hosch, William L. (ed.) (2010) The Britannica Guide to Numbers and Measurement New York, NY: Britannica Educational Publications, 1st edition. ISBN 978-1-61530-108-9, p.206
//	 */
//	PES(ROMAN_FOOT, "pes", "pedes"),
//	PALMIPES(1.25 * ROMAN_FOOT, "palmipes"),
//	CUBITUM(1.5 * ROMAN_FOOT, "cubitum"),
//	GRADUS(2.5 * ROMAN_FOOT, "gradus", "pes-sestertius"),
//	PASSUS(5 * ROMAN_FOOT, "passus"),
//	DECEMPEDA(10 * ROMAN_FOOT, "decempeda", "pertica"),
//	ACTUS(120 * ROMAN_FOOT, "actus"),
//	STADIUM(625 * ROMAN_FOOT, "stadium"),
//	MILLE_PASSUS(5_000 * ROMAN_FOOT, "mille-passus", "mille-pasuum"),
//	LEUGA(7_500 * ROMAN_FOOT, "leuga", "leuca"),
//	
//	// area
//	PES_QUADRATUS(ROMAN_SQUARE_FOOT, "pes-quadratus"),
//	DIMIDIUM_SCRUPULUM(ROMAN_JUGERUM / 576, "dimidium-scrupulum"),
//	DECEMPEDA_QUADRATA(ROMAN_JUGERUM / 288, "scrupulum", "decempeda-quadrata"),
//	DUO_SCRUPULA(ROMAN_JUGERUM / 144, "duo-scrupula"),
//	SEXTULA(ROMAN_JUGERUM / 72, "sextula"),
//	ACTUS_SIMPLEX(480 * ROMAN_SQUARE_FOOT, "actus-simplex"),
//	SICILICUS(ROMAN_JUGERUM / 48, "sicilicus"),
//	SEMIUNCIA(ROMAN_JUGERUM / 24, "semiuncia"),
//	UNCIA_AREA(ROMAN_JUGERUM / 12, "uncia-(area)"),
//	CLIMA(3_600 * ROMAN_SQUARE_FOOT, "clima"),
//	SEXTANS(ROMAN_JUGERUM / 6, "sextans"),
//	QUADRANS(ROMAN_JUGERUM / 4, "quadrans"),
//	TRIENS(ROMAN_JUGERUM / 3, "triens"),
//	QUINCUNX(ROMAN_JUGERUM * 5. / 12, "quincunx"),
//	ACTUS_QUADRATUS(ROMAN_JUGERUM / 4, "actus-quadratus", "acnua", "semis"),
//	SEPTUNX(ROMAN_JUGERUM * 7. / 12, "septunx"),
//	BES(ROMAN_JUGERUM * 2. / 3, "bes"),
//	DODRANS(ROMAN_JUGERUM * 3. / 4, "dodrans"),
//	DEXTANS(ROMAN_JUGERUM * 5. / 6, "dextans"),
//	DEUNX(ROMAN_JUGERUM * 11. / 12, "deunx"),
//	JUGERUM(ROMAN_JUGERUM, "jugerum"),
//	HEREDIUM(2 * ROMAN_JUGERUM, "heredium"),
//	CENTURIA(200 * ROMAN_JUGERUM, "centuria"),
//	SALTUS(800 * ROMAN_JUGERUM, "saltus"),
//
//	// volume
//	LIGULA(ROMAN_CUBE_FOOT / (288 * 8), "ligula"),
//	CYATHUS(ROMAN_CUBE_FOOT / (72 * 8), "cyathus"),
//	ACETABULUM(ROMAN_CUBE_FOOT / (48 * 8), "acetabulum"),
//	QUARTARIUS(ROMAN_CUBE_FOOT / (24 * 8), "quartarius"),
//	HEMINA(ROMAN_CUBE_FOOT / (12 * 8), "hemina", "cotyla"),
//	SEXTARIUS(ROMAN_CUBE_FOOT / (6 * 8), "sextarius"),
//	CONGIUS(ROMAN_CUBE_FOOT / 8, "congius"),
//	SEMIMODIUS(ROMAN_CUBE_FOOT * 4 / (3 * 8), "semimodius"),
//	MODIUS(ROMAN_CUBE_FOOT * 8 / (3 * 8), "modius"),
//	MODIUS_CASTRENSIS(ROMAN_CUBE_FOOT / 2 /* deviation on wiki! */, "modius-castrensis"),
//	URNA(ROMAN_CUBE_FOOT / (2 * 48), "urna"),
//	AMPHORA_QUADRANTAL(ROMAN_CUBE_FOOT, "amphora-quadrantal"),
//	CULEUS(ROMAN_CUBE_FOOT * 40 / 2, "culeus"),
//
//	// weight
//	GRANUM(ROMAN_LIBRA / 6_912, "granum"),
//	CHALCUS(ROMAN_LIBRA / 4_608, "chalcus"),
//	SILIQUA(ROMAN_LIBRA / 1_728, "siliqua"),
//	OBOLUS(ROMAN_LIBRA / 576, "obolus"),
//	SCRUPULUM_WEIGHT(ROMAN_LIBRA / 288, "scrupulum-(weight)"),
//	DRACHMA(ROMAN_LIBRA / 96, "drachma"),
//	SICILICUS_WEIGHT(ROMAN_LIBRA / 48, "sicilicus"),

	/**
	 * The uncia (for weight) corresponds to one twelfth of a {@link #LIBRA}. The
	 * {@link eu.hoefel.unit.imperial.ImperialUnit#OUNCE} originated from it.
	 */
	UNCIA_WEIGHT(ROMAN_LIBRA / 12, "uncia-(weight)"),

	/**
	 * The libra corresponds to roughly 0.325
	 * {@link eu.hoefel.unit.si.SiBaseUnit#KILOGRAM kg}. The
	 * {@link eu.hoefel.unit.imperial.ImperialUnit#POUND} originated from it.
	 */
	LIBRA(ROMAN_LIBRA, "libra"),
//	MINA(4 * ROMAN_LIBRA / 3, "mina")
	;

	/** The symbols representing the roman unit. */
	private final List<String> symbols;

	/** The factor to convert from the roman unit to the SI base units. */
	private final double factor;

	/** The SI base units to which the roman unit corresponds. */
	private Map<Unit, Integer> baseUnits;

	/**
	 * Constructor for historical roman units.
	 * 
	 * @param factor  the conversion factor to base SI units
	 * @param symbols the symbols representing the roman unit
	 */
	private RomanUnit(double factor, String... symbols) {
		this.factor = factor;
		this.symbols = List.of(symbols);
	}

	@Override
	public Map<Unit, Integer> baseUnits() {
		if (baseUnits == null) {
			baseUnits = switch (this) {
				case UNCIA_WEIGHT, LIBRA -> Map.of(SiBaseUnit.KILOGRAM, 1);
				case UNCIA -> Map.of(SiBaseUnit.METER, 1);
			};
		}
//		if (baseUnits == null) {
//			baseUnits = switch (this) {
//			case DIGITUS, UNCIA, PALMUS, PALMUS_MAIOR, 
//				PES, PALMIPES, CUBITUM, GRADUS, PASSUS,
//				DECEMPEDA, ACTUS, STADIUM, MILLE_PASSUS,
//				LEUGA -> Map.of(SiBaseUnit.METER, 1);
//			case PES_QUADRATUS, DIMIDIUM_SCRUPULUM,
//				DECEMPEDA_QUADRATA, DUO_SCRUPULA,
//				SEXTULA, ACTUS_SIMPLEX, SICILICUS,
//				SEMIUNCIA, UNCIA_AREA, CLIMA, SEXTANS,
//				QUADRANS, TRIENS, QUINCUNX,
//				ACTUS_QUADRATUS, SEPTUNX, BES, DODRANS,
//				DEXTANS, DEUNX, JUGERUM, HEREDIUM,
//				CENTURIA, SALTUS -> Map.of(SiBaseUnit.METER, 2);
//			case LIGULA, CYATHUS, ACETABULUM, QUARTARIUS,
//				HEMINA, SEXTARIUS, CONGIUS, SEMIMODIUS,
//				MODIUS, MODIUS_CASTRENSIS, URNA,
//				AMPHORA_QUADRANTAL, CULEUS -> Map.of(SiBaseUnit.METER, 3);
//			case GRANUM, CHALCUS, SILIQUA, OBOLUS,
//				SCRUPULUM_WEIGHT, DRACHMA,
//				SICILICUS_WEIGHT, UNCIA_WEIGHT, LIBRA,
//				MINA -> Map.of(SiBaseUnit.KILOGRAM, 1);
//			};
//		}
		return baseUnits;
	}

	@Override public double factor(String symbol) { return factor; }
	@Override public List<String> symbols() { return symbols; }
	@Override public boolean prefixAllowed(String symbol) { return false; }
	@Override public boolean isConversionLinear() { return true; }
	@Override public double convertToBaseUnits(double value) { return factor * value; }
	@Override public double convertFromBaseUnits(double value) { return value / factor; }
	@Override public Set<UnitPrefix> prefixes() { return Units.EMPTY_PREFIXES; }
	@Override public boolean isBasic() { return false; }
}
