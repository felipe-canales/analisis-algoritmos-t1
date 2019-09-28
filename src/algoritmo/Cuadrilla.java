package algoritmo;

import java.io.File;

public class Cuadrilla extends AbstractAlgoritmoEnDisco {

    private int bloquesVert;
    private int bloquesHor;
    private byte[] substr1;
    private byte[] substr2;
    private short[] bordeVert;
    private short[] bordeHor;

    public Cuadrilla(int bloques, int largoBloque) {
        super(largoBloque);
        this.bloquesHor = bloques / 8;
        this.bloquesVert = this.bloquesHor;
        if (bloques % 8 >= 5) {
            this.bloquesHor++;
        } else if (bloques % 8 >= 3) {
            this.bloquesVert++;
        }
        bordeVert = new short[bloquesVert * getLargoBloque() + 1];
        bordeHor = new short[bloquesHor * getLargoBloque()];
    }

    @Override
    public int resolver(File palabra1, int largo1, File palabra2, int largo2) {
        setReaders(palabra1, palabra2);
        RAM r = new RAM();
        int tablas_w = ((largo2 - 1)/ (getLargoBloque() * bloquesHor)) + 1;
        int tablas_h = ((largo1 - 1)/ (getLargoBloque() * bloquesVert)) + 1;
        // 1era fila de tablas
        setBordeVert(0);
        cargarSubstr1(0);
        int l1 = (largo1 < substr1.length? largo1 : substr1.length);
        int l2;
        for (int i = 0; i < tablas_w; i++) {
            setBordeHor(i);
            cargarSubstr2(i);
            l2 = (largo2 < (i + 1) * substr2.length? largo2 % substr2.length : substr2.length);
            bordeHor = r.resolverSubtabla(bordeHor, bordeVert, substr1, l1, substr2, l2);
            guardarBordeInferior(i);
        }
        for (int j = 1; j < tablas_h; j++) {
            setBordeVert(j);
            cargarSubstr1(j);
            l1 = (largo1 < (j + 1) * substr1.length? largo1 % substr1.length : substr1.length);
            for (int i = 0; i < tablas_w; i++) {
                cargarBordeSuperior(i);
                cargarSubstr2(i);
                l2 = (largo2 < (i + 1) * substr2.length? largo2 % substr2.length : substr2.length);
                bordeHor = r.resolverSubtabla(bordeHor, bordeVert, substr1, l1, substr2, l2);
                guardarBordeInferior(i);
            }
        }
        return bordeHor[(largo2 - 1) % (bloquesHor * getLargoBloque())];
    }

    private void guardarBordeInferior(int tabla) {
        int tablaStart = tabla * bloquesHor;
        for (int b = 0; b < bloquesHor; b++) {
            int offset = b * getLargoBloque();
            // Se podría liberar memoria si con estos dos bloques se supera el límite.
            // Alternativamente se pueden reservar dos bloques para esto
            // (restar 2 a los bloques disponibles)
            byte[] supers = new byte[getLargoBloque()];
            byte[] infers = new byte[getLargoBloque()];
            for (int i = 0; i < getLargoBloque(); i++) {
                if (i + offset >= bordeHor.length) {
                    break;
                }
                supers[i] = (byte)((bordeHor[i + offset] & 65280) >> 8);
                infers[i] = (byte)(bordeHor[i + offset] & 255);
            }
            try {
                getRwMat().write(2 * (tablaStart + b), supers);
                getRwMat().write(2 * (tablaStart + b) + 1, infers);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(2);
            }
        }
    }

    private void cargarBordeSuperior(int tabla) {
        int tablaStart = tabla * bloquesHor;
        bordeHor = new short[bloquesHor * getLargoBloque()];
        for (int b = 0; b < bloquesHor; b++) {
            int offset = b * getLargoBloque();
            byte[] supers = {0};
            byte[] infers = {0};
            try {
                supers = getRwMat().read(2 * (tablaStart + b));
                infers = getRwMat().read(2 * (tablaStart + b) + 1);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(3);
            }
            for (int i = 0; i < getLargoBloque(); i++) {
                int inf = infers[i] < 0? infers[i] + 256 : infers[i];
                int sup = supers[i] < 0? supers[i] + 256 : supers[i];
                int val = (sup << 8) + inf;
                bordeHor[i + offset] = (short)val;
            }
        }
    }

    private void setBordeVert(int tabla_y) {
        int inicio = bloquesVert * getLargoBloque() * tabla_y;
        for (int i = 0; i < bordeVert.length; i++) {
            bordeVert[i] = (short)(inicio + i);
        }
    }

    private void setBordeHor(int tabla_x) {
        int inicio = bloquesHor * getLargoBloque() * tabla_x + 1;
        for (int i = 0; i < bordeHor.length; i++) {
            bordeHor[i] = (short)(inicio + i);
        }
    }

    private void cargarSubstr1(int tabla_y) {
        try {
            substr1 = getReader1().read(tabla_y * bloquesVert, bloquesVert);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(3);
        }
    }

    private void cargarSubstr2(int tabla_x) {
        try {
            substr2 = getReader2().read(tabla_x * bloquesHor, bloquesHor);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(3);
        }
    }
}
