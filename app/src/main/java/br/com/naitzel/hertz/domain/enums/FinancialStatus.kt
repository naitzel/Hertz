package br.com.naitzel.hertz.domain.enums

enum class FinancialStatus(val id: Byte, val description: String) {
    PAID(0, "Pago"),
    PENDING(1, "Pendente"),
    OVERDUE(2, "Atrasado")
}