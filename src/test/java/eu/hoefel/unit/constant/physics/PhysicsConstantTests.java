package eu.hoefel.unit.constant.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;
import eu.hoefel.unit.constant.Constant;
import eu.hoefel.unit.si.SiBaseUnit;
import eu.hoefel.unit.si.SiDerivedUnit;

/**
 * This class intends to test the physics constants (only very roughly, so one
 * catches mistakes when updating the values).
 * 
 * @author Udo Hoefel
 */
@DisplayName("Physics constants")
@SuppressWarnings("javadoc")
class PhysicsConstantTests {

	@DisplayName("Tests toString")
	@Test
	void testToString() {
		assertEquals("(6.6446573357e-27 ± 2.0e-36) kg", PhysicsConstant.ALPHA_PARTICLE_MASS.toString());
	}

	@DisplayName("Testing SI conversions")
	@Test
	void testConversion() {
		assertEquals(11604.518121550082, Constant.of(1, 0, "eV").div(PhysicsConstant.BOLTZMANN_CONSTANT).inUnitsOf("K").value());
		assertEquals(1.0792528488e9, PhysicsConstant.SPEED_OF_LIGHT_IN_VACUUM.inUnitsOf("km h^-1").value());
	}

	@DisplayName("Tests the alpha particle mass")
	@Test
	void testAlphaParticleMass() {
		assertEquals(6.6446e-27, PhysicsConstant.ALPHA_PARTICLE_MASS.value(), 1e-31);
		assertEquals(SiBaseUnit.KILOGRAM, PhysicsConstant.ALPHA_PARTICLE_MASS.unit());
	}

	@DisplayName("Tests Angstrom*")
	@Test
	void testAngstromStar() {
		assertEquals(1.000014e-10, PhysicsConstant.ANGSTROM_STAR.value(), 1e-16);
		assertEquals(SiBaseUnit.METER, PhysicsConstant.ANGSTROM_STAR.unit());
	}

	@DisplayName("Tests the atomic mass constant")
	@Test
	void testAtomicMassConstant() {
		assertEquals(1.660e-27, PhysicsConstant.ATOMIC_MASS_CONSTANT.value(), 1e-30);
		assertEquals(SiBaseUnit.KILOGRAM, PhysicsConstant.ATOMIC_MASS_CONSTANT.unit());
	}

	@DisplayName("Tests the Avogadro constant")
	@Test
	void testAvogadroConstant() {
		assertEquals(6.022e23, PhysicsConstant.AVOGADRO_CONSTANT.value(), 1e20);
		assertEquals(Unit.of("mol^-1"), PhysicsConstant.AVOGADRO_CONSTANT.unit());
	}

	@DisplayName("Tests the Bohr magneton")
	@Test
	void testBohrMagneton() {
		assertEquals(9.2740e-24, PhysicsConstant.BOHR_MAGNETON.value(), 1e-28);
		assertEquals(Unit.of("J T^-1"), PhysicsConstant.BOHR_MAGNETON.unit());
	}

	@DisplayName("Tests the Bohr radius")
	@Test
	void testBohrRadius() {
		assertEquals(5.2917e-11, PhysicsConstant.BOHR_RADIUS.value(), 1e-15);
		assertEquals(SiBaseUnit.METER, PhysicsConstant.BOHR_RADIUS.unit());
	}

	@DisplayName("Tests the Bohr radius")
	@Test
	void testBoltzmannConstant() {
		assertEquals(1.380e-23, PhysicsConstant.BOLTZMANN_CONSTANT.value(), 1e-26);
		assertEquals(Unit.of("J K^-1"), PhysicsConstant.BOLTZMANN_CONSTANT.unit());
	}

	@DisplayName("Tests the characteristic impedance of vacuum")
	@Test
	void testCharacteristicImpedance() {
		assertEquals(376.730, PhysicsConstant.CHARACTERISTIC_IMPEDANCE_OF_VACUUM.value(), 1e-3);
		assertEquals(SiDerivedUnit.OHM, PhysicsConstant.CHARACTERISTIC_IMPEDANCE_OF_VACUUM.unit());
	}

	@DisplayName("Tests the classical electron radius")
	@Test
	void testClassicalElectronRadius() {
		assertEquals(2.8179e-15, PhysicsConstant.CLASSICAL_ELECTRON_RADIUS.value(), 1e-19);
		assertEquals(SiBaseUnit.METER, PhysicsConstant.CLASSICAL_ELECTRON_RADIUS.unit());
	}

	@DisplayName("Tests the Compton wavelength")
	@Test
	void testComptonWavelength() {
		assertEquals(2.4263e-12, PhysicsConstant.COMPTON_WAVELENGTH.value(), 1e-16);
		assertEquals(SiBaseUnit.METER, PhysicsConstant.COMPTON_WAVELENGTH.unit());
	}

	@DisplayName("Tests the conductance quantum")
	@Test
	void testConductanceQuantum() {
		assertEquals(7.7480e-5, PhysicsConstant.CONDUCTANCE_QUANTUM.value(), 1e-9);
		assertEquals(SiDerivedUnit.SIEMENS, PhysicsConstant.CONDUCTANCE_QUANTUM.unit());
	}

	@DisplayName("Tests the conventional value of the Josephson constant")
	@Test
	void testConventionalValueOfJosephsonConstant() {
		assertEquals(4.8359e14, PhysicsConstant.CONVENTIONAL_VALUE_OF_JOSEPHSON_CONSTANT.value(), 1e10);
		assertEquals(Unit.of("Hz V^-1"), PhysicsConstant.CONVENTIONAL_VALUE_OF_JOSEPHSON_CONSTANT.unit());
	}

	@DisplayName("Tests the conventional value of the von Klitzing constant")
	@Test
	void testConventionalValueOfVonKlitzingConstant() {
		assertEquals(25812.80, PhysicsConstant.CONVENTIONAL_VALUE_OF_VON_KLITZING_CONSTANT.value(), 1e-2);
		assertEquals(SiDerivedUnit.OHM, PhysicsConstant.CONVENTIONAL_VALUE_OF_VON_KLITZING_CONSTANT.unit());
	}

	@DisplayName("Tests the copper x unit")
	@Test
	void testCopperXUnit() {
		assertEquals(1.0020e-13, PhysicsConstant.COPPER_X_UNIT.value(), 1e-17);
		assertEquals(SiBaseUnit.METER, PhysicsConstant.COPPER_X_UNIT.unit());
	}

	@DisplayName("Tests the deuteron g factor")
	@Test
	void testDeuteronGFactor() {
		assertEquals(0.8574, PhysicsConstant.DEUTERON_G_FACTOR.value(), 1e-4);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.DEUTERON_G_FACTOR.unit());
	}

	@DisplayName("Tests the deuteron magnetic moment")
	@Test
	void testDeuteronMagMom() {
		assertEquals(4.3307e-27, PhysicsConstant.DEUTERON_MAG_MOM.value(), 1e-31);
		assertEquals(Unit.of("J T^-1"), PhysicsConstant.DEUTERON_MAG_MOM.unit());
	}

	@DisplayName("Tests the deuteron mass")
	@Test
	void testDeuteronMass() {
		assertEquals(3.3435e-27, PhysicsConstant.DEUTERON_MASS.value(), 1e-31);
		assertEquals(SiBaseUnit.KILOGRAM, PhysicsConstant.DEUTERON_MASS.unit());
	}

	@DisplayName("Tests the deuteron rms charge radius")
	@Test
	void testDeuteronRmsChargeRadius() {
		assertEquals(2.127e-15, PhysicsConstant.DEUTERON_RMS_CHARGE_RADIUS.value(), 1e-18);
		assertEquals(SiBaseUnit.METER, PhysicsConstant.DEUTERON_RMS_CHARGE_RADIUS.unit());
	}

	@DisplayName("Tests the electron g factor")
	@Test
	void testElectronGFactor() {
		assertEquals(-2.0023, PhysicsConstant.ELECTRON_G_FACTOR.value(), 1e-4);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.ELECTRON_G_FACTOR.unit());
	}

	@DisplayName("Tests the electron magnetic moment")
	@Test
	void testElectronMagMom() {
		assertEquals(-9.2847e-24, PhysicsConstant.ELECTRON_MAG_MOM.value(), 1e-28);
		assertEquals(Unit.of("J T^-1"), PhysicsConstant.ELECTRON_MAG_MOM.unit());
	}

	@DisplayName("Tests the electron magnetic moment anomaly")
	@Test
	void testElectronMagMomAnomaly() {
		assertEquals(1.1596e-3, PhysicsConstant.ELECTRON_MAG_MOM_ANOMALY.value(), 1e-7);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.ELECTRON_MAG_MOM_ANOMALY.unit());
	}

	@DisplayName("Tests the electron mass")
	@Test
	void testElectronMass() {
		assertEquals(9.1093e-31, PhysicsConstant.ELECTRON_MASS.value(), 1e-35);
		assertEquals(SiBaseUnit.KILOGRAM, PhysicsConstant.ELECTRON_MASS.unit());
	}

	@DisplayName("Tests the electron mass")
	@Test
	void testElementaryCharge() {
		assertEquals(1.6021e-19, PhysicsConstant.ELEMENTARY_CHARGE.value(), 1e-23);
		assertEquals(SiDerivedUnit.COULOMB, PhysicsConstant.ELEMENTARY_CHARGE.unit());
	}

	@DisplayName("Tests the Faraday constant")
	@Test
	void testFaradayConstant() {
		assertEquals(96485.33, PhysicsConstant.FARADAY_CONSTANT.value(), 1e-2);
		assertEquals(Unit.of("C mol^-1"), PhysicsConstant.FARADAY_CONSTANT.unit());
	}

	@DisplayName("Tests the Fermi coupling constant")
	@Test
	void testFermiCouplingConstant() {
		assertEquals(1.1663e-5, PhysicsConstant.FERMI_COUPLING_CONSTANT.value(), 1e-9);
		assertEquals(Unit.of("GeV^-2"), PhysicsConstant.FERMI_COUPLING_CONSTANT.unit());
	}

	@DisplayName("Tests the Fermi coupling constant")
	@Test
	void testFineStructureConstant() {
		assertEquals(7.2973e-3, PhysicsConstant.FINE_STRUCTURE_CONSTANT.value(), 1e-7);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.FINE_STRUCTURE_CONSTANT.unit());
	}

	@DisplayName("Tests the first radiation constant")
	@Test
	void testFirstRadiationConstant() {
		assertEquals(3.7417e-16, PhysicsConstant.FIRST_RADIATION_CONSTANT.value(), 1e-20);
		assertEquals(Unit.of("W m^2"), PhysicsConstant.FIRST_RADIATION_CONSTANT.unit());
	}

	@DisplayName("Tests the Hartree energy")
	@Test
	void testHartreeEnergy() {
		assertEquals(4.3597e-18, PhysicsConstant.HARTREE_ENERGY.value(), 1e-22);
		assertEquals(SiDerivedUnit.JOULE, PhysicsConstant.HARTREE_ENERGY.unit());
	}

	@DisplayName("Tests the helion g factor")
	@Test
	void testHelionGFactor() {
		assertEquals(-4.2552, PhysicsConstant.HELION_G_FACTOR.value(), 1e-4);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.HELION_G_FACTOR.unit());
	}

	@DisplayName("Tests the helion magnetic moment")
	@Test
	void testHelionMagMom() {
		assertEquals(-1.0746e-26, PhysicsConstant.HELION_MAG_MOM.value(), 1e-30);
		assertEquals(Unit.of("J T^-1"), PhysicsConstant.HELION_MAG_MOM.unit());
	}

	@DisplayName("Tests the helion mass")
	@Test
	void testHelionMass() {
		assertEquals(5.0064e-27, PhysicsConstant.HELION_MASS.value(), 1e-31);
		assertEquals(SiBaseUnit.KILOGRAM, PhysicsConstant.HELION_MASS.unit());
	}

	@DisplayName("Tests the helion shielding shift")
	@Test
	void testHelionShieldingShift() {
		assertEquals(5.9967e-5, PhysicsConstant.HELION_SHIELDING_SHIFT.value(), 1e-9);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.HELION_SHIELDING_SHIFT.unit());
	}

	@DisplayName("Tests the hyperfine transition frequency of Cs133")
	@Test
	void testHyperfineTransitionFreqOfCs133() {
		assertEquals(9.1926e9, PhysicsConstant.HYPERFINE_TRANSITION_FREQUENCY_OF_CS_133.value(), 1e5);
		assertEquals(SiDerivedUnit.HERTZ, PhysicsConstant.HYPERFINE_TRANSITION_FREQUENCY_OF_CS_133.unit());
	}

	@DisplayName("Tests the Josephson constant")
	@Test
	void testJosephsonConstant() {
		assertEquals(483597e9, PhysicsConstant.JOSEPHSON_CONSTANT.value(), 1e9);
		assertEquals(Unit.of("Hz V^-1"), PhysicsConstant.JOSEPHSON_CONSTANT.unit());
	}

	@DisplayName("Tests the lattice parameter of Si")
	@Test
	void testLatticeParameterOfSi() {
		assertEquals(5.4310e-10, PhysicsConstant.LATTICE_PARAMETER_OF_SILICON.value(), 1e-14);
		assertEquals(SiBaseUnit.METER, PhysicsConstant.LATTICE_PARAMETER_OF_SILICON.unit());
	}

	@DisplayName("Tests the lattice spacing of ideal Si 220")
	@Test
	void testLatticeSpacingOfIdealSi220() {
		assertEquals(1.92015e-10, PhysicsConstant.LATTICE_SPACING_OF_IDEAL_SI_220.value(), 1e-15);
		assertEquals(SiBaseUnit.METER, PhysicsConstant.LATTICE_SPACING_OF_IDEAL_SI_220.unit());
	}

	@DisplayName("Tests the Loschmidt constant at standard state pressure")
	@Test
	void testLoschmidtConstantStandardStatePressure() {
		assertEquals(2.65164e25, PhysicsConstant.LOSCHMIDT_CONSTANT_273_15_K_100_KPA.value(), 1e20);
		assertEquals(Unit.of("m^-3"), PhysicsConstant.LOSCHMIDT_CONSTANT_273_15_K_100_KPA.unit());
	}

	@DisplayName("Tests the Loschmidt constant at standard atmosphere")
	@Test
	void testLoschmidtConstantStandardAtmosphere() {
		assertEquals(2.68678e25, PhysicsConstant.LOSCHMIDT_CONSTANT_273_15_K_101_325_KPA.value(), 1e20);
		assertEquals(Unit.of("m^-3"), PhysicsConstant.LOSCHMIDT_CONSTANT_273_15_K_101_325_KPA.unit());
	}

	@DisplayName("Tests the luminous efficacy")
	@Test
	void testLuminousEfficacy() {
		assertEquals(683, PhysicsConstant.LUMINOUS_EFFICACY.value(), 1e-1);
		assertEquals(Unit.of("lm W^-1"), PhysicsConstant.LUMINOUS_EFFICACY.unit());
	}

	@DisplayName("Tests the magnetic flux quantum")
	@Test
	void testMagFluxQuantum() {
		assertEquals(2.0678e-15, PhysicsConstant.MAG_FLUX_QUANTUM.value(), 1e-19);
		assertEquals(SiDerivedUnit.WEBER, PhysicsConstant.MAG_FLUX_QUANTUM.unit());
	}

	@DisplayName("Tests the molar gas constant")
	@Test
	void testMolarGasConstant() {
		assertEquals(8.3144, PhysicsConstant.MOLAR_GAS_CONSTANT.value(), 1e-4);
		assertEquals(Unit.of("J mol^-1 K^-1"), PhysicsConstant.MOLAR_GAS_CONSTANT.unit());
	}

	@DisplayName("Tests the molar mass constant")
	@Test
	void testMolarMassConstant() {
		assertEquals(9.999999e-4, PhysicsConstant.MOLAR_MASS_CONSTANT.value(), 1e-10);
		assertEquals(Unit.of("kg mol^-1"), PhysicsConstant.MOLAR_MASS_CONSTANT.unit());
	}

	@DisplayName("Tests the molar mass of C12")
	@Test
	void testMolarMassC12() {
		assertEquals(0.011999, PhysicsConstant.MOLAR_MASS_OF_CARBON_12.value(), 1e-6);
		assertEquals(Unit.of("kg mol^-1"), PhysicsConstant.MOLAR_MASS_OF_CARBON_12.unit());
	}

	@DisplayName("Tests the molar volume of an ideal gas at standard state pressure")
	@Test
	void testMolarVolumeOfIdealGasStandardStatePressure() {
		assertEquals(0.0227, PhysicsConstant.MOLAR_VOLUME_OF_IDEAL_GAS_273_15_K_100_KPA.value(), 1e-4);
		assertEquals(Unit.of("m^3 mol^-1"), PhysicsConstant.MOLAR_VOLUME_OF_IDEAL_GAS_273_15_K_100_KPA.unit());
	}

	@DisplayName("Tests the molar volume of an ideal gas at standard atmosphere")
	@Test
	void testMolarVolumeOfIdealGasStandardAtmosphere() {
		assertEquals(0.0224, PhysicsConstant.MOLAR_VOLUME_OF_IDEAL_GAS_273_15_K_101_325_KPA.value(), 1e-4);
		assertEquals(Unit.of("m^3 mol^-1"), PhysicsConstant.MOLAR_VOLUME_OF_IDEAL_GAS_273_15_K_101_325_KPA.unit());
	}

	@DisplayName("Tests the molar volume of Si")
	@Test
	void testMolarVolumeOfSi() {
		assertEquals(1.20588e-5, PhysicsConstant.MOLAR_VOLUME_OF_SILICON.value(), 1e-10);
		assertEquals(Unit.of("m^3 mol^-1"), PhysicsConstant.MOLAR_VOLUME_OF_SILICON.unit());
	}

	@DisplayName("Tests the molybdenum x unit")
	@Test
	void testMolybdenumXUnit() {
		assertEquals(1.00209e-13, PhysicsConstant.MOLYBDENUM_X_UNIT.value(), 1e-18);
		assertEquals(SiBaseUnit.METER, PhysicsConstant.MOLYBDENUM_X_UNIT.unit());
	}

	@DisplayName("Tests the Compton wavelength of the muon")
	@Test
	void testMuonComptonWavelength() {
		assertEquals(1.1734e-14, PhysicsConstant.MUON_COMPTON_WAVELENGTH.value(), 1e-18);
		assertEquals(SiBaseUnit.METER, PhysicsConstant.MUON_COMPTON_WAVELENGTH.unit());
	}

	@DisplayName("Tests the muon g factor")
	@Test
	void testMuonGFactor() {
		assertEquals(-2.0023, PhysicsConstant.MUON_G_FACTOR.value(), 1e-4);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.MUON_G_FACTOR.unit());
	}

	@DisplayName("Tests the muon magnetic moment")
	@Test
	void testMuonMagneticMoment() {
		assertEquals(-4.4904e-26, PhysicsConstant.MUON_MAG_MOM.value(), 1e-30);
		assertEquals(Unit.of("J T^-1"), PhysicsConstant.MUON_MAG_MOM.unit());
	}

	@DisplayName("Tests the muon magnetic moment anomaly")
	@Test
	void testMuonMagneticMomentAnomaly() {
		assertEquals(1.1659e-3, PhysicsConstant.MUON_MAG_MOM_ANOMALY.value(), 1e-7);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.MUON_MAG_MOM_ANOMALY.unit());
	}

	@DisplayName("Tests the muon mass")
	@Test
	void testMuonMass() {
		assertEquals(1.8835e-28, PhysicsConstant.MUON_MASS.value(), 1e-32);
		assertEquals(SiBaseUnit.KILOGRAM, PhysicsConstant.MUON_MASS.unit());
	}

	@DisplayName("Tests the neutron Compton wavelength")
	@Test
	void testNeutronComptonWavelength() {
		assertEquals(1.3195e-15, PhysicsConstant.NEUTRON_COMPTON_WAVELENGTH.value(), 1e-19);
		assertEquals(SiBaseUnit.METER, PhysicsConstant.NEUTRON_COMPTON_WAVELENGTH.unit());
	}

	@DisplayName("Tests the neutron g factor")
	@Test
	void testNeutronGFactor() {
		assertEquals(-3.8260, PhysicsConstant.NEUTRON_G_FACTOR.value(), 1e-4);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.NEUTRON_G_FACTOR.unit());
	}

	@DisplayName("Tests the neutron magnetic moment")
	@Test
	void testNeutronMagneticMoment() {
		assertEquals(-9.6623e-27, PhysicsConstant.NEUTRON_MAG_MOM.value(), 1e-31);
		assertEquals(Unit.of("J T^-1"), PhysicsConstant.NEUTRON_MAG_MOM.unit());
	}

	@DisplayName("Tests the neutron mass")
	@Test
	void testNeutronMass() {
		assertEquals(1.6749e-27, PhysicsConstant.NEUTRON_MASS.value(), 1e-31);
		assertEquals(SiBaseUnit.KILOGRAM, PhysicsConstant.NEUTRON_MASS.unit());
	}

	@DisplayName("Tests Newtons constant of gravitation")
	@Test
	void testNewtonsConstantOfGravitation() {
		assertEquals(6.674e-11, PhysicsConstant.NEWTONIAN_CONSTANT_OF_GRAVITATION.value(), 1e-14);
		assertEquals(Unit.of("m^3 kg^-1 s^-2"), PhysicsConstant.NEWTONIAN_CONSTANT_OF_GRAVITATION.unit());
	}

	@DisplayName("Tests the nuclear magneton")
	@Test
	void testNuclearMagneton() {
		assertEquals(5.0507e-27, PhysicsConstant.NUCLEAR_MAGNETON.value(), 1e-31);
		assertEquals(Unit.of("J T^-1"), PhysicsConstant.NUCLEAR_MAGNETON.unit());
	}

	@DisplayName("Tests Plancks constant")
	@Test
	void testPlanckConstant() {
		assertEquals(6.6260e-34, PhysicsConstant.PLANCK_CONSTANT.value(), 1e-38);
		assertEquals(Unit.of("J Hz^-1"), PhysicsConstant.PLANCK_CONSTANT.unit());
	}

	@DisplayName("Tests the Planck length")
	@Test
	void testPlanckLength() {
		assertEquals(1.6162e-35, PhysicsConstant.PLANCK_LENGTH.value(), 1e-39);
		assertEquals(SiBaseUnit.METER, PhysicsConstant.PLANCK_LENGTH.unit());
	}

	@DisplayName("Tests the Planck mass")
	@Test
	void testPlanckMass() {
		assertEquals(2.1764e-8, PhysicsConstant.PLANCK_MASS.value(), 1e-12);
		assertEquals(SiBaseUnit.KILOGRAM, PhysicsConstant.PLANCK_MASS.unit());
	}

	@DisplayName("Tests the Planck temperature")
	@Test
	void testPlanckTemperature() {
		assertEquals(1.4167e32, PhysicsConstant.PLANCK_TEMPERATURE.value(), 1e28);
		assertEquals(SiBaseUnit.KELVIN, PhysicsConstant.PLANCK_TEMPERATURE.unit());
	}

	@DisplayName("Tests the Planck time")
	@Test
	void testPlanckTime() {
		assertEquals(5.3912e-44, PhysicsConstant.PLANCK_TIME.value(), 1e-48);
		assertEquals(SiBaseUnit.SECOND, PhysicsConstant.PLANCK_TIME.unit());
	}

	@DisplayName("Tests the proton Compton wavelength")
	@Test
	void testProtonComptonWavelength() {
		assertEquals(1.3214e-15, PhysicsConstant.PROTON_COMPTON_WAVELENGTH.value(), 1e-19);
		assertEquals(SiBaseUnit.METER, PhysicsConstant.PROTON_COMPTON_WAVELENGTH.unit());
	}

	@DisplayName("Tests the proton g factor")
	@Test
	void testProtonGFactor() {
		assertEquals(5.5856, PhysicsConstant.PROTON_G_FACTOR.value(), 1e-4);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.PROTON_G_FACTOR.unit());
	}

	@DisplayName("Tests the proton magnetic moment")
	@Test
	void testProtonMagneticMoment() {
		assertEquals(1.4106e-26, PhysicsConstant.PROTON_MAG_MOM.value(), 1e-30);
		assertEquals(Unit.of("J T^-1"), PhysicsConstant.PROTON_MAG_MOM.unit());
	}

	@DisplayName("Tests the proton magnetic shielding correction")
	@Test
	void testProtonMagneticShieldingCorrection() {
		assertEquals(2.56e-5, PhysicsConstant.PROTON_MAG_SHIELDING_CORRECTION.value(), 1e-7);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.PROTON_MAG_SHIELDING_CORRECTION.unit());
	}

	@DisplayName("Tests the proton mass")
	@Test
	void testProtonMass() {
		assertEquals(1.6726e-27, PhysicsConstant.PROTON_MASS.value(), 1e-31);
		assertEquals(SiBaseUnit.KILOGRAM, PhysicsConstant.PROTON_MASS.unit());
	}

	@DisplayName("Tests the proton rms charge radius")
	@Test
	void testProtonRmsChargeRadius() {
		assertEquals(8.4e-16, PhysicsConstant.PROTON_RMS_CHARGE_RADIUS.value(), 1e-17);
		assertEquals(SiBaseUnit.METER, PhysicsConstant.PROTON_RMS_CHARGE_RADIUS.unit());
	}

	@DisplayName("Tests the quantum of circulation")
	@Test
	void testQuantumOfCirculation() {
		assertEquals(3.6369e-4, PhysicsConstant.QUANTUM_OF_CIRCULATION.value(), 1e-8);
		assertEquals(Unit.of("m^2 s^-1"), PhysicsConstant.QUANTUM_OF_CIRCULATION.unit());
	}

	@DisplayName("Tests the Rydberg constant")
	@Test
	void testRydbergConstant() {
		assertEquals(10973731.568, PhysicsConstant.RYDBERG_CONSTANT.value(), 1e-3);
		assertEquals(Unit.of("m^-1"), PhysicsConstant.RYDBERG_CONSTANT.unit());
	}

	@DisplayName("Tests the Sackur Tetrode constant at standard state pressure")
	@Test
	void testSackurTetrodeConstantStandardStatePressure() {
		assertEquals(-1.1517, PhysicsConstant.SACKUR_TETRODE_CONSTANT_1_K_100_KPA.value(), 1e-4);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.SACKUR_TETRODE_CONSTANT_1_K_100_KPA.unit());
	}

	@DisplayName("Tests the Sackur Tetrode constant at standard atmosphere")
	@Test
	void testSackurTetrodeConstantStandardAtmosphere() {
		assertEquals(-1.1648, PhysicsConstant.SACKUR_TETRODE_CONSTANT_1_K_101_325_KPA.value(), 1e-4);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.SACKUR_TETRODE_CONSTANT_1_K_101_325_KPA.unit());
	}

	@DisplayName("Tests the second radiation constant")
	@Test
	void testSecondRadiationConstant() {
		assertEquals(1.4387e-2, PhysicsConstant.SECOND_RADIATION_CONSTANT.value(), 1e-6);
		assertEquals(Unit.of("m K"), PhysicsConstant.SECOND_RADIATION_CONSTANT.unit());
	}

	@DisplayName("Tests the shielded helion magnetic moment")
	@Test
	void testShieldedHelionMagMom() {
		assertEquals(-1.0745e-26, PhysicsConstant.SHIELDED_HELION_MAG_MOM.value(), 1e-30);
		assertEquals(Unit.of("J T^-1"), PhysicsConstant.SHIELDED_HELION_MAG_MOM.unit());
	}

	@DisplayName("Tests the shielded proton magnetic moment")
	@Test
	void testShieldedProtonMagMom() {
		assertEquals(1.4105e-26, PhysicsConstant.SHIELDED_PROTON_MAG_MOM.value(), 1e-30);
		assertEquals(Unit.of("J T^-1"), PhysicsConstant.SHIELDED_PROTON_MAG_MOM.unit());
	}

	@DisplayName("Tests the shielding difference of D and P in HD")
	@Test
	void testShieldingDifferenceDPInHD() {
		assertEquals(2.02e-8, PhysicsConstant.SHIELDING_DIFFERENCE_OF_D_AND_P_IN_HD.value(), 1e-10);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.SHIELDING_DIFFERENCE_OF_D_AND_P_IN_HD.unit());
	}

	@DisplayName("Tests the shielding difference of T and P in HT")
	@Test
	void testShieldingDifferenceTPInHT() {
		assertEquals(2.41e-8, PhysicsConstant.SHIELDING_DIFFERENCE_OF_T_AND_P_IN_HT.value(), 1e-10);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.SHIELDING_DIFFERENCE_OF_T_AND_P_IN_HT.unit());
	}

	@DisplayName("Tests the speed of light in vacuum")
	@Test
	void testSpeedOfLightInVacuum() {
		assertEquals(2.99792458e8, PhysicsConstant.SPEED_OF_LIGHT_IN_VACUUM.value(), 1);
		assertEquals(Unit.of("m s^-1"), PhysicsConstant.SPEED_OF_LIGHT_IN_VACUUM.unit());
	}

	@DisplayName("Tests the standard acceleration of gravity")
	@Test
	void testStandardAccelerationOfGravity() {
		assertEquals(9.8066, PhysicsConstant.STANDARD_ACCELERATION_OF_GRAVITY.value(), 1e-4);
		assertEquals(Unit.of("m s^-2"), PhysicsConstant.STANDARD_ACCELERATION_OF_GRAVITY.unit());
	}

	@DisplayName("Tests the standard atmosphere")
	@Test
	void testStandardAtmosphere() {
		assertEquals(101325, PhysicsConstant.STANDARD_ATMOSPHERE.value(), 1);
		assertEquals(SiDerivedUnit.PASCAL, PhysicsConstant.STANDARD_ATMOSPHERE.unit());
	}

	@DisplayName("Tests the standard state pressure")
	@Test
	void testStandardStatePressure() {
		assertEquals(100000.0, PhysicsConstant.STANDARD_STATE_PRESSURE.value(), 1);
		assertEquals(SiDerivedUnit.PASCAL, PhysicsConstant.STANDARD_STATE_PRESSURE.unit());
	}

	@DisplayName("Tests the Stefan Boltzmann constant")
	@Test
	void testStefanBoltzmannConstant() {
		assertEquals(5.6703e-8, PhysicsConstant.STEFAN_BOLTZMANN_CONSTANT.value(), 1e-12);
		assertEquals(Unit.of("W m^-2 K^-4"), PhysicsConstant.STEFAN_BOLTZMANN_CONSTANT.unit());
	}

	@DisplayName("Tests the tau Compton wavelength")
	@Test
	void testTauComptonWavelength() {
		assertEquals(6.977e-16, PhysicsConstant.TAU_COMPTON_WAVELENGTH.value(), 1e-19);
		assertEquals(SiBaseUnit.METER, PhysicsConstant.TAU_COMPTON_WAVELENGTH.unit());
	}

	@DisplayName("Tests the tau mass")
	@Test
	void testTauMass() {
		assertEquals(3.167e-27, PhysicsConstant.TAU_MASS.value(), 1e-30);
		assertEquals(SiBaseUnit.KILOGRAM, PhysicsConstant.TAU_MASS.unit());
	}

	@DisplayName("Tests the Thomson cross section")
	@Test
	void testThomsonCrossSection() {
		assertEquals(6.6524e-29, PhysicsConstant.THOMSON_CROSS_SECTION.value(), 1e-33);
		assertEquals(Unit.of("m^2"), PhysicsConstant.THOMSON_CROSS_SECTION.unit());
	}

	@DisplayName("Tests the triton g factor")
	@Test
	void testTritonGFactor() {
		assertEquals(5.9579, PhysicsConstant.TRITON_G_FACTOR.value(), 1e-4);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.TRITON_G_FACTOR.unit());
	}

	@DisplayName("Tests the triton magnetic moment")
	@Test
	void testTritonMagMom() {
		assertEquals(1.5046e-26, PhysicsConstant.TRITON_MAG_MOM.value(), 1e-30);
		assertEquals(Unit.of("J T^-1"), PhysicsConstant.TRITON_MAG_MOM.unit());
	}

	@DisplayName("Tests the triton mass")
	@Test
	void testTritonMass() {
		assertEquals(5.0073e-27, PhysicsConstant.TRITON_MASS.value(), 1e-31);
		assertEquals(SiBaseUnit.KILOGRAM, PhysicsConstant.TRITON_MASS.unit());
	}

	@DisplayName("Tests the unified atomic mass unit")
	@Test
	void testUnifiedAtomicMassUnit() {
		assertEquals(1.6605e-27, PhysicsConstant.UNIFIED_ATOMIC_MASS_UNIT.value(), 1e-31);
		assertEquals(SiBaseUnit.KILOGRAM, PhysicsConstant.UNIFIED_ATOMIC_MASS_UNIT.unit());
	}

	@DisplayName("Tests the vacuum electric permittivity")
	@Test
	void testVacuumElectricPermittivity() {
		assertEquals(8.8541e-12, PhysicsConstant.VACUUM_ELECTRIC_PERMITTIVITY.value(), 1e-16);
		assertEquals(Unit.of("F m^-1"), PhysicsConstant.VACUUM_ELECTRIC_PERMITTIVITY.unit());
	}

	@DisplayName("Tests the vacuum magnetic permeability")
	@Test
	void testVacuumMagneticPermeability() {
		assertEquals(1.2566e-6, PhysicsConstant.VACUUM_MAG_PERMEABILITY.value(), 1e-10);
		assertEquals(Unit.of("N A^-2"), PhysicsConstant.VACUUM_MAG_PERMEABILITY.unit());
	}

	@DisplayName("Tests the von Klitzing constant")
	@Test
	void testVonKlitzingConstant() {
		assertEquals(25812.807, PhysicsConstant.VON_KLITZING_CONSTANT.value(), 1e-3);
		assertEquals(SiDerivedUnit.OHM, PhysicsConstant.VON_KLITZING_CONSTANT.unit());
	}

	@DisplayName("Tests the weak mixing angle")
	@Test
	void testWeakMixingAngle() {
		assertEquals(0.2229, PhysicsConstant.WEAK_MIXING_ANGLE.value(), 1e-3);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.WEAK_MIXING_ANGLE.unit());
	}

	@DisplayName("Tests the Wien frequency displacement law constant")
	@Test
	void testWienFreqDisplacementLawConstant() {
		assertEquals(5.8789e10, PhysicsConstant.WIEN_FREQUENCY_DISPLACEMENT_LAW_CONSTANT.value(), 1e6);
		assertEquals(Unit.of("Hz K^-1"), PhysicsConstant.WIEN_FREQUENCY_DISPLACEMENT_LAW_CONSTANT.unit());
	}

	@DisplayName("Tests the Wien wavelength displacement law constant")
	@Test
	void testWienWavelengthDisplacementLawConstant() {
		assertEquals(0.0028, PhysicsConstant.WIEN_WAVELENGTH_DISPLACEMENT_LAW_CONSTANT.value(), 1e-4);
		assertEquals(Unit.of("m K"), PhysicsConstant.WIEN_WAVELENGTH_DISPLACEMENT_LAW_CONSTANT.unit());
	}

	@DisplayName("Tests the W to Z mass ratio")
	@Test
	void testWToZMassRatio() {
		assertEquals(0.8815, PhysicsConstant.W_TO_Z_MASS_RATIO.value(), 1e-3);
		assertEquals(Units.EMPTY_UNIT, PhysicsConstant.W_TO_Z_MASS_RATIO.unit());
	}
}
