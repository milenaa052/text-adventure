package model;

public class Objeto {
    private Integer idObjeto;
    private String nomeObjeto;
    private String descricaoCheck;
    private Integer inventarioBool;
    private Integer idCenaObjeto;

    public Objeto(Integer idObjeto, String nomeObjeto, String descricaoCheck, Integer inventarioBool, Integer idCenaObjeto) {
        this.idObjeto = idObjeto;
        this.nomeObjeto = nomeObjeto;
        this.descricaoCheck = descricaoCheck;
        this.inventarioBool = inventarioBool;
        this.idCenaObjeto = idCenaObjeto;
    }

    // Construtor padrão (sem argumentos)
    public Objeto() {
    }

    public Integer getIdCenaObjeto() {
        return idCenaObjeto;
    }

    public void setIdCenaObjeto(Integer idCenaObjeto) {
        this.idCenaObjeto = idCenaObjeto;
    }

    public Integer getInventarioBool() {
        return inventarioBool;
    }

    public void setInventarioBool(Integer inventarioBool) {
        this.inventarioBool = inventarioBool;
    }

    public String getDescricaoCheck() {
        return descricaoCheck;
    }

    public void setDescricaoCheck(String descricaoCheck) {
        this.descricaoCheck = descricaoCheck;
    }

    public String getNomeObjeto() {
        return nomeObjeto;
    }

    public void setNomeObjeto(String nomeObjeto) {
        this.nomeObjeto = nomeObjeto;
    }

    public Integer getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(Integer idObjeto) {
        this.idObjeto = idObjeto;
    }

    @Override
    public String toString() {
        return "Objeto{" +
                "idObjeto=" + idObjeto +
                ", nomeObjeto='" + nomeObjeto + '\'' +
                ", descricaoCheck='" + descricaoCheck + '\'' +
                ", inventarioBool=" + inventarioBool +
                ", idCenaObjeto=" + idCenaObjeto +
                '}';
    }
}
