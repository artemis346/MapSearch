
import Destinations.MAP_ROUTE
import Destinations.SEARCH_ROUTE
import androidx.navigation.NavHostController

object Destinations {
    const val MAP_ROUTE = "map"
    const val SEARCH_ROUTE = "searchRoute"
}


fun NavHostController.navigateSearch() {
    navigate(SEARCH_ROUTE)
}

fun NavHostController.navigateMap() {
    navigate(MAP_ROUTE)
}
