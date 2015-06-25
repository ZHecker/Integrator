import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Function {


	private String function;
	private final double PRECISION = 1000000000000.0;

	public Function(String function) {
		this.function = function.toLowerCase();
	}

	public double getFunctionValue(double xVal)
	{
		String regex = "((?<=\\*|\\/|\\+|\\^)|(?=\\*|\\/|\\+|\\^))";

		String lFunction = function.replace("x",Double.toString(xVal));
		Pattern p = Pattern.compile("\\((.*?)\\)");
		Matcher m = p.matcher(lFunction);

		while(m.find()) {
			String inside = m.group(1);
			ArrayList<String> insideArray = new ArrayList<>(Arrays.asList(inside.split(regex)));
			evaluateArray(insideArray);
			lFunction = lFunction.replace(inside,insideArray.toString().replaceAll("\\[|\\]",""));
		}

		ArrayList<String> functionArray = new ArrayList<>(Arrays.asList(lFunction.split(regex)));
		evaluateArray(functionArray);


		if(functionArray.size() > 1)
		{
			System.out.println("--------------------");

			for(String element : functionArray)
			{
				System.out.println(element);
			}

			return 0;

		}

		return Double.parseDouble(functionArray.get(0));

	}

	private void evaluateArray(ArrayList<String> functionArray)
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


				if(functionArray.get(i).equals("^"))
				{
					double base = Double.parseDouble(functionArray.get(i-1));
					double exponent = Double.parseDouble(functionArray.get(i+1));
					double result = Math.pow(base,exponent);

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

	private double round(double d)
	{
		return Math.round(d * PRECISION) / PRECISION;
	}

}
