import io.MatrixReaderWriter;

public class Main {

    public static void main(String[] args) {
        /*      Testing word creation
        Creador creador = new Creador();
        File pal1 = creador.palabraPrueba();
        */

        /*      Testing RAM
        File pal1 = new File("p_t1.txt");
        File pal2 = new File("p_t2.txt");

        RAM a = new RAM();
        System.out.println(a.imprimirMatriz(pal1, pal2));
         */

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

        /*      Testing ReaderWriter*/
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
        }


    }

}
