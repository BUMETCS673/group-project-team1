/**
 * Login page for users to authenticate.
 *
 * @author Jean Dorancy
 */

import React from "react";
import Container from "react-bootstrap/Container";
import { Col, Row } from "react-bootstrap";
import LoginForm from "./login-form";

const Login = (props) => {
  return (
    <Container className="mt-md-5">
      <Row>
        <Col sm={4} className="mx-auto">
          <LoginForm handleLoginSubmit={props.handleLoginSubmit} />
        </Col>
      </Row>
    </Container>
  );
};

export default Login;
