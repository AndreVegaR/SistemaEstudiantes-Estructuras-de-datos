package dominio;

import excepciones.ListException;
import java.util.Objects;
import listas.ArrayList;

/**
 * Representa un estudiante dentro
 * del sistema
 */
public class Estudiante implements Comparable<Estudiante> {
    
    //Atributos
    private String matricula;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String correo;
    private String calle;
    private String numero;
    private String colonia;
    private String ciudad;
    private ArrayList<Calificacion> calificaciones;

    /**
     * Constructor 
     * 
     * @param matricula
     * @param nombres
     * @param apellidoPaterno
     * @param apellidoMaterno
     * @param telefono
     * @param correo
     * @param calle
     * @param numero
     * @param colonia
     * @param ciudad 
     */
    public Estudiante(String matricula, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String correo, String calle, String numero, String colonia, String ciudad) {
        this.matricula = matricula;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correo = correo;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.calificaciones = new ArrayList<>(1);
    }
    public Estudiante() {}
    
    
    /** Agrega una calificación al estudiante */
    public void agregarCalificacion(Calificacion calificacion) {
        if (calificacion.getValor() < 0) {
            throw new ListException("La calificación no puede ser menor a 0");
        }
        this.calificaciones.append(calificacion);
    }

    
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    @Override
    public int compareTo(Estudiante otro) {
        return this.matricula.compareTo(otro.getMatricula());
    }

    @Override
    public String toString() {
        return "Estudiante{" + "matricula=" + matricula + ", nombres=" + nombres + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", telefono=" + telefono + ", correo=" + correo + ", calle=" + calle + ", numero=" + numero + ", colonia=" + colonia + ", ciudad=" + ciudad + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Estudiante other = (Estudiante) obj;
        return Objects.equals(this.matricula, other.matricula);
    }
    
    
}