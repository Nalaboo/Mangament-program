package sample;

/**
 * Created by Maria on 28/08/2015.
 */
public class Presupuestoc {
        private String idcliente;
        private double preciototal;
        private double precioiva;
        private String nombrepre;

        public Presupuestoc( String nombrepre, String idcliente, Double preciototal, Double precioiva){

            this.idcliente = idcliente;
            this.preciototal = preciototal;
            this.precioiva = precioiva;
            this.nombrepre = nombrepre;
        }

    public String getIdcliente() {return idcliente;}

    public void setIdcliente(String idcliente) {this.idcliente = idcliente;}

    public double getPreciototal() {return preciototal;}

    public void setPreciototal(double preciototal) {this.preciototal = preciototal;}

    public double getPrecioiva() {return precioiva;}

    public void setPrecioiva(double precioiva) {this.precioiva = precioiva;}

    public String getNombrepre() {return nombrepre;}

    public void setNombrepre(String nombrepre) {this.nombrepre = nombrepre;}

    public Presupuestoc (){};

}
