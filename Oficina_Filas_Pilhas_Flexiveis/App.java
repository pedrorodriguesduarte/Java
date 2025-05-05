
public class App {
    
public static void main(String [] args ){
Pilha <Integer> p = new Pilha <>();
int num1 = 1;
int num2 = 2;
p.empilhar(num1);
p.empilhar(num2);
System.out.println(p.consultarTopo());
p.desempilhar();
System.out.println(p.consultarTopo());

}

}
