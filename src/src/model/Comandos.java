package model;

public class Comandos {
    private Integer idComando;
    private String comando;
    private String resultadoPositivo;
    private Integer idComandoObj;
    private Integer idCenaAtual;
    private Integer idCenaDestino;

    public Integer getIdCenaDestino() {
        return idCenaDestino;
    }

    public void setIdCenaDestino(Integer idCenaDestino) {
        this.idCenaDestino = idCenaDestino;
    }

    public Integer getIdCenaAtual() {
        return idCenaAtual;
    }

    public void setIdCenaAtual(Integer idCenaAtual) {
        this.idCenaAtual = idCenaAtual;
    }

    public Integer getIdComandoObj() {
        return idComandoObj;
    }

    public void setIdComandoObj(Integer idComandoObj) {
        this.idComandoObj = idComandoObj;
    }

    public String getResultadoPositivo() {
        return resultadoPositivo;
    }

    public void setResultadoPositivo(String resultadoPositivo) {
        this.resultadoPositivo = resultadoPositivo;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public Integer getIdComando() {
        return idComando;
    }

    public void setIdComando(Integer idComando) {
        this.idComando = idComando;
    }

    @Override
    public String toString() {
        return "Comandos{" +
                "idComando=" + idComando +
                ", comando='" + comando + '\'' +
                ", resultadoPositivo='" + resultadoPositivo + '\'' +
                ", idComandoObj=" + idComandoObj +
                ", idCenaAtual=" + idCenaAtual +
                ", idCenaDestino=" + idCenaDestino +
                '}';
    }
}