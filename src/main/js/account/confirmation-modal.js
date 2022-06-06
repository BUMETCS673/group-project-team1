/**
 * Bank confirmation modal component.
 *
 * @author Jean Dorancy
 */
import React from "react";
import { Button, Modal } from "react-bootstrap";

const ConfirmationModal = ({
  selectedBankAccount,
  handleBankAccountDeleteConfirmation,
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
      <Modal.Body>Are you sure you want to delete this account?</Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={props.onHide}>
          No
        </Button>
        <Button
          variant="primary"
          onClick={() =>
            handleBankAccountDeleteConfirmation(selectedBankAccount.id)
          }
        >
          Yes
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ConfirmationModal;
