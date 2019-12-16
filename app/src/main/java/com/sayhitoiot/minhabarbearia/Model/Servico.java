package com.sayhitoiot.minhabarbearia.Model;

public class Servico {
    String servico;
    String valor;

    public Servico() {
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Servico{" +
                "servico='" + servico + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
