package eu.hoefel.unit.context;

import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Supplier;

import eu.hoefel.unit.UnitContext;

/**
 * Physics contexts, i.e., contexts that involve all kind of branches of
 * physics. Note that some of these might have an overlap with some chemical or
 * engineering branches (which should not pose problems, though).
 * 
 * @author Udo Hoefel
 */
public enum PhysicsContext implements UnitContext {

    /**
     * A general physics context comprises quantities like time, length and the
     * like.
     */
    GENERAL(PhysicsContext::generalTypes),

    /**
     * The classical mechanic context comprises e.g. stress, torque and momentum.
     */
    CLASSICAL_MECHANIC(PhysicsContext::classicalMechanicTypes),

    /**
     * The electromagnetic context comprises e.g. magnetic induction, capacitance
     * and the 2nd hyper-susceptibility.
     */
    ELECTROMAGNETISM(PhysicsContext::electromagneticTypes),

    /**
     * The radiometric context comprises e.g. étendue, radiosity and spectral
     * exposure.
     */
    RADIOMETRY(PhysicsContext::radiometryTypes),

    /**
     * The photometric context comprises e.g. illuminance and luminous exitance.
     */
    PHOTOMETRY(PhysicsContext::photometryTypes),

    /**
     * The solid state context comprises e.g. the Bragg angle and the Thomson coefficient.
     */
    SOLID_STATE(PhysicsContext::solidStateTypes);

    /** The named symbols. */
    private final NavigableMap<String, List<String>> namedSymbols;

    /**
     * Constructor.
     * 
     * @param namedSymbols the named symbols supplier. Note that the supplied map
     *                     does not need to be unmodifiable, this is taken care of.
     */
    PhysicsContext(Supplier<NavigableMap<String, List<String>>> namedSymbols) {
        this.namedSymbols = Collections.unmodifiableNavigableMap(namedSymbols.get());
    }

    @Override
    public NavigableMap<String, List<String>> namedSymbols() {
        return namedSymbols;
    }

    /**
     * Gets the named symbols within a generic context.
     * 
     * @return the names of generic physics units
     */
    private static NavigableMap<String, List<String>> generalTypes() {
        NavigableMap<String, List<String>> types = new TreeMap<>();
        types.put("length", List.of("m"));
        types.put("area", List.of("m^2"));
        types.put("volume", List.of("m^3"));
        types.put("plane angle", List.of("rad"));
        types.put("solid angle", List.of("sr"));
        types.put("time", List.of("s"));
        types.put("frequency", List.of("Hz"));
        types.put("velocity", List.of("m s^-1"));
        types.put("acceleration", List.of("m s^-2"));
        types.put("angular velocity", List.of("rad s^-1"));
        types.put("mass", List.of("kg"));
        types.put("elementary charge", List.of("C"));
        types.put("energy", List.of("J"));
        return types;
    }

    /**
     * Gets the named symbols within a classical mechanic context.
     * 
     * @return the names of classical mechanic units
     */
    private static NavigableMap<String, List<String>> classicalMechanicTypes() {
        NavigableMap<String, List<String>> types = new TreeMap<>();
        types.put("mass", List.of("kg"));
        types.put("surface density", List.of("kg m^-2"));
        types.put("density", List.of("kg m^-3"));
        types.put("specific volume", List.of("m^3 kg^-1"));
        types.put("momentum", List.of("kg m s^-1"));
        types.put("angular momentum", List.of("J s"));
        types.put("action", List.of("J s"));
        types.put("moment of inertia", List.of("kg m^2"));
        types.put("force", List.of("N"));
        types.put("torque", List.of("N m"));
        types.put("energy", List.of("J"));
        types.put("work", List.of("J"));
        types.put("pressure", List.of("Pa", "N m^-2"));
        types.put("surface tension", List.of("N m^-1", "J m^-2"));
        types.put("stress", List.of("Pa"));
        types.put("strain", List.of(""));
        types.put("viscosity", List.of("Pa s"));
        types.put("fluidity", List.of("m kg^-1 s"));
        types.put("kinematic viscosity", List.of("m^2 s^-1"));
        types.put("power", List.of("W"));
        return types;
    }

    /**
     * Gets the named symbols within an electromagnetic context.
     * 
     * @return the names of electromagnetic units
     */
    private static NavigableMap<String, List<String>> electromagneticTypes() {
        NavigableMap<String, List<String>> types = new TreeMap<>();
        types.put("electric charge", List.of("C"));
        types.put("charge density", List.of("C m^-3"));
        types.put("surface charge density", List.of("C m^-2"));
        types.put("electric potential", List.of("V", "J C^-1"));
        types.put("electric potential difference", List.of("V"));
        types.put("electric field strength", List.of("V m^-1"));
        types.put("capacitance", List.of("F", "C V^-1"));
        types.put("permittivity", List.of("F m^-1"));
        types.put("dielectric polarization", List.of("C m^-2"));
        types.put("1st hyper-susceptibility", List.of("C m J^-1"));
        types.put("2nd hyper-susceptibility", List.of("C^2 m^2 J^-2"));
        types.put("electric dipole moment", List.of("C m"));
        types.put("electric current", List.of("A"));
        types.put("electric current density", List.of("A m^-2"));
        types.put("magnetic flux density", List.of("T"));
        types.put("electric resistance", List.of("Ω"));
        types.put("conductance", List.of("S"));
        types.put("reactance", List.of("Ω"));
        types.put("impedance,", List.of("Ω"));
        types.put("admittance", List.of("S"));
        types.put("susceptance", List.of("S"));
        types.put("resistivity", List.of("Ω m"));
        types.put("conductivity", List.of("S m^-1"));
        types.put("Poynting vector", List.of("W m^-2"));
        types.put("magnetic induction", List.of("T"));
        types.put("magnetic flux", List.of("Wb"));
        types.put("magnetic field strength", List.of("A m^-1"));
        types.put("permeability", List.of("N A^-2", "H m^-1"));
        types.put("magnetization", List.of("A m^-1"));
        types.put("magnetic susceptibility", List.of(""));
        types.put("molar magnetic susceptibility", List.of("m^3 mol^-1"));
        types.put("magnetic dipole moment", List.of("A m^2", "J T^-1"));
        types.put("self-inductance", List.of("H"));
        types.put("mutual inductance", List.of("H"));
        types.put("magnetic vector potential", List.of("Wb m^-1"));
        return types;
    }

    /**
     * Gets the named symbols within a radiometric context.
     * 
     * @return the names of radiometric units
     */
    private static NavigableMap<String, List<String>> radiometryTypes() {
        NavigableMap<String, List<String>> types = new TreeMap<>();
        types.put("radiant energy", List.of("J"));
        types.put("radiant energy density", List.of("J m^-3"));
        types.put("radiant flux", List.of("W"));
        types.put("spectral flux", List.of("W Hz^-1", "W m^-1"));
        types.put("radiant intensity", List.of("W sr^-1"));
        types.put("spectral intensity", List.of("W sr^-1 Hz^-1", "W sr^-1 m^-1"));
        types.put("radiance", List.of("W sr^-1 m^-2"));
        types.put("spectral radiance", List.of("W sr^-1 m^-3", "W sr^-1 m^-2 Hz^-1"));
        types.put("irradiance", List.of("W m^-2"));
        types.put("spectral irradiance", List.of("W m^-2 Hz^-1", "W m^-3"));
        types.put("radiosity", List.of("W m^-2"));
        types.put("spectral radiosity", List.of("W m^-2 Hz^-1", "W m^-3"));
        types.put("radiant exitance", List.of("W m^-2"));
        types.put("spectral exitance", List.of("W m^-2 Hz^-1", "W m^-3"));
        types.put("radiant exposure", List.of("J m^-2"));
        types.put("spectral exposure", List.of("J m^-2 Hz^-1", "J m^-3"));
        types.put("hemispherical emissivity", List.of(""));
        types.put("spectral hemispherical emissivity", List.of(""));
        types.put("directional emissivity", List.of(""));
        types.put("spectral directional emissivity", List.of(""));
        types.put("hemispherical absorptance", List.of(""));
        types.put("spectral hemispherical absorptance", List.of(""));
        types.put("directional absorptance", List.of(""));
        types.put("spectral directional absorptance", List.of(""));
        types.put("hemispherical reflectance", List.of(""));
        types.put("spectral hemispherical reflectance", List.of(""));
        types.put("directional reflectance", List.of(""));
        types.put("spectral directional reflectance", List.of(""));
        types.put("hemispherical transmittance", List.of(""));
        types.put("spectral hemispherical transmittance", List.of(""));
        types.put("directional transmittance", List.of(""));
        types.put("spectral directional transmittance", List.of(""));
        types.put("hemispherical attenuation coefficient", List.of("m^-1"));
        types.put("spectral hemispherical attenuation coefficient", List.of("m^-1"));
        types.put("directional attenuation coefficient", List.of("m^-1"));
        types.put("spectral directional attenuation coefficient", List.of("m^-1"));
        types.put("wavelength", List.of("m"));
        types.put("wavenumber", List.of("m^-1"));
        types.put("frequency", List.of("Wb m^-1"));
        types.put("refractive index", List.of(""));
        types.put("fluence", List.of("J m^-2"));
        types.put("emittance", List.of(""));
        types.put("étendue", List.of("m^2 sr"));
        types.put("resolving power", List.of(""));
        types.put("resolution", List.of("m^-1"));
        types.put("free spectral range", List.of("m^-1"));
        types.put("finesse", List.of(""));
        types.put("quality factor", List.of(""));
        types.put("specifc optical rotatory power", List.of("rad m^2 kg^-1"));
        types.put("molar optical rotatory power", List.of("rad m^2 mol^-1"));
        return types;
    }

    /**
     * Gets the named symbols within a photometric context.
     * 
     * @return the names of photometric units
     */
    private static NavigableMap<String, List<String>> photometryTypes() {
        NavigableMap<String, List<String>> types = new TreeMap<>();
        types.put("luminous energy", List.of("lm s"));
        types.put("luminous flux", List.of("lm"));
        types.put("luminous intensity", List.of("cd"));
        types.put("luminance", List.of("cd m^-2"));
        types.put("illuminance", List.of("lx"));
        types.put("luminous exitance", List.of("lm m^-2"));
        types.put("luminous exposure", List.of("lx s"));
        types.put("luminous energy density", List.of("lm s m^-3"));
        types.put("luminous efficacy", List.of("lm W^-1"));
        types.put("luminous efficiency", List.of(""));
        return types;
    }

    /**
     * Gets the named symbols within a solid state physics context.
     * 
     * @return the names of solid state units
     */
    private static NavigableMap<String, List<String>> solidStateTypes() {
        NavigableMap<String, List<String>> types = new TreeMap<>();
        types.put("lattice vector", List.of("m"));
        types.put("reciprocal lattice vector", List.of("m^-1"));
        types.put("unit cell length", List.of("m"));
        types.put("unit cell angle", List.of("rad", ""));
        types.put("reciprocal unit cell length", List.of("m^-1"));
        types.put("reciprocal unit cell angle", List.of("rad^-1", ""));
        types.put("atomic scattering factor", List.of(""));
        types.put("lattice plane spacing", List.of("m"));
        types.put("Bragg angle", List.of("rad", ""));
        types.put("Burgers vector", List.of("m"));
        types.put("Grüneisen parameter", List.of(""));
        types.put("Madelung constant", List.of(""));
        types.put("density of states", List.of("J^-1 m^-3"));
        types.put("density of vibrational modes", List.of("s m^-3"));
        types.put("resistivity tensor", List.of("Ω m"));
        types.put("conductivity tensor", List.of("S m^-1"));
        types.put("thermal conductivity tensor", List.of("W m^-1 K^-1"));
        types.put("residual resistivity", List.of("Ω m"));
        types.put("relaxation time", List.of("s"));
        types.put("Lorenz coefficient", List.of("V^2 K^-2"));
        types.put("Hall coefficient", List.of("C^-1 m^3"));
        types.put("thermoelectric force", List.of("V"));
        types.put("Peltier coefficient", List.of("V"));
        types.put("Thomson coefficient", List.of("V K^-1"));
        types.put("work function", List.of("J"));
        types.put("number density", List.of("m^-3"));
        types.put("gap energy", List.of("J"));
        types.put("ionization energy", List.of("J"));
        types.put("Fermi energy", List.of("J"));
        types.put("effective mass", List.of("kg"));
        types.put("mobility", List.of("m^2 V^-1 s^-1"));
        types.put("diffusion coefficient", List.of("m^2 s^-1"));
        types.put("diffusion length", List.of("m"));
        types.put("characteristic temperature", List.of("K"));
        types.put("Curie temperature", List.of("K"));
        types.put("Néel temperature", List.of("K"));
        return types;
    }
}
