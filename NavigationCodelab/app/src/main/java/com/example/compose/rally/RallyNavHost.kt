package com.example.compose.rally

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.rally.ui.accounts.AccountsScreen
import com.example.compose.rally.ui.accounts.SingleAccountScreen
import com.example.compose.rally.ui.bills.BillsScreen
import com.example.compose.rally.ui.overview.OverviewScreen

@Composable
fun RallyNavHost(
	navController: NavHostController,
	modifier: Modifier = Modifier
) {
	NavHost(
		navController = navController,
		startDestination = Overview.route,
		modifier = modifier
	) {
		composable(Overview.route) {
			OverviewScreen(
				onClickSeeAllAccounts =
				{navController.navigateSingleTopTo(Accounts.route)},
				onClickSeeAllBills =
				{navController.navigateSingleTopTo(Bills.route)},
				onAccountClick = {navController.navigateToSingleAccount(it)}
			)
		}
		composable(Accounts.route) {
			AccountsScreen(onAccountClick = {navController.navigateToSingleAccount(it)})
		}
		composable(
			route = SingleAccount.routeWithArguments,
			arguments = SingleAccount.arguments,
			deepLinks = SingleAccount.deepLinks
		) { backStackEntry ->
			val accountType = backStackEntry.arguments?.getString(SingleAccount.accountTypeArg)
			SingleAccountScreen(accountType)
		}
		composable(Bills.route) { BillsScreen() }
	}
}

fun NavHostController.navigateSingleTopTo(route: String) =
	this.navigate(route) {
		popUpTo(
			this@navigateSingleTopTo.graph.findStartDestination().id
		) {
			saveState = true
		}
		launchSingleTop = true
		restoreState = true
	}

private fun NavHostController.navigateToSingleAccount(accountType: String) {
	this.navigateSingleTopTo("${SingleAccount.route}/$accountType")
}