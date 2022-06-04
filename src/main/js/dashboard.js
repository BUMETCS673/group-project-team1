/**
 * User dashboard page which will present various data about accounts and transactions.
 *
 * @author Jean Dorancy
 */

import React from "react";
import Container from "react-bootstrap/Container";
import { Col, Row } from "react-bootstrap";
import Account from "./account/account"

const Dashboard = (props) => {
  return (
    <Container className="mt-md-4" fluid>
      <Row>
        <Col md={4}>
          <Row
            className="shadow me-lg-1 bg-white rounded"
            style={{
              border: "1px solid #000",
            }}
          >
            <Row>
              <h3
                style={{
                  margin: "10px 0px 30px 0px",
                }}
              >
                Accounts
              </h3>
            </Row>
            <Row id={"dashboardAccounts"}>
              <Account bankAccounts={props.bankAccounts}/>
            </Row>
          </Row>
        </Col>
        <Col md={8}>
          <Row className="shadow bg-white rounded">
            <Row>
              <h3>Transactions</h3>
            </Row>
            <Row>
              <Col>hello</Col>
              <Col>world</Col>
            </Row>
          </Row>
        </Col>
      </Row>
    </Container>
  );
};

export default Dashboard;
