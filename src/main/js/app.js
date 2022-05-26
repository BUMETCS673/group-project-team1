import React from "react";
import ReactDOM from "react-dom";
import {Routes} from "react-router";
import {BrowserRouter, Route} from "react-router-dom";
import Home from "./home";
import Layout from "./layout";
import Login from "./login";
import "bootstrap/dist/css/bootstrap.css";

class App extends React.Component {
	render() {
		return (
			<BrowserRouter>
				<Routes>
					<Route path="/" element={<Layout />}>
						<Route index element={<Home />} />
						<Route path="login" element={<Login />} />
					</Route>
				</Routes>
			</BrowserRouter>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)