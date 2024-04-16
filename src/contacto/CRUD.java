package contacto;

// <editor-fold defaultstate="collapsed" desc="Librerías">
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Clases importadas">
import contacto.Contacto.Correo;
import contacto.Contacto.Telefono;
import contacto.Contacto.tipoCorreo;
import contacto.Contacto.tipoTelefono;
// </editor-fold>

/**
 * @author Daniel Brito Negrín
 * @see Contacto
 * @see Menu
 * @see Main
 * @version 1.0 09-04-2024
 */
public class CRUD { 
    // <editor-fold defaultstate="collapsed" desc="Atributos"> 
    private ArrayList<Contacto> listaContactos = new ArrayList<>();
    private ArrayList<Contacto> listaContactosEliminados = new ArrayList<>();
    private String nombreArchivo;
    private int contador;
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="Constructores">
    public CRUD() {
        
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters">
    /**
     * Método getter para obtener lista de contactos.
     * @return Devuelve la lista de contactos.
     */
    public ArrayList<Contacto> getListaContactos() {
        return this.listaContactos;
    }

    /**
     * Método getter para obtener lista de contactos eliminados.
     * @return Devuelve la lista de contactos eliminados.
     */
    public ArrayList<Contacto> getListaContactosEliminados() {
        return this.listaContactosEliminados;
    }

    /**
     * Método getter para obtener el nombre del fichero de texto que se está manejando.
     * @return Devuelve el nombre del fichero de texto.
     */
    public String getNombreArchivo() {
        return this.nombreArchivo;
    }
    
    /**
     * Método getter para obtener el contador de veces que se ha utilizado el CRUD desde que se ha iniciado el programa.
     * @return Devuelve el número de veces.
     */
    public int getContador() {
        return this.contador;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setters">
    /**
     * Método setter para establecer una lista de contactos.
     * @param listaContactos Lista de contactos a establecer.
     */
    public void setListaContactos(ArrayList<Contacto> listaContactos) {
        this.listaContactos = listaContactos;
    }

    /**
     * Método setter oara estabelcer una lista de contactos eliminados.
     * @param listaContactosEliminados Lista de contactos eliminados a establecer.
     */
    public void setListaContactosEliminados(ArrayList<Contacto> listaContactosEliminados) {
        this.listaContactosEliminados = listaContactosEliminados;
    }

    /**
     * Método setter para establecer el nombre del fichero de texto que se maneja.
     * @param nombreArchivo Nombre del fichero de texto a establecer.
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * Método setter para establecer el contador del programa.
     * @param contador Número de contador a establecer.
     */
    public void setContador(int contador) {
        this.contador = contador;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos privados">
    /**
     * Método privado que sirve para leer un fichero de texto especifico (lee el fichero que esté como atributo del objeto), y ordenar la información de dentro de este
     * para establecer la lista de contactos, o lista de contactos eliminados. Hay 7 datos para establecer, "Nombre", "Apellidos", "Notas", "Fecha de nacimiento", "Telefonos" y "Correos".
     * Cada línea del fichero corresponde a un contacto. Toda esa línea de texto se almacena en un array de String, utilizando el tokenizer "||". Se diferencia si existe uno o varios números de teléfono,
     * con su respectivo tipo, al igual que los correos, con su respectivo tipo. Por último, según el valor de la lista, se establecerá en la lista de contactos o la lista
     * de contactos eliminados.
     * @param lista Este número establece que lista de contactos se va a modificar. Si es igual a 1, se modifica la lista de contactos y si no, se modifica la lista de contactos
     * eliminados.
     */
    private void leerFichero(int lista) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\Contactos\\" + getNombreArchivo()));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\\|\\|");

                int id = Integer.parseInt(datos[0]);
                String nombre = datos[1];
                String apellidos = datos[2];
                String notas = datos[3];
                String nacimiento = datos[4];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fechaNacimiento = LocalDate.parse(nacimiento, formatter);
                ArrayList<Telefono> telefonos = new ArrayList<>();
                ArrayList<Correo> correos = new ArrayList<>();
                if (datos[5].contains(",")) {
                    String[] telefonoTipoTelefono = tokenizerComa(datos[5]);
                    for (int i = 0; i < telefonoTipoTelefono.length; i++) {
                        String[] telefonoSeparado = tokenizerGuion(telefonoTipoTelefono[i]);
                        Telefono telefono = new Telefono(telefonoSeparado[0].trim(), tipoTelefono.valueOf(telefonoSeparado[1].toUpperCase().trim()));
                        telefonos.add(telefono);
                    }
                } else {
                    String[] telefonoSeparados = tokenizerGuion(datos[5]);
                    Telefono telefono = new Telefono(telefonoSeparados[0], tipoTelefono.valueOf(telefonoSeparados[1]));
                    telefonos.add(telefono);
                }
                if (datos[6].contains(",")) {
                    String[] correoTipoCorreo = tokenizerComa(datos[6]);
                    for (int i = 0; i < correoTipoCorreo.length; i++) {
                        String[] correoSeparado = tokenizerGuion(correoTipoCorreo[i]);
                        Correo correo = new Correo(correoSeparado[0].trim(), tipoCorreo.valueOf(correoSeparado[1].trim().toUpperCase()));
                        correos.add(correo);
                    }
                } else {
                    String[] correoSeparado = tokenizerGuion(datos[6]);
                    Correo correo = new Correo(correoSeparado[0], tipoCorreo.valueOf(correoSeparado[1]));
                    correos.add(correo);
                }
                if (lista == 1) {
                    ArrayList<Contacto> contactos = getListaContactos();
                    setListaContactos(rellenarContacto(id, nombre, apellidos, notas, fechaNacimiento, telefonos, correos, contactos));
                    setNombreArchivo("contactos.txt");
                } else {
                    ArrayList<Contacto> contactosEliminados = getListaContactosEliminados();
                    setListaContactosEliminados(rellenarContacto(id, nombre, apellidos, notas, fechaNacimiento, telefonos, correos, contactosEliminados));
                    setNombreArchivo("contactosEliminados.txt");
                }
            }
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al abrir el archivo, porfavor revise la ubicación del mismo.");
        }
    }

    /**
     * Método privado que escribe un fichero de texto con la información almacenada en la lista de contactos, ya sea la lista de contactos normal o la lista de contactos
     * eliminadas. Primeramente, comprueba si el fichero de texto (el valor que tenga el atributo "nombreArchivo" del objeto) existe. Si no es así, lo crea. Si es así, lo escribe
     * nuevamente. Luego, según el parámetro, elije la lista de contactos a escribir. Por último, va recuperando los datos del contacto y los va escribiendo mediante el
     * "BufferedWriter". Se recorre todo el ArrayList hasta que no haya más elementos.
     * @param lista Número que corresponde a la lista de contactos que se desea escribir. Si es igual a 1, se escribirá la lista de contactos normales. Si no, se escribirá
     * la lista de contactos eliminados.
     */
    private void escribirFichero(int lista) {
        try {
            File file = new File(System.getProperty("user.dir") + "\\Contactos\\" + getNombreArchivo());
            if (!file.exists()) {
                file.createNewFile();
            }
            ArrayList<Contacto> contactos = new ArrayList<>();
            if (lista == 1) {
                contactos = getListaContactos();
            } else {
                contactos = getListaContactosEliminados();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\Contactos\\" + getNombreArchivo()));
            for (Contacto contacto : contactos) {
                contacto.getTelefonos().trimToSize();
                contacto.getCorreos().trimToSize();
                bw.write(contacto.getId() + "||" + contacto.getNombre() + "||" + contacto.getApellidos() + "||" + contacto.getNotas() + "||" + contacto.getFechaNacimiento().toString() + "||" );
                int i = 1;
                for (Telefono telefono : contacto.getTelefonos()) {
                    if(i == contacto.getTelefonos().size()) {
                        bw.write(telefono.getNumero() + "-" + telefono.getTipo());
                    } else {
                        bw.write(telefono.getNumero() + "-" + telefono.getTipo() + ", ");
                    }
                    i++;
                }
                bw.write("||");
                i = 1;
                for (Correo correo : contacto.getCorreos()) {
                    if (i == contacto.getCorreos().size()) {
                        bw.write(correo.getDireccion() + "-" + correo.getTipo());
                    } else {
                        bw.write(correo.getDireccion() + "-" + correo.getTipo() + ", ");
                    }
                    i++;
                }
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un problema al escribir el archivo.");
        }
    }

    /**
     * Método privado para añadir un contacto a la lista de contactos. Va pidiendo al usuario todos los datos, y comprobando si son válidos. También da la opción de 
     * añadir más de un número de teléfono, así como de correos electrónicos. Este método llama al método de escribirFichero(), con la opción solo de la lista de contactos,
     * ya que es la única que se modifica.
     */
    private void añadirContacto() {
        ArrayList<Contacto> contactos = getListaContactos();
        ArrayList<Telefono> telefonos = new ArrayList<>();
        ArrayList<Correo> correos = new ArrayList<>();
        System.out.println("Por favor, ingrese el nombre del contacto: ");
        String nombre = pedirString();
        System.out.println("Por favor, ingrese los apellidos del contacto: ");
        String apellidos = pedirString();
        System.out.println("Escriba una nota sobre el contacto (si no desea escribir ninguna nota, pulse 'Enter'): ");
        String notas = pedirString();
        System.out.println("Por favor, ingrese la fecha de nacimiento: ");
        System.out.println("Día (1-31): ");
        int dia = pedirInt();
        while(!comprobarDia(dia)) {
            System.out.println("El día introducido no es un día válido. Por favor, vuelva a introducir un día: ");
            dia = pedirInt();
        }
        System.out.println("Mes (1-12): ");
        int mes = pedirInt();
        while (!comprobarMes(mes)) {
            System.out.println("El mes introducido no es un mes válido. Por favor, vuelva a introducir un mes: ");
            mes = pedirInt();
        }
        System.out.println("Año (1900-2024): ");
        int anyo = pedirInt();
        while (!comprobarAnyo(anyo)) {
            System.out.println("El año introducido no es un año válido. Por favor, vuelva a introducir un año: ");
            anyo = pedirInt();
        }
        LocalDate fechaNacimiento = darFormatoFecha(dia, mes, anyo);
        System.out.println("Eligue la cantidad de números de teléfonos que vas a asociar a este contacto: ");
        int cantidad = pedirInt();
        while (cantidad > 0) {
            System.out.println("Introduzca el tipo del teléfono que introduce (MOVIL, FIJO, TRABAJO): ");
            String tipo = pedirString().toUpperCase();
            while (!comprobaTipoTelefono(tipo)) {
                System.out.println("Este tipo de teléfono no es válido. Por favor, ingrese un tipo de teléfono correcto: ");
                tipo = pedirString().toUpperCase();
            }
            System.out.println("Introduzca el número de telefono: ");
            String numeroTelefono = pedirString();
            while(!comprobarTelefono(numeroTelefono)) {
                System.out.println("[" + numeroTelefono + "] no es un correo válido. Por favor, introduce un número de teléfono válido: ");
                numeroTelefono = pedirString();
            }
            Telefono telefono = new Telefono(numeroTelefono, tipoTelefono.valueOf(tipo));
            telefonos.add(telefono);
            cantidad--;
        }
        System.out.println("Eligue la cantidad de correos electrónicos que vas a asociar a este contacto: ");
        cantidad = pedirInt();
        while (cantidad > 0) {
            System.out.println("Introduzca el tipo de correo electrónico que introduce (PERSONAL, TRABAJO, EMPRESA): ");
            String tipo = pedirString().toUpperCase();
            while (!comprobaTipoCorreo(tipo)) {
                System.out.println("Este tipo de correo no es válido. Por favor, ingrese un tipo de correo correcto: ");
                tipo = pedirString().toUpperCase();
            }
            System.out.println("Introduzca la dirección de correo electrónico: ");
            String direccionCorreo = pedirString();
            while (!comprobarCorreo(direccionCorreo)) {
                System.out.println("[" + direccionCorreo + "] no es un correo válido. Por favor, introduce una dirección de correo válida: ");
                direccionCorreo = pedirString();
            }
            Correo correo = new Correo(direccionCorreo, tipoCorreo.valueOf(tipo));
            correos.add(correo);
            cantidad--;
        }
        Contacto contacto = new Contacto(nombre, apellidos, notas, fechaNacimiento, telefonos, correos);
        contactos.add(contacto);
        setListaContactos(contactos);
        setNombreArchivo("contactos.txt");
        escribirFichero(1);
    }

    /**
     * Método privado que sirve para ver la lista de contactos, además de ofrecer la opción de filtrar por campos.
     */
    private void verContacto() {
        ArrayList<Contacto> contactos = getListaContactos();
        contactos.trimToSize();
        for (Contacto contacto : contactos) {
            imprimirContacto(contacto);
        }
        verFiltrado();
    }

    /**
     * Método privado para actualizar la información de un contacto. Primeramente, se muestran todos los contactos, para posteriormente decidir cuál es el que se quiere
     * modificar. Una vez terminada la modificación, se vuelve a escribir la nueva información llamando al método escribirFichero().
     */
    private void actualizarContacto() {
        ArrayList<Contacto> contactos = getListaContactos();
        for (Contacto contacto : contactos) {
            System.out.println(contacto.getId() + " || " + contacto.getNombre() + " " + contacto.getApellidos());
        }
        System.out.println("¿Qué contacto desea actualizar? (Introduzca el número que aparece al principio de cada contacto)");
        int actualizarContacto = pedirInt() - 1;
        while(actualizarContacto < 0 || actualizarContacto > contactos.size()) {
            System.out.println("Número de contacto introducido es incorrecto. Por favor, vuelva a introducirlo: ");
            actualizarContacto = pedirInt() - 1;
        }
        Contacto contactoActualizar = contactos.get(actualizarContacto);
        System.out.println("Elige el campo que quieras actualizar del contacto (Nombre, Apellidos, Notas, Fecha de nacimiento, Telefonos, Correos): ");
        String opcionActualizar = pedirString();
        switch (opcionActualizar.toLowerCase()) {
            case "nombre":
                System.out.println("Introduzca el nuevo nombre a actualizar: ");
                String nuevoNombre = pedirString();
                contactoActualizar.setNombre(nuevoNombre);
                break;
            case "apellidos":
                System.out.println("Introduzca los nuevos apellidos a actualizar: ");
                String nuevosApellidos = pedirString();
                contactoActualizar.setApellidos(nuevosApellidos);
            case "notas":
                System.out.println("Introduzca la nueva nota a actualizar: ");
                String nuevaNota = pedirString();
                contactoActualizar.setNotas(nuevaNota);
                break;
            case "fecha de nacimiento":
                System.out.println("Introduzca el día: ");
                int nuevoDia = pedirInt();
                System.out.println("Introduzca el mes: ");
                int nuevoMes = pedirInt();
                System.out.println("Introduzca el año: ");
                int nuevoAnyo = pedirInt();
                LocalDate fechaNacimiento = darFormatoFecha(nuevoDia, nuevoMes, nuevoAnyo);
                contactoActualizar.setFechaNacimiento(fechaNacimiento);
                break;
            case "telefonos":
                ArrayList<Telefono> nuevosTelefonos = new ArrayList<>();
                int opcionTelefono;
                do {
                    System.out.println("Introduzca el tipo de teléfono (MOVIL, FIJO, TRABAJO): ");
                    String nuevoTipo = pedirString();
                    System.out.println("Introduzca el nuevo número de teléfono: ");
                    String nuevoNumero = pedirString();
                    Telefono nuevoTelefono = new Telefono(nuevoNumero, tipoTelefono.valueOf(nuevoTipo));
                    nuevosTelefonos.add(nuevoTelefono);
                    System.out.println("¿Desea introducir otro número de teléfono? (1: Si, 0: No)");
                    opcionTelefono = pedirInt();
                } while (!(opcionTelefono == 0) || opcionTelefono == 1);
                contactoActualizar.setTelefonos(nuevosTelefonos);
            case "correos":
                ArrayList<Correo> nuevosCorreos = new ArrayList<>();
                int opcionCorreo;
                do {
                    System.out.println("Introduzca el tipo de correo (TRABAJO, PERSONAL, EMPRESA): ");
                    String nuevoTipo = pedirString();
                    System.out.println("Introduzca la nueva dirección de correo electrónico:");
                    String nuevaDireccion = pedirString();
                    Correo correo = new Correo(nuevaDireccion, tipoCorreo.valueOf(nuevoTipo));
                    nuevosCorreos.add(correo);
                    System.out.println("¿Desea introducir otro correo electrónico? (1: Si, 0: No)");
                    opcionCorreo = pedirInt();
                } while (!(opcionCorreo == 0) || opcionCorreo == 1);
                contactoActualizar.setCorreos(nuevosCorreos);
                break;
            default:
                System.out.println("Ha elegido una opción incorrecta.");
                break;
        }
        contactos.remove(actualizarContacto);
        contactos.add(actualizarContacto, contactoActualizar);
        setListaContactos(contactos);
        setNombreArchivo("contactos.txt");
        escribirFichero(1);
    }

    /**
     * Método privado que permite eliminar algún contacto. Primeramente, muestra los contactos existentes, para luego pedir al usuario cuál es el contacto que se desea
     * eliminar. Una vez se haya decidio el contacto, se llama al método escribirFichero() para modificar el fichero de texto de "contactosEliminados.txt", que permite 
     * almacenar la información de todos los contactos eliminados por seguridad. Por último, se modifica la lista de contactos normales, eliminando el contacto que el 
     * usuario decidió.
     */
    private void eliminarContacto() {
        ArrayList<Contacto> contactos = getListaContactos();
        ArrayList<Contacto> contactosEliminados = getListaContactosEliminados();
        for (Contacto contacto : contactos) {
            System.out.println(contacto.getId() + " || " + contacto.getNombre() + " " + contacto.getApellidos());
        }
        System.out.println("¿Qué contacto desea eliminar? (Introduzca el número que aparece al principio de cada contacto)");
        int eliminarContacto = pedirInt() - 1;
        while(eliminarContacto < 0 || eliminarContacto > contactos.size()) {
            System.out.println("Número de contacto introducido es incorrecto. Por favor, vuelva a introducirlo: ");
            eliminarContacto = pedirInt() - 1;
        }
        Contacto eliminado = contactos.get(eliminarContacto);
        contactosEliminados.add(eliminado);
        contactos.remove(eliminarContacto);
        setListaContactosEliminados(contactosEliminados);
        setNombreArchivo("contactosEliminados.txt");
        for (int i = eliminarContacto; i < contactos.size(); i++) {
            Contacto contacto = contactos.get(i);
            contacto.setId(i + 1);
        }
        escribirFichero(0);
        setNombreArchivo("contactos.txt");
        escribirFichero(1);
    }

    /**
     * Método privado que sirve para consultar el número total de contactos almacenados.
     */
    private void consultarTotalContactos() {
        ArrayList<Contacto> contactos = getListaContactos();
        contactos.trimToSize();
        System.out.println("La cantidad de contactos almacenada es de " + contactos.size() + " contactos");
    }

    /**
     * Método privado que permite ver la lista de los contactos eliminados, almacenados en el fichero de texto "contactosEliminados.txt"
     */
    private void verContactosEliminados() {
        ArrayList<Contacto> contactosEliminados = getListaContactosEliminados();
        System.out.println("Aquí tiene la lista de los contactos: ");
        for (Contacto contacto : contactosEliminados) {
            System.out.print(contacto.getId() + " || " + contacto.getNombre() + " " + contacto.getApellidos() + " || " + contacto.getNotas() + " || " + contacto.getFechaNacimiento().toString() + " || ");
            for (Telefono telefono : contacto.getTelefonos()) {
                System.out.print(telefono.getNumero() + " - " + telefono.getTipo() + ", ");
            }
            System.out.print(" || ");
            for (Correo correo : contacto.getCorreos()) {
                System.out.print(correo.getDireccion() + " - " + correo.getTipo() + ", ");
            }
            System.out.println("");
        }
    }

    /**
     * Método privado para pedir por teclado al usuario una cadena de texto
     * @return Cadena de texto introducida por el usuario.
     */
    private String pedirString() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    /**
     * Método privado para pedir por teclado al usuario un número entero.
     * @return Número entero introducido por el usuario.
     */
    private int pedirInt() {
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }

    /**
     * Método privado que divida una cadena de texto por el token "-".
     * @param dato Cadena de texto a dividir.
     * @return Array de String con las palabras de la cadena ya separadas por el token.
     */
    private String[] tokenizerGuion(String dato) {
        return dato.split("-");
    }

    /**
     * Método privado que divide una cadena de texto por el token ","
     * @param dato Cadena de texto a dividir.
     * @return Array de String con las palabras de la cadena ya separadas por el token.
     */
    private String[] tokenizerComa(String dato) {
        return dato.split(",");
    }

    /**
     * Método privado que sirve para añadir un contacto nuevo a la lista de contactos existente.
     * @param id Número de identificación del contacto.
     * @param nombre Nombre del contacto.
     * @param apellidos Apellidos del contacto.
     * @param notas Notas del contacto.
     * @param fechaNacimiento Fecha de nacimiento del contacto.
     * @param telefonos ArrayList de los teléfonos del contacto.
     * @param correos ArrayList de los correos electrónicos del contacto.
     * @param contactos Lista de contactos a la que se va a añadir el contacto.
     * @return Devuelve la lista de contactos con el nuevo contacto ya añadido.
     */
    private ArrayList<Contacto> rellenarContacto(int id, String nombre, String apellidos, String notas, LocalDate fechaNacimiento, ArrayList<Telefono> telefonos, ArrayList<Correo> correos, ArrayList<Contacto> contactos) {
        Contacto contacto = new Contacto(id, nombre, apellidos, notas, fechaNacimiento, telefonos, correos);
        contactos.add(contacto);
        return contactos;
    }

    /**
     * Método privado que sirve para comprobar si un día es válido
     * @param dia Día a comprobar.
     * @return Verdadero si el día es válido, falso si no lo es.
     */
    private boolean comprobarDia(int dia) {
        if (dia < 0 || dia > 31) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Método privado que sirve para comprobar si un mes es válido.
     * @param mes Mes a comprobar.
     * @return Verdadero si el mes es válido, falso si no lo es.
     */
    private boolean comprobarMes(int mes) {
        if (mes < 0 || mes > 12) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Método privado que sirve para comprobar si un año es válido.
     * @param anyo Año a comprobar.
     * @return Verdadero si es un año válido, falso si no lo es.
     */
    private boolean comprobarAnyo(int anyo) {
        if (anyo < 1900 || anyo > 2024) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Método privado que utiliza una expresión regular para comprobar si la cadena de texto es un número de teléfono.
     * @param numero Número de teléfono a comprobar.
     * @return Verdadero si es un número válido, falso si no lo es.
     */
    private boolean comprobarTelefono(String numero) {
        if (!numero.matches("\\d{9}")) {
            return false;
        }
        return true;
    }
    
    /**
     * Método privado que utiliza una expresión regular para comprobar si la dirección de correo electrónico es válida.
     * @param direccion Dirección de correo electrónico a comprobar.
     * @return Verdadero si es una dirección válida, falso si no lo es.
     */
    private boolean comprobarCorreo(String direccion) {
        if (!direccion.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            return false;
        }
        return true;
    }

    /**
     * Método privado que comprueba si el tipo del teléfono es válido.
     * @param tipoTelefono Tipo de teléfono a comprobar.
     * @return Verdadero si es un tipo válido, falso si no lo es.
     */
    private boolean comprobaTipoTelefono(String tipoTelefono) {
        if(tipoTelefono.equals("MOVIL") || tipoTelefono.equals("FIJO") || tipoTelefono.equals("TRABAJO")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método privado que comprueba si el tipo de correo electrónico es válido.
     * @param tipoCorreo Tipo de correo a comoprobar.
     * @return Verdadero si es un tipo válido, falso si no lo es.
     */
    private boolean comprobaTipoCorreo(String tipoCorreo) {
        if(tipoCorreo.equals("PERSONAL") || tipoCorreo.equals("EMPRESA") || tipoCorreo.equals("TRABAJO")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método privado que obtiene una fecha de nacimiento usando "LocalDate", a través de tres números enteros (día, mes y año).
     * @param dia Día de la fecha.
     * @param mes Mes de la fecha.
     * @param anyo Año de la fecha.
     * @return Devuelve un objeto de la clase LocalDate, con el formato que le hayamos dado.
     */
    private LocalDate darFormatoFecha(int dia, int mes, int anyo) {
        String diaFormateado = String.format("%02d", dia);
        String mesFormateado = String.format("%02d", mes);
        String fecha = anyo + "-" + mesFormateado + "-" + diaFormateado;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimiento = LocalDate.parse(fecha, formato);
        return fechaNacimiento;
    }

    /**
     * Método privado que sirve para imprimir la información del contacto que se le pase por parámetros.
     * @param contacto Contacto que se quiera imprimir su información.
     */
    private void imprimirContacto(Contacto contacto) {
        System.out.print(contacto.getId() + " || " + contacto.getNombre() + " " + contacto.getApellidos() + " || " + contacto.getNotas() + " || " + contacto.getFechaNacimiento().toString() + " || ");
        int i = 1;
        for (Telefono telefono : contacto.getTelefonos()) {
            if(i == contacto.getTelefonos().size()) {
                System.out.print(telefono.getNumero() + "-" + telefono.getTipo());
            } else {
                System.out.print(telefono.getNumero() + "-" + telefono.getTipo() + ", ");
            }
            i++;
        }
        System.out.print(" ");
        i = 1;
        for (Correo correo : contacto.getCorreos()) {
            if (i == contacto.getCorreos().size()) {
                System.out.print(correo.getDireccion() + "-" + correo.getTipo());
            } else {
                System.out.print(correo.getDireccion() + "-" + correo.getTipo() + ", ");
            }
            i++;
        }
        System.out.println("");
    }

    /**
     * Método privado que tiene la capacidad de permitir un filtrado por campos de la información del contacto, mediante lo que el usuario indique.
     */
    private void verFiltrado() {
        ArrayList<Contacto> contactos = getListaContactos();
        String filtrado;
        do {
            System.out.println("Por favor, elija el campo por el que quiere filtrar [Nombre, Apellidos, Notas, Fecha, Telefono, Correo , Salir]: ");
            filtrado = pedirString();
            switch (filtrado.trim().toLowerCase()) {
                case "nombre":
                System.out.println("Introduce el nombre: ");
                String nombre = pedirString();
                    for (Contacto contacto : contactos) {
                        if (contacto.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                            imprimirContacto(contacto);
                        }
                    }
                    break;
                case "apellidos":
                    System.out.println("Introduce el apellido: ");
                    String apellido = pedirString();
                    for (Contacto contacto : contactos) {
                        if (contacto.getApellidos().toLowerCase().contains(apellido.toLowerCase())) {
                            imprimirContacto(contacto);
                        }
                    }
                    break;
                case "notas":
                    System.out.println("Introduce la nota: ");
                    String notas = pedirString();
                    for (Contacto contacto : contactos) {
                        if (contacto.getNotas().toLowerCase().contains(notas.toLowerCase())) {
                            imprimirContacto(contacto);
                        }
                    }
                    break;
                case "fecha":
                    System.out.println("Introduce por que campo quieres filtrar (Dia, Mes o Anyo):");
                    String campo = pedirString();
                    switch (campo) {
                        case "dia":
                            System.out.println("Introduce el dia (1-31): ");
                            int dia = pedirInt();
                            for (Contacto contacto : contactos) {
                                if(dia == contacto.getFechaNacimiento().getDayOfMonth()) {
                                    imprimirContacto(contacto);
                                }
                            }
                            break;
                        case "mes":
                            System.out.println("Introduce el dia (1-31): ");
                            int mes = pedirInt();
                            for (Contacto contacto : contactos) {
                                if(mes == contacto.getFechaNacimiento().getMonthValue()) {
                                    imprimirContacto(contacto);
                                }
                            }
                            break;
                        case "anyo":
                            System.out.println("Introduce el dia (1-31): ");
                            int anyo = pedirInt();
                            for (Contacto contacto : contactos) {
                                if(anyo == contacto.getFechaNacimiento().getYear()) {
                                    imprimirContacto(contacto);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case "telefono":
                    System.out.println("Introduce el número de teléfono: ");
                    String numero = pedirString();
                    for (Contacto contacto : contactos) {
                        for (Telefono telefono : contacto.getTelefonos()) {
                            if (telefono.getNumero().contains(numero)) {
                                imprimirContacto(contacto);
                            }
                        }
                    }
                    break;
                case "correo":
                    System.out.println("Introduce el correo electrónico: ");
                    String direccion = pedirString();
                    for (Contacto contacto : contactos) {
                        for (Correo correo : contacto.getCorreos()) {
                            if (correo.getDireccion().contains(direccion)) {
                                imprimirContacto(contacto);
                            }
                        }
                    }
                    break;
                case "salir":
                    System.out.println("Ha salido correctamente.");
                    break;
                default:
                    System.out.println("Ha introducido un campo erróneo. Por favor, vuelva a introducir un campo válido: ");
                    break;
            }
            System.out.println("===================================================================================================================================");
            for (Contacto contacto : contactos) {
                imprimirContacto(contacto);
            }
        } while (!filtrado.toLowerCase().equals("salir"));
        
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos públicos">
    /**
     * Método público que sirve para iniciar el CRUD. Primero, lee los datos de los ficheros de texto "contactos.txt" y "contactosEliminados.txt", y luego muestra las
     * opciones posibles de este CRUD al usuario. Se pueden hacer todas las operaciones que se quiera, hasta que se indique explícitamente la opción de "Salir".
     * @param opcion
     */
    public void iniciar(int opcion) {
        if (contador == 0) {
            setNombreArchivo("contactos.txt");
            leerFichero(1);
            setNombreArchivo("contactosEliminados.txt");
            leerFichero(0);
        }
        switch (opcion) {
            case 1:
                System.out.println("Ha elegido la opción 'Añadir contacto'.");
                añadirContacto();
                break;
            case 2:
                System.out.println("Ha elegido la opción 'Ver contacto'.");
                System.out.println("Aquí tiene la lista de los contactos: ");
                verContacto();
                break;
            case 3:
                System.out.println("Ha elegido la opción 'Actualizar contacto'.");
                System.out.println("Aquí tiene la lista de los contactos: ");
                actualizarContacto();
                break;
            case 4:
                System.out.println("Ha elegido la opción 'Eliminar contacto'.");
                System.out.println("Aquí tiene la lista de los contactos: ");
                eliminarContacto();
                break;
            case 5:
                System.out.println("Ha elegido la opción 'Consultar total de contactos'.");
                consultarTotalContactos();
                break;
            case 6:
                System.out.println("Ha elegido la opción 'Ver contactos eliminados'.");
                verContactosEliminados();
                break;
            case 0:
                System.out.println("Se ha salido correctamente.");
                break;
        }
        setContador(getContador() + 1);
    }
    // </editor-fold>
}
