package de.jossiwolf.common.util.viewModel

import kotlin.reflect.KClass

/**
 * Factory for [ViewModel]s. This can be used to implement naive factories (i.e. [DefaultConstructorBasedViewModelFactory])
 * or more complex factories for use with i.e. dependency injection frameworks.
 */
interface ViewModelFactory {
    fun<VM: ViewModel> create(vmClass: KClass<VM>): VM
}
