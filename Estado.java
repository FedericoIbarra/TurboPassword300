package sample;

import java.util.Vector;

//Clase Estado

public class Estado {

    private int numeroDeEstado;
    private Vector transiciones;
    private int multiplicador;


    /*

        Constructor que tiene como parametros Numero de estado, el vector de transiciones y el multiplicador.
        Este ultimo tambien sirve para saber si un estado es final.
        Donde (qn es final <-> multiplicador > 0).

     */

    public Estado(int est, Vector trs, int multiplicador){
        setNumeroDeEstado(est);
        setTransiciones(trs);
        setMultiplicador(multiplicador);
    }

    public void setNumeroDeEstado(int numeroDeEstado) {
        this.numeroDeEstado = numeroDeEstado;
    }

    public void setTransiciones(Vector transiciones) {
        this.transiciones = transiciones;
    }


    public void setMultiplicador(int multiplicador) {
        this.multiplicador = multiplicador;
    }

    public int getMultiplicadr(){
        return this.multiplicador;
    }
}
