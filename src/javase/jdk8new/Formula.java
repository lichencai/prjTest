package javase.jdk8new;

public interface Formula {
	double calculate(int a);
	// ���Զ���Ĭ�Ϸ���
	default double sqrt(int a) {
		return Math.sqrt(a);
	}
}
