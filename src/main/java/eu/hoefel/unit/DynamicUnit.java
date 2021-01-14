package eu.hoefel.unit;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public final class DynamicUnit implements Unit {
	
	private final List<String> symbols;
	private final Set<UnitPrefix> prefixes;
	private final Predicate<String> prefixAllowed;
	private final boolean isBasic;
	private final ToDoubleFunction<String> factor;
	private final DoubleUnaryOperator toBase;
	private final DoubleUnaryOperator fromBase;
	private final boolean canUseFactor;
	private final Map<Unit, Integer> baseUnits;
	private final Set<Unit> compatibleUnits;

	// TODO
	public DynamicUnit(List<String> symbols, Set<UnitPrefix> prefixes, Predicate<String> prefixAllowed, boolean isBasic,
			ToDoubleFunction<String> factor, DoubleUnaryOperator toBase, DoubleUnaryOperator fromBase,
			boolean canUseFactor, Map<Unit, Integer> baseUnits, Set<Unit> compatibleUnits) {
		this.symbols = List.copyOf(symbols);
		this.prefixes = Set.copyOf(prefixes);
		this.prefixAllowed = prefixAllowed;
		this.isBasic = isBasic;
		this.factor = factor;
		this.toBase = toBase;
		this.fromBase = fromBase;
		this.canUseFactor = canUseFactor;
		this.baseUnits = Map.copyOf(baseUnits);
		this.compatibleUnits = Set.copyOf(compatibleUnits);
	}
	
	@Override
	public String toString() {
		return "DynamicUnit[symbols=" + symbols + ", prefixes=" + prefixes + ", isBasic=" + isBasic + ", canUseFactor="
				+ canUseFactor + ", baseUnits=" + baseUnits + ", compatibleUnits=" + compatibleUnits + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(baseUnits, canUseFactor, compatibleUnits, isBasic, prefixes, symbols);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj instanceof Unit other) {
			return Objects.equals(baseUnits, other.baseUnits()) && canUseFactor == other.canUseFactor()
					&& Objects.equals(compatibleUnits, other.compatibleUnits()) && isBasic == other.isBasic()
					&& Objects.equals(prefixes, other.prefixes()) && Objects.equals(symbols, other.symbols());
		}
		return false;
	}

	@Override public List<String> symbols() { return symbols; }
	@Override public boolean prefixAllowed(String symbol) { return prefixAllowed.test(symbol); }
	@Override public boolean canUseFactor() { return canUseFactor; }
	@Override public double factor(String symbol) { return factor.applyAsDouble(symbol); }
	@Override public Map<Unit, Integer> baseUnits() { return baseUnits; }
	@Override public double convertToBaseUnits(double value) { return toBase.applyAsDouble(value); }
	@Override public double convertFromBaseUnits(double value) { return fromBase.applyAsDouble(value); }
	@Override public Set<UnitPrefix> prefixes() { return prefixes; }
	@Override public boolean isBasic() { return isBasic; }
	@Override public Set<Unit> compatibleUnits() { return compatibleUnits; };
}
