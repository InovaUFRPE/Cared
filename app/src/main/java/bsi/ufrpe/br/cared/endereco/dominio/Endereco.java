package bsi.ufrpe.br.cared.endereco.dominio;

public class Endereco {
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private double lat;
    private double lng;

    public Endereco() {}

    public Endereco(String rua, String numero, String bairro, String cidade) {
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
