package javase.jdk8new;

import util.PrintUtil;

public class FormulaObject implements Formula{

	@Override
	public double calculate(int a) {
		return sqrt(a * 100);
	}
	
	public static void main(String[] args) {
		FormulaObject obj = new FormulaObject();
		PrintUtil.printf(obj.sqrt(100));
		PrintUtil.printf(obj.calculate(100));
	}
}
