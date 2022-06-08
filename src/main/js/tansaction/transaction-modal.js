/**
 * Bank account modal component.
 *
 * @author Xiaobing Hou
 */
import React from "react";
import { FormGroup, FormLabel, Modal } from "react-bootstrap";
import TransactionForm from "./transaction-form";
import { Field } from "formik";

const TransactionModal = ({
  bankAccounts,
  bankAccountId,
  isAddTransaction,
  selectedTransaction,
  handleEditTransactions,
  handleAddTransactions,
  ...props
}) => {
  let title;
  if (!isAddTransaction) {
    title = (
      <Modal.Title id="contained-modal-title-vcenter">
        Bank account Id: {bankAccountId}
        <br />
        Transaction Id: {selectedTransaction ? selectedTransaction.id : "None"}
      </Modal.Title>
    );
  }
  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>{title}</Modal.Header>
      <Modal.Body>
        <TransactionForm
          selectedTransaction={selectedTransaction}
          handleEditTransactions={handleEditTransactions}
          handleAddTransactions={handleAddTransactions}
          bankAccountId={bankAccountId}
          bankAccounts={bankAccounts}
          isAddTransaction={isAddTransaction}
        />
      </Modal.Body>
    </Modal>
  );
};

export default TransactionModal;
