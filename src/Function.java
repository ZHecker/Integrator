import java.util.ArrayList;
import java.util.Arrays;

public class Function {


	private final boolean debug = false;
	private String function;
	private final double PRECISION = 1000000000000.0;

	public Function(String function) {

		if(isValid(function))
		{
			this.function = function.toLowerCase().replace("e",Double.toString(Math.E)).replace("pi",Double.toString(Math.PI));
		}
		else
		{
			System.out.println("Not VALID!");
		}

	}

	public double getFunctionValue(double xVal)
	{
		//System.out.println("Function: " + function + " ; x = " + xVal);
		String evlFunction = function.replace("x", Double.toString(xVal));
		return evalExpression(evlFunction);
	}

	private boolean isValid(String expr)
	{
		int openBracketsCount = 0;
		int closedBracketsCount = 0;

		for (int i = 0; i < expr.length(); i++) {

			if(expr.charAt(i) == '(')
				openBracketsCount++;
			else if (expr.charAt(i) == ')')
				closedBracketsCount++;

		}

		if(openBracketsCount == closedBracketsCount)
		{
			return true;
		}

		return false;

	}

	private double evalExpression(String expr)
	{
		if(debug)
		{
			System.out.println("Evaluating: " + expr);
			System.out.println("--------------DEBUG-----------------");
		}
		String trig = evalTrig(expr);
		double value = evalBrackets(trig);
		if(debug)
		{
			System.out.println("--------------/DEBUG----------------");
		}
		return value;
	}

	private double evalBrackets(String expr)
	{
		if(debug)
		{
			System.out.println("evalBrackets: " + expr);
		}

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

			if(debug)
			{
				System.out.println("obEval: " + stringBuilder.toString() + " = " + res);
			}
			return res;
		}
		else //!openBracketFound
		{
			double res = numEvaluateArray(expr);
			if(debug)
			{
				System.out.println("!obEval: " + expr + " = " + res);
			}
			return res;
		}
	}

	private String evalTrig(String expr)
	{
		if(debug)
		{
			System.out.println("evalTrig: " + expr);
		}

		StringBuilder subString = new StringBuilder(expr);

		for (int i = 0; i < expr.length(); i++) {

			if(expr.charAt(i) == 's' && expr.charAt(i+1) == 'i' && expr.charAt(i+2) == 'n')
			{

				int openBracketCount = 0;
				int closingBracketCount = 0;
				int openBracketIndex = i+3;
				int closingBracketIndex = i+3;

				for (int j = i; j < expr.length(); j++) {

					if(expr.charAt(j) == '(')
					{
						openBracketCount++;
					}
					else if(expr.charAt(j) == ')')
					{
						closingBracketCount++;
					}

					if(closingBracketCount == openBracketCount && (closingBracketCount != 0 || openBracketCount != 0))
					{
						closingBracketIndex = j;
						break;
					}
				}

				String part = subString.substring(openBracketIndex +1, closingBracketIndex);
				double res = evalBrackets(part);
				subString.replace(i,closingBracketIndex+1,Double.toString(round(Math.sin(res))));
				expr = subString.toString();
			}
		}


		for (int i = 0; i < expr.length(); i++) {

			if(expr.charAt(i) == 'c' && expr.charAt(i+1) == 'o' && expr.charAt(i+2) == 's')
			{

				int openBracketCount = 0;
				int closingBracketCount = 0;
				int openBracketIndex = i+3;
				int closingBracketIndex = i+3;


				for (int j = i; j < expr.length(); j++) {

					if(expr.charAt(j) == '(')
					{
						openBracketCount++;
					}
					else if(expr.charAt(j) == ')')
					{
						closingBracketCount++;
					}

					if(closingBracketCount == openBracketCount && (closingBracketCount != 0 || openBracketCount != 0))
					{
						closingBracketIndex = j;
						break;
					}
				}


				String part = subString.substring(openBracketIndex +1, closingBracketIndex);
				double res = evalBrackets(part);
				subString.replace(i,closingBracketIndex+1,Double.toString(round(Math.cos(res))));
				expr = subString.toString();
			}
		}


		if(debug)
		{
			System.out.println("Trig Result: " + expr);
		}

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
