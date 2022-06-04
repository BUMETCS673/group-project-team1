/**
 * All bank accounts page.
 *
 * @author Jean Dorancy
 */
import React from "react";
import Container from "react-bootstrap/Container";
import { Row } from "react-bootstrap";
import BankAccount from "./bank-account";

const Account = (props) => {
  return (
    <Container className="mt-md-4">
      <Row className="me-lg-1 bg-white">
        <h3>All Accounts</h3>
      </Row>
      {props.bankAccounts.map((account) => (
        <BankAccount key={account.id} account={account} />
      ))}
    </Container>
  );
};

export default Account;
