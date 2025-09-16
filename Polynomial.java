public class Polynomial{

	private double [] coefficients;

	public Polynomial(){
		coefficients = new double[1];
		coefficients[0] = 0;
	}
	public Polynomial(double args[]){
		coefficients = args;
	}
	public Polynomial add(Polynomial other){
		int greater;
		if(coefficients.length>other.coefficients.length){
			greater=coefficients.length;
		}
		else{
			greater=other.coefficients.length;
		}
		double arr [] = new double[greater];
		for(int i=0;i<greater;i++){
			if(i<coefficients.length){
				arr[i] += coefficients[i];
			}
			if(i<other.coefficients.length){
				arr[i] += other.coefficients[i];
			}
		}
		return new Polynomial(arr);

	}
	public double evaluate(double arg){
		double total=0;
		for(int i=0;i<coefficients.length;i++){
			total = total + coefficients[i]*Math.pow(arg, i);
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

}