package chapter5;

import org.moeaframework.core.PRNG;
import org.moeaframework.core.Variable;

public class Color implements Variable {

	private static final long serialVersionUID = 8461741347578471248L;

	private int r;
	
	private int g;
	
	private int b;
	
	public Color() {
		super();
	}
	
	public Color(int r, int g, int b) {
		this();
		this.r = r;
		this.g = g;
		this.b = b;
	}

	protected int getR() {
		return r;
	}

	protected void setR(int r) {
		this.r = r;
	}

	protected int getG() {
		return g;
	}

	protected void setG(int g) {
		this.g = g;
	}

	protected int getB() {
		return b;
	}

	protected void setB(int b) {
		this.b = b;
	}

	@Override
	public Variable copy() {
		return new Color(r, g, b);
	}

	@Override
	public void randomize() {
		r = PRNG.nextInt(256);
		g = PRNG.nextInt(256);
		b = PRNG.nextInt(256);
	}

	@Override
	public String toString() {
		return "Color [r=" + r + ", g=" + g + ", b=" + b + "]";
	}
	
}
