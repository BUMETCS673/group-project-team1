/**
 * Dashboard container to manage state and logic for the user dashboard page.
 *
 * @author Jean Dorancy
 * @editor Xiaobing Hou
 */

import React, { useEffect, useState } from "react";
import Dashboard from "./dashboard";
import AccountService from "./account/account-service";
import TransactionService from "./tansaction/transaction-service";

const DashboardContainer = (props) => {
  const accountService = new AccountService();
  const transactionService = new TransactionService();
  const [bankAccounts, setBankAccounts] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [transactionModalShow, setTransactionModalShow] = useState(false);
  const [transactionConfModalShow, setTransactionConfModalShow] =
    useState(false);
  const [selectedTransaction, setSelectedTransaction] = useState();
  const [isAddTransaction, setIsAddTransaction] = useState(false);

  /**
   * Handle click on bank account, init the bank accounts list and transactions list
   *
   * @author Xiaobing Hou
   */
  const handleBankAccounts = () => {
    accountService
      .findAllBankAccount()
      .then(function (response) {
        setBankAccounts(response.data.additionalData || []);
        handleTransactions(setTransactions);
      })
      .catch(function (error) {
        props.setAlert({
          show: true,
          variant: "danger",
          message: `${error.response.data.message}`,
        });
        setTimeout(() => props.setAlert({ show: false }), 2000);
      });
  };

  useEffect(() => {
    handleBankAccounts();
  }, []);

  /**
   * The purpose of this method is to get the data for transactions list
   *
   * @param setTransactions
   * @author Xiaobing Hou
   */
  const handleTransactions = (setTransactions) => {
    transactionService
      .findAllTransactions()
      .then(function (response) {
        setTransactions(response.data.additionalData || []);
      })
      .catch(function (error) {
        props.setAlert({
          show: true,
          variant: "danger",
          message: `${error.response.data.message}`,
        });
        setTimeout(() => props.setAlert({ show: false }), 2000);
      });
  };

  /**
   * The purpose of this method is to delete a transaction
   *
   * @param transactionId
   * @param bankAccountId
   * @author Xiaobing Hou
   */
  const handleDelTransactions = (transactionId, bankAccountId) => {
    transactionService
      .deleteTranByTranIdAndBankId(transactionId, bankAccountId)
      .then(function (response) {
        handleTransactions(setTransactions);
        setTransactionConfModalShow(false);
      })
      .catch(function (error) {
        props.setAlert({
          show: true,
          variant: "danger",
          message: `${error.response.data.message}`,
        });
        setTimeout(() => props.setAlert({ show: false }), 2000);
      });
  };

  /**
   * The purpose of this method is to set ConfModal showing state
   *
   * @param item
   * @author Xiaobing Hou
   */
  const handleDelClick = (item) => {
    setSelectedTransaction(item);
    setTransactionConfModalShow(true);
  };

  /**
   * The purpose of this method is to handle the edit transaction feature
   *
   * @param transactionId
   * @param value
   * @author Xiaobing Hou
   */
  const handleEditTransactions = (transactionId, value) => {
    transactionService
      .editTranByTranIdAndBankId(transactionId, value)
      .then(function (response) {
        handleTransactions(setTransactions);
        setTransactionModalShow(false);
      })
      .catch(function (error) {
        props.setAlert({
          show: true,
          variant: "danger",
          message: `${error.response.data.message}`,
        });
        setTimeout(() => props.setAlert({ show: false }), 2000);
      });
  };

  /**
   * The purpose of this method is to set transaction Modal showing state for editing
   *
   * @param item
   * @author Xiaobing Hou
   */
  const handleEditClick = (item) => {
    setIsAddTransaction(false);
    setSelectedTransaction(item);
    setTransactionModalShow(true);
  };

  /**
   * The purpose of this method is to handle the add transaction feature
   * @param value
   * @author Xiaobing Hou
   */
  const handleAddTransactions = (value) => {
    transactionService
      .addTransaction(value)
      .then(function (response) {
        handleTransactions(setTransactions);
        setTransactionModalShow(false);
      })
      .catch(function (error) {
        props.setAlert({
          show: true,
          variant: "danger",
          message: `${error.response.data.message}`,
        });
        setTimeout(() => props.setAlert({ show: false }), 2000);
      });
  };

  /**
   * The purpose of this method is to set transaction Modal showing state for adding
   *
   * @author Xiaobing Hou
   */
  const handleAddClick = () => {
    setIsAddTransaction(true);
    setTransactionModalShow(true);
  };

  return (
    <Dashboard
      transactions={transactions}
      bankAccounts={bankAccounts}
      selectedTransaction={selectedTransaction}
      isAddTransaction={isAddTransaction}
      transactionModalShow={transactionModalShow}
      setTransactionModalShow={setTransactionModalShow}
      handleEditClick={handleEditClick}
      setTransactionConfModalShow={setTransactionConfModalShow}
      transactionConfModalShow={transactionConfModalShow}
      handleDelClick={handleDelClick}
      handleAddTransactions={handleAddTransactions}
      handleAddClick={handleAddClick}
      handleEditTransactions={handleEditTransactions}
      handleDelTransactions={handleDelTransactions}
    />
  );
};

export default DashboardContainer;
