/**
 * Bank account modal component.
 *
 * @author Xiaobing Hou
 */
import React from "react";
import { Modal } from "react-bootstrap";
import TransactionForm from "./transaction-form";

const TransactionModal = ({
  bankAccounts,
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
        Account:{" "}
        {selectedTransaction
          ? selectedTransaction.bankAccount.accountDescription
          : "None"}
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
          bankAccounts={bankAccounts}
          isAddTransaction={isAddTransaction}
        />
      </Modal.Body>
    </Modal>
  );
};

export default TransactionModal;
