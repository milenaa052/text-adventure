package model;

public class Comandos {
    private Integer idComando;
    private String comando;
    private String resultadoPositivo;
    private Integer idComandoObj;
    private Integer idCenaAtual;
    private Integer idCenaDestino;

    public Comandos(Integer idComando, String comando, String resultadoPositivo, Integer idComandoObj, Integer idCenaAtual, Integer idCenaDestino) {
        this.idComando = idComando;
        this.comando = comando;
        this.resultadoPositivo = resultadoPositivo;
        this.idComandoObj = idComandoObj;
        this.idCenaAtual = idCenaAtual;
        this.idCenaDestino = idCenaDestino;
    }

    public Comandos() {
    }

    public Integer getIdComando() {
        return idComando;
    }

    public void setIdComando(Integer idComando) {
        this.idComando = idComando;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getResultadoPositivo() {
        return resultadoPositivo;
    }

    public void setResultadoPositivo(String resultadoPositivo) {
        this.resultadoPositivo = resultadoPositivo;
    }

    public Integer getIdComandoObj() {
        return idComandoObj;
    }

    public void setIdComandoObj(Integer idComandoObj) {
        this.idComandoObj = idComandoObj;
    }

    public Integer getIdCenaAtual() {
        return idCenaAtual;
    }

    public void setIdCenaAtual(Integer idCenaAtual) {
        this.idCenaAtual = idCenaAtual;
    }

    public Integer getIdCenaDestino() {
        return idCenaDestino;
    }

    public void setIdCenaDestino(Integer idCenaDestino) {
        this.idCenaDestino = idCenaDestino;
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
