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
          <Col md={1} className="text-end">
            <Link
              to="#"
              onClick={() => props.handleBankAccountEditClick(props.account.id)}
            >
              Edit
            </Link>
          </Col>
          <Col md={1} className="text-end">
            <Link
              to="#"
              onClick={() =>
                props.handleBankAccountDeleteClick(props.account.id)
              }
            >
              Delete
            </Link>
          </Col>
        </Row>
        <Row className="mt-md-4">
          <Col md={8} className="text-start">
            {props.account.accountType} - {props.account.id}
          </Col>
          <Col md={4} className="text-end">
            <b>Balance:</b> ${props.account.balance}
          </Col>
        </Row>
      </Card.Body>
    </Card>
  );
};

export default BankAccount;
