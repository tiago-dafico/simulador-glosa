package br.com.zgsolucoes.simuladorglosa.dominio

enum ItemType {
	PROCEDIMENTO('Procedimento', 1.55),
	MATERIAL('Material', 1.2),
	MEDICAMENTO('Medicamento', 1.3),
	TAXA('Taxa', 1.15)

	String description
	BigDecimal value

	ItemType(String description, BigDecimal value) {
		this.description = description
		this.value = value
	}

	String getDescription() {
		return this.description
	}

	BigDecimal getValue() {
		return this.value
	}
}
