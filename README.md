# Units

[![](https://img.shields.io/github/issues/uhoefel/units?style=flat-square)](https://github.com/uhoefel/units/issues)
[![](https://img.shields.io/github/stars/uhoefel/units?style=flat-square)](https://github.com/uhoefel/units/stargazers)
[![DOI](https://zenodo.org/badge/309825766.svg)](https://zenodo.org/badge/latestdoi/309825766)
[![Maven Central](https://img.shields.io/maven-central/v/eu.hoefel/units.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22eu.hoefel%22%20AND%20a:%22units%22)
[![](https://img.shields.io/github/license/uhoefel/units?style=flat-square)](https://choosealicense.com/licenses/mit/)

Units is a [Java](https://openjdk.java.net/) library designed to handle units and constants.
It is designed to handle string based units as well as sharper defined units.
Some of the supported features include:
- conversions of values, e.g.:
  ```java
  Units.convert(3, "m", "mm");
  Units.convert(3, SiBaseUnit.METER, "mm");
  ```
  would both return `3000`.
- simplification of string based units, e.g.:
  ```java
  Units.simplify("kg^3 m^4 s^-6 A^-1");
  ```
  would return `"J^2 T"`.
  As the simplification is an expensive operation, the results are cached internally.
- finding the names of a unit in a specific context, e.g.:
  ```java
  Units.inContext("lx s", UnitContextMatch.COMPATIBLE, PhysicsContext.PHOTOMETRY)
  ```
  would return a navigable set containing `("luminous exposure")`.
- supports [SI units](https://www.bipm.org/en/publications/si-brochure/), binary units, [imperial units](https://www.legislation.gov.uk/ukpga/1985/72), [US customary units](https://en.wikipedia.org/wiki/United_States_customary_units), [atomic units](https://en.wikipedia.org/wiki/Hartree_atomic_units), [planck units](https://en.wikipedia.org/wiki/Planck_units) and many more. The user can also easily define own units.
- supports SI prefixes, binary prefixes and allows the user to easily implement own prefixes
- Can handle unknown units if not relevant, e.g.:
  ```java
  Units.convert(3, "m^2 this_is_not_a_unit", "mm^2 this_is_not_a_unit");
  ```
  would return `3e6`, as the unknown unit `this_is_not_a_unit` is the same on both sides of the conversion.
- for performance critical parts of the code one can obtain the conversion factor (if the conversion is purely multiplicative), e.g.:
  ```java
  Units.factor("kg", "t");
  ```
  will return `1e-3`.
- Allows to check for equivalence, e.g.
  ```java
  Units.equivalent(1, "s", "min");
  ```
  will return false, as `1min` is not the same as `1s`. On the other hand, checking for convertibility
  ```java
  Units.convertible("s", "min");
  ```
  will return `true`.
- tightly integrated in the [coordinates](https://github.com/uhoefel/coordinates) library

The constants are implemented via a `Constant` interface that supports e.g.:
- definition of own constants, e.g.
  ```java
  // (3 ± 0.2) mole
  Constant.of(3, 0.2, "mole");
  ```
- chaining commands, e.g.
  ```java
  // constant with the distance travelled by light in vacuum in (2 ± 0) seconds as value
  PhysicsConstant.SPEED_OF_LIGHT_IN_VACUUM.mul(2, 0, SiBaseUnit.SECOND);

  // constant of the elementary charge per (electron) mass
  PhysicsConstant.ELEMENTARY_CHARGE.div(PhysicsConstant.ELECTRON_MASS);

  Constant c = Constant.of(3, 0.2, "mole");
  PhysicsConstant.SHIELDING_DIFFERENCE_OF_T_AND_P_IN_HT.mul(c);
  ```
- (simple) uncertainty propagation
- the `Constant` interface provides default implementations for the `Texable` interface from the [jatex](https://github.com/uhoefel/jatex) module, such that a constant can easily return proper LaTeX code.
- properly documented implementations for most of the physics constants as defined by NIST, as well as some mathematical constants.

Installation
============

The artifact can be found at maven central:
```xml
<dependency>
    <groupId>eu.hoefel</groupId>
    <artifactId>units</artifactId>
    <version>1.0.2</version>
</dependency>
```

Requirements
============
Units is designed to work with Java 15+. It needs preview-features enabled.