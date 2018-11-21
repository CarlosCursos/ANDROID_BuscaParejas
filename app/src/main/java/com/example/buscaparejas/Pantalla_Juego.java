package com.example.buscaparejas;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Pantalla_Juego extends AppCompatActivity implements View.OnClickListener {
    private final int TOTAL_CASILLAS=8;

    private ImageView[] casillas = new ImageView[TOTAL_CASILLAS];
    private ArrayList<Integer> posiciones = new ArrayList();
    //Para saber si es el primer o segundo toque:
    private int contador_toques;
    //Para saber en que casilla se hace el primer toque:
    private int posicion_primer_toque;
    //Para saber que imagen hay en esa casilla del primer toque:
    private int img_primer_toque;

    //Para saber en que casilla se hace el segundo toque:
    private int posicion_segundo_toque;
    //Para saber que imagen hay en esa casilla del primer toque:
    private int img_segundo_toque;

    //Para contar el numero de aciertos
    private int aciertos = 0;

    //Para controlar que no pueda pinchar sobre mas de dos imágenes cada vez:
    private boolean puede_tocar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_juego);

        //Asociamos cada posición del array casillas con los ImageViews del interfaz;
        casillas[0] = findViewById(R.id.img0);
        casillas[1] = findViewById(R.id.img1);
        casillas[2] = findViewById(R.id.img2);
        casillas[3] = findViewById(R.id.img3);
        casillas[4] = findViewById(R.id.img4);
        casillas[5] = findViewById(R.id.img5);
        casillas[6] = findViewById(R.id.img6);
        casillas[7] = findViewById(R.id.img7);

        /*
        //Podemos hacer la asignación anterior mediante un bucle que obtenga de forma dinámica el id de resource
        for (int i=0; i<TOTAL_CASILLAS; i++) {
            int res_id = getResources().getIdentifier(String.format("img%s",i),"id",getPackageName());
            casillas[i] = findViewById(res_id);
        }
        */

        //Registramos el evento click sobre cada imagen. En este caso, con una sola funcion onClick
        //atendemos a todas las imágenes:
        casillas[0].setOnClickListener(Pantalla_Juego.this);
        casillas[1].setOnClickListener(Pantalla_Juego.this);
        casillas[2].setOnClickListener(Pantalla_Juego.this);
        casillas[3].setOnClickListener(Pantalla_Juego.this);
        casillas[4].setOnClickListener(Pantalla_Juego.this);
        casillas[5].setOnClickListener(Pantalla_Juego.this);
        casillas[6].setOnClickListener(Pantalla_Juego.this);
        casillas[7].setOnClickListener(Pantalla_Juego.this);

        /*
        //Lo mismo que antes, mediante un bucle:
        for (int i=0;i<=TOTAL_CASILLAS;i++) {
            casillas[i].setOnClickListener(Pantalla_Juego.this);
        }
        */

        //Añadimos las imagenes en parejas:
        posiciones.add(R.drawable.manzana);
        posiciones.add(R.drawable.manzana);
        posiciones.add(R.drawable.pera);
        posiciones.add(R.drawable.pera);
        posiciones.add(R.drawable.cerezas);
        posiciones.add(R.drawable.cerezas);
        posiciones.add(R.drawable.tomate);
        posiciones.add(R.drawable.tomate);

        nuevaPartida();
    }

    private void nuevaPartida(){
        //Las desordenamos para que aparezcan de forma aleatoria:
        Collections.shuffle(posiciones);
        //Ponemos los aciertos a 0:
        aciertos = 0;
        //Contador de toques a 0:
        contador_toques = 0;
        //Mostramos en todas las casillas la imagen por defecto:
        casillas[0].setImageResource(R.drawable.img_ocultar);
        casillas[1].setImageResource(R.drawable.img_ocultar);
        casillas[2].setImageResource(R.drawable.img_ocultar);
        casillas[3].setImageResource(R.drawable.img_ocultar);
        casillas[4].setImageResource(R.drawable.img_ocultar);
        casillas[5].setImageResource(R.drawable.img_ocultar);
        casillas[6].setImageResource(R.drawable.img_ocultar);
        casillas[7].setImageResource(R.drawable.img_ocultar);

        /*
        //Lo mismo con un bucle:
        for (int i=0;i<TOTAL_CASILLAS;i++) {
            casillas[i].setImageResource(R.drawable.img_ocultar);
        }
        */

        //Habilitamos todas las casillas:
        casillas[0].setEnabled(true);
        casillas[1].setEnabled(true);
        casillas[2].setEnabled(true);
        casillas[3].setEnabled(true);
        casillas[4].setEnabled(true);
        casillas[5].setEnabled(true);
        casillas[6].setEnabled(true);
        casillas[7].setEnabled(true);

        /*
        //Lo mismo con un bucle:
        for (int i=0;i<TOTAL_CASILLAS;i++) {
            casillas[i].setEnabled(true);
        }
        */

        //Ya puede empezar a jugar:
        puede_tocar = true;

    }

    @Override
    public void onClick(View casilla_tocada) {
        //Si aun no puede tocar, no hacemos nada:
        if (puede_tocar == false) return;
        //Posicion sobre la que se ha tocado:
        int posicion_toque = Integer.valueOf(casilla_tocada.getTag().toString());
        //Contamos el toque:
        contador_toques = contador_toques + 1;
        //Mostramos la imagen que hay en esa posicion:
        casillas[posicion_toque].setImageResource(posiciones.get(posicion_toque));
        //Si es el primer toque, guardamos que
        //posicion es, para comparar cuando se haga el segundo toque:
        if (contador_toques == 1) {
            posicion_primer_toque = posicion_toque;
            img_primer_toque = posiciones.get(posicion_toque);
        } else {
            //Es el segundo toque, a ver que imagen hay y en qué posición:
            posicion_segundo_toque = posicion_toque;
            img_segundo_toque = posiciones.get(posicion_toque);
            //comparamos con lo que imagen del primer toque a ver si hay acierto o fallo:
            if (img_primer_toque == img_segundo_toque) {
                //Deshabilitamos las casillas para que no se puedan volver a tocar:
                casillas[posicion_primer_toque].setEnabled(false);
                casillas[posicion_segundo_toque].setEnabled(false);
                //Sumamos un acierto y dejamos las imagenes descubiertas:
                aciertos = aciertos + 1;
                //Si ya ha descubierto todas, mensaje de fin de juego:
                if (aciertos == TOTAL_CASILLAS/2) {
                    Toast.makeText(Pantalla_Juego.this, "FIN", Toast.LENGTH_SHORT).show();
                    //Esperamos 2 segundos y empezamos nueva partida:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Pantalla_Juego.this, "Nueva partida!!", Toast.LENGTH_SHORT).show();
                            nuevaPartida();
                        }
                    },2000);
                }
            } else {
                //Bloqueamos para que no haga click hasta que no ocultemos las imagenes:
                puede_tocar = false;
                //Ha fallado, volvemos a ocultar las dos imagenes con un pequeño delay
                //para que de tiempo a ver lo que hay:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        casillas[posicion_primer_toque].setImageResource(R.drawable.img_ocultar);
                        casillas[posicion_segundo_toque].setImageResource(R.drawable.img_ocultar);
                        //Ya puede toca sobre otra imagen:
                        puede_tocar = true;
                    }
                }, 700);
            }
            //Finalmente ponemos el contador de toques a 0 para volver a distinguir entre el primero o segundo:
            contador_toques = 0;
        }
    }
}