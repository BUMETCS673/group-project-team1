import React from 'react';
import ReactDOM from 'react-dom';
import Container from 'react-bootstrap/Container'
import {Nav, Navbar, Col, Row, ThemeProvider} from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.css";

class App extends React.Component {
	render() {
		return (
			<Container>
				<Row>
					<Col>
						<Navbar bg="light" expand="lg">
							<Container>
								<Navbar.Brand href="#home">Trackr</Navbar.Brand>
								<Navbar.Toggle aria-controls="basic-navbar-nav" />
								<Navbar.Collapse id="basic-navbar-nav">
									<Nav className="me-auto">
										<Nav.Link href="#home">Home</Nav.Link>
										<Nav.Link href="#login">Login</Nav.Link>
									</Nav>
								</Navbar.Collapse>
							</Container>
						</Navbar>
					</Col>
				</Row>
				<Row>
					<Col>
						Hello world! From Bootstrap!
					</Col>
				</Row>
			</Container>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)