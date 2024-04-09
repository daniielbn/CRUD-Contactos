package contacto;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Daniel Brito Negrín
 * @see Main
 * @version 1.0 02-04-2024
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
        public Telefono(String numero, tipoTelefono tipo) {
            this.numero = numero;
            this.tipo = tipo;
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters"> 
        public String getNumero() {
            return this.numero;
        }
        
        
        public tipoTelefono getTipo() {
            return this.tipo;
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Setters"> 
        public void setNumero(String numero) {
            this.numero = numero;
        }
        
        public void setTipo(tipoTelefono tipo) {
            this.tipo = tipo;
        }
        // </editor-fold>
    }
    
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
        public Correo(String direccion, tipoCorreo tipo) {
            this.direccion = direccion;
            this.tipo = tipo;
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters"> 
        public String getDireccion() {
            return this.direccion;
        }
        
        
        public tipoCorreo getTipo() {
            return this.tipo;
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Setters"> 
        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }
        
        public void setTipo(tipoCorreo tipo) {
            this.tipo = tipo;
        }
        // </editor-fold>
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructores"> 
    public Contacto() {
        this.id = ++count;
        this.nombre = "";
        this.apellidos = "";
        this.notas = "";
        this.fechaNacimiento = LocalDate.now();
        this.telefonos = new ArrayList<Telefono>();
        this.correos = new ArrayList<Correo>();
    }
    
    
    public Contacto(int id, String nombre, String apellidos, String notas, LocalDate fechaNacimiento, ArrayList<Telefono> telefonos, ArrayList<Correo> correos) {
        this.id = id;
        ++count;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.notas = notas;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonos = telefonos;
        this.correos = correos;
    }

    public Contacto(String nombre, String apellidos, String notas, LocalDate fechaNacimiento, ArrayList<Telefono> telefonos, ArrayList<Correo> correos) {
        this.id = ++count;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.notas = notas;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonos = telefonos;
        this.correos = correos;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters"> 
    public int getId() {
        return this.id;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public String getApellidos() {
        return this.apellidos;
    }

    public String getNotas() {
        return this.notas;
    }

    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }
    
    public ArrayList<Telefono> getTelefonos() {
        return this.telefonos;
    }
    
    public ArrayList<Correo> getCorreos() {
        return this.correos;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Setters"> 
    public void setId(int id) {
        this.id = id;
        --count;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public void setTelefonos(ArrayList<Telefono> telefonos) {
        if (comprobarTelefono(telefonos)) {
            this.telefonos = telefonos;
        }
    }
    
    public void setCorreos(ArrayList<Correo> correos) {
        if (comprobarCorreo(correos)) {
            this.correos = correos;
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos">
    private boolean comprobarTelefono(ArrayList<Telefono> telefonos) {
        for(Telefono telefono : telefonos) {
            String numero = telefono.getNumero();
            if (!numero.matches("\\d{9}")) {
                System.out.println("[" + numero + "] no es un número de telefono válido.");
                return false;
            }
        }
        return true;
    }
    
    private boolean comprobarCorreo(ArrayList<Correo> correos) {
        for (Correo correo : correos) {
            String direccion = correo.getDireccion();
            if (!direccion.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
               System.out.println("[" + direccion + "] no es un correo válido.");
               return false;
            }
        }
        return true;
    }
    
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
