/**
 * Bank confirmation modal component.
 *
 * @author Xiaobing Hou
 */
import React from "react";
import { Button, Modal } from "react-bootstrap";

const TransactionConfModal = ({
  selectedTransaction,
  handleDelTransactions,
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
          Transaction Id:{" "}
          {selectedTransaction ? selectedTransaction.id : "None"}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>Are you sure you want to delete this transaction?</Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={props.onHide}>
          No
        </Button>
        <Button
          variant="primary"
          onClick={() =>
            handleDelTransactions(
              selectedTransaction.id,
              selectedTransaction.bankAccount.id
            )
          }
        >
          Yes
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default TransactionConfModal;
