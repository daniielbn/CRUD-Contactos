package contacto;

import java.util.Scanner;

/**
 * @author Daniel Brito Negrín
 * @see Contacto
 * @version 1.0 02-04-2024
 */
public class Menu {
    private int opcion;
    private String texto;

    public Menu() {
        this.opcion = -1;
        this.texto = "";
    }

    public Menu(String texto) {
        this.opcion = -1;
        this.texto = texto;
    }

    public int getOpcion() {
        return this.opcion;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    private void imprimirMenu() {
        System.out.print(getTexto());
    }

    private void pedirOpcion() {
        Scanner in = new Scanner(System.in);
        setOpcion(in.nextInt());
    }

    public void inicio() {
        CRUD crud = new CRUD();
        do {
            imprimirMenu();
            try {
                pedirOpcion();
            } catch (Exception e) {
                System.out.println("Ha ocurrido un error.");
            }
            while(getOpcion() < 0 || getOpcion() > 6) {
                System.out.println("Debe introducir una opción correcta. Por favor, vuelva a intentarlo.");
                try {
                    pedirOpcion();
                } catch (Exception e) {
                    System.out.println("Ha ocurrido un error.");
                }
            }
            crud.iniciar(getOpcion());
        } while (getOpcion() != 0);
        
    }
}

