public class Main {


	private static final int INCREMENT_STRIPE_AMOUNT = 1000;
	private static final int START_STRIPES = 10000;

	public static void main(String args[])
	{

		Function f1 = new Function("cos(x)*sin(2^x)");
		System.out.println(calculateArea(f1, 0, 2, 0.00001));

	}



	private static double calculateArea(Function f,double minX,double maxX,int stripes)
	{
		double area = 0.0;
		double deltaX = (maxX - minX)/stripes;

		for (int i = 0; i < stripes; i++) {
			double h = (f.getFunctionValue(i*deltaX) + f.getFunctionValue((i + 1) * deltaX))/2;
			area += deltaX * h;
		}

		return area;

	}

	private static double calculateArea(Function f,double minX,double maxX, double epsilon)
	{
		int stripes = START_STRIPES;
		double diff;
		double area;

		do{
			area = calculateArea(f,minX, maxX, stripes+INCREMENT_STRIPE_AMOUNT);
			diff = Math.abs(area - calculateArea(f,minX, maxX, stripes));
			stripes += INCREMENT_STRIPE_AMOUNT;

		}while (diff > epsilon);

		System.out.println("Used " + stripes + " stripes!");
		return area;

	}

}
