public class Main {


	private static final int INCREMENT_STRIPE_AMOUNT = 1000;
	private static final int START_STRIPES = 1000;

	public static void main(String args[])
	{

		Function f1 = new Function("((x+x*x)+3)*x+cos(x*(x+1))");
		Function f2 = new Function("sin(x+(x*(x+x)))");
		Function f3 = new Function("x^2-1");

		calculateArea_Simpson(f2, -5, 5, START_STRIPES);
		calculateArea(f2,-5,5,START_STRIPES * 5);

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

		System.out.println("------Numerical integration (RECHT)-------");
		System.out.println("Integrated from " + minX + " to " + maxX);
		System.out.println("deltaX = " + deltaX + " => " + stripes);
		System.out.println("The Area is: " + area + " LE^2");
		System.out.println("-------/Numerical Integration--------");

		return area;

	}
	private static double calculateArea(Function f,double minX,double maxX, double epsilon)
	{
		int stripes = START_STRIPES;
		double diff;

		double oldArea;
		double newArea;

		oldArea = calculateArea(f, minX, maxX, stripes);

		do{
			newArea = calculateArea(f, minX, maxX, stripes+INCREMENT_STRIPE_AMOUNT);
			diff = Math.abs(newArea - oldArea);
			oldArea = newArea;
			stripes += INCREMENT_STRIPE_AMOUNT;

		}while (diff > epsilon);

		return newArea;
	}

	private static double calculateArea_Simpson(Function f,double minX,double maxX,int stripes)
	{
		double area = 0.0;
		double deltaX = (maxX - minX)/stripes;

		for (int i = 0; i < stripes; i++) {

			double a = minX + (i*deltaX);
			double b = minX + (i+1)*deltaX;
			area += (b-a)/6.0*( f.getFunctionValue(a)+ 4 * f.getFunctionValue((a+b)/2.0) + f.getFunctionValue(b));

		}


		System.out.println("------Numerical integration (SIMPSON)-------");
		System.out.println("Integrated from " + minX + " to " + maxX);
		System.out.println("deltaX = " + deltaX + " => " + stripes);
		System.out.println("The Area is: " + area + " LE^2");
		System.out.println("-------/Numerical Integration--------");

		return area;
	}

}
