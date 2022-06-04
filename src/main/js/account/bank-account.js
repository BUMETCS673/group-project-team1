/**
 * Bank account component.
 *
 * @author Jean Dorancy
 */
import React from "react";
import { Card, Col, Row } from "react-bootstrap";
import { Link } from "react-router-dom";

const BankAccount = (props) => {
  return (
    <Card className="m-2 shadow bg-white rounded">
      <Card.Body>
        <Row>
          <Col md={10}>
            <h5>{props.account.accountDescription}</h5>
          </Col>
          <Col md={2} className="text-right">
            <Link to="#">Edit</Link>
          </Col>
        </Row>
        <Row>
          <Col md={10} className="text-left">
            {props.account.accountType} - {props.account.id}
          </Col>
          <Col md={2} className="text-right">
            ${props.account.balance}
          </Col>
        </Row>
      </Card.Body>
    </Card>
  );
};

export default BankAccount;
