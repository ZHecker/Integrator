public class Main {


	private static final int INCREMENT_STRIPE_AMOUNT = 1000;
	private static final int START_STRIPES = 10000;

	public static void main(String args[])
	{

		Function f1 = new Function("((x+x*x)+3)*x+cos(x*(x+1))");
		calculateArea(f1,-1,1,100);


		//System.out.println(f1.getFunctionValue(2));
		//System.out.println(calculateArea(f1, 0, 1, 10));

	}



	private static double calculateArea(Function f,double minX,double maxX,int stripes)
	{
		double area = 0.0;
		double deltaX = (maxX - minX)/stripes;


		double saveComputingTime = f.getFunctionValue(minX);

		for (int i = 0; i < stripes; i++) {
			double xNew = f.getFunctionValue(minX + (i + 1) * deltaX);
			double h = (saveComputingTime+xNew)/2;
			saveComputingTime = xNew;
			area += deltaX * h;
		}

		System.out.println("------Numerical integration-------");
		System.out.println("Integrated from " + minX + " to " + maxX);
		System.out.println("deltaX = " + deltaX);
		System.out.println("The Area is: " + area + " LE^2");
		System.out.println("-------/Numerical Integration--------");

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
