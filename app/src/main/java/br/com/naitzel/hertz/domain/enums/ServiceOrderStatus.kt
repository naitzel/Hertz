package br.com.naitzel.hertz.domain.enums

enum class ServiceOrderStatus(val id: Byte, val description: String) {
    PENDING(0, "Pendente"),
    IN_PROGRESS(1, "Em progresso"),
    COMPLETED(2, "Concluído"),
    CANCELLED(3, "Cancelado")
}