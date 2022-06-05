import React, { useEffect, useState } from "react";
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
  const [selectedTransaction, setSelectedTransaction] = useState();
  const [isAddTransaction, setIsAddTransaction] = useState(false);

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
          onClick={() => {
            setIsAddTransaction(true);
            props.handleAddClick();
          }}
          type={"button"}
        >
          Add transaction
        </Button>{" "}
      </div>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Date</th>
            <th>Counterparty</th>
            <th>Description</th>
            <th>Amount</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {props.transactions.map((item) => (
            <tr key={item.id}>
              <td valign={"middle"} align={"center"}>
                {item.time}
              </td>
              <td valign={"middle"} align={"center"}>
                {item.counterparty}
              </td>
              <td valign={"middle"} align={"center"}>
                {item.transactionDescription}
              </td>
              <td valign={"middle"} align={"center"}>
                ${item.money}
              </td>
              <td valign={"middle"} align={"center"}>
                <Button
                  variant="link"
                  style={{
                    padding: "0px 0px 0px 0px",
                  }}
                  onClick={() => {
                    props.handleEditClick();
                    setIsAddTransaction(false);
                    setSelectedTransaction(item);
                  }}
                >
                  Edit
                </Button>
                <Button
                  style={{
                    padding: "0px 0px 0px 10px",
                  }}
                  variant="link"
                  onClick={() => {
                    props.handleDelClick();
                    setSelectedTransaction(item);
                  }}
                >
                  Delete
                </Button>{" "}
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
      <TransactionModal
        selectedTransaction={selectedTransaction}
        onHide={() => props.setTransactionModalShow(false)}
        show={props.transactionModalShow}
        handleEditTransactions={props.handleEditTransactions}
        handleAddTransactions={props.handleAddTransactions}
        isAddTransaction={isAddTransaction}
        bankAccountId={props.bankAccountId}
      />
      <TransactionConfModal
        selectedTransaction={selectedTransaction}
        onHide={() => props.setTransactionConfModalShow(false)}
        show={props.transactionConfModalShow}
        handleDelTransactions={props.handleDelTransactions}
      />
    </Container>
  );
};

export default TransactionPreview;
