Lenguajes Formales
Proyecto 3: TurboPassword


Rosendo Federico Ibarra López

24 de Noviembre, 2017

Solución al problema

Para mi es importante la seguridad en internet y creo que nos solo para mi sino para millones de personas alrededor del mundo, por lo que tener una contraseña segura es primordial.
Una de las maneras mas rápidas de ‘hackear’ una contraseña es utilizar algoritmos de fuerza bruta o mediante diccionarios por lo que tener un palabra común o muy corta hace que nuestras cuentas online sean vulnerables.

	Es por eso que para evitar dichos ataques se elaboro el siguiente programa, el cual define que tan segura es una cadena de texto dependiendo de que tan diferentes sean los caracteres utilizados (valor ACII), la longitud de la cadena y la utilización de mayusculas, símbolos y números. Dicho programa no acepta cadenas con caracteres iguales repetidos como “qqq” o “111111111” y da prioridad a los símbolos especiales tal como ‘!’.

	El programa es sencillo, introduces una cadena, presionas el botón de “Verificar” y te debe aparecer alguno de los siguientes resultados:
			
			- “Extremadamente Segura"
 		 	- “Muy Segura"
      - “Segura"
     	- “Debil"
     	- “Muy Débil”
		 	-“ Inválida”

donde cada uno de los resultado define tu contraseña explícitamente.

	Para la elaboración de dicho programa se utilizaron las herramientas que se explicaran en la próxima sección del documento.

















Algoritmos diseñados

Para este proyecto fue necesaria la elaboración de dos algoritmos así como un autómata finito determinista, dichos algoritmos son para calcular un valor numérico que sea determinado por el autómata tomando este como si fuera un grafo ponderado  y el otro algoritmo toma como base este dígito y según que tan grande sea determina que tan segura es la contraseña, donde entre mayor sea el numero mayor es la seguridad de la cadena.

Automata Q1:
	
	-Q = {q0, q1, q2, q3, q4}
	-∑ = {{q,w,e,r,t,y,u,i,o,p,a,s,d,f,g,h,j,k,l,z,x,c,v,b,n,m} = m,
	         {Q,W,E,R,T,Y,U,I,O,P,A,S,D,F,G,H,J,K,L,Z,X,C,V,B,N,M} = M,
	         {1,2,3,4,5,6,7,8,9,0} = N,
	         {!,”,#,$,%,&,/,(,),*,+,’,-,.} = S,
	         {x | x ¢ m ^ x ¢ M ^ x ¢ N ^ x ¢ S} = E}

	-q0
	-s = 	(q0,m) = q0
		(q0,M) = q1
		(q0,N) = q2
		(q0,S) = q3
		(q0,E) = q4
		
		(q1,m) = q0
		(q1,M) = q1
		(q1,N) = q2
		(q1,S) = q3
		(q1,E) = q4
		
		(q2,m) = q0
		(q2,M) = q1
		(q2,N) = q2
		(q2,S) = q3
		(q2,E) = q4
		
		(q3,m) = q0
		(q3,M) = q1
		(q3,N) = q2
		(q3,S) = q3
		(q3,E) = q4
		
		(q4,∑) = q4

	-F = {q0,q1,q2,q3}
automata[0] = new Estado(0, transiciones, 1);
automata[1] = new Estado(1, transiciones, 10);
automata[2] = new Estado(2, transiciones, 100);
automata[3] = new Estado(3, transiciones, 1500);
automata[4] = new Estado(4, transicionesError, 0);





Algoritmo 1

	1-Resta el valor ASCII de Xn a X+1

	2-El valor anterior es siempre positivo

	3-Este valor es multiplicado por el ‘multiplicador’ (mx) que depende de cada 		   estado (q0 = 1, q1 = 10, q2= 100, q3 = 1000, q4 = 0)

	4-Por ultimo es sumado al valor calculado en la transición pasada.
	
Al final se tiene un numero mayor o igual a 0 donde si es 0 es porque solo se introdujo el mismo carácter n veces o se insertó una cadena no aceptada por el autómata
public static int calcularGanancia(char actual, char pasado, int estado, Estado [] automata){
    int ganancia, diferencia;
    int actualInt = (int) actual;
    int pasadoInt = (int) pasado;
    diferencia = pasadoInt-actualInt;

    if(diferencia < 0){
        diferencia = diferencia * (-1);
    }

    ganancia = diferencia * automata[estado].getMultiplicadr();
    //System.out.println(ganancia);

    return ganancia;
}

Algoritmo 2

	1-Recibe como parámetro la sumatoria obtenida por el algoritmo 1.

	2-Da el siguiente resultado en texto
		 si( valor > 5000) 		-> "Extremadamente Segura"
 		 si( 2000 < valor < 5000) 	-> "Muy Segura"
        		 si( 1500 < valor < 2000) 	-> "Segura"
                      si( 500   < valor < 100) 	-> "Debil"
                      si( 60     < valor < 500)	-> "Muy Débil”
		 si( valor = 0)			-> “Inválida”
public static String esSegura(int valor){
        if( valor > 5000) return "Extremadamente Segura";
        else if( valor > 2000 && valor < 5000) return "Muy Segura";
        else if( valor > 1500 && valor < 2000) return "Segura";
        else if( valor > 500 &&  valor < 100) return "Debil";
        else if( valor > 60 && valor < 500) return "Muy Debil";
        else return "Invalida";
    }
}





















Ejemplos












































Implementación


	Para la implementación de dicho programa se utilizaron los algoritmos anteriormente descritos, en el lenguaje Java desarrollado en IntelliJ IDEA y las librerías gráficas de JavaFX.

	El  programa consta de dos clases, Main en la cual están incluidos los métodos gráficos de JavaFX, los dos algoritmos y la creación del autómata así como la integración de todo esto; y la clase Estado que tal cual como su nombre lo indica es la clase en la que se define cada uno de los estados del autómata y sus propiedades individuales (numero de estado, fila de transiciones y multiplicador). 
public class Estado {

    private int numeroDeEstado;
    private Vector transiciones;
    private int multiplicador;
}






Conclusiones

	El desarrollo de este pequeño programa me deja con un panorama mucho mas amplio de las aplicaciones que pueden tener los autómatas así como los vistos en clase ya opté por utilizar AFD sin embargo, se pudieron haber utilizado expresiones regulares o autómatas con pila para la implementación de este problema. Finalmente, es importante saber como utilizar las herramientas ya que de nada sirve la mejor herramienta del mundo si no sabemos utilizarla, de nada sirve toda la teoría sin no la ponemos en practica y creo que este proyecto aporta eso, el poder desarrollar algo útil con toda la teoría vista en clase.









Referencias Bibliográficas

-Chakraborty, Samarjit (17 de marzo de 2003). «Formal Languages and Automata Theory. Regular Expressions and Finite Automata». Computer Engineering and Networks Laboratory. Swiss Federal Institute of Technology (ETH) Zürich (en inglés): 17. Consultado el 30 de marzo de 2010

-«Sun Microsystems Unveils JavaFX 1.0 with Immersive Media Capabilities Targeted at Market's 800 Million Java Powered Desktops»






























