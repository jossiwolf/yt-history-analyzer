package de.jossiwolf.common.util.viewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import kotlin.reflect.KClass


/**
 * Create and [remember] a [ViewModel] and register it with the [ViewModelRegistry].
 *
 * The registry will be notified when the [viewModel] composable enters the composition as
 * well as when it leaves the composition, removing it from the list of known instances and
 * calling [ViewModel.onClear]. The destruction of the [ViewModel], and thus it's [ViewModel.viewModelScope]
 * is scheduled after a grace period, by default 5 seconds.
 *
 * @param registry The [ViewModelRegistry] this VM will be registered with
 * @param factory The factory producing the instance
 */
@Composable
inline fun <reified VM : ViewModel> viewModel(
    registry: ViewModelRegistry = DefaultViewModelRegistry,
    factory: ViewModelFactory = DefaultConstructorBasedViewModelFactory
): VM {
    val viewModel = remember(factory) { factory.create(VM::class) }
    DisposableEffect(factory) {
        registry.add(viewModel)
        onDispose { registry.remove(viewModel) }
    }
    return viewModel
}

/**
 * Create and [remember] a [ViewModel] and register it with the [ViewModelRegistry].
 *
 * The registry will be notified when the [viewModel] composable enters the composition as
 * well as when it leaves the composition, removing it from the list of known instances and
 * calling [ViewModel.onClear]. The destruction of the [ViewModel], and thus it's [ViewModel.viewModelScope]
 * is scheduled after a grace period, by default 5 seconds.
 *
 * @param registry The [ViewModelRegistry] this VM will be registered with
 * @param factory The factory producing the instance
 */
@Composable
inline fun <reified VM : ViewModel> viewModel(
    registry: ViewModelRegistry = DefaultViewModelRegistry,
    noinline factory: (vmClass: KClass<VM>) -> VM = DefaultConstructorBasedViewModelFactory::create
): VM = viewModel(registry, remember(factory) { ViewModelFactory(factory) })

fun <VM : ViewModel> ViewModelFactory(create: (vmClass: KClass<VM>) -> VM) = object : ViewModelFactory {
    @Suppress("UNCHECKED_CAST")
    override fun <VM1 : ViewModel> create(vmClass: KClass<VM1>): VM1 {
        return create.invoke(vmClass as KClass<VM>) as VM1
    }
}
