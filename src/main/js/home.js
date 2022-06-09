/**
 * Application home page which also renders the registration form. All presentation no logic.
 *
 * @author Jean Dorancy
 */

import React from "react";
import Container from "react-bootstrap/Container";
import { Col, Row } from "react-bootstrap";
import UserForm from "./user/user-form";
import * as Yup from "yup";

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
          <UserForm
              handleUserFormSubmit={props.handleUserFormSubmit}
              validationSchema={Yup.object({
                firstName: Yup.string()
                    .max(100, "Must be 100 characters or less")
                    .required("Required"),
                lastName: Yup.string()
                    .max(100, "Must be 100 characters or less")
                    .required("Required"),
                username: Yup.string()
                    .max(50, "Must be 50 characters or less")
                    .required("Required"),
                email: Yup.string()
                    .max(50, "Must be 50 characters or less")
                    .email("Invalid email address")
                    .required("Required"),
                password: Yup.string()
                    .min(6, "Must be at least 6 characters")
                    .max(200, "Must be less than 200 characters")
                    .required("Required"),
              })}
          />
        </Col>
      </Row>
    </Container>
  );
};

export default Home;
