package eu.hoefel.unit.constant.math;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;
import eu.hoefel.unit.constant.Constant;

/**
 * This enum contains some basic math constants. Note that the accuracy is
 * necessarily limited as the variable holding the value is a double.
 */
public enum MathConstant implements Constant {

	/**
	 * Archimedes' constant is defined as the ratio of a circle's circumference to
	 * its diameter. It is represented by the greek lowercase letter π. It is named
	 * after Archimedes as he created an Algorithm over two millenia ago to
	 * calculate it.
	 */
	ARCHIMEDES_CONSTANT(Math.PI, "A000796"),

	/**
	 * The golden ratio is defined as<br>
	 * <i>φ</i>=(1+5<sup>½</sup>)/2,<br>
	 * i.e. two values are in the golden ratio if their ratio is the same as the
	 * ratio of their sum to the larger of the two values. It is an irrational
	 * number.
	 */
	GOLDEN_RATIO(1.41421356237309504880, "A002193"),

	/**
	 * There are many different ways to define this constant, which is the base of
	 * the natural logarithm, for example: the limit of
	 * (1+1/<i>n</i>)<sup><i>n</i></sup> with <i>n</i> to ∞. The constant was
	 * discovered by Jacob Bernoulli and is named in honour of Leonhard Euler.
	 */
	EULERS_NUMBER(Math.E, "A001113"),

	/**
	 * The Euler-Mascheroni constant, named after Leonhard Euler and Lorenzo
	 * Mascheroni, is a constant mainly used in number theory and analysis. It is
	 * denoted by the greek lowercase letter <i>γ</i>. Currently, it is unknown
	 * whether the constant is rational or irrational.
	 */
	EULER_MASCHERONI_CONSTANT(0.577215664901532860606512090082402431042, "A001620"),

	/**
	 * Apéry's constant is defined as &sum;<sub><i>n</i>=1</sub><sup>∞</sup>
	 * <i>n</i><sup>-3</sup>.
	 */
	APERY_CONSTANT(1.2020569031595942853997, "A002117"),

	/**
	 * The Erdős-Borwein constant is named after Paul Erdős and Peter Borwein. It is
	 * defined as the sum of the reciprocals of the Mersenne numbers and is denoted
	 * <i>E</i>:<br>
	 * <i>E</i>=&sum;<sub><i>n</i>=1</sub><sup>∞</sup>(2<sup><i>n</i></sup>-1)<sup>-1</sup>.
	 */
	ERDOS_BORWEIN_CONSTANT(1.60669515241529176378330152319092458048057967150575643577807955369, "A065442"),

	/**
	 * The Ramanujan-Soldner constant, named after Srinivasa Ramanujan and Johann
	 * Georg von Soldner, is defined as the only positive zero of the logarithmic
	 * integral function.
	 */
	RAMANUJAN_SOLDNER_CONSTANT(1.45136923488338105028396848589, "A070769"),

	/**
	 * Gauss's constant, named after Carl Friedrich Gauss, is defined as the
	 * reciprocal of the arithmetic–geometric mean of 1 and the square root of 2. It
	 * is denoted <i>G</i>.
	 */
	GAUSS_CONSTANT(2.622057554292119810464839589891119413682754951431623162816821703, "A062539"),

	/**
	 * Catalan's constant, named after Eugène Catalan, is the value of Dirichlet's
	 * beta function at 2, <i>β</i>(2). It is denoted <i>G</i>.
	 */
	CATALANS_CONSTANT(0.91596559417721901505460351493238411077414937428167213426649811962176301977, "A006752"),

	/**
	 * The Meissel–Mertens constant, named after Ernst Meissel and Franz Mertens, is
	 * the limiting difference between the harmonic series summed only over primes
	 * and the natural logarithm of the natural logarithm.
	 */
	MEISSEL_MERTENS_CONSTANT(0.26149721284764278375542683860869585905156664826119920619206421392, "A077761"),

	/**
	 * The Glaisher–Kinkelin constant, named after James Whitbread Lee Glaisher and
	 * Hermann Kinkelin, appears in some sums and integrals. It is denoted by
	 * <i>A</i>.
	 */
	GLAISHER_KINKELIN_CONSTANT(1.2824271291006226368753425688697917277676889273250011920637400217404, "A074962"),

	/**
	 * Cahen's constant, named after Eugène Cahen, is a transcendent number defined
	 * as the limit of an alternating series of unit fractions.
	 */
	CAHEN_CONSTANT(0.6434105462883380261, "A118227"),

	/**
	 * Sierpiński's constant, named after Wacław Sierpiński, is usually denoted by
	 * <i>K</i>.
	 */
	SIERPINSKI_CONSTANT(2.5849817595792532170658935873831711600880516518526309173215, "A062089"),

	/**
	 * The Landau–Ramanujan constant, named after Edmund Landau and Srinivasa
	 * Ramanujan, is used in number theory.
	 */
	LANDAU_RAMANUJAN_CONSTANT(0.76422365358922066299069873125009232811679054139340951472168667374, "A064533"),

	/**
	 * Gieseking's constant, named after Hugo Giesekeing, is the maximal volume of
	 * hyperbolic tetrahedra. It is usually denoted <i>G</i>.
	 */
	GIESEKING_CONSTANT(1.0149416064096536250, "A143298"),

	/**
	 * Bernstein's constant, named after Sergei Natanovich Bernstein, is usually
	 * denoted <i>β</i>.
	 */
	BERNSTEIN_CONSTANT(0.280169499023869133036436491230672000042482139, "A073001"),

	/**
	 * The Golomb-Dickman constant, named after Solomon W. Golomb and Karl Dickman,
	 * is used in combinatorics and number theory. It is unknown whether the
	 * constant is rational or irrational.
	 */
	GOLOMB_DICKMAN_CONSTANT(0.62432998854355087, "A084945"),

	/**
	 * Khinchin's constant, named after Aleksandr Yakovlevich Khinchin, is used in
	 * number theory. It is the geometric mean of coefficients <i>a<sub>i</sub></i>
	 * of the continued fraction expansion of (almost) all real numbers <i>x</i>,
	 * and is independent of <i>x</i>.
	 */
	KHINCHINS_CONSTANT(2.685452001065306445309714835481795693820382293994462953051152345557218, "A002210"),

	/**
	 * Mills' constant, named after William H. Mills, is a constant in number
	 * theory. It is defined as the smallest positive real number <i>A</i> such that
	 * the floor function of the double exponential function
	 * ⌊<i>A</i><sup>3<sup><i>n</i></sup></sup>⌋ is prime for all natural numbers
	 * <i>n</i>.
	 */
	MILLS_CONSTANT(1.3063778838630806904686144926026057129167845851567136443680537599664340537668, "A051021"),

	/**
	 * Niven'S constant, named after Ivan M. Niven (cf, his work
	 * <a href="https://doi.org/10.1090/S0002-9939-1969-0241373-5">here</a>), is
	 * used in number theory. It is defined as the limit of the arithmetic mean of
	 * the maximum exponents of the prime factorization of any natural number
	 * <i>n</i> for <i>n</i> to ∞.
	 */
	NIVENS_CONSTANT(1.70521114010536776428, "A033150"),

	/**
	 * Porter's constant, named after J. W. Porter, is found in the analysis of
	 * Euclid's algorithm (which finds the greatest common divisor of two positive
	 * integers <i>m</i> and <i>n</i>), and represents the average number of
	 * iterations for fixed <i>n</i>, averaged over all choices of relatively prime
	 * integers <i>m</i>&lt;<i>n</i>. It was calculated to high accuracy by
	 * <a href="https://doi.org/10.1016%2F0898-1221%2876%2990025-0">Knuth</a>.
	 */
	PORTERS_CONSTANT(1.4670780794339754728977984847072299534499033224149, "A086237"),

	/**
	 * Chaitin's constant, named after Gregory Chaitin, is used in algorithmic
	 * information theory and describes the probability that a randomly constructed
	 * program will halt. It is not computable, however, Calude et al. checked, for
	 * a certain universal Turing machine, all programs of up to 80 bit length. This
	 * allowed to infer the first digits of the constant.
	 */
	CHAITINS_CONSTANT(0.0078749969978123844, "A100264"),

	/**
	 * The first Feigenbaum constant, named after Mitchell J. Feigenbaum, is also
	 * known as the Feigenbaum bifurcation velocity. It is the limiting ratio of
	 * each bifurcation interval to the next between every period doubling, of a
	 * one-parameter map,
	 * <i>x</i><sub><i>i</i>+1</sub>=<i>f</i>(<i>x<sub>i</sub></i>), with
	 * <i>f</i>(<i>x</i>) being parameterized by the bifurcation parameter <i>a</i>.
	 */
	FIRST_FEIGENBAUM_CONSTANT(4.669201609102990671853203820466201617258185577475768632745651343004134, "A006890"),

	/**
	 * The second Feigenbaum constant, named after Mitchell J. Feigenbaum, is the
	 * ratio between the width of a tine and the width of one of its two subtines,
	 * with the exception of the tine closest to the fold. The constant has to be
	 * negated if the ratio between lower subtine and width of the tine is
	 * considered.
	 */
	SECOND_FEIGENBAUM_CONSTANT(2.502907875095892822283902873218215786381271376727149977336192056779235, "A006891"),

	/**
	 * The Fransén–Robinson constant, named after Arne Fransén and Herman P.
	 * Robinson, is defined as the area between the graph of the reciprocal Gamma
	 * function and the positive <i>x</i> axis. It is denoted <i>F</i>.
	 */
	FRANSEN_ROBINSON_CONSTANT(2.807770242028519365221501186557772932308085920930198291220054809597100, "A058655"),

	/**
	 * The Hafner–Sarnak–McCurley constant, named after J. L. Hafner, P. Sarnak and
	 * K. McCurley, is the probability that the determinants of two randomly chosen
	 * square integer matrices (<i>n</i>&times;<i>n</i>) will be relatively prime in
	 * the limit <i>n</i> to ∞.
	 */
	HAFNER_SARNAK_MCCURLEY_CONSTANT(0.3532363718549959845435165504326820112801647785666904464160859428, "A085849");

	/** The numeric value of the constant. */
	private final double value;

	/** The <a href="https://oeis.org/">OEIS</a> number. */
	private final String oeis;

	/**
	 * Constructs a mathematical constant.
	 * 
	 * @param value the value of the constant
	 * @param oeis the OEIS number
	 */
	private MathConstant(double value, String oeis) {
		this.value = value;
		this.oeis = oeis;
	}

	/**
	 * Gets the <a href="https://oeis.org/">OEIS</a> (Online Encyclopedia of Integer
	 * Sequences) number.
	 * 
	 * @return the OEIS number
	 */
	public String oeis() {
		return oeis;
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
	@Override public double uncertainty() { return 0.0; }
	@Override public Unit unit() { return Units.EMPTY_UNIT; }
}
