/**
 * Application layout component which defines the navigation bar for all pages.
 *
 * @author Jean Dorancy
 */

import React from "react";
import { Outlet } from "react-router";
import { Alert, Container, Nav, Navbar } from "react-bootstrap";
import LogoutContainer from "./auth/logout-container";

const Layout = (props) => {
  return (
    <Container>
      <Navbar bg="light" expand="lg">
        <Container>
          {props.isAuthenticated ? (
            <Navbar.Brand href="/dashboard">Trackr</Navbar.Brand>
          ) : (
            <Navbar.Brand href="/">Trackr</Navbar.Brand>
          )}
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              {props.isAuthenticated ? (
                <Nav.Link href="/dashboard">Dashboard</Nav.Link>
              ) : (
                <Nav.Link href="/">Home</Nav.Link>
              )}
              {props.isAuthenticated && (
                <Nav.Link href="/accounts">Accounts</Nav.Link>
              )}
              {props.isAuthenticated && (
                <Nav.Link href="/profile">Profile</Nav.Link>
              )}
              {props.isAuthenticated ? (
                <LogoutContainer setAlert={props.setAlert} />
              ) : (
                <Nav.Link href="/login">Login</Nav.Link>
              )}
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
