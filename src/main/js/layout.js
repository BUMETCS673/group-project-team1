/**
 * Application layout component which defines the navigation bar for all pages.
 *
 * @author Jean Dorancy
 */

import React from "react";
import { Outlet } from "react-router";
import { Container, Navbar, Nav, Alert } from "react-bootstrap";

const Layout = (props) => {
  console.log("layout", props.alert);
  return (
    <Container>
      <Navbar bg="light" expand="lg">
        <Container>
          <Navbar.Brand href="/">Trackr</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link href="/">Home</Nav.Link>
              <Nav.Link href="/login">Login</Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
      <Container className="mt-md-2">
        {props.alert.show && (
          <Alert
            key={props.alert.variant}
            variant={props.alert.variant}
            onClose={() => props.setAlert({ show: false })}
            dismissible
          >
            {props.alert.message}
          </Alert>
        )}
      </Container>
      <Outlet />
    </Container>
  );
};

export default Layout;
