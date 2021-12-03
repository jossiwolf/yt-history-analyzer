package de.jossiwolf.common.util.viewModel

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * A [ViewModelFactory] that attempts to instantiate the requested [ViewModel] class by its
 * primary constructor. This requires a default, empty primary constructor to be present on
 * the class.
 */
object DefaultConstructorBasedViewModelFactory: ViewModelFactory {
    override fun<VM: ViewModel> create(vmClass: KClass<VM>): VM {
        val primaryCtor = vmClass.primaryConstructor
        requireNotNull(primaryCtor) { "The ViewModel class $vmClass does not have an accessible primary constructor" }
        check(primaryCtor.parameters.isEmpty()) { "$vmClass's constructor must have a default empty constructor" }
        return primaryCtor.call()
    }
}
