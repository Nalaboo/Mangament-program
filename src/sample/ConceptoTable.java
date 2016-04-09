package sample;


public class ConceptoTable {
    private String consulta;
    private Double precio;

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public ConceptoTable (){};
    public ConceptoTable(String con,Double pre){
        this.consulta = con;
        this.precio = pre;
    }
}
