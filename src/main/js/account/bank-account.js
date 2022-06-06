/**
 * Bank account component.
 *
 * @author Jean Dorancy
 */
import React from "react";
import { Button, Card, Col, Row } from "react-bootstrap";

const BankAccount = (props) => {
  return (
    <Card className="m-2 shadow bg-white rounded">
      <Card.Body>
        <Row>
          <Col md={10}>
            <h5>{props.account.accountDescription}</h5>
          </Col>
          <Col md={1}>
            <Button
              variant="link"
              className="float-end"
              onClick={() => props.handleBankAccountEditClick(props.account.id)}
            >
              Edit
            </Button>
          </Col>
          <Col md={1}>
            <Button
              variant="link"
              className="float-end"
              onClick={() =>
                props.handleBankAccountDeleteClick(props.account.id)
              }
            >
              Delete
            </Button>
          </Col>
        </Row>
        <Row className="mt-md-5">
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
