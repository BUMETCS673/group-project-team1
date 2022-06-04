/**
 * Bank account modal component.
 *
 * @author Jean Dorancy
 */
import React from "react";
import { Modal } from "react-bootstrap";
import BankAccountForm from "./bank-account-form";

const BankAccountModal = ({
  selectedBankAccount,
  handleBankAccountFormSubmit,
  ...props
}) => {
  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Bank Account Id:{" "}
          {selectedBankAccount.id ? selectedBankAccount.id : "None"}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <BankAccountForm
          bankAccount={selectedBankAccount}
          handleBankAccountFormSubmit={handleBankAccountFormSubmit}
        />
      </Modal.Body>
    </Modal>
  );
};

export default BankAccountModal;
