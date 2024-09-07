package model;

public class Objeto {
    private Integer idObjeto;
    private String nomeObjeto;
    private String descricaoCheck;
    private boolean inventarioBool;
    private Integer idCenaObjeto;

    // Construtor com todos os parâmetros
    public Objeto(Integer idObjeto, String nomeObjeto, String descricaoCheck, boolean inventarioBool, Integer idCenaObjeto) {
        this.idObjeto = idObjeto;
        this.nomeObjeto = nomeObjeto;
        this.descricaoCheck = descricaoCheck;
        this.inventarioBool = inventarioBool;
        this.idCenaObjeto = idCenaObjeto;
    }

    // Construtor padrão (sem argumentos)
    public Objeto() {
    }

    // Getters e Setters
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
                ", inventarioBool=" + inventarioBool +
                ", idCenaObjeto=" + idCenaObjeto +
                '}';
    }
}
