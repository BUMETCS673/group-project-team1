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
  bankAccountId,
  isAddTransaction,
  selectedTransaction,
  handleEditTransactions,
  handleAddTransactions,
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
          Bank account Id: {bankAccountId}
          <br />
          Transaction Id:{" "}
          {selectedTransaction
            ? isAddTransaction
              ? "None"
              : selectedTransaction.id
            : "None"}
        </Modal.Title>
      </Modal.Header>
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
