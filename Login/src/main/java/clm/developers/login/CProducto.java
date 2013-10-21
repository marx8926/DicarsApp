package clm.developers.login;


public class CProducto {
    private String nombre;
    private String cantidad;

    public CProducto(){

    }

    public CProducto(String nombre, String cantidad) {
        setNombre(nombre);
        setCantidad(cantidad);

    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCantidad() {
        return cantidad;
    }
}
