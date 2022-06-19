import React from "react";
import Container from "react-bootstrap/Container";
import { Button, Table } from "react-bootstrap";
import TransactionModal from "./transaction-modal";
import TransactionConfModal from "./transaction-conf-modal";

/**
 * The purpose of this method is to generate the bank account preview table.
 *
 * @author Xiaobing Hou
 * @date 06/03/2022
 */
const TransactionPreview = (props) => {
  return (
    <Container
      style={{
        margin: "0px 0px 0px 0px",
      }}
    >
      <div
        style={{
          float: "right",
        }}
      >
        <Button
          style={{
            padding: "3px 5px 3px 5px",
            margin: "0px 0px 5px 0px",
          }}
          onClick={() => props.handleAddClick()}
          type={"button"}
        >
          Add transaction
        </Button>{" "}
      </div>

      <Table striped bordered hover>
        <thead>
          <tr valign={"middle"} align={"center"}>
            <th>Date</th>
            <th>Counterparty</th>
            <th>Description</th>
            <th>Account</th>
            <th>Amount</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {props.transactions.map((item) => (
            <tr key={item.id} valign={"middle"} align={"center"}>
              <td>{item.time}</td>
              <td>{item.counterparty}</td>
              <td>{item.transactionDescription}</td>
              <td>{item.bankAccount.accountDescription}</td>
              <td>${item.money}</td>
              <td>
                <Button
                  variant="link"
                  style={{
                    padding: "0px 0px 0px 0px",
                  }}
                  onClick={() => props.handleEditClick(item)}
                >
                  Edit
                </Button>
                <Button
                  style={{
                    padding: "0px 0px 0px 10px",
                  }}
                  variant="link"
                  onClick={() => props.handleDelClick(item)}
                >
                  Delete
                </Button>{" "}
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
      <TransactionModal
        selectedTransaction={props.selectedTransaction}
        onHide={() => props.setTransactionModalShow(false)}
        show={props.transactionModalShow}
        handleEditTransactions={props.handleEditTransactions}
        handleAddTransactions={props.handleAddTransactions}
        isAddTransaction={props.isAddTransaction}
        bankAccounts={props.bankAccounts}
      />
      <TransactionConfModal
        selectedTransaction={props.selectedTransaction}
        onHide={() => props.setTransactionConfModalShow(false)}
        show={props.transactionConfModalShow}
        handleDelTransactions={props.handleDelTransactions}
      />
    </Container>
  );
};

export default TransactionPreview;
