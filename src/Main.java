import algoritmo.Adaptado;
import algoritmo.Algoritmo;
import algoritmo.Cuadrilla;
import algoritmo.RAM;
import io.MatrixReaderWriter;
import palabras.Creador;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        /*      Testing word creation
        Creador creador = new Creador();
        File created = creador.palabraCorta();
        /**/

        /*      Testing RAM*/
        File pal1 = new File("p_4262.txt");
        File pal2 = new File("p_2721.txt");
        int l1 = 4262;
        int l2 = 2721;
        int b_n = 20;
        int b_s = 1024;

        RAM a = new RAM();
        System.out.println(a.resolver(pal1, l1, pal2, l2));
         /**/

        /*      Testing Adaptado*/
        Algoritmo adp = new Adaptado(b_n, b_s);
        System.out.println(adp.resolver(pal1, l1, pal2, l2));

        /**/

        /*      Testing Cuadrilla*/
        Algoritmo cuad = new Cuadrilla(b_n, b_s);
        System.out.println(cuad.resolver(pal1, l1, pal2, l2));

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
        }/**/

        /*      Testing ReaderWriter
        try {
            String s = "%c %c %c %c";
            byte[] x = {'A', 'B', 'C', 'D'};
            byte[] z = {'X', 'Y', 'Z', 'W'};
            MatrixReaderWriter rw = new MatrixReaderWriter(4);
            rw.write(0, x);
            rw.write(1, z);
            byte[] b1 = rw.read(1);
            // Deberia ser z
            System.out.println(String.format(s, b1[0], b1[1], b1[2], b1[3]));
            byte[] b0 = rw.read(0);
            // Deberia ser x
            System.out.println(String.format(s, b0[0], b0[1], b0[2], b0[3]));
            rw.write(0,z);
            byte[] bc = rw.read(0);
            // Deberia ser z
            System.out.println(String.format(s, bc[0], bc[1], bc[2], bc[3]));
            rw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }/**/


    }

}
