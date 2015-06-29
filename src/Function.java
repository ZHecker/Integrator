import java.util.ArrayList;
import java.util.Arrays;

public class Function {


	private String function;
	private final double PRECISION = 1000000000000.0;

	public Function(String function) {
		this.function = function.toLowerCase().replace("e",Double.toString(Math.E)).replace("pi",Double.toString(Math.PI));
	}

	public double getFunctionValue(double xVal)
	{
		System.out.println("Function: " + function + " ; x = " + xVal);
		String evlFunction = function.replace("x",Double.toString(xVal));
		return evalExpression(evlFunction);
	}

	private double evalExpression(String expr)
	{
		System.out.println("Evaluating: " + expr);
		System.out.println("--------------DEBUG-----------------");
		String trig = evalTrig(expr);
		double value = evalBrackets(trig);
		System.out.println("--------------/DEBUG----------------");
		return value;
	}

	private double evalBrackets(String expr)
	{
		System.out.println("evalBrackets: " + expr);

		boolean openBracketFound = false;
		int openBracketIndex = -1;
		int closingBracketIndex = -1;

		for (int i = 0; i < expr.length(); i++) {

			if(expr.charAt(i) == '(' && !openBracketFound)
			{
				openBracketFound = true;
				openBracketIndex = i;
			}

			if(expr.charAt(i) == ')')
			{
				closingBracketIndex = i;
			}
		}

		if(openBracketFound)
		{
			double value = evalBrackets(expr.substring(openBracketIndex + 1, closingBracketIndex));
			StringBuilder stringBuilder = new StringBuilder(expr);
			stringBuilder.replace(openBracketIndex, closingBracketIndex + 1,Double.toString(value));

			double res = numEvaluateArray(stringBuilder.toString());
			System.out.println("obEval: " + stringBuilder.toString() + " = " + res);
			return res;
		}
		else //!openBracketFound
		{
			double res = numEvaluateArray(expr);
			System.out.println("!obEval: " + expr + " = " + res);
			return res;
		}
	}

	private String evalTrig(String expr)
	{

		System.out.println("evalTrig: " + expr);
		StringBuilder subString = new StringBuilder(expr);

		for (int i = 0; i < expr.length(); i++) {

			if(expr.charAt(i) == 'c' && expr.charAt(i+1) == 'o' && expr.charAt(i+2) == 's')
			{
				boolean foundClose = false;
				int openBracketIndex = i+3;
				int closingBracketIndex = i+3;

				while (!foundClose)
				{
					if(expr.charAt(closingBracketIndex) == ')')
					{
						foundClose = true;
					}
					else
					{
						closingBracketIndex++;
					}
				}

				double res = evalBrackets(subString.substring(openBracketIndex + 1, closingBracketIndex + 1));
				subString.replace(i,closingBracketIndex+2,Double.toString(round(Math.cos(res))));
				expr = subString.toString();
			}
			else if(expr.charAt(i) == 's' && expr.charAt(i+1) == 'i' && expr.charAt(i+2) == 'n')
			{
				boolean foundClose = false;
				int openBracketIndex = i+3;
				int closingBracketIndex = i+3;

				while (!foundClose)
				{
					if(expr.charAt(closingBracketIndex) == ')')
					{
						foundClose = true;
					}
					else
					{
						closingBracketIndex++;
					}
				}

				double res = evalBrackets(subString.substring(openBracketIndex + 1, closingBracketIndex + 1));
				subString.replace(i,closingBracketIndex+2,Double.toString(round(Math.sin(res))));
				expr = subString.toString();
			}
		}

		System.out.println("Trig Result: " + expr);
		return expr;
	}

	private double numEvaluateArray(String expr)
	{
		String regex = "((?<=\\*|\\/|\\+|\\^)|(?=\\*|\\/|\\+|\\^))";
		ArrayList<String> exprArray = new ArrayList<>(Arrays.asList(expr.split(regex)));
		return numEvaluateArray(exprArray);
	}

	private double numEvaluateArray(ArrayList<String> functionArray)
	{
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

		return Double.parseDouble(functionArray.get(0));
	}

	private double round(double d)
	{
		return Math.round(d * PRECISION) / PRECISION;
	}

}
