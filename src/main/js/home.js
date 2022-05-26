import React from "react";
import Container from "react-bootstrap/Container";
import {Col, Row} from "react-bootstrap";

const Home = () => {
    return (
        <Container>
            <Row>
                <Col sm={8} className="mt-md-5">
                    <h1>Welcome to Trackr!</h1>
                    <h2>Sign Up!</h2>
                </Col>
                <Col sm={4}>
                    Form to fill out.
                </Col>
            </Row>
        </Container>
    );
};

export default Home;