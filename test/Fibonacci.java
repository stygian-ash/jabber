public class Fibonacci {
	public static long fibonacci(long n) {
		if (n <= 0)
			return 0;
		if (n == 1)
			return 1;
		return fibonacci(n - 1) + fibonacci(n - 2);
	}

	public static void main(String[] args) {
		for (int i = 1; i <= 10; i++)
			System.out.printf("F_%d = %d\n", i, fibonacci(i));
	}
}
