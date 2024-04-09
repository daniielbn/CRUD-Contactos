package contacto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import contacto.Contacto.Correo;
import contacto.Contacto.Telefono;
import contacto.Contacto.tipoCorreo;
import contacto.Contacto.tipoTelefono;

/**
 * @author Daniel Brito Negrín
 * @see Contacto
 * @version 1.0 02-04-2024
 */
public class CRUD { 
    private ArrayList<Contacto> listaContactos = new ArrayList<>();
    private ArrayList<Contacto> listaContactosEliminados = new ArrayList<>();
    private String nombreArchivo;
    private int contador;

    public CRUD() {
        
    }

    public ArrayList<Contacto> getListaContactos() {
        return this.listaContactos;
    }

    public ArrayList<Contacto> getListaContactosEliminados() {
        return this.listaContactosEliminados;
    }

    public String getNombreArchivo() {
        return this.nombreArchivo;
    }
    
    public int getContador() {
        return this.contador;
    }

    public void setListaContactos(ArrayList<Contacto> listaContactos) {
        this.listaContactos = listaContactos;
    }

    public void setListaContactosEliminados(ArrayList<Contacto> listaContactosEliminados) {
        this.listaContactosEliminados = listaContactosEliminados;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

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

    private void verContacto() {
        ArrayList<Contacto> contactos = getListaContactos();
        contactos.trimToSize();
        for (Contacto contacto : contactos) {
            imprimirContacto(contacto);
        }
        verFiltrado();
    }

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

    private void consultarTotalContactos() {
        ArrayList<Contacto> contactos = getListaContactos();
        contactos.trimToSize();
        System.out.println("La cantidad de contactos almacenada es de " + contactos.size() + " contactos");
    }

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

    private String pedirString() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    private int pedirInt() {
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }

    private String[] tokenizerGuion(String dato) {
        return dato.split("-");
    }

    private String[] tokenizerComa(String datos) {
        return datos.split(",");
    }

    private ArrayList<Contacto> rellenarContacto(int id, String nombre, String apellidos, String notas, LocalDate fechaNacimiento, ArrayList<Telefono> telefonos, ArrayList<Correo> correos, ArrayList<Contacto> contactos) {
        Contacto contacto = new Contacto(id, nombre, apellidos, notas, fechaNacimiento, telefonos, correos);
        contactos.add(contacto);
        return contactos;
    }

    private boolean comprobarDia(int dia) {
        if (dia < 0 || dia > 31) {
            return false;
        } else {
            return true;
        }
    }

    private boolean comprobarMes(int mes) {
        if (mes < 0 || mes > 12) {
            return false;
        } else {
            return true;
        }
    }

    private boolean comprobarAnyo(int anyo) {
        if (anyo < 1900 || anyo > 2024) {
            return false;
        } else {
            return true;
        }
    }

    private boolean comprobarTelefono(String numero) {
        if (!numero.matches("\\d{9}")) {
            return false;
        }
        return true;
    }
    
    private boolean comprobarCorreo(String direccion) {
        if (!direccion.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            return false;
        }
        return true;
    }

    private boolean comprobaTipoTelefono(String tipoTelefono) {
        if(tipoTelefono.equals("MOVIL") || tipoTelefono.equals("FIJO") || tipoTelefono.equals("TRABAJO")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean comprobaTipoCorreo(String tipoCorreo) {
        if(tipoCorreo.equals("PERSONAL") || tipoCorreo.equals("EMPRESA") || tipoCorreo.equals("TRABAJO")) {
            return true;
        } else {
            return false;
        }
    }

    private LocalDate darFormatoFecha(int dia, int mes, int anyo) {
        String diaFormateado = String.format("%02d", dia);
        String mesFormateado = String.format("%02d", mes);
        String fecha = anyo + "-" + mesFormateado + "-" + diaFormateado;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimiento = LocalDate.parse(fecha, formato);
        return fechaNacimiento;
    }

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

    private void verFiltrado() {
        ArrayList<Contacto> contactos = getListaContactos();
        String filtrado;
        do {
            System.out.println("Por favor, elija el campo por el que quiere filtrar [Nombre, Apellidos, Notas, Fecha de Nacimiento, Telefono, Correo , Salir]: ");
            filtrado = pedirString();
            switch (filtrado.trim().toLowerCase()) {
                case "nombre":
                System.out.println("Introduce el nombre: ");
                String nombre = pedirString();
                    for (Contacto contacto : contactos) {
                        if (contacto.getNombre().toLowerCase().equals(nombre.toLowerCase())) {
                            imprimirContacto(contacto);
                        }
                    }
                    break;
                case "apellidos":
                    System.out.println("Introduce el apellido: ");
                    String apellido = pedirString();
                    String[] apellidos = apellido.split(" ");
                    for (Contacto contacto : contactos) {
                        String[] apellidosContacto = contacto.getApellidos().split(" ");
                        for (int i = 0; i < apellidosContacto.length; i++) {
                            for(int j = 0; j < apellidos.length; j++) {
                                if (apellidosContacto[i].toLowerCase().equals(apellidos[j].toLowerCase())) {
                                    imprimirContacto(contacto);
                                }
                            }
                        }
                    }
                    break;
                case "notas":
                    System.out.println("Introduce la nota: ");
                    String notas = pedirString();
                    for (Contacto contacto : contactos) {
                        if (contacto.getNotas().toLowerCase().equals(notas.toLowerCase())) {
                            imprimirContacto(contacto);
                        }
                    }
                    break;
                case "fechadenacimiento":
                    System.out.println("Introduce el dia (1-31): ");
                    int dia = pedirInt();
                    System.out.println("Introduce el mes (1-12): ");
                    int mes = pedirInt();
                    System.out.println("Introduce el año (1900-2024): ");
                    int anyo = pedirInt();
                    LocalDate fecha = darFormatoFecha(dia, mes, anyo);
                    for (Contacto contacto : contactos) {
                        if (contacto.getFechaNacimiento().isEqual(fecha)) {
                            imprimirContacto(contacto);
                        }
                    }
                    break;
                case "telefono":
                    System.out.println("Introduce el número de teléfono: ");
                    String numero = pedirString();
                    for (Contacto contacto : contactos) {
                        for (Telefono telefono : contacto.getTelefonos()) {
                            if (telefono.getNumero().equals(numero)) {
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
                            if (correo.getDireccion().equals(direccion)) {
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
        } while (!filtrado.toLowerCase().equals("salir"));
        
    }

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
}
