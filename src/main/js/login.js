import React from "react";
import Container from "react-bootstrap/Container";
import {Col, Row} from "react-bootstrap";

const Login = () => {
    return (
        <Container>
            <Row>
                <Col sm={8} className="mt-md-5">
                    <h1>Login</h1>
                </Col>
            </Row>
        </Container>
    );
};

export default Login;