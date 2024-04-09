package contacto;

// <editor-fold defaultstate="collapsed" desc="Librerías">
import java.util.Scanner;
// </editor-fold>

/**
 * @author Daniel Brito Negrín
 * @see Contacto
 * @see CRUD
 * @see Main
 * @version 1.0 09-04-2024
 */
public class Menu {
    // <editor-fold defaultstate="collapsed" desc="Atributos">
    private int opcion;
    private String texto;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor vacío de la clase Menu.
     */
    public Menu() {
        this.opcion = -1;
        this.texto = "";
    }

    /**
     * Constructor parametrizado de la clase Menu.
     * @param texto Texto del menú para imprimir.
     */
    public Menu(String texto) {
        this.opcion = -1;
        this.texto = texto;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters">
    /**
     * Método getter para obtener la opción del menú.
     * @return Devuelve la opción.
     */
    public int getOpcion() {
        return this.opcion;
    }

    /**
     * Método getter para obtener el texto del menú.
     * @return Devuelve el texto.
     */
    public String getTexto() {
        return this.texto;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setters">
    /**
     * Método setter para establecer la opción del menú.
     * @param opcion Opción a establecer.
     */
    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    /**
     * Método setter para establecer el texto del menú.
     * @param texto Texto a establecer.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos privados">
    /**
     * Método privado para imprimir texto el menú.
     */
    private void imprimirMenu() {
        System.out.print(getTexto());
    }

    /**
     * Método privado para pedir por teclado al usuario la opción.
     */
    private void pedirOpcion() {
        Scanner in = new Scanner(System.in);
        setOpcion(in.nextInt());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos públicos">
    /**
     * Método público para iniciar el menú.
     */
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
    // </editor-fold>
}

