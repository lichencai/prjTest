package javase.jdk8new;

public interface Formula {
	double calculate(int a);
	// 可以定义默认方法
	default double sqrt(int a) {
		return Math.sqrt(a);
	}
}
