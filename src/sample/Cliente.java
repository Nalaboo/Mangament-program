package sample;


public class Cliente {

    private String cif;
    private String nombreFiscal;
    private String nombreComercial;
    private String direccion;
    private String pais;
    private Integer codigoPostal;
    private String poblacion;
    private String personaContacto;
    private String telefono;
    private String movil;
    private String fax;
    private String email;
    private String paginaWeb;

    public Cliente(String cif, String nombreFiscal, String nombreComercial,String direccion,String pais,Integer codigoPostal,String poblacion,String personaContacto,String telefono,String movil,String fax,String email,String paginaWeb){
        this.cif = cif;
        this.nombreFiscal = nombreFiscal;
        this.nombreComercial = nombreComercial;
        this.direccion = direccion;
        this.pais = pais;
        this.codigoPostal = codigoPostal;
        this.poblacion = poblacion;
        this.personaContacto = personaContacto;
        this.telefono = telefono;
        this.movil = movil;
        this.fax = fax;
        this.email = email;
        this.paginaWeb = paginaWeb;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNombreFiscal() {
        return nombreFiscal;
    }

    public void setNombreFiscal(String nombreFiscal) {
        this.nombreFiscal = nombreFiscal;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public Cliente (){};
}
