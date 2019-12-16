package com.sayhitoiot.minhabarbearia.Model;

public class AgendamentoModel {
    String ano;
    String cliente;
    String barbeiro;
    String dia;
    String horario;
    String mes;
    String preco;
    String servico;
    String key_barbeiro;
    String key_cliente;

    public String getKey_barbeiro() {
        return key_barbeiro;
    }

    public void setKey_barbeiro(String key_barbeiro) {
        this.key_barbeiro = key_barbeiro;
    }

    public String getKey_cliente() {
        return key_cliente;
    }

    public void setKey_cliente(String key_cliente) {
        this.key_cliente = key_cliente;
    }

    public String getBarbeiro() {
        return barbeiro;
    }

    public void setBarbeiro(String barbeiro) {
        this.barbeiro = barbeiro;
    }

    public AgendamentoModel() {
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    @Override
    public String toString() {
        return "AgendamentoModel{" +
                "ano='" + ano + '\'' +
                ", cliente='" + cliente + '\'' +
                ", barbeiro='" + barbeiro + '\'' +
                ", dia='" + dia + '\'' +
                ", horario='" + horario + '\'' +
                ", mes='" + mes + '\'' +
                ", preco='" + preco + '\'' +
                ", servico='" + servico + '\'' +
                ", key_barbeiro='" + key_barbeiro + '\'' +
                ", key_cliente='" + key_cliente + '\'' +
                '}';
    }
}
