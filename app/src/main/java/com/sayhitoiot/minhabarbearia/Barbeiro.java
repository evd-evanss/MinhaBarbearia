package com.sayhitoiot.minhabarbearia;

public class Barbeiro {
    String nome;
    String patchImage;
    String id;

    public Barbeiro() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPatchImage() {
        return patchImage;
    }

    public void setPatchImage(String patchImage) {
        this.patchImage = patchImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Barbeiro{" +
                        "id='" + id + '\'' +
                        ", nome='" + nome + '\'' +
                        ", patchImage='" + patchImage + '\'' +
                        '}';
    }
}
