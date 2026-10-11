
class Inheritence{
    
    public static void main(String[] a){
	Xinu.printint(new a().a());
    Xinu.printint(new b().a());

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