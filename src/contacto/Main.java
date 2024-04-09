package contacto;

/**
 * @author Daniel Brito Negrín
 * @see Contacto
 * @see CRUD
 * @see Menu
 * @version 1.0 09-04-2024
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir") + "\\Contactos\\" + "contactos.txt");
        Menu menu = new Menu("""
                =============================================================
                Opción 1: Crear contacto.
                Opción 2: Ver contacto.
                Opcion 3: Actualizar contacto.
                Opción 4: Eliminar contacto.
                Opción 5: Consultar total de contactos.
                Opción 6: Ver contactos eliminados.
                Opción 0: Salir.
                =============================================================
                """);

        menu.inicio();
    }
    
}
