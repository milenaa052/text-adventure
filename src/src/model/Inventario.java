package model;

public class Inventario {
    private Integer idInventario;
    private Integer idInventarioObj;

    public Integer getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Integer idInventario) {
        this.idInventario = idInventario;
    }

    public Integer getIdInventarioObj() {
        return idInventarioObj;
    }

    public void setIdInventarioObj(Integer idInventarioObj) {
        this.idInventarioObj = idInventarioObj;
    }

    @Override
    public String toString() {
        return "Inventario{" +
                "idInventario=" + idInventario +
                ", idInventarioObj=" + idInventarioObj +
                '}';
    }
}