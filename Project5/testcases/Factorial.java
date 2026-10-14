class Factorial{
    public static void main(String[] a){
	Xinu.printint(new Fac().ComputeFac(10));
	Xinu.printint(new Fac().ComputeFac(12));
	
    }
	

	
}

class Fac {

    public int ComputeFac(int num){
	int num_aux = 0;
	if (num < 1)
	    num_aux = 1 ;
	
	return num_aux ;
    }

}
