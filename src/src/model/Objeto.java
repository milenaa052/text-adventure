package model;

public class Objeto {
    private Integer idObjeto;
    private String nomeObjeto;
    private String descricaoCheck;
    private Integer idCenaObjeto; // Verifique se esse campo está presente

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

    public Integer getIdCenaObjeto() {
        return idCenaObjeto;
    }

    public void setIdCenaObjeto(Integer idCenaObjeto) {
        this.idCenaObjeto = idCenaObjeto;
    }

    @Override
    public String toString() {
        return "Objeto{" +
                "idObjeto=" + idObjeto +
                ", nomeObjeto='" + nomeObjeto + '\'' +
                ", descricaoCheck='" + descricaoCheck + '\'' +
                ", idCenaObjeto=" + idCenaObjeto +
                '}';
    }
}
