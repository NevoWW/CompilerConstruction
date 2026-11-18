
class Inheritence{
    
    public static void main(String[] args){
    int[][] name;
	Xinu.printint(new a().a());
    Xinu.printint(new b().a());

    Xinu.print("5");
	Xinu.println("5");
	Xinu.readint(5);


    }
}

class a {
    int x;
    int y;
    public int a(){return 5;}
}
class b extends a{
    int z;
    public int a(){return 6;}
}
