/**
 * Bank account component.
 *
 * @author Jean Dorancy
 */
import React from "react";
import {Card, Col, Row} from "react-bootstrap";
import {Link} from "react-router-dom";

const BankAccount = (props) => {
    return (
        <Card className="m-2 p-2 shadow bg-white rounded">
            <Row>
                <Col md={10} className="text-left"><h5>{props.account.description}</h5></Col>
                <Col md={2} ><p className="text-right"><Link to="#">Edit</Link></p></Col>
            </Row>
            <Row>
                <Col md={10} className="text-left">{props.account.type} - {props.account.id}</Col>
                <Col md={2} className="text-right">${props.account.balance}</Col>
            </Row>
        </Card>
    );
};

export default BankAccount;