/**
 * All bank accounts page.
 *
 * @author Jean Dorancy
 */
import React from "react";
import Container from "react-bootstrap/Container";
import { Row } from "react-bootstrap";
import BankAccount from "./bank-account";
import BankAccountModal from "./bank-account-modal";

const Account = (props) => {
    console.log("selected bank account", props.selectedBankAccount);
  return (
    <Container className="mt-md-4">
      <Row className="me-lg-1 bg-white">
        <h3>All Accounts</h3>
      </Row>
      {props.bankAccounts.map((account) => (
        <BankAccount
            key={account.id}
            account={account}
            handleBankAccountEditClick={props.handleBankAccountEditClick}
        />
      ))}
        <BankAccountModal
            selectedBankAccount={props.selectedBankAccount}
            show={props.modalShow}
            onHide={() => props.setModalShow(false)}
        />
    </Container>
  );
};

export default Account;
