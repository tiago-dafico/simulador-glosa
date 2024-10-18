package br.com.zgsolucoes.simuladorglosa.dominio

enum TipoItem {
    PROCEDIMENTO(1.55, "Procedimento"),
    MATERIAL(1.2, "Material"),
    MEDICAMENTO(1.3, "Medicamento"),
    TAXA(1.15, "Taxa");

    BigDecimal multiplicador;
    String valor;
}
