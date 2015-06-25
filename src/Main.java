import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {


	private static final int INCREMENT_STRIPE_AMOUNT = 100000;
	private static final double PRECISION = 1000000000000.0;

	public static void main(String args[])
	{

		getFunctionValue("(5.1+1.1)*(2.2+3.2)",0);

	}

	private static double round(double d)
	{
		return Math.round(d * PRECISION) / PRECISION;
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
		input = input.replace("x",Double.toString(xVal));


		Pattern p = Pattern.compile("\\((.*?)\\)");
		Matcher m = p.matcher(input);

		while(m.find()) {
			String inside = m.group(1);
			ArrayList<String> insideArray = new ArrayList<>(Arrays.asList(inside.split("((?<=\\*|\\/|\\+)|(?=\\*|\\/|\\+))")));
			evaluateArray(insideArray);
			input = input.replace(inside,insideArray.toString().replaceAll("\\[|\\]",""));
		}

		ArrayList<String> functionArray = new ArrayList<String>(Arrays.asList(input.split("((?<=\\*|\\/|\\+)|(?=\\*|\\/|\\+))")));
		evaluateArray(functionArray);

		System.out.println("--------------------");

		for(String element : functionArray)
		{
			System.out.println(element);
		}


	}



	private static void evaluateArray(ArrayList<String> functionArray)
	{
		// Evaluate SIN and COS

		for (int i = 0; i < functionArray.size(); i++) {

			if(functionArray.get(i).contains("sin"))
			{
				String str = functionArray.get(i);
				double inside = Double.parseDouble(str.substring(str.indexOf("(") + 1, str.indexOf(")")));
				functionArray.set(i, Double.toString(round(Math.sin(inside))));

			}
			else if(functionArray.get(i).contains("cos"))
			{
				String str = functionArray.get(i);
				double inside = Double.parseDouble(str.substring(str.indexOf("(")+1,str.indexOf(")")));
				functionArray.set(i, Double.toString(round((Math.cos(inside)))));
			}

		}


		for (int i = 0; i < functionArray.size(); i++) {


			functionArray.set(i,functionArray.get(i).replaceAll("\\(|\\)",""));

		}




		boolean foundChar = true;

		// First Multiplication and Division
		// Second Addition and 'Subtraction'


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
					double dividend = Double.parseDouble(functionArray.get(i - 1));
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

	}


	private static double functionValue(double x)
	{
			return Math.sin(x);
	}



}
