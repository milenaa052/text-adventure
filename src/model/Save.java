package model;

public class Save {
    private Integer idSave;
    private Integer idSaveCenaAtual;
    private String data;

    public Save(Integer idSave, Integer idSaveCenaAtual, String data) {
        this.idSave = idSave;
        this.idSaveCenaAtual = idSaveCenaAtual;
        this.data = data;
    }

    public Save() {
    }

    public Integer getIdSave() {
        return idSave;
    }

    public void setIdSave(Integer idSave) {
        this.idSave = idSave;
    }

    public Integer getIdSaveCenaAtual() {
        return idSaveCenaAtual;
    }

    public void setIdSaveCenaAtual(Integer idSaveCenaAtual) {
        this.idSaveCenaAtual = idSaveCenaAtual;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Save{" +
                "idSave=" + idSave +
                ", idSaveCenaAtual=" + idSaveCenaAtual +
                ", data='" + data + '\'' +
                '}';
    }
}