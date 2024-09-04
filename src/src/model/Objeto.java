package model;

public class Objeto {
    private Integer idObjeto;
    private String nomeObjeto;
    private String descricaoCheck;
    private boolean inventarioBool;

    public Integer getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(Integer idObjeto) {
        this.idObjeto = idObjeto;
    }

    public String getNomeObjeto() {
        return nomeObjeto;
    }

    public void setNomeObjeto(String nomeObjeto) {
        this.nomeObjeto = nomeObjeto;
    }

    public String getDescricaoCheck() {
        return descricaoCheck;
    }

    public void setDescricaoCheck(String descricaoCheck) {
        this.descricaoCheck = descricaoCheck;
    }

    public boolean isInventarioBool() {
        return inventarioBool;
    }

    public void setInventarioBool(boolean inventarioBool) {
        this.inventarioBool = inventarioBool;
    }

    @Override
    public String toString() {
        return "Objeto{" +
                "idObjeto=" + idObjeto +
                ", nomeObjeto='" + nomeObjeto + '\'' +
                ", descricaoCheck='" + descricaoCheck + '\'' +
                ", inventarioBool=" + inventarioBool +
                '}';
    }
}