/**
 * Application home page which also renders the registration form. All presentation no logic.
 *
 * @author Jean Dorancy
 */

import React from "react";
import Container from "react-bootstrap/Container";
import { Col, Row } from "react-bootstrap";
import SignUpForm from "./user/sign-up-form";

const Home = (props) => {
  return (
    <Container>
      <Row>
        <Col sm={8} className="mt-md-4">
          <h1>Let's figure out together where your money goes.</h1>
          <img
            alt="Trackr"
            src={"./images/logo.png"}
            className="rounded mx-auto d-block"
          />
        </Col>
        <Col sm={4} className="mt-md-4">
          <SignUpForm handleUserFormSubmit={props.handleUserFormSubmit} />
        </Col>
      </Row>
    </Container>
  );
};

export default Home;
