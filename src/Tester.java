import algoritmo.Adaptado;
import algoritmo.Algoritmo;
import algoritmo.Cuadrilla;
import palabras.Creador;

import java.io.File;

public class Tester {

    public static void main(String[] args) {
        Creador c = new Creador();

        int[] bns = {20, 40, 80};
        int bsize = 1024;
        long time;
        for ( int bn : bns ) {
            long[] timesAdp = new long[20];
            long[] timesStb = new long[20];
            Algoritmo adp = new Adaptado(bn, bsize);
            Algoritmo stb = new Cuadrilla(bn, bsize);
            for (int i = 0; i < 20; i++) {
                File pal1 = c.palabraLarga();
                //File pal1 = c.palabraCorta();
                int l1 = c.getLargo();
                File pal2 = c.palabraLarga();
                //File pal2 = c.palabraCorta();
                int l2 = c.getLargo();

                time = System.currentTimeMillis();
                adp.resolver(pal1, l1, pal2, l2);
                timesAdp[i] = System.currentTimeMillis() - time;
                time = System.currentTimeMillis();
                stb.resolver(pal1, l1, pal2, l2);
                timesStb[i] = System.currentTimeMillis() - time;
            }
            System.out.println(String.format("Results for %d blocks\n", bn));
            for (long t : timesAdp ) {
                System.out.print(String.format("%d ", t));
            }
            System.out.print("\n");
            for (long t : timesStb ) {
                System.out.print(String.format("%d ", t));
            }
            System.out.print("\n");
        }
    }

}
