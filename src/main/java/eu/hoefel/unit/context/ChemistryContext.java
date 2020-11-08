package eu.hoefel.unit.context;

import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Supplier;

import eu.hoefel.unit.UnitContext;

/**
 * Chemistry contexts, i.e., contexts that involve all kind of branches of
 * chemistry. Note that some of these might have an overlap with some physics or
 * engineering branches (which should not pose problems, though).
 * 
 * @author Udo Hoefel
 */
public enum ChemistryContext implements UnitContext {

	/**
	 * A general chemistry context comprises quantities like amount, solubility and
	 * the like.
	 */
	GENERAL(ChemistryContext::generalTypes),

	/**
	 * The chemical thermodynamic context comprises quantities like entropy,
	 * chemical potential and fugacity, which arise in the study of the
	 * interrelation of heat/work with chemical reactions within the realm of the
	 * laws of thermodynamics or changes of state (also within the realm of the laws
	 * of thermodynamics).
	 */
	THERMODYNAMICS(ChemistryContext::thermodynamicTypes),

	/**
	 * The chemical (reaction) kinetics context comprises quantities like the rate
	 * of conversion and the mean free path, which arise in the study of the rates
	 * of chemical reactions.
	 */
	KINETICS(ChemistryContext::kineticTypes),

	/**
	 * The electrochemical context comprises quantities like the pH value, the
	 * overpotential and the ionic conductivity, which arise in the study of the
	 * interrelation of electricity and chemical change.
	 */
	ELECTROCHEMISTRY(ChemistryContext::electrochemicalTypes),

	/**
	 * The colloid and surface chemistry context comprises quantities like the
	 * specific surface area, the film tension and the total surface excess
	 * concentration. Note that colloid (i.e. small particles dispersed in a medium)
	 * chemistry is deeply connected to surface chemistry due to the large surface
	 * area of the (small) particles, which is why they are combined here.
	 */
	COLLOID_AND_SURFACE_CHEMISTRY(ChemistryContext::colloidAndSurfaceChemistryTypes),

	/**
	 * The chemical transport context comprises quantities like heat flux, volume
	 * flow rate and the Reynolds number, which arise in the study of all kind of
	 * fluxes (e.g. heat and particles).
	 */
	TRANSPORT(ChemistryContext::transportTypes);

	/** The named symbols. */
	private final NavigableMap<String, List<String>> namedSymbols;

	/**
	 * Constructor.
	 * 
	 * @param namedSymbols the named symbols supplier. Note that the supplied map
	 *                     does not need to be unmodifiable, this is taken care of.
	 */
	ChemistryContext(Supplier<NavigableMap<String, List<String>>> namedSymbols) {
		this.namedSymbols = Collections.unmodifiableNavigableMap(namedSymbols.get());
	}

	@Override
	public NavigableMap<String, List<String>> namedSymbols() {
		return namedSymbols;
	}

	/**
	 * Gets the named symbols within a generic chemistry context.
	 * 
	 * @return the names of generic chemistry units
	 */
	private static final NavigableMap<String, List<String>> generalTypes() {
		NavigableMap<String, List<String>> types = new TreeMap<>();
		types.put("amount", List.of("mol"));
		types.put("molar mass", List.of("kg mol^-1"));
		types.put("molar volume", List.of("m^3 mol^-1"));
		types.put("pressure", List.of("Pa"));
		types.put("mass concentration", List.of("kg m^-3"));
		types.put("number concentration", List.of("m^-3"));
		types.put("concentration", List.of("mol m^-3"));
		types.put("solubility", List.of("mol m^-3"));
		types.put("molality", List.of("mol kg^-1"));
		types.put("surface concentration", List.of("mol m^-2"));
		types.put("stoichiometric number", List.of(""));
		types.put("extent of reaction", List.of("mol"));
		types.put("degree of reaction", List.of(""));
		return types;
	}

	/**
	 * Gets the named symbols within a chemical thermodynamics context.
	 * 
	 * @return the names of chemical thermodynamics units
	 */
	private static final NavigableMap<String, List<String>> thermodynamicTypes() {
		NavigableMap<String, List<String>> types = new TreeMap<>();
		types.put("heat", List.of("J"));
		types.put("work", List.of("J"));
		types.put("internal energy", List.of("J"));
		types.put("enthalpy", List.of("J"));
		types.put("Helmholtz energy", List.of("J"));
		types.put("Gibbs energy", List.of("J"));
		types.put("entropy", List.of("J K^-1"));
		types.put("Massieu function", List.of("J K^-1"));
		types.put("Planck function", List.of("J K^-1"));
		types.put("surface tension", List.of("J m^-2", "N m^-1"));
		types.put("pressure coefficient", List.of("Pa K^-1"));
		types.put("compressibility", List.of("Pa^-1"));
		types.put("heat capacity", List.of("J K^-1"));
		types.put("Joule-Thomson coefficient", List.of("K Pa^-1"));
		types.put("second virial coefficient", List.of("m^3 mol^-1"));
		types.put("third virial coefficient", List.of("m^6 mol^-2"));
		types.put("compression factor", List.of(""));
		types.put("chemical potential", List.of("J mol^-1"));
		types.put("activity", List.of(""));
		types.put("molar enthalpy", List.of("J mol^-1"));
		types.put("molar entropy", List.of("J mol^-1 K^-1"));
		types.put("standard reaction Gibbs energy", List.of("J mol^-1"));
		types.put("affinity of reaction", List.of("J mol^-1"));
		types.put("standard partial molar enthalpy", List.of("J mol^-1"));
		types.put("standard partial molar enthalpy", List.of("J mol^-1"));
		types.put("fugacity", List.of("Pa"));
		types.put("osmotic pressure", List.of("Pa"));
		return types;
	}

	/**
	 * Gets the named symbols within a chemical kinetics context.
	 * 
	 * @return the names of chemical kinetic units
	 */
	private static final NavigableMap<String, List<String>> kineticTypes() {
		NavigableMap<String, List<String>> types = new TreeMap<>();
		types.put("rate of conversion", List.of("mol s^-1"));
		types.put("rate of concentration change", List.of("mol m^-3 s^-1"));
		types.put("rate of reaction", List.of("mol m^-3 s^-1"));
		types.put("half life", List.of("s"));
		types.put("relaxation time", List.of("s"));
		types.put("activation energy", List.of("J mol^-1"));
		types.put("volume of activation", List.of("m^3 mol^-1"));
		types.put("hard sphere radius", List.of("m"));
		types.put("collision diameter", List.of("m"));
		types.put("collision cross section", List.of("m^2"));
		types.put("collision frequency", List.of("s^-1"));
		types.put("collision density", List.of("s^-1 m^-3"));
		types.put("collision frequency factor", List.of("m^3 mol^-1 s^-1"));
		types.put("mean free path", List.of("m"));
		types.put("impact parameter", List.of("m"));
		types.put("scattering angle", List.of("rad", ""));
		types.put("differential cross section", List.of("m^2 sr^-1"));
		types.put("total cross section", List.of("m^2"));
		types.put("scattering matrix", List.of(""));
		types.put("transition probability", List.of(""));
		types.put("standard enthalpy of activation", List.of("J mol^-1"));
		types.put("standard internal energy of activation", List.of("J mol^-1"));
		types.put("standard entropy of activation", List.of("J mol^-1 K^-1"));
		types.put("standard Gibbs energy of activation", List.of("J mol^-1"));
		types.put("quantum yield", List.of(""));
		return types;
	}

	/**
	 * Gets the named symbols within an electrochemical context.
	 * 
	 * @return the names of electrochemical units
	 */
	private static final NavigableMap<String, List<String>> electrochemicalTypes() {
		NavigableMap<String, List<String>> types = new TreeMap<>();
		types.put("charge number of an ion", List.of(""));
		types.put("ionic strength (molality basis)", List.of("mol kg^-1"));
		types.put("ionic strength (concentration basis)", List.of("mol m^-3"));
		types.put("electric potential", List.of("V"));
		types.put("electromotive force", List.of("V"));
		types.put("pH", List.of(""));
		types.put("electrochemical potential", List.of("J mol^-1"));
		types.put("electric current", List.of("A"));
		types.put("(electric) current density", List.of("A m^-2"));
		types.put("(surface) charge density", List.of("C m^-2"));
		types.put("mass transfer coefficient", List.of("m s^-1"));
		types.put("diffusion rate constant", List.of("m s^-1"));
		types.put("thickness of diffusion layer", List.of("m"));
		types.put("electrochemical transfer coefficient", List.of(""));
		types.put("overpotential", List.of("V"));
		types.put("electrokinetic potential", List.of("V"));
		types.put("conductivity", List.of("S m^-1"));
		types.put("conductivity cell constant", List.of("m^-1"));
		types.put("molar conductivity (of an electrolyte)", List.of("S m^2 mol^-1"));
		types.put("electric mobility", List.of("m^2 V^-1 s^-1"));
		types.put("ionic conductivity", List.of("S m^2 mol^-1"));
		types.put("transport number", List.of(""));
		types.put("reciprocal radius of ionic atmosphere", List.of("m^-1"));
		return types;
	}

	/**
	 * Gets the named symbols within a colloid/surface chemistry context.
	 * 
	 * @return the names of colloid/surface chemistry units
	 */
	private static final NavigableMap<String, List<String>> colloidAndSurfaceChemistryTypes() {
		NavigableMap<String, List<String>> types = new TreeMap<>();
		types.put("specific surface area", List.of("m^2 kg^-1"));
		types.put("surface amount of entity", List.of("mol"));
		types.put("adsorbed amount of entity", List.of("mol"));
		types.put("surface excess of entity", List.of("mol"));
		types.put("surface excess concentration of entity", List.of("mol m^-2"));
		types.put("total surface excess concentration", List.of("mol m^-2"));
		types.put("area per molecule", List.of("m^2"));
		types.put("surface coverage", List.of(""));
		types.put("contact angle", List.of("", "rad"));
		types.put("film thickness", List.of("m"));
		types.put("surface tension", List.of("N m^-1", "J m^-2"));
		types.put("interfacial tension", List.of("N m^-1", "J m^-2"));
		types.put("film tension", List.of("N m^-1"));
		types.put("sedimentation coefficient", List.of("s"));
		types.put("van der Waals constant", List.of("J"));
		types.put("van der Waals—Hamaker constan", List.of("J"));
		types.put("surface pressure", List.of("N m^-1"));
		return types;
	}

	/**
	 * Gets the named symbols within a transport context.
	 * 
	 * @return the names of transport units
	 */
	private static final NavigableMap<String, List<String>> transportTypes() {
		NavigableMap<String, List<String>> types = new TreeMap<>();
		types.put("volume flow rate", List.of("m^3 s^-1"));
		types.put("mass flow rate", List.of("kg s^-1"));
		types.put("mass transfer coefficient", List.of("kg s^-1"));
		types.put("heat flow rate", List.of("W"));
		types.put("heat flux", List.of("W m^-2"));
		types.put("thermal conductance", List.of("W K^-1"));
		types.put("thermal resistance", List.of("K W^-1"));
		types.put("thermal conductivity", List.of("W m^-1 K^-1"));
		types.put("coefficient of heat transfer", List.of("W m^-2 K^-1"));
		types.put("thermal diffusivity", List.of("m^2 s^-1"));
		types.put("diffusion coefficient", List.of("m^2 s^-1"));
		types.put("Reynolds number", List.of(""));
		types.put("Euler number", List.of(""));
		types.put("Froude number", List.of(""));
		types.put("Grashof number", List.of(""));
		types.put("Weber number", List.of(""));
		types.put("Mach number", List.of(""));
		types.put("Knudsen number", List.of(""));
		types.put("Strouhal number", List.of(""));
		types.put("Fourier number", List.of(""));
		types.put("Péclet number", List.of(""));
		types.put("Rayleigh number", List.of(""));
		types.put("Nusselt number", List.of(""));
		types.put("Stanton number", List.of(""));
		types.put("Stanton number", List.of(""));
		types.put("Prandtl number", List.of(""));
		types.put("Schmidt number", List.of(""));
		types.put("Lewis number", List.of(""));
		types.put("Alfvén number", List.of(""));
		types.put("Hartmann number", List.of(""));
		types.put("Cowling number", List.of(""));
		return types;
	}
}
