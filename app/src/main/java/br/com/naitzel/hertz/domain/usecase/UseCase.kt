package br.com.naitzel.hertz.domain.usecase

/**
 * Classe base para casos de uso que não recebem parâmetros
 */
abstract class UseCase<out Type> where Type : Any {
    abstract suspend operator fun invoke(): Type
}