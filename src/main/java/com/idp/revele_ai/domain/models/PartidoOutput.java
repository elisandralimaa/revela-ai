package com.idp.revele_ai.domain.models;

public class PartidoOutput {
    private Integer id;
    private String sigla;
    private String nome;
    private String uri;
    private PartidoStatus status;
    private String numeroEleitoral = null;
    private String urlLogo;
    private String urlWebSite = null;
    private String urlFacebook = null;

    public PartidoOutput() {
    }
    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
}


