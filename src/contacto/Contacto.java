package contacto;

// <editor-fold defaultstate="collapsed" desc="Librerías">
import java.time.LocalDate;
import java.util.ArrayList;
// </editor-fold>

/**
 * @author Daniel Brito Negrín
 * @see Main
 * @see CRUD
 * @see Menu
 * @version 1.0 09-04-2024
 */
public class Contacto {
    // <editor-fold defaultstate="collapsed" desc="Atributos"> 
    private static int count = 0;
    private int id;
    private String nombre;
    private String apellidos;
    private String notas;
    private LocalDate fechaNacimiento;
    private ArrayList<Telefono> telefonos;
    private ArrayList<Correo> correos;
    
    /**
     * Enumerado para establecer el tipo del teléfono. Solo hay tres.
     */
    public enum tipoTelefono {
        MOVIL, 
        FIJO, 
        TRABAJO,
    }
    
    public static class Telefono {
        // <editor-fold defaultstate="collapsed" desc="Atributos"> 
        private String numero;
        private tipoTelefono tipo;
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructor"> 
        /**
         * Constructor parametrizado de la clase Telefono.
         * @param numero Número del teléfono.
         * @param tipo Tipo del teléfono.
         */
        public Telefono(String numero, tipoTelefono tipo) {
            this.numero = numero;
            this.tipo = tipo;
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters"> 
        /**
         * Método getter para obtener el número del teléfono.
         * @return Devuelve el número de teléfono.
         */
        public String getNumero() {
            return this.numero;
        }
        
        /**
         * Método getter para obtener el tipo del teléfono.
         * @return Devuelve el tipo del teléfono.
         */
        public tipoTelefono getTipo() {
            return this.tipo;
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Setters"> 
        /**
         * Método setter para establecer el número de teléfono.
         * @param numero Número de teléfono a establecer.
         */
        public void setNumero(String numero) {
            this.numero = numero;
        }
        
        /**
         * Método setter para establecer el tipo del teléfono.
         * @param tipo Tipo del teléfono a establecer.
         */
        public void setTipo(tipoTelefono tipo) {
            this.tipo = tipo;
        }
        // </editor-fold>
    }
    
    /**
     * Enumerado de los tipos de correos electrónicos. Solo hay tres.
     */
    public enum tipoCorreo {
        PERSONAL, 
        TRABAJO,
        EMPRESA
    }
    
    public static class Correo {
        // <editor-fold defaultstate="collapsed" desc="Atributos"> 
        private String direccion;
        private tipoCorreo tipo;
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructor"> 
        /**
         * Constructor parametrizado de la clase Correo.
         * @param direccion Dirección de correo electrónico.
         * @param tipo Tipo del correo electrónico.
         */
        public Correo(String direccion, tipoCorreo tipo) {
            this.direccion = direccion;
            this.tipo = tipo;
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters"> 
        /**
         * Método getter para obtener la dirección de correo electrónico.
         * @return Devuelve la dirección de correo electrónico.
         */
        public String getDireccion() {
            return this.direccion;
        }
        
        /**
         * Método getter para obtener el tipo de correo electrónico.
         * @return Devuelve el tipo de correo electrónico.
         */
        public tipoCorreo getTipo() {
            return this.tipo;
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Setters"> 
        /**
         * Método setter para establecer la dirección de correo electrónico.
         * @param direccion Dirección de correo electrónico a establecer.
         */
        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }
        
        /**
         * Método setter para establecer el tipo de correo electrónico.
         * @param tipo Tipo de correo electrónico a establecer.
         */
        public void setTipo(tipoCorreo tipo) {
            this.tipo = tipo;
        }
        // </editor-fold>
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructores"> 
    /**
     * Constructor vacío de la Clase Contacto.
     */
    public Contacto() {
        this.id = ++count;
        this.nombre = "";
        this.apellidos = "";
        this.notas = "";
        this.fechaNacimiento = LocalDate.now();
        this.telefonos = new ArrayList<Telefono>();
        this.correos = new ArrayList<Correo>();
    }
    
    /**
     * Constructor parametrizado con número de identificación manual de la clase Contacto.
     * @param id Número de identificación del contacto.
     * @param nombre Nombre del contacto.
     * @param apellidos Apellidos del contacto.
     * @param notas Notas del contacto.
     * @param fechaNacimiento Fecha de nacimiento del contacto.
     * @param telefonos ArrayList de telefonos del contacto.
     * @param correos ArrayList de correos electrónicos del contacto.
     */
    public Contacto(int id, String nombre, String apellidos, String notas, LocalDate fechaNacimiento, ArrayList<Telefono> telefonos, ArrayList<Correo> correos) {
        this.id = id;
        count++;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.notas = notas;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonos = telefonos;
        this.correos = correos;
    }

    /**
     * Constructor parametrizado con número de identificación automático de la clase Contacto.
     * @param nombre Nombre del contacto.
     * @param apellidos Apellidos del contacto.
     * @param notas Notas del contacto.
     * @param fechaNacimiento Fecha de nacimiento del contacto.
     * @param telefonos ArrayList de telefonos del contacto.
     * @param correos ArrayList de correos electrónicos del contacto.
     */
    public Contacto(String nombre, String apellidos, String notas, LocalDate fechaNacimiento, ArrayList<Telefono> telefonos, ArrayList<Correo> correos) {
        this.id = count++;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.notas = notas;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonos = telefonos;
        this.correos = correos;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters"> 
    /**
     * Método getter para obtener el número de identificación del contacto.
     * @return Devuelve el número de identificación del contacto.
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * Método getter para obtener el nombre del contacto.
     * @return Devuelve el nombre del contacto.
     */
    public String getNombre() {
        return this.nombre;
    }
    
    /**
     * Método getter para obtener los apellidos del contacto.
     * @return Devuelve los apellidos del contacto.
     */
    public String getApellidos() {
        return this.apellidos;
    }

    /**
     * Método getter para obtener las notas del contacto.
     * @return Devuelve las notas del contacto.
     */
    public String getNotas() {
        return this.notas;
    }

    /**
     * Método getter para obtener la fecha de nacimiento del contacto.
     * @return Devuelve la fecha de nacimiento del contacto.
     */
    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }
    
    /**
     * Método getter para obtener el ArrayList de los teléfonos del contacto.
     * @return Devuelve el ArrayList de teléfonos del contacto.
     */
    public ArrayList<Telefono> getTelefonos() {
        return this.telefonos;
    }
    
    /**
     * Método getter para obtener el ArrayList de los correos electrónicos del contacto.
     * @return Devuelve el ArrayList de correos electrónicos del contacto.
     */
    public ArrayList<Correo> getCorreos() {
        return this.correos;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Setters"> 
    /**
     * Método setter para establecer el número de identificación del contacto.
     * @param id Número de identificación a establecer.
     */
    public void setId(int id) {
        this.id = id;
        --count;
    }

    /**
     * Método setter para establecer el nombre del contacto.
     * @param nombre Nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Método setter para establecer los apellidos del contacto.
     * @param apellidos Apellidos a establecer.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Método setter para establecer las notas del contacto.
     * @param notas Notas a establecer.
     */
    public void setNotas(String notas) {
        this.notas = notas;
    }

    /**
     * Método setter para establecer la fecha de nacimiento del contacto.
     * @param fechaNacimiento Fecha de nacimiento a establecer.
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    /**
     * Método setter para establecer el ArrayList de teléfonos del contacto.
     * @param telefonos ArrayList de teléfonos a establecer.
     */
    public void setTelefonos(ArrayList<Telefono> telefonos) {
        this.telefonos = telefonos;
    }
    
    /**
     * Método setter para establecer el ArrayList de correos electrónicos del contacto.
     * @param correos ArrayList de correos electrónicos a establecer.
     */
    public void setCorreos(ArrayList<Correo> correos) {
        this.correos = correos;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos">
    /**
     * Método publico de sobreescritura para pasar el objeto a un String
     * @return Devuelve una cadena de texto con todos los datos del contacto.
     */
    @Override
    public String toString() {
        String telefonos = new String();
        String correos = new String();
        for (Telefono telefono : this.telefonos) {
            telefonos += telefono.getNumero() + "||" + telefono.getTipo();
        }
        for (Correo correo : this.correos) {
            correos += correo.getDireccion() + "||" + correo.getTipo();
        }
        return this.nombre + " " + this.apellidos + " " + telefonos + " " + correos + "\n";
    }
    // </editor-fold>
}
