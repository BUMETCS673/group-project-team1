/**
 * Bank account component.
 *
 * @author Jean Dorancy
 */
import React from "react";
import { Card, Col, Dropdown, Row } from "react-bootstrap";
import { Bank2, GearFill } from "react-bootstrap-icons";

const BankAccount = (props) => {
  return (
    <Card className="m-2 shadow bg-white rounded">
      <Card.Body>
        <Row>
          <Col md={12}>
            <h5>{props.account.accountDescription}</h5>
          </Col>
        </Row>
        <Row className="m-2">
          <Col md={11}>
            <Bank2 size="75" />
          </Col>
          <Col md={1} className="mt-md-2">
            <Dropdown>
              <Dropdown.Toggle id="dropdown-basic" variant="secondary">
                <GearFill size="25" />
              </Dropdown.Toggle>
              <Dropdown.Menu>
                <Dropdown.Item
                  href="#"
                  onClick={() =>
                    props.handleBankAccountEditClick(props.account.id)
                  }
                >
                  Edit
                </Dropdown.Item>
                <Dropdown.Item
                  href="#"
                  onClick={() =>
                    props.handleBankAccountDeleteClick(props.account.id)
                  }
                >
                  Delete
                </Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          </Col>
        </Row>
        <Row className="mt-md-2">
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
