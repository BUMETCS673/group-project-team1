/**
 * All bank accounts page.
 *
 * @author Jean Dorancy
 */
import React from "react";
import Container from "react-bootstrap/Container";
import { Button, Col, Row } from "react-bootstrap";
import BankAccount from "./bank-account";
import BankAccountModal from "./bank-account-modal";
import ConfirmationModal from "./confirmation-modal";

const Account = (props) => {
  return (
    <Container className="mt-md-4">
      <Row className="me-lg-1 bg-white">
        <Col md={10}>
          <h3>All Accounts</h3>
        </Col>
        <Col md={2}>
          <Button
            onClick={() => props.setBankAccountModalShow(true)}
            variant="primary"
            className="mt-md-4 float-end"
          >
            Add Account
          </Button>
        </Col>
      </Row>
      {props.bankAccounts.map((account) => (
        <BankAccount
          key={account.id}
          account={account}
          setConfirmationModalShow={props.setConfirmationModalShow}
          handleBankAccountEditClick={props.handleBankAccountEditClick}
          handleBankAccountDeleteClick={props.handleBankAccountDeleteClick}
        />
      ))}
      <BankAccountModal
        selectedBankAccount={props.selectedBankAccount}
        show={props.bankAccountModalShow}
        onHide={() => props.setBankAccountModalShow(false)}
        handleBankAccountFormSubmit={props.handleBankAccountFormSubmit}
      />
      <ConfirmationModal
        selectedBankAccount={props.selectedBankAccount}
        show={props.confirmationModalShow}
        onHide={() => props.setConfirmationModalShow(false)}
        handleBankAccountDeleteConfirmation={
          props.handleBankAccountDeleteConfirmation
        }
      />
    </Container>
  );
};

export default Account;
