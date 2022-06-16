/**
 * User dashboard page which will present various data about accounts and transactions.
 *
 * @author Jean Dorancy
 * @editor Xiaobing Hou
 */

import React from "react";
import Container from "react-bootstrap/Container";
import { Col, Row } from "react-bootstrap";
import AccountPreview from "./account/account-preview";
import TransactionPreview from "./tansaction/transaction-preview";

const Dashboard = (props) => {
  return (
    <Container className="mt-md-4" fluid>
      <Row>
        <Col md={4}>
          <Row
            style={{ padding: "0px 0px 20px 0px" }}
            className="shadow me-lg-1 bg-white rounded border"
          >
            <Row>
              <h4
                style={{
                  margin: "10px 0px 0px 0px",
                }}
              >
                Accounts
              </h4>
            </Row>
            <Row>
              <AccountPreview bankAccounts={props.bankAccounts} />
            </Row>
          </Row>
        </Col>
        <Col md={8}>
          <Row className="shadow bg-white rounded border">
            <Row>
              <h4
                style={{
                  margin: "10px 0px 0px 0px",
                }}
              >
                Transactions
              </h4>
            </Row>
            <Row>
              <TransactionPreview
                transactions={props.transactions}
                bankAccounts={props.bankAccounts}
                selectedTransaction={props.selectedTransaction}
                isAddTransaction={props.isAddTransaction}
                setTransactionConfModalShow={props.setTransactionConfModalShow}
                transactionConfModalShow={props.transactionConfModalShow}
                handleDelClick={props.handleDelClick}
                handleEditClick={props.handleEditClick}
                handleAddTransactions={props.handleAddTransactions}
                handleAddClick={props.handleAddClick}
                transactionModalShow={props.transactionModalShow}
                setTransactionModalShow={props.setTransactionModalShow}
                handleEditTransactions={props.handleEditTransactions}
                handleDelTransactions={props.handleDelTransactions}
              />
            </Row>
          </Row>
        </Col>
      </Row>
    </Container>
  );
};

export default Dashboard;
