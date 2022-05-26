import React from "react";
import Container from "react-bootstrap/Container";
import {Col, Row} from "react-bootstrap";
import SignUpForm from "./sign-up-form";

const Home = (props) => {
    return (
        <Container>
            <Row>
                <Col sm={7} className="mt-md-4">
                    <h1>Let's figure out together where your money goes.</h1>
                    <img alt="Trackr" src={"./images/logo.png"} className="rounded mx-auto d-block" />
                </Col>
                <Col sm={5} className="mt-md-4">
                    <h2>Sign Up</h2>
                    <SignUpForm handleSignUpFormSubmit={props.handleSignUpFormSubmit} />
                </Col>
            </Row>
        </Container>
    );
};

export default Home;