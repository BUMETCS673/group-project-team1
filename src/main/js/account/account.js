import React from "react";
import Container from "react-bootstrap/Container";
import { Row } from "react-bootstrap";
import BankAccount from "./bank-account";

const dummy = [
  {
    id: 39903,
    type: "SAVINGS",
    description: "Game PC Fund",
    balance: 500,
  },
  {
    id: 155,
    type: "CHECKING",
    description: "Money for Bills",
    balance: 500,
  },
  {
    id: 4432,
    type: "MONEY MARKET",
    description: "House Fund",
    balance: 100230393009,
  },
];

const Account = (props) => {
  return (
    <Container className="mt-md-4">
      <Row className="me-lg-1 bg-white">
        <h3>All Accounts</h3>
      </Row>
      {dummy.map((account) => (
        <BankAccount key={account.id} account={account} />
      ))}
    </Container>
  );
};

export default Account;
