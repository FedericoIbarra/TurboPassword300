package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import sample.Estado;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class Main extends Application {

    Button botonVerificar;

    //Interfaz Grafica JavaFX
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("TuboPassword 3000");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);

        Text scenetitle = new Text("Inserte Password a verificar");
        grid.add(scenetitle, 0, 0, 2, 1);

        //javafx.scene.control.TextField contra = new javafx.scene.control.TextField();
        //grid.add(contra, 1, 1);
        //contra = contra.getText

        TextField contra = new TextField();
        grid.add(contra, 1,1);
        //seguridad = contra.getText();

        javafx.scene.control.Button botonVerificar = new javafx.scene.control.Button();
        botonVerificar.setText("Verificar");

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().add(botonVerificar);
        grid.add(hbBtn,1,4);

        Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        botonVerificar.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
               // actiontarget.setFill(Color.FIREBRICK);
                //actiontarget.setText(seguridad);
                actiontarget.setText(inico(contra.getText()));
            }
        });

        primaryStage.show();
    }


    //Metodo principal de la clase
    public static void main(String[] args) {

        launch(args);

    }


    //Metodo principal del algoritmo
    public static String inico(String contrasena){

        int valorDeContraseña = 0, i;
        String contra = contrasena;
        char actual = 0, pasado;
        int estadoActual = 0;
        Estado [] automata = new Estado[5];

        //Crea los vectores de transicion para los estados q0-q3
        Vector transiciones = new Vector();
        transiciones.add(0);
        transiciones.add(1);
        transiciones.add(2);
        transiciones.add(3);
        transiciones.add(4);

        //Transiciones del estado q4 (Error)
        Vector transicionesError = new Vector();
        transicionesError.add(4);
        transicionesError.add(4);
        transicionesError.add(4);
        transicionesError.add(4);
        transicionesError.add(4);


        //Crea cada uno de los estados donde F={q0,q1,q2,q3}
        automata[0] = new Estado(0, transiciones, 1);
        automata[1] = new Estado(1, transiciones, 10);
        automata[2] = new Estado(2, transiciones, 100);
        automata[3] = new Estado(3, transiciones, 1500);
        automata[4] = new Estado(4, transicionesError, 0);


        //Hace el recorrido del automata con la cadena introducida
        for(i = 0; i < contra.length() ; i++){

            if(i == 0){
                actual = contra.charAt(0);
                pasado = contra.charAt(0);
            }
            else{
                pasado = actual;
                actual = contra.charAt(i);
            }

            estadoActual = cambiarEstado(contra.charAt(i),estadoActual,transiciones);

            if(estadoActual == 4) valorDeContraseña = 0;
            valorDeContraseña = valorDeContraseña + calcularGanancia(actual,pasado,estadoActual,automata);

        }
        //System.out.println(valorDeContraseña);
        return esSegura(valorDeContraseña);
    }


    //Metodo que hace el movimiento entre estados dependiendo de el caracter que se este verificando

    public static int cambiarEstado(char caracter, int estado, Vector trs){
        int nuevoEstado = 0;
        int caracterAscii = (int) caracter;

        if((caracterAscii > 96) && (caracterAscii < 123)){
            nuevoEstado = (int) trs.elementAt(0);
        }

        else if((caracterAscii > 64) && (caracterAscii < 90)){
            nuevoEstado = (int) trs.elementAt(1);
        }

        else if((caracterAscii > 47) && (caracterAscii < 58)){
            nuevoEstado = (int) trs.elementAt(2);
        }

        else if((caracterAscii > 32) && (caracterAscii < 48)){
            nuevoEstado = (int) trs.elementAt(3);
        }

        else{
            nuevoEstado = (int) trs.elementAt(4);
        }

        return nuevoEstado;
    }


    /*
        Este metodo toma al automata como si fuera un grafo.
        Toma como base la diferencia que existe entre los valores de los caracteres segun el codigo ASCII.
        Y lo multiplica segun el estado al que se movió.
     */

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


    /*
        Este metodo es el encargado de determinar la seguridad de la cadena introducinda
        según los resutlados del algorito anterior.
     */

    public static String esSegura(int valor){
        if( valor > 5000) return "Extremadamente Segura";
        else if( valor > 2000 && valor < 5000) return "Muy Segura";
        else if( valor > 1500 && valor < 2000) return "Segura";
        else if( valor > 500 &&  valor < 100) return "Debil";
        else if( valor > 60 && valor < 500) return "Muy Debil";
        else return "Invalida";
    }
}
