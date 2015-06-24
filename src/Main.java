import java.util.ArrayList;
import java.util.Arrays;

public class Main {


	private static final int INCREMENT_STRIPE_AMOUNT = 100000;

	public static void main(String args[])
	{
		getFunctionValue("5-3*4",0);
		//System.out.println(calculateArea(0, Math.PI, 0.00000000000001));

	}


	private static double calculateArea(double minX,double maxX,int stripes)
	{
		double area = 0.0;
		double deltaX = (maxX - minX)/stripes;

		for (int i = 0; i < stripes; i++) {
			double h = (functionValue(i*deltaX) + functionValue((i+1)*deltaX))/2;
			area += deltaX * h;
		}

		return area;

	}


	private static double calculateArea(double minX,double maxX, double epsilon)
	{
		int stripes = 100000;
		double diff;
		double area;

		do{
			area = calculateArea(minX, maxX, stripes+INCREMENT_STRIPE_AMOUNT);
			diff = Math.abs(area - calculateArea(minX, maxX, stripes));
			stripes += INCREMENT_STRIPE_AMOUNT;

		}while (diff > epsilon);

		System.out.println("Used " + stripes + " stripes!");
		return area;

	}



	private static void getFunctionValue(String input,double xVal)
	{
		input = input.toLowerCase();
		ArrayList<String> functionArray = new ArrayList<String>(Arrays.asList(input.split("((?<=\\*|\\/|\\+)|(?=\\*|\\/|\\+))")));


		// Replace X's with their Value
		for (int i = 0; i < functionArray.size(); i++) {

			if(functionArray.get(i).equals("x"))
			{
				functionArray.set(i,Double.toString(xVal));
			}

			System.out.println(functionArray.get(i));

		}


		boolean foundChar = true;


		// First Multiplication and Division
		// Second Addition and Subtraction


		while (foundChar)
		{

			foundChar = false;

			for (int i = 0; i < functionArray.size(); i++) {

				if(functionArray.get(i).equals("*"))
				{
					double factor1 = Double.parseDouble(functionArray.get(i-1));
					double factor2 = Double.parseDouble(functionArray.get(i+1));
					double result = factor1*factor2;

					functionArray.set(i,Double.toString(result));
					functionArray.remove(i+1);
					functionArray.remove(i-1);

					foundChar = true;
					break;
				}

				if(functionArray.get(i).equals("/"))
				{
					double dividend = Double.parseDouble(functionArray.get(i-1));
					double divisor = Double.parseDouble(functionArray.get(i+1));
					double result = dividend/divisor;

					functionArray.set(i,Double.toString(result));
					functionArray.remove(i+1);
					functionArray.remove(i-1);

					foundChar = true;
					break;
				}

			}

		}


		foundChar = true;

		while(foundChar)
		{

			foundChar = false;

			for (int i = 0; i < functionArray.size(); i++) {

				if (functionArray.get(i).equals("+")) {
					double summand1 = Double.parseDouble(functionArray.get(i - 1));
					double summand2 = Double.parseDouble(functionArray.get(i + 1));
					double result = summand1 + summand2;

					functionArray.set(i, Double.toString(result));
					functionArray.remove(i + 1);
					functionArray.remove(i - 1);

					foundChar = true;
					break;
				}

			}
		}




		System.out.println("--------------------");


		for(String element : functionArray)
		{
			System.out.println(element);
		}







	}


	private static double functionValue(double x)
	{
			return Math.sin(x);
	}



}
