package com.melhorgrupo.printserverclient.arquivosEnviados;

import java.io.Serializable;

public class ArquivoEnviado implements Serializable {
    private int id;
    private String nome;
    private String enviadoEm;
    private String ultimaImpressao;

    public ArquivoEnviado(int id, String nome, String enviadoEm) {
        this.id = id;
        this.nome = nome;
        this.enviadoEm = enviadoEm;
        this.ultimaImpressao = "";
    }

    public ArquivoEnviado(int id, String nome, String enviadoEm, String ultimaImpressao) {
        this.id = id;
        this.nome = nome;
        this.enviadoEm = enviadoEm;
        this.ultimaImpressao = ultimaImpressao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEnviadoEm() {
        return enviadoEm;
    }

    public void setEnviadoEm(String enviadoEm) {
        this.enviadoEm = enviadoEm;
    }

    public String getUltimaImpressao() {
        return ultimaImpressao;
    }

    public void setUltimaImpressao(String ultimaImpressao) {
        this.ultimaImpressao = ultimaImpressao;
    }

    @Override
    public boolean equals(Object o) {
        return this.id == ((ArquivoEnviado) o).id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}
