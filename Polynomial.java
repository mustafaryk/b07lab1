import java.io.*;

public class Polynomial{

	private double [] coefficients;
	private int [] exponents;

	public Polynomial(){
		coefficients = new double[1];
		coefficients[0] = 0;
		exponents = new int[1];
		exponents[0] = 0;
	}
	
	public Polynomial(double coefficients_args[], int exponents_args[]){
		coefficients = coefficients_args;
		exponents = exponents_args;
	}
	
	public Polynomial(File file) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader(file));
		String line = input.readLine();
		input.close();
		line = line.replace("-", "+-");
		String[] split_strings = line.split("\\+");
		double new_coefficients[] = new double[split_strings.length];
		int new_exponents[] = new int[split_strings.length];
		for (int i=0;i<split_strings.length; i++){
			String str = split_strings[i];
			if (str.contains("x")){
				String[] meow = str.split("x");
				new_coefficients[i] = Double.parseDouble(meow[0]);
				new_exponents[i] = Integer.parseInt(meow[1]);
			}
			else {
				new_exponents[i] = 0;
				new_coefficients[i] = Double.parseDouble(str);
			}
		}
		coefficients = new_coefficients;
		exponents = new_exponents;

	}
	
	public Polynomial multiply(Polynomial other) {
		double new_coefficients_temp [] = new double[other.coefficients.length*coefficients.length];
		int new_exponents_temp [] = new int[other.coefficients.length*coefficients.length];
		int counter = 0;
		for (int i=0; i< coefficients.length; i++) {
			for (int j=0; j < other.coefficients.length; j++) {
				new_coefficients_temp[counter] = coefficients[i] * other.coefficients[j];
				new_exponents_temp[counter] =	exponents[i] + other.exponents[j];
				counter++;
			}
		
		}
		double new_coefficients_temp_temp [] = new double[other.coefficients.length*coefficients.length];
		int new_exponents_temp_temp [] = new int[other.coefficients.length*coefficients.length];
		int new_poly_length = 0;
		for(int i=0; i< coefficients.length * other.coefficients.length; i++) {
			if(new_exponents_temp[i] != - 1){
				int exponent_ref = new_exponents_temp[i];
				double sum = 0;
				for(int j=i;j< coefficients.length * other.coefficients.length; j++) {
					if(new_exponents_temp[j] == exponent_ref) {
						sum += new_coefficients_temp[j];
						new_exponents_temp[j] = -1;
					}
				}
				new_coefficients_temp_temp[new_poly_length] = sum;
				new_exponents_temp_temp[new_poly_length] = exponent_ref;
				new_poly_length++;
				
			}
		}
		double new_coefficients [] = new double[new_poly_length];
		int new_exponents [] = new int[new_poly_length];
		for (int i=0; i<new_poly_length; i++) {
			new_coefficients[i] = new_coefficients_temp_temp[i];
			new_exponents[i] = new_exponents_temp_temp[i];
		}
		
		return new Polynomial(new_coefficients, new_exponents);
		
	}
	public Polynomial add(Polynomial other){
		
		double new_coefficients_temp [] = new double[other.coefficients.length+coefficients.length];
		int new_exponents_temp [] = new int[other.coefficients.length+coefficients.length];
		for (int i=0; i< coefficients.length; i++) {
			new_coefficients_temp[i] = coefficients[i];
			new_exponents_temp[i] =	exponents[i];
		
		}
		int new_poly_length = coefficients.length;
		
		for (int i=0; i< other.coefficients.length; i++) {
			boolean in_other = false;
			for(int j=0; j < new_poly_length; j++){
				if(new_exponents_temp[j] == other.exponents[i]) {
					in_other = true;
					new_coefficients_temp[j] += other.coefficients[i];
					break;
				}
				
			}
			if (in_other == false) {
				new_coefficients_temp[new_poly_length] = other.coefficients[i];
				new_exponents_temp[new_poly_length] =	other.exponents[i];
				new_poly_length++;
			}
		
		}
		
		double new_coefficients [] = new double[new_poly_length];
		int new_exponents [] = new int[new_poly_length];
		for (int i=0; i<new_poly_length; i++) {
			new_coefficients[i] = new_coefficients_temp[i];
			new_exponents[i] = new_exponents_temp[i];
		}
	
		
		return new Polynomial(new_coefficients, new_exponents);

	}
	public double evaluate(double arg){
		double total=0;
		for(int i=0;i<coefficients.length;i++){
			total = total + coefficients[i]*Math.pow(arg, exponents[i]);
		}
		return total;
	}
	public boolean hasRoot(double arg){
		double answer = evaluate(arg);
		if (answer == 0){
			return true;
		}
		else{
			return false;
		}
	}
	public void saveToFile(String file_name) throws IOException{
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<coefficients.length;i++) {
			double coefficient = coefficients[i];
			int exponent = exponents[i];
			if(exponent == 0) {
				sb.append(coefficient);
			}
			else {
				if(coefficient<0) {
					sb.append(coefficient);
				}
				else {
					sb.append("+");
					sb.append(coefficient);
				}
				sb.append("x");
				sb.append(exponent);
			}
		}
		PrintStream output = new PrintStream(file_name);
		output.print(sb.toString());
	}

}