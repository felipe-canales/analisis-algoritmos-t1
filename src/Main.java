import algoritmo.Algoritmo;
import algoritmo.RAM;
import io.Reader;
import palabras.Creador;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        /*      Testing word creation
        Creador creador = new Creador();
        File pal1 = creador.palabraPrueba();
        */

        /*      Testing RAM */
        File pal1 = new File("p_t1.txt");
        File pal2 = new File("p_t2.txt");

        RAM a = new RAM();
        System.out.println(a.imprimirMatriz(pal1, pal2));
         /**/

        /*      Testing Reader
        try {
            Reader rw = new Reader(new File("p_t2.txt"), 2);
            byte[] b01 = rw.read(0);
            byte[] b45 = rw.read(2);
            System.out.print((char)b01[0]);
            System.out.println((char)b01[1]);
            System.out.print((char)b45[0]);
            System.out.println((char)b45[1]);
            rw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/


    }

}
