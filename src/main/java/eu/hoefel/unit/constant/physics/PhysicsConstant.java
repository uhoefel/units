package eu.hoefel.unit.constant.physics;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;
import eu.hoefel.unit.constant.Constant;
import eu.hoefel.unit.si.SiBaseUnit;
import eu.hoefel.unit.si.SiDerivedUnit;
import eu.hoefel.unit.special.AtomicUnit;
import eu.hoefel.unit.special.ConventionalElectricalUnit;
import eu.hoefel.unit.special.PlanckUnit;

/**
 * This enum contains the basic physics constants. The values of the basic
 * constants are based on the <a href="https://codata.org/">CODATA</a>
 * recommendations. Note however, that the derived quantities, like e.g. the
 * {@link #RYDBERG_CONSTANT Rydberg constant}, are calculated from the basic
 * constants to ensure consistency instead of taking their CODATA value, as
 * there are deviations to the recommended CODATA values that really should not
 * be there.
 */
public enum PhysicsConstant implements Constant {

	/**
	 * The mass of 2 protons and 2 neutrons bound together into a particle identical
	 * to a helium nucleus. Note that this is not just the mass of 2 protons and 2
	 * neutrons as the binding energy needs to be taken into account as well.
	 */
	ALPHA_PARTICLE_MASS(6.6446573357e-27, 0.0000000020e-27, SiBaseUnit.KILOGRAM),

	/**
	 * J. A. Bearden
	 * <a href="https://doi.org/10.1103/PhysRev.137.B455">introduced</a> this unit
	 * in 1965 as a replacement for the X unit (an old unit used previously in X-ray
	 * spectroscopy). Its length is defined by taking the wavelength of the tungsten
	 * Kα1 line as exactly 0.209 010 0 Å*. This value has been chosen to make 1 Å*
	 * equal to 1 Å within 5 parts per million (it later turned out to be slightly
	 * more off, ca. 15 parts per million). See the above quoted paper at page B460.
	 */
	ANGSTROM_STAR(1.00001495e-10, 0.00000090e-10, SiBaseUnit.METER),

	/**
	 * The atomic mass constant is defined as 1/12 of an unbound neutral carbon atom
	 * in its nuclear and electronic ground state and at rest (see also
	 * <a href="https://doi.org/10.1351/goldbook">IUPACs gold book</a>). It is
	 * identical to the {@link #UNIFIED_ATOMIC_MASS_UNIT unified atomic mass unit}
	 * (old name: atomic mass unit). It is used to denote atomic and molecule
	 * masses. In biochemistry, the US and in organical chemistry it is also called
	 * Dalton (unit symbol: Da). It has the practical property that all known
	 * nucleus and atomic masses are close to integer multiples of the atomic mass
	 * constant, with deviations typically less than 10 % of one unified atomic mass
	 * unit. The corresponding integer number equals the number of nucleons in the
	 * nucleus.
	 */
	ATOMIC_MASS_CONSTANT(1.6605390666e-27, 0.00000000050e-27, SiBaseUnit.KILOGRAM),

	/**
	 * The Avogadro constant (usually denoted <i>N</i><sub>A</sub>) is named after
	 * Amedeo Avogadro. It is the factor that yields in combination with the amount
	 * of a substance (in {@link SiBaseUnit#MOLE mole} mole) the number of particles
	 * of that substance (typically atoms, ions or molecules). The value of this
	 * constant was chosen so that the mass of one mole of a chemical compound, in
	 * grams, is numerically (approximately) equal to the average mass of one
	 * nuclide or molecule of the chemical compound, in terms of the
	 * {@link #ATOMIC_MASS_CONSTANT atomic mass constant}.
	 */
	AVOGADRO_CONSTANT(6.02214076e23, 0.0, Unit.of("mol^-1")),

	/**
	 * The Bohr magneton (usually denoted <i>µ</i><sub>B</sub>) is named after Niels
	 * Bohr. It is the magnitude of the magnetic moment created by the orbital
	 * angular momentum of an electron with an orbital angular momentum quantum
	 * number of 1. For the history of the Bohr model see
	 * <a href="https://doi.org/10.2307/27757291">here</a>.
	 */
	BOHR_MAGNETON(9.2740100783e-24, 0.0000000028e-24, Unit.of("J T^-1")),

	/**
	 * The Bohr radius (usually denoted <i>a</i><sub>0</sub>) is named after Niels
	 * Bohr. It is the radius of the first electron shell of a hydrogen atom in its
	 * ground state in view of the Bohr model (see
	 * <a href="https://doi.org/10.2307/27757291">here</a> for historic background).
	 * This corresponds to the maximum of the radial probability density to find the
	 * electron. Note that the expected location of the electron is at a notably
	 * larger radius, though. The Small correction arising from taking into account
	 * that the nucleus is also moving around a common center is neglected for the
	 * Bohr radius.
	 */
	BOHR_RADIUS(5.29177210903e-11, 0.00000000080e-11, SiBaseUnit.METER),

	/**
	 * The Boltzmann constant (often abbreviated as <i>k</i><sub>B</sub> or
	 * <i>k</i>) is named after Ludwig Boltzmann. It relates the average relative
	 * kinetic energy of particles in a gas with the temperature of the gas. Its
	 * dimension is the same as the dimension of entropy. The Boltzmann constant
	 * <a href="https://doi.org/10.1088/1681-7575/ab0013">is used to define a base
	 * SI unit</a>, the {@link SiBaseUnit#KELVIN kelvin}.
	 */
	BOLTZMANN_CONSTANT(1.380649e-23, 0.0, Unit.of("J K^-1")),

	/**
	 * The characteristic impedance of vacuum relates the electric and magnetic
	 * fields of a plane wave traveling through free space.
	 */
	CHARACTERISTIC_IMPEDANCE_OF_VACUUM(376.730313668, 0.000000057, SiDerivedUnit.OHM),

	/**
	 * The classical electron radius ties the classical electrostatic
	 * self-interaction energy of a homogeneous charge distribution to the
	 * electron's rest energy. As far as we know today, the elctron is a point
	 * particle (i.e. it has no spatial extent) with a point charge.
	 */
	CLASSICAL_ELECTRON_RADIUS(2.8179403262e-15, 0.0000000013e-15, SiBaseUnit.METER),

	/**
	 * The Compton wavelength of the electron is a characteristic quantum mechanical
	 * property of it. It describes the wavelength increase of a photon scattered at
	 * a right angle at the electron. It is named after Artur Compton, who used it
	 * to <a href="https://doi.org/10.1103/PhysRev.21.483">describe</a> the
	 * scattering of photons by electrons.
	 */
	COMPTON_WAVELENGTH(2.42631023867e-12, 0.00000000073e-12, SiBaseUnit.METER),

	/**
	 * The conductance quantum appears e.g. when
	 * <a href="https://doi.org/10.1103%2FPhysRevLett.60.848">measuring the
	 * conductance of a quantum point contact</a>. It does not imply that the
	 * conductance of any system must be an integer multiple of it.
	 * 
	 * An intuitive derivation can be found <a href=
	 * "https://en.wikipedia.org/wiki/Conductance_quantum#Motivation_from_the_uncertainty_principle">here</a>.
	 */
	CONDUCTANCE_QUANTUM(7.748091729e-5, 0.0, SiDerivedUnit.SIEMENS),

	/**
	 * The conventional value of the Josephson constant (commonly denoted
	 * <i>K</i><sub>J-90</sub>) was defined as a fixed value from 1990 onwards (see
	 * <a href="https://www.bipm.org/en/CIPM/db/1988/1/">here</a> and
	 * <a href="https://doi.org/10.1088/0026-1394/26/1/006">here</a>) to increase
	 * the precision of measurements, as the Josephson effect could be measured very
	 * precisely. This, together with the
	 * {@link #HYPERFINE_TRANSITION_FREQUENCY_OF_CS_133 hyperfine transition
	 * frequency of caesium 133} and the
	 * {@link #CONVENTIONAL_VALUE_OF_VON_KLITZING_CONSTANT conventional value of the
	 * von Klitzing constant}, define the {@link ConventionalElectricalUnit
	 * conventional electrical units}. As of 2019, the
	 * <a href="https://doi.org/10.1088/1681-7575/ab0013">redefinition</a> of the SI
	 * units leads to a fixed, exact ratio when converting from/to SI units.
	 */
	CONVENTIONAL_VALUE_OF_JOSEPHSON_CONSTANT(4.835979e14, 0.0, Unit.of("Hz V^-1")),

	/**
	 * The conventional value of the von Klitzing constant (commonly denoted
	 * <i>R</i><sub>K-90</sub>) was defined as a fixed value from 1990 onwards (see
	 * <a href="https://www.bipm.org/en/CIPM/db/1988/2/">here</a> and
	 * <a href="https://doi.org/10.1088/0026-1394/26/1/006">here</a>) to increase
	 * the precision of measurements, as the quantum Hall effect could be measured
	 * very precisely. This, together with the
	 * {@link #HYPERFINE_TRANSITION_FREQUENCY_OF_CS_133 hyperfine transition
	 * frequency of caesium 133} and the
	 * {@link #CONVENTIONAL_VALUE_OF_JOSEPHSON_CONSTANT conventional Josephson
	 * constant}, defines the {@link ConventionalElectricalUnit conventional
	 * electrical units}. As of 2019, the
	 * <a href="https://doi.org/10.1088/1681-7575/ab0013">redefinition</a> of the SI
	 * units leads to a fixed, exact ratio when converting from/to SI units.
	 */
	CONVENTIONAL_VALUE_OF_VON_KLITZING_CONSTANT(25812.807, 0.0, SiDerivedUnit.OHM),

	/**
	 * The copper x unit is defined so that the wavelength of the Kα1 line of copper
	 * is exactly 1537.400 xu(Cu-Kα1), with xu(Cu-Kα1) being the copper x unit (see
	 * also <a href="https://doi.org/10.1088/1681-7575/aa99bc">here</a>). The copper
	 * Kα1 line is often used in X-ray crystallography. It is commonly used for the
	 * wavelengths of X- and gamma rays.
	 */
	COPPER_X_UNIT(1.00207697e-13, 0.00000028e-13, SiBaseUnit.METER),

	/**
	 * The nucleus of deuterium is called deuteron. Its g factor relates its
	 * {@link #DEUTERON_MAG_MOM magnetic moment} to its angular momentum quantum
	 * number and the {@link #NUCLEAR_MAGNETON nuclear magneton}.
	 */
	DEUTERON_G_FACTOR(0.8574382338, 0.0000000022, Units.EMPTY_UNIT),

	/**
	 * The nucleus of deuterium is called deuteron. This constant holds the magnetic
	 * moment of the deuteron.
	 */
	DEUTERON_MAG_MOM(4.330735094e-27, 0.000000011e-27, Unit.of("J T^-1")),

	/**
	 * The mass of a deuterium nucleus. Note that this is not just the mass of a
	 * proton and a neutron as the binding energy needs to be taken into account as
	 * well.
	 */
	DEUTERON_MASS(3.3435837724e-27, 0.0000000010e-27, SiBaseUnit.KILOGRAM),

	/**
	 * Similar like the radius for an entire atom, defining a radius for a nucleus
	 * (here, the deuterium nucleus) is problematic as no definite boundaries exist.
	 * However, one can model the nucleus as a positively charged sphere to
	 * interpret electron scattering experiments. The electrons experience a range
	 * of cross sections, for which one can find the mean. The "rms" (root mean
	 * square) is used as the nuclear cross section, which is proportional to the
	 * radius squared, is determining to the electron scattering.
	 */
	DEUTERON_RMS_CHARGE_RADIUS(2.12799e-15, 0.00074e-15, SiBaseUnit.METER),

	/**
	 * The electron g factor relates its {@link #ELECTRON_MAG_MOM magnetic moment}
	 * to its angular momentum quantum number and the {@link #BOHR_MAGNETON Bohr
	 * magneton}.
	 */
	ELECTRON_G_FACTOR(-2.00231930436256, 0.00000000000035, Units.EMPTY_UNIT),

	/** This constant holds the magnetic moment of the electron. */
	ELECTRON_MAG_MOM(-9.2847647043e-24, 0.0000000028e-24, Unit.of("J T^-1")),

	/**
	 * In quantum electrodynamics (QED), the anomalous magnetic moment of the
	 * electron stems from quantum mechanical contributions (expressible via Feynman
	 * diagrams with loops) to the magnetic moment.
	 * 
	 * It is defined by <i>a</i><sub>e</sub> = (<i>g</i><sub>e</sub> - 2) / 2. In
	 * there, <i>g</i><sub>e</sub> is the {@link #ELECTRON_MAG_MOM magnetic moment}.
	 */
	ELECTRON_MAG_MOM_ANOMALY(1.15965218128e-3, 0.00000000018e-3, Units.EMPTY_UNIT),

	/** The mass of an electron. */
	ELECTRON_MASS(9.1093837015e-31, 0.0000000028e-31, SiBaseUnit.KILOGRAM),

	/**
	 * The elementary charge, often denoted by <i>e</i> or sometimes
	 * <i>q</i><sub>e</sub>, is the single proton electric charge or, equivalently,
	 * the absolute value of the negative electric charge carried by a single
	 * electron. The elementary charge
	 * <a href="https://doi.org/10.1088/1681-7575/ab0013">is used to define a base
	 * SI unit</a>, the {@link SiBaseUnit#AMPERE ampere}.
	 */
	ELEMENTARY_CHARGE(1.602176634e-19, 0.0, SiDerivedUnit.COULOMB),

	/**
	 * The Faraday constant represents the absolute value of electric charge per
	 * mole of electrons.
	 */
	FARADAY_CONSTANT(96485.33212, 0.0, Unit.of("C mol^-1")),
	
	/**
	 * The strength of the Fermi interaction is determined by the Fermi coupling
	 * constant. The constant is related to the Higgs vacuum expectation value.
	 * 
	 * @see <a href="https://doi.org/10.1103/PhysRevD.98.030001">Review of the
	 *      particle data group</a>
	 */
	FERMI_COUPLING_CONSTANT(1.1663787e-5, 0.0000006e-5, Unit.of("GeV^-2")),

	/**
	 * The fine structure constant (often denoted α), also known as Sommerfeld's
	 * constant (as he introduced the constant in
	 * <a href="https://doi.org/10.1002/andp.19163561702">1916</a>), characterizes
	 * the strength of the electromagnetic force.
	 */
	FINE_STRUCTURE_CONSTANT(7.2973525693e-3, 0.0000000011e-3, Units.EMPTY_UNIT),

	/**
	 * The first radiation constant, often denoted <i>c</i><sub>1</sub>, and the
	 * {@link #SECOND_RADIATION_CONSTANT second radiation constant} can be used to
	 * simplify Plancks law. Note that the first radiation constant is used to
	 * calculate Planck's law for the spectral radiant exitance. For Planck's law
	 * for the spectral radiance <i>c</i><sub>1<i>L</i></sub>=<i>c</i><sub>1</sub>/π
	 * has to be used.
	 * 
	 * @see <a href=
	 *      "https://en.wikipedia.org/wiki/Planck%27s_law#First_and_second_radiation_constants">Wikipedia</a>
	 */
	FIRST_RADIATION_CONSTANT(3.741771852e-16, 0.0, Unit.of("W m^2")),

	/**
	 * The Hartree energy is a physical constant that is used in the
	 * {@link AtomicUnit atomic units}.
	 * 
	 * Douglas Rayner Hartree defined the energy nowadays called Hartree energy in
	 * his book „The calculation of atomic structures“ (see Wiley, New York, NY 1957
	 * (IX, 181 pages, chapter „Atomic Units“ on page 5: „Unit of energy (...) the
	 * mutual potential energy of two unit charges at unit distance appart.“).
	 */
	HARTREE_ENERGY(4.3597447222071e-18, 0.0000000000085e-18, SiDerivedUnit.JOULE),

	/**
	 * The nucleus of helium 3 is called helion. Its g factor relates its
	 * {@link #HELION_MAG_MOM magnetic moment} to its angular momentum quantum
	 * number and the {@link #NUCLEAR_MAGNETON nuclear magneton}.
	 */
	HELION_G_FACTOR(-4.255250615, 0.000000050, Units.EMPTY_UNIT),

	/**
	 * This constant holds the magnetic moment of the helion, a helium 3 nucleus.
	 */
	HELION_MAG_MOM(-1.074617532e-26, 0.000000013e-26, Unit.of("J T^-1")),

	/**
	 * The mass of a helium 3 nucleus. Note that this is not just the mass of a 2
	 * protons and a neutron as the binding energy needs to be taken into account as
	 * well.
	 */
	HELION_MASS(5.0064127796e-27, 0.0000000015e-27, SiBaseUnit.KILOGRAM),

	/**
	 * The magnetic moment of the nucleus (here, a helium 3 nucleus, also known as
	 * helion) couples weaker to an external magnetic field if the nucleus is in a
	 * closed shell atom, as the electrons produce a partial shielding when compared
	 * to the free nucleus. This shielding is quantified by the dimensionless
	 * constant <i>σ</i> in the equation <i>H</i>=-<i>µ·B</i>(1-<i>σ</i>).
	 * 
	 * @see <a href="https://doi.org/10.1063/1.3159674">The paper in which this
	 *      constant was calculated</a>
	 * @see <a href="https://doi.org/10.1063/1.4954402">The CODATA 2014
	 *      reference</a>
	 * @see #SHIELDED_HELION_MAG_MOM shielded helion magnetic moment
	 */
	HELION_SHIELDING_SHIFT(5.996743e-5, 0.000010e-5, Units.EMPTY_UNIT),

	/**
	 * The unperturbed ground-state hyperfine transition frequency
	 * ∆<i>ν</i><sub>Cs</sub> of the caesium atom. This constant
	 * <a href="https://doi.org/10.1088/1681-7575/ab0013">is used to define a base
	 * SI unit</a>, the {@link SiBaseUnit#SECOND second}.
	 */
	HYPERFINE_TRANSITION_FREQUENCY_OF_CS_133(9.19263177e9, 0.0, SiDerivedUnit.HERTZ),

	/**
	 * The inverse {@link #MAG_FLUX_QUANTUM magnetic flux quantum} is the Josephson
	 * constant, often denoted <i>K<sub>J</sub></i>. It relates the potential
	 * difference across a Josephson junction to the irradiation frequency.
	 * 
	 * The first experimental evidence for flux quantization was found in 1961 by
	 * <a href="https://doi.org/10.1103%2FPhysRevLett.7.43">Bascom S. Deaver, Jr.
	 * and William M. Fairbank</a> and
	 * <a href="https://doi.org/10.1103%2FPhysRevLett.7.51">R. Doll and M.
	 * Näbauer</a>.
	 */
	JOSEPHSON_CONSTANT(483597.8484e9, 0.0, Unit.of("Hz V^-1")),

	/**
	 * The lattice parameter (unit cell edge length, often denoted
	 * <i>a</i><sub>0</sub>) of an ideal single crystal of naturally occurring
	 * silicon, free of impurities and imperfections, and is deduced from
	 * measurements on extremely pure and nearly perfect single crystals of silicon.
	 * The effects of effects of impurities are corrected for. It is related to the
	 * {@link #LATTICE_SPACING_OF_IDEAL_SI_220 lattice spacing of ideal silicon
	 * (220)} by the equation
	 * <i>a</i><sub>0</sub>=8<sup>0.5</sup><i>d</i><sub>220</sub>.
	 * 
	 * @see <a href="https://doi.org/10.1088/1681-7575/aa99bc">CODATA data analysis,
	 *      2017</a>
	 * @see <a href="https://doi.org/10.1088/1681-7575/aa76af">A new analysis for
	 *      diffraction correction in optical interferometry, 2017</a>
	 * @see <a href="https://doi.org/10.1088/1367-2630/11/5/053013">Measurement of
	 *      the lattice parameter of a silicon crystal, 2009</a>
	 */
	LATTICE_PARAMETER_OF_SILICON(5.431020511e-10, 0.000000089e-10, SiBaseUnit.METER),

	/**
	 * The lattice spacing of an ideal silicon crystal (often denoted
	 * <i>d</i><sub>220</sub>). It is related to the
	 * {@link #LATTICE_PARAMETER_OF_SILICON lattice parameter of silicon} by the
	 * equation <i>a</i><sub>0</sub>=8<sup>0.5</sup><i>d</i><sub>220</sub>.
	 * 
	 * @see <a href="https://doi.org/10.1088/1681-7575/aa99bc">CODATA data analysis,
	 *      2017</a>
	 * @see <a href="https://doi.org/10.1088/1681-7575/aa76af">A new analysis for
	 *      diffraction correction in optical interferometry, 2017</a>
	 * @see <a href="https://doi.org/10.1088/1367-2630/11/5/053013">Measurement of
	 *      the lattice parameter of a silicon crystal, 2009</a>
	 */
	LATTICE_SPACING_OF_IDEAL_SI_220(1.920155716e-10, 0.000000032e-10, SiBaseUnit.METER),

	/**
	 * The Loschmidt constant (denoted <i>n</i><sub>0</sub>) corresponds to the
	 * number of molecules, <i>N</i>, per volume of an ideal gas under standard
	 * conditions, <i>V</i><sub>0</sub>, i.e.
	 * <i>n</i><sub>0</sub>=<i>N</i>/<i>V</i><sub>0</sub>. The constant is based on
	 * Loschmidt's <a href=
	 * "https://books.google.de/books?id=ppEAAAAAYAAJ&pg=PA395#v=onepage">work</a>
	 * „Zur Größe der Luftmoleküle“ (On the Size of Air Molecules), published in
	 * 1866. The value of this constant is valid at standard temperature and
	 * {@link #STANDARD_STATE_PRESSURE standard state pressure} as defined by the
	 * <a href="https://doi.org/10.1351/goldbook.">IUPAC</a> since 1982. The
	 * {@link #LOSCHMIDT_CONSTANT_273_15_K_101_325_KPA Loschmidt constant at 273.15K
	 * and 101.325kPa} uses the "old" standard temperature and pressure values as
	 * they were recommended by IUPAC pre-1982.
	 */
	LOSCHMIDT_CONSTANT_273_15_K_100_KPA(2.651645804e25, 0.0, Unit.of("m^-3")),

	/**
	 * The Loschmidt constant (denoted <i>n</i><sub>0</sub>) corresponds to the
	 * number of molecules, <i>N</i>, per volume of an ideal gas under standard
	 * conditions, <i>V</i><sub>0</sub>, i.e.
	 * <i>n</i><sub>0</sub>=<i>N</i>/<i>V</i><sub>0</sub>. The constant is based on
	 * Loschmidt's <a href=
	 * "https://books.google.de/books?id=ppEAAAAAYAAJ&pg=PA395#v=onepage">work</a>
	 * „Zur Größe der Luftmoleküle“ (On the Size of Air Molecules), published in
	 * 1866. The value of this constant is valid at standard temperature and
	 * {@link #STANDARD_ATMOSPHERE standard atmosphere} as defined by the IUPAC
	 * pre-1982. The {@link #LOSCHMIDT_CONSTANT_273_15_K_100_KPA Loschmidt constant
	 * at 273.15K and 100kPa} uses the new standard temperature and pressure values
	 * as they are recommended by
	 * <a href="https://doi.org/10.1351/goldbook.">IUPAC</a> since 1982.
	 */
	LOSCHMIDT_CONSTANT_273_15_K_101_325_KPA(2.686780111e25, 0.0, Unit.of("m^-3")),

	/**
	 * The defined luminous efficacy (which generally is a measure of how well a
	 * light source produces visible light) of monochromatic radiation of frequency
	 * 540THz. This constant <a href="https://doi.org/10.1088/1681-7575/ab0013">is
	 * used to define a base SI unit</a>, the {@link SiBaseUnit#CANDELA candela}.
	 */
	LUMINOUS_EFFICACY(683.0, 0.0, Unit.of("lm W^-1")),

	/**
	 * The inverse {@link #JOSEPHSON_CONSTANT Jospehson constant} is the magnetic
	 * flux quantum, often denoted <i>Φ</i><sub>0</sub>. The magnetic flux threading
	 * a superconducting loop is quantized by magnetic flux quanta.
	 * 
	 * The first experimental evidence for flux quantization was found in 1961 by
	 * <a href="https://doi.org/10.1103%2FPhysRevLett.7.43">Bascom S. Deaver, Jr.
	 * and William M. Fairbank</a> and
	 * <a href="https://doi.org/10.1103%2FPhysRevLett.7.51">R. Doll and M.
	 * Näbauer</a>.
	 */
	MAG_FLUX_QUANTUM(2.067833848e-15, 0.0, SiDerivedUnit.WEBER),

	/**
	 * The molar gas constant (denoted <i>R</i>) is the product of the
	 * {@link #AVOGADRO_CONSTANT Avogadro constant} and the
	 * {@link #BOLTZMANN_CONSTANT Boltzmann constant}. It appears e.g. in the
	 * <a href="https://en.wikipedia.org/wiki/Ideal_gas_law">ideal gas law</a>.
	 */
	MOLAR_GAS_CONSTANT(8.314462618, 0.0, Unit.of("J mol^-1 K^-1")),

	/**
	 * The molar mass constant (denoted <i>M</i><sub>u</sub>) is the ratio of the
	 * molar mass of a chemical compound and its relative mass. The SI redefinition
	 * in <a href="https://doi.org/10.1088/1681-7575/ab0013">2019</a>, changed the
	 * definition of the mole, hence the molar mass constant is no longer 1 g
	 * mol^-1.
	 */
	MOLAR_MASS_CONSTANT(9.9999999965e-4, 0.00000000030e-3, Unit.of("kg mol^-1")),

	/**
	 * The molar mass of carbon. The SI redefinition in
	 * <a href="https://doi.org/10.1088/1681-7575/ab0013">2019</a>, changed the
	 * definition of the mole, since then the molar mass of carbon is no longer
	 * exactly 12 g mol^-1.
	 */
	MOLAR_MASS_OF_CARBON_12(0.0119999999958, 0.0000000036e-3, Unit.of("kg mol^-1")),

	/**
	 * The molar volume (denoted <i>V</i><sub>m</sub>) of an ideal gas can be
	 * determined via the ideal gas equation:
	 * <i>V</i><sub>m</sub>=<i>RT</i>/<i>P</i> with <i>R</i> the
	 * {@link #MOLAR_GAS_CONSTANT molar gas constant}, <i>T</i> the temperature and
	 * <i>P</i> the pressure. The value of this constant is valid at standard
	 * temperature and {@link #STANDARD_STATE_PRESSURE} as defined by the
	 * <a href="https://doi.org/10.1351/goldbook.">IUPAC</a> since 1982.
	 * {@link #MOLAR_VOLUME_OF_IDEAL_GAS_273_15_K_101_325_KPA molar volume of an
	 * ideal gas at 273.15K and 101.325kPa} uses the "old" standard temperature and
	 * pressure values as they were recommended by IUPAC pre-1982. The value of this
	 * constant is a good approximation for many real gases at standard temperature
	 * and pressure.
	 */
	MOLAR_VOLUME_OF_IDEAL_GAS_273_15_K_100_KPA(0.02271095464, 0.0, Unit.of("m^3 mol^-1")),

	/**
	 * The molar volume (denoted <i>V</i><sub>m</sub>) of an ideal gas can be
	 * determined via the ideal gas equation:
	 * <i>V</i><sub>m</sub>=<i>RT</i>/<i>P</i> with <i>R</i> the
	 * {@link #MOLAR_GAS_CONSTANT molar gas constant}, <i>T</i> the temperature and
	 * <i>P</i> the pressure. The value of this constant is valid at standard
	 * temperature and {@link #STANDARD_ATMOSPHERE} as defined by the IUPAC
	 * pre-1982. {@link #MOLAR_VOLUME_OF_IDEAL_GAS_273_15_K_100_KPA molar volume of
	 * an ideal gas at 273.15K and 100kPa} uses the new standard temperature and
	 * pressure values as they are recommended by
	 * <a href="https://doi.org/10.1351/goldbook.">IUPAC</a> since 1982. The value
	 * of this constant is a good approximation for many real gases at standard
	 * temperature and pressure.
	 */
	MOLAR_VOLUME_OF_IDEAL_GAS_273_15_K_101_325_KPA(0.02241396954, 0.0, Unit.of("m^3 mol^-1")),

	/**
	 * The molar volume (denoted <i>V</i><sub>m</sub>) is the volume occupied by one
	 * mole of a substance. This constant yields the molar volume of silicon. It is
	 * related to the {@link #AVOGADRO_CONSTANT Avogadro constant}.
	 */
	MOLAR_VOLUME_OF_SILICON(1.205883199e-5, 0.000000060e-5, Unit.of("m^3 mol^-1")),

	/**
	 * The molybdenum x unit is defined so that the wavelength of the Kα1 line of
	 * molybdenum is exactly 707.831 xu(Mo-Kα1), with xu(Mo-Kα1) being the
	 * molybdenum x unit (see also
	 * <a href="https://doi.org/10.1088/1681-7575/aa99bc">here</a>). The molybdenum
	 * Kα1 line is often used in X-ray crystallography. It is commonly used for the
	 * wavelengths of X- and gamma rays.
	 */
	MOLYBDENUM_X_UNIT(1.00209952e-13, 0.00000053e-13, SiBaseUnit.METER),

	/**
	 * The Compton wavelength of the muon is a characteristic quantum mechanical
	 * property of it. It describes the wavelength increase of a photon scattered at
	 * a right angle at the muon.
	 * 
	 * @see #COMPTON_WAVELENGTH Compton wavelength
	 */
	MUON_COMPTON_WAVELENGTH(1.173444110e-14, 0.000000026e-14, SiBaseUnit.METER),

	/**
	 * The electron g factor relates its {@link #MUON_MAG_MOM magnetic moment} to
	 * its angular momentum quantum number and the {@link #BOHR_MAGNETON Bohr
	 * magneton}. While it is close to the {@link #ELECTRON_G_FACTOR electron g
	 * factor}, there is a small deviation. Most of it can be understood with
	 * quantum electrodynamics. However, a small fraction of the deviation is not
	 * yet understood and could point to physics beyond the standard model.
	 */
	MUON_G_FACTOR(-2.0023318418, 0.0000000013, Units.EMPTY_UNIT),

	/**
	 * The magnetic moment of the muon (note also its {@link #MUON_MAG_MOM_ANOMALY
	 * magnetic moment anomaly}).
	 * 
	 * @see <a href="https://doi.org/10.1103/PhysRevD.98.030001">Review of the
	 *      particle data group</a>
	 * @see <a href=
	 *      "http://pdg.lbl.gov/2019/listings/rpp2019-list-muon.pdf">Comments about
	 *      the muon</a>
	 */
	MUON_MAG_MOM(-4.4904483e-26, 0.00000010e-26, Unit.of("J T^-1")),

	/**
	 * In quantum electrodynamics (QED), the anomalous magnetic moment of the muon
	 * stems from quantum mechanical contributions (expressable via Feynman diagrams
	 * with loops) to the magnetic moment.
	 * 
	 * It is defined by <i>a</i><sub>µ</sub> = (<i>g</i><sub>µ</sub> - 2) / 2. In
	 * there, <i>g</i><sub>µ</sub> is the {@link #MUON_MAG_MOM magnetic moment of
	 * the muon}.
	 * 
	 * @see <a href="https://doi.org/10.1103/PhysRevD.98.030001">Review of the
	 *      particle data group</a>
	 * @see <a href=
	 *      "http://pdg.lbl.gov/2019/reviews/rpp2018-rev-g-2-muon-anom-mag-moment.pdf">Explicit
	 *      muon anomalous magnetic moment comments</a>
	 */
	MUON_MAG_MOM_ANOMALY(1.16592089e-3, 0.00000063e-3, Units.EMPTY_UNIT),

	/**
	 * The mass of the muon.
	 * 
	 * @see <a href="https://doi.org/10.1103/PhysRevD.98.030001">Review of the
	 *      particle data group</a>
	 * @see <a href=
	 *      "http://pdg.lbl.gov/2019/listings/rpp2019-list-muon.pdf">Comments about
	 *      the muon</a>
	 */
	MUON_MASS(1.883531627e-28, 0.000000042e-28, SiBaseUnit.KILOGRAM),

	/**
	 * The Compton wavelength of the neutron is a characteristic quantum mechanical
	 * property of it. It describes the wavelength increase of a photon scattered at
	 * a right angle at the neutron.
	 * 
	 * @see #COMPTON_WAVELENGTH Compton wavelength
	 */
	NEUTRON_COMPTON_WAVELENGTH(1.31959090581e-15, 0.00000000075e-15, SiBaseUnit.METER),

	/**
	 * The neutron has a g factor that relates its {@link #NEUTRON_MAG_MOM magnetic
	 * moment} to its angular momentum quantum number and the
	 * {@link #NUCLEAR_MAGNETON nuclear magneton}.
	 */
	NEUTRON_G_FACTOR(-3.82608545, 0.00000090, Units.EMPTY_UNIT),

	/**
	 * The magnetic moment of the neutron (usually denoted <i>μ</i><sub>n</sub>).
	 * 
	 * @see <a href="https://doi.org/10.1103/PhysRevD.98.030001">Review of the
	 *      particle data group</a>
	 * @see <a href= "http://pdg.lbl.gov/2018/listings/rpp2018-list-n.pdf">Comments
	 *      about the neutron</a>
	 */
	NEUTRON_MAG_MOM(-9.6623651e-27, 0.0000023e-27, Unit.of("J T^-1")),

	/**
	 * The mass of a neutron, see
	 * <a href="https://doi.org/10.1103/PhysRevLett.56.819">here</a> for a reference
	 * for neutron mass determination.
	 */
	NEUTRON_MASS(1.67492749804e-27, 0.00000000095e-27, SiBaseUnit.KILOGRAM),

	/**
	 * The constant of gravitation is the proportionality factor that is necessary
	 * to get the force with which two spherical bodies with masses
	 * <i>m</i><sub>1</sub> and <i>m</i><sub>2</sub> in distance <i>r</i> attract
	 * each other.
	 * 
	 * @see <a href="https://doi.org/10.1063/PT.3.2447">The search for Newtons
	 *      constant</a>
	 */
	NEWTONIAN_CONSTANT_OF_GRAVITATION(6.6743e-11, 0.00015e-11, Unit.of("m^3 kg^-1 s^-2")),

	/**
	 * The nuclear magneton (usually denoted <i>µ</i><sub>N</sub>) is the magnitude
	 * of the magnetic moment created by the orbital angular momentum of a proton
	 * with an orbital angular momentum quantum number of 1. If a proton would be a
	 * point particle, its {@link #PROTON_MAG_MOM magnetic moment} would be
	 * identical to the nuclear magneton. This was an early indication that protons
	 * are not point particles.
	 */
	NUCLEAR_MAGNETON(5.0507837461e-27, 0.0000000015e-27, Unit.of("J T^-1")),

	/**
	 * The Planck constant (commonly abbreviated as <i>h</i>, or <i>ħ</i> in its
	 * 'reduced' form (i.e. this constant divided by 2π)) is named after Max Planck
	 * and is the quantum of electromagnetic action, i.e. it relates the photon
	 * energy to its frequency. As energy and mass are equivalent, the Planck
	 * constant also relates mass to frequency. The Planck constant
	 * <a href="https://doi.org/10.1088/1681-7575/ab0013">is used to define a base
	 * SI unit</a>, the {@link SiBaseUnit#KILOGRAM kilogram}.
	 */
	PLANCK_CONSTANT(6.62607015e-34, 0.0, Unit.of("J Hz^-1")),

	/**
	 * The Planck length (commonly abbreviated as <i>l</i><sub>P</sub>) is the
	 * distance light travels in one {@link #PLANCK_TIME Planck time}. It also
	 * corresponds to the reduced {@link #COMPTON_WAVELENGTH Compton wavelength} of
	 * a particle with the {@link #PLANCK_MASS Planck mass}. It is a base unit in
	 * the system of {@link PlanckUnit Planck units}. It is the smallest distance
	 * about which current physics models can make (in principle) testable
	 * predictions.
	 */
	PLANCK_LENGTH(1.616255e-35, 0.000018e-35, SiBaseUnit.METER),

	/**
	 * The Planck mass (commonly abbreviated as <i>m</i><sub>P</sub>) is a base unit
	 * in the system of {@link PlanckUnit Planck units}.
	 */
	PLANCK_MASS(2.176434e-8, 0.000024e-8, SiBaseUnit.KILOGRAM),

	/**
	 * The Planck temperature (commonly abbreviated as <i>T</i><sub>P</sub>) is a
	 * base unit in the system of {@link PlanckUnit Planck units}. It is thought to
	 * be the upper limit of the possible temperatures.
	 */
	PLANCK_TEMPERATURE(1.416784e32, 0.000016e32, SiBaseUnit.KELVIN),

	/**
	 * The Planck time (commonly abbreviated as <i>t</i><sub>P</sub>) is the time it
	 * takes light to travel one {@link #PLANCK_LENGTH Planck length} in vacuum. It
	 * is a base unit in the system of {@link PlanckUnit Planck units}. It is the
	 * shortest time about which current physics models can make (in principle)
	 * testable predictions.
	 */
	PLANCK_TIME(5.391247e-44, 0.000060e-44, SiBaseUnit.SECOND),

	/**
	 * The Compton wavelength of the proton is a characteristic quantum mechanical
	 * property of it. It describes the wavelength increase of a photon scattered at
	 * a right angle at the proton.
	 * 
	 * @see #COMPTON_WAVELENGTH Compton wavelength
	 */
	PROTON_COMPTON_WAVELENGTH(1.32140985539e-15, 0.00000000040e-15, SiBaseUnit.METER),

	/**
	 * The proton has a g factor that relates its {@link #PROTON_MAG_MOM magnetic
	 * moment} to its angular momentum quantum number and the
	 * {@link #NUCLEAR_MAGNETON nuclear magneton}.
	 */
	PROTON_G_FACTOR(5.5856946893, 0.0000000016, Units.EMPTY_UNIT),

	/** The magnetic moment of the proton. */
	PROTON_MAG_MOM(1.41060679736e-26, 0.00000000060e-26, Unit.of("J T^-1")),

	/**
	 * The magnetic moment of the nucleus (here, a single proton, respectively a
	 * hydrogen nucleus) couples weaker to an external magnetic field if the nucleus
	 * is in a closed shell atom, as the electrons produce a partial shielding when
	 * compared to the free nucleus. This shielding is quantified by the
	 * dimensionless constant <i>σ</i> in the equation
	 * <i>µ</i><sub>bound</sub>=(1-<i>σ</i>)<i>µ</i><sub>free</sub>.
	 * 
	 * @see <a href="https://doi.org/10.1063/1.4954402">The CODATA 2014
	 *      reference</a>
	 */
	PROTON_MAG_SHIELDING_CORRECTION(2.5689e-5, 0.0011e-5, Units.EMPTY_UNIT),

	/**
	 * The mass of a proton. Besides the
	 * <a href="http://physics.nist.gov/cgi-bin/cuu/Value?mp">NIST reference</a>, an
	 * example for how the proton mass is determined can be found
	 * <a href="https://doi.org/10.1103/PhysRevLett.119.033001">here</a>.
	 */
	PROTON_MASS(1.67262192369e-27, 0.00000000051e-27, SiBaseUnit.KILOGRAM),

	/**
	 * Similar like the radius for an entire atom, defining a radius for a nucleus
	 * (here, only a proton) is problematic as no definite boundaries exist.
	 * However, one can model the proton as a positively charged sphere to interpret
	 * electron scattering experiments. The electrons experience a range of cross
	 * sections, for which one can find the mean. The "rms" (root mean square) is
	 * used as the nuclear cross section, which is proportional to the radius
	 * squared, is determining to the electron scattering.
	 */
	PROTON_RMS_CHARGE_RADIUS(8.414e-16, 0.019e-16, SiBaseUnit.METER),

	/**
	 * The quantisation of circulation was first observed by
	 * <a href="https://doi.org/10.1103/PhysRevLett.43.214">Yarmchuk et al.
	 * (1979)</a>. It plays a role e.g. in rotating superfluid helium (see Yarmchuks
	 * paper).
	 */
	QUANTUM_OF_CIRCULATION(3.6369475516e-4, 0.0000000011e-4, Unit.of("m^2 s^-1")),

	/**
	 * The Rydberg constant (denoted <i>R</i><sub>∞</sub>) was named after Johannes
	 * Rydberg. It first was an empirical fitting parameter in the Rydberg formula
	 * before Bohr found the combination of constants that yielded the value. The
	 * constant corresponds to the lowest-energy photon capable of ionizing a
	 * hydrogen atom from its ground state.
	 */
	RYDBERG_CONSTANT(10973731.568160, 0.000021, Unit.of("m^-1")),

	/**
	 * The Sackur Tetrode equation is a formula to calculate the entropy <i>S</i> of
	 * an ideal gas, see e.g. <a href=
	 * "https://en.wikipedia.org/wiki/Sackur%E2%80%93Tetrode_equation#Formula">here</a>.
	 * The Sackur Tetrode constant (denoted <i>S</i><sub>0</sub>/<i>R</i>)
	 * corresponds to the left hand side of the Sackur Tetrode equation at a
	 * temperature of 1 {@link SiBaseUnit#KELVIN kelvin}, at
	 * {@link #STANDARD_STATE_PRESSURE standard state pressure} (cf. the
	 * <a href="https://doi.org/10.1351/goldbook.">IUPAC</a> definition valid since
	 * 1982) for one mole of an ideal gas with particle masses equal to the
	 * {@link #UNIFIED_ATOMIC_MASS_UNIT unified atomic mass unit}. The
	 * {@link #SACKUR_TETRODE_CONSTANT_1_K_101_325_KPA Sackur-Tetrode constant at 1K
	 * and 101.325kPa} uses the "old" standard temperature and pressure values as
	 * they were recommended by IUPAC pre-1982.
	 */
	SACKUR_TETRODE_CONSTANT_1_K_100_KPA(-1.15170753706, 0.00000000045, Units.EMPTY_UNIT),

	/**
	 * The Sackur Tetrode equation is a formula to calculate the entropy <i>S</i> of
	 * an ideal gas, see e.g. <a href=
	 * "https://en.wikipedia.org/wiki/Sackur%E2%80%93Tetrode_equation#Formula">here</a>.
	 * The Sackur Tetrode constant (denoted <i>S</i><sub>0</sub>/<i>R</i>)
	 * corresponds to the left hand side of the Sackur Tetrode equation at a
	 * temperature of 1 {@link SiBaseUnit#KELVIN kelvin}, at
	 * {@link #STANDARD_ATMOSPHERE standard atmosphere} (the IUPAC definition valid
	 * before 1982) for one mole of an ideal gas with particle masses equal to the
	 * {@link #UNIFIED_ATOMIC_MASS_UNIT unified atomic mass unit}. The
	 * {@link #SACKUR_TETRODE_CONSTANT_1_K_100_KPA Sackur-Tetrode constant at 1K and
	 * 100kPa} uses the new standard temperature and pressure values as they are
	 * recommended by <a href="https://doi.org/10.1351/goldbook.">IUPAC</a> since
	 * 1982.
	 */
	SACKUR_TETRODE_CONSTANT_1_K_101_325_KPA(-1.16487052358, 0.00000000045, Units.EMPTY_UNIT),

	/**
	 * The second radiation constant, often denoted <i>c</i><sub>2</sub>, and the
	 * {@link #FIRST_RADIATION_CONSTANT first radiation constant} can be used to
	 * simplify Planck's law.
	 * 
	 * @see <a href=
	 *      "https://en.wikipedia.org/wiki/Planck%27s_law#First_and_second_radiation_constants">Wikipedia</a>
	 */
	SECOND_RADIATION_CONSTANT(1.43876877e-2, 0.0, Unit.of("m K")),

	/**
	 * The magnetic moment of a shielded helion (denoted <i>μ</i><sub>h</sub>'),
	 * i.e. a helium 3 nucleus bound in a helium 3 atom. The exact shape of the
	 * gaseous helium 3 sample should not matter, though it is assumed to be
	 * spherical, at 25°C and surrounded by vacuum. See
	 * <a href="https://doi.org/10.1063/1.4954402">CODATA (2014)</a>. Note that this
	 * value is up to date, not the one from 2014.
	 * 
	 * @see #HELION_SHIELDING_SHIFT helion shielding shift
	 */
	SHIELDED_HELION_MAG_MOM(-1.07455309e-26, 0.000000013e-26, Unit.of("J T^-1")),

	/**
	 * The magnetic moment of a proton (denoted <i>μ</i><sub>p</sub>') in a
	 * spherical sample of pure H<sub>2</sub>O at 25°C surrounded by vacuum. See
	 * <a href="https://doi.org/10.1063/1.4954402">CODATA (2014)</a>. Note that this
	 * value is up to date, not the one from 2014.
	 */
	SHIELDED_PROTON_MAG_MOM(1.41057056e-26, 0.000000015e-26, Unit.of("J T^-1")),

	/**
	 * This constant is defined by the difference of the shielding corrections for
	 * the deuteron and the proton, which are bound together to a HD molecule, i.e.
	 * <i>σ</i><sub>dp</sub>=<i>σ</i><sub>d</sub>(HD)-<i>σ</i><sub>p</sub>(HD).
	 * 
	 * See <a href="https://doi.org/10.1063/1.4954402">CODATA (2014)</a>. Note that
	 * this value is up to date, not the one from 2014.
	 */
	SHIELDING_DIFFERENCE_OF_D_AND_P_IN_HD(2.02e-8, 0.0020e-8, Units.EMPTY_UNIT),

	/**
	 * This constant is defined by the difference of the shielding corrections for
	 * the triton and the proton, which are bound together to a HT molecule, i.e.
	 * <i>σ</i><sub>tp</sub>=<i>σ</i><sub>t</sub>(HT)-<i>σ</i><sub>p</sub>(HT).
	 * 
	 * See <a href="https://doi.org/10.1063/1.4954402">CODATA (2014)</a>. Note that
	 * this value is up to date, not the one from 2014.
	 */
	SHIELDING_DIFFERENCE_OF_T_AND_P_IN_HT(2.414e-8, 0.0020e-8, Units.EMPTY_UNIT),

	/**
	 * The speed of light in vacuum, often denoted c, is a universal physical
	 * constant important in many areas of physics. It is exact in the International
	 * System of Units (SI) and is used to define the {@link SiBaseUnit#METER
	 * meter}. The speed of light in vacuum is the upper limit for the speed at
	 * which conventional matter and information can travel.
	 */
	SPEED_OF_LIGHT_IN_VACUUM(2.99792458e8, 0.0, Unit.of("m s^-1")),

	/**
	 * The standard acceleration of gravity (denoted <i>g</i><sub>0</sub> or
	 * <i>g</i><sub>n</sub>) was defined by the BIPM in
	 * <a href="https://www1.bipm.org/en/CGPM/db/3/2/">1901</a>. It is close to the
	 * acceleration of a free falling body at sea level at a geodetic latitude of
	 * 45°.
	 */
	STANDARD_ACCELERATION_OF_GRAVITY(9.80665, 0.0, Unit.of("m s^-2")),

	/**
	 * The atmospheric pressure is defined to be the roughly the average air
	 * pressure at sea level. Note the small difference to the
	 * {@link #STANDARD_STATE_PRESSURE standard state pressure}.
	 */
	STANDARD_ATMOSPHERE(101325.0, 0.0, SiDerivedUnit.PASCAL),

	/**
	 * The standard state pressure corresponds to 1 bar since
	 * <a href="https://doi.org/10.1351/goldbook">1982</a>. Note the small
	 * difference to the {@link #STANDARD_ATMOSPHERE standard atmosphere}.
	 */
	STANDARD_STATE_PRESSURE(100000.0, 0.0, SiDerivedUnit.PASCAL),

	/**
	 * The Stefan-Boltzmann law (see
	 * <a href="http://dx.doi.org/10.1002/andp.18842580616">here</a> for Boltzmanns
	 * derivation of Stefans empirically found law),
	 * <i>P</i>=<i>σAT</i><sup>4</sup>, relates the power <i>P</i> of a blackbody
	 * emitter with the blackbodys surface <i>A</i> and its temperature <i>T</i> to
	 * the power of 4. The proportionality constant, <i>σ</i>, is called
	 * Stefan-Boltzmann constant.
	 */
	STEFAN_BOLTZMANN_CONSTANT(5.670374419e-8, 0.0, Unit.of("W m^-2 K^-4")),

	/**
	 * The Compton wavelength of the tauon is a characteristic quantum mechanical
	 * property of it. It describes the wavelength increase of a photon scattered at
	 * a right angle at the tauon.
	 * 
	 * @see #COMPTON_WAVELENGTH Compton wavelength
	 */
	TAU_COMPTON_WAVELENGTH(6.97771e-16, 0.00047e-16, SiBaseUnit.METER),

	/**
	 * The mass of a tauon.
	 * 
	 * @see <a href="https://doi.org/10.1103/PhysRevD.98.030001">Review of the
	 *      particle data group</a>
	 */
	TAU_MASS(3.16754e-27, 0.00021e-27, SiBaseUnit.KILOGRAM),

	/**
	 * The scattering of photons with energies much smaller than the electrons at
	 * which they are scattered can be described by the Thomson cross section, named
	 * after J. J. Thomson. An important property of the Thomson cross section is
	 * that it is independent of the photon wavelength.
	 */
	THOMSON_CROSS_SECTION(6.6524587321e-29, 0.0000000060e-29, Unit.of("m^2")),

	/**
	 * The nucleus of tritium is called triton. Its g factor relates its
	 * {@link #TRITON_MAG_MOM magnetic moment} to its angular momentum quantum
	 * number and the {@link #NUCLEAR_MAGNETON nuclear magneton}.
	 */
	TRITON_G_FACTOR(5.957924931, 0.000000012, Units.EMPTY_UNIT),

	/** The magnetic moment of the triton, i.e. the tritium nucleus. */
	TRITON_MAG_MOM(1.5046095202e-26, 0.0000000030e-26, Unit.of("J T^-1")),

	/**
	 * The mass of tritium nucleus. Note that this is not just the mass of a proton
	 * and two neutrons as the binding energy needs to be taken into account as
	 * well.
	 */
	TRITON_MASS(5.0073567446e-27, 0.0000000015e-27, SiBaseUnit.KILOGRAM),

	/**
	 * The unified atomic mass unit (denoted u) is defined to be 1/12 of the mass of
	 * a carbon atom. This holds true after the
	 * <a href="https://doi.org/10.1088/1681-7575/ab0013">redefinition</a> of the SI
	 * units in 2019.
	 */
	UNIFIED_ATOMIC_MASS_UNIT(1.6605390666e-27, 0.00000000050e-27, SiBaseUnit.KILOGRAM),

	/**
	 * The vacuum electric permittivity (denoted <i>ε</i><sub>0</sub>), also called
	 * dielectric constant, vacuum permittivity or permittivity of free space
	 * describes the capability of an electric field to permeate a (classical)
	 * vacuum.
	 */
	VACUUM_ELECTRIC_PERMITTIVITY(8.8541878128e-12, 0.0000000013e-12, Unit.of("F m^-1")),

	/**
	 * The vacuum magnetic permeability (denoted <i>μ</i><sub>0</sub>), also called
	 * permeability of free space or magnetic constant describes the capability of a
	 * magnetic field to permeate a (classical) vacuum. This constant used to be
	 * exactly of the value 4π×10<sup>−7</sup>, but after the
	 * <a href="https://doi.org/10.1088/1681-7575/ab0013">redefinition</a> of the SI
	 * units in 2019 it has to be determined experimentally.
	 */
	VACUUM_MAG_PERMEABILITY(1.25663706212e-6, 0.00000000019e-6, Unit.of("N A^-2")),

	/**
	 * The von Klitzing constant (commonly denoted <i>R</i><sub>K</sub>) represents
	 * the quantum of the quantum Hall effect and is named after its discoverer,
	 * Klaus von Klitzing.
	 */
	VON_KLITZING_CONSTANT(25812.80745, 0.0, SiDerivedUnit.OHM),

	/**
	 * The value of the weak mixing angle (denoted <i>θ</i><sub>W</sub>), also known
	 * as Weinberg angle, is not predicted by the standard model of particle physics
	 * and consequently needs to be measured. Its cosine corresponds to the
	 * {@link #W_TO_Z_MASS_RATIO W to Z mass ratio}.
	 */
	WEAK_MIXING_ANGLE(0.2229, 0.00030, Units.EMPTY_UNIT),

	/**
	 * Wien's displacement law defines that the peak of the spectral radiance of
	 * black body radiation per unit frequency can be found by:
	 * <p>
	 * <i>ν</i><sub>peak</sub>=<i>b</i>'<i>T</i>,
	 * <p>
	 * With <i>T</i> the temperature and <i>b</i>' this constant. While the
	 * structure of the equation stays the same if one is considering wavelengths
	 * instead of frequencies, a different constant,
	 * {@link #WIEN_WAVELENGTH_DISPLACEMENT_LAW_CONSTANT the constant of Wiens
	 * wavelength displacement law}, needs to be used.
	 * 
	 * @see <a href=
	 *      "http://bibliothek.bbaw.de/bbaw/bibliothek-digital/digitalequellen/schriften/anzeige/index_html?band=10-sitz/1893-1&seite:int=70">Willy
	 *      Wiens „Eine neue Beziehung der Strahlung schwarzer Körper zum zweiten
	 *      Hauptsatz der Wärmetheorie“</a>
	 */
	WIEN_FREQUENCY_DISPLACEMENT_LAW_CONSTANT(5.878925757e10, 0.0, Unit.of("Hz K^-1")),

	/**
	 * Wien's displacement law defines that the peak of the spectral radiance of
	 * black body radiation per unit wavelength can be found by:
	 * <p>
	 * <i>λ</i><sub>peak</sub>=<i>b</i>/<i>T</i>,
	 * <p>
	 * With <i>T</i> the temperature and <i>b</i> this constant. While the structure
	 * of the equation stays the same if one is considering frequencies instead of
	 * wavelengths, a different constant,
	 * {@link #WIEN_FREQUENCY_DISPLACEMENT_LAW_CONSTANT the constant of Wiens
	 * frequency displacement law}, needs to be used.
	 * 
	 * @see <a href=
	 *      "http://bibliothek.bbaw.de/bbaw/bibliothek-digital/digitalequellen/schriften/anzeige/index_html?band=10-sitz/1893-1&seite:int=70">Willy
	 *      Wiens „Eine neue Beziehung der Strahlung schwarzer Körper zum zweiten
	 *      Hauptsatz der Wärmetheorie“</a>
	 */
	WIEN_WAVELENGTH_DISPLACEMENT_LAW_CONSTANT(0.002897771955, 0.0, Unit.of("m K")),

	/**
	 * The ratio of the W boson mass and the Z boson mass. It is closely related to
	 * the {@link #WEAK_MIXING_ANGLE weak mixing angle}.
	 */
	W_TO_Z_MASS_RATIO(0.88153, 0.00017, Units.EMPTY_UNIT);

	/** The numeric value of the constant. */
	private final double value;

	/** The uncertainty of the numeric value of the constant. */
	private final double uncertainty;

	/** The units in which the numeric value of the constant is expressed. */
	private final Unit units;

	/**
	 * Constructs a physics constant.
	 * 
	 * @param value       the value of the constant
	 * @param uncertainty the uncertainty of the constant
	 * @param units       the units of the constant
	 */
	private PhysicsConstant(double value, double uncertainty, Unit units) {
		this.value = value;
		this.uncertainty = uncertainty;
		this.units = units;
	}

	/**
	 * Gets a textual representation of this constant.
	 * 
	 * @return the textual representation of this constant
	 */
	@Override
	public String toString() {
		return Constant.toString(this);
	}

	@Override public double value() { return value; }
	@Override public double uncertainty() { return uncertainty; }
	@Override public Unit unit() { return units; }
}
