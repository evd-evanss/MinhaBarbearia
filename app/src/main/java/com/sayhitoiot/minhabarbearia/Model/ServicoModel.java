package com.sayhitoiot.minhabarbearia.Model;

public class ServicoModel {
    String barba;
    String botox;
    String corte_botox;
    String corte_selagem;
    String corte_tintura;
    String selagem;
    String sobrancelha;
    String tintura;
    String corte_cabelo;

    public ServicoModel() {
    }

    public String getBarba() {
        return barba;
    }

    public void setBarba(String barba) {
        this.barba = barba;
    }

    public String getBotox() {
        return botox;
    }

    public void setBotox(String botox) {
        this.botox = botox;
    }

    public String getCorte_botox() {
        return corte_botox;
    }

    public void setCorte_botox(String corte_botox) {
        this.corte_botox = corte_botox;
    }

    public String getCorte_selagem() {
        return corte_selagem;
    }

    public void setCorte_selagem(String corte_selagem) {
        this.corte_selagem = corte_selagem;
    }

    public String getCorte_tintura() {
        return corte_tintura;
    }

    public void setCorte_tintura(String corte_tintura) {
        this.corte_tintura = corte_tintura;
    }

    public String getSelagem() {
        return selagem;
    }

    public void setSelagem(String selagem) {
        this.selagem = selagem;
    }

    public String getSobrancelha() {
        return sobrancelha;
    }

    public void setSobrancelha(String sobrancelha) {
        this.sobrancelha = sobrancelha;
    }

    public String getTintura() {
        return tintura;
    }

    public void setTintura(String tintura) {
        this.tintura = tintura;
    }

    public String getCorte_cabelo() {
        return corte_cabelo;
    }

    public void setCorte_cabelo(String corte_cabelo) {
        this.corte_cabelo = corte_cabelo;
    }

    @Override
    public String toString() {
        return "ServicoModel{" +
                "barba='" + barba + '\'' +
                ", botox='" + botox + '\'' +
                ", corte_botox='" + corte_botox + '\'' +
                ", corte_selagem='" + corte_selagem + '\'' +
                ", corte_tintura='" + corte_tintura + '\'' +
                ", selagem='" + selagem + '\'' +
                ", sobrancelha='" + sobrancelha + '\'' +
                ", tintura='" + tintura + '\'' +
                ", corte_cabelo='" + corte_cabelo + '\'' +
                '}';
    }
}
