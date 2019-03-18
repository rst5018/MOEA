package chapter7;

import org.moeaframework.util.tree.Environment;
import org.moeaframework.util.tree.Node;

public class Hypot extends Node {
	
	public Hypot() {
		super(Number.class, Number.class, Number.class);
	}

	@Override
	public Node copyNode() {
		return new Hypot();
	}

	@Override
	public Object evaluate(Environment environment) {
		Number x = (Number)getArgument(0).evaluate(environment);
		Number y = (Number)getArgument(1).evaluate(environment);
		
		return Math.hypot(x.doubleValue(), y.doubleValue());
	}

}
