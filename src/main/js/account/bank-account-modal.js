/**
 * Bank account modal component.
 *
 * @author Jean Dorancy
 */
import React from "react";
import {Button, Modal} from "react-bootstrap";
import BankAccountForm from "./bank-account-form";

const BankAccountModal = ({selectedBankAccount, ...props}) => {
    return (
        <Modal
            {...props}
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    {selectedBankAccount.accountDescription}
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <h6>Bank Account Id: {selectedBankAccount.id}</h6>
                <BankAccountForm
                    bankAccount={selectedBankAccount}
                />
            </Modal.Body>
        </Modal>
    );
};

export default BankAccountModal;
