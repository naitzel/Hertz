package br.com.naitzel.hertz.domain.usecase

/**
 * Classe base para casos de uso que recebem parâmetros
 */
abstract class UseCaseWithParams<in Params, out Type> where Type : Any {
    abstract suspend operator fun invoke(params: Params): Type
}