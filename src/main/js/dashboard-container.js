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
  const [bankAccountId, setBankAccountId] = useState(0);
  const [transactions, setTransactions] = useState([]);
  const [transactionModalShow, setTransactionModalShow] = useState(false);
  const [transactionConfModalShow, setTransactionConfModalShow] =
    useState(false);

  /**
   * Handle click on bank account, init the bank accounts list and transactions list
   *
   * @param bankAccountId
   * @author Xiaobing Hou
   */
  const handleBankAccounts = (bankAccountId) => {
    accountService
      .findAllBankAccount()
      .then(function (response) {
        setBankAccounts(response.data.additionalData || []);
        if (bankAccountId) {
          setBankAccountId(bankAccountId);
          handleTransactions(setTransactions, bankAccountId);
        } else {
          setBankAccountId(response.data.additionalData[0].id);
          handleTransactions(
            setTransactions,
            response.data.additionalData[0].id
          );
        }
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
   * @param bankAccountId
   * @author Xiaobing Hou
   */
  const handleTransactions = (setTransactions, bankAccountId) => {
    transactionService
      .findAllTransactionsByBankId(bankAccountId)
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
        handleTransactions(setTransactions, bankAccountId);
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
   * @author Xiaobing Hou
   */
  const handleDelClick = () => {
    setTransactionConfModalShow(true);
  };

  /**
   * The purpose of this method is to handle the edit transaction feature
   *
   * @author Xiaobing Hou
   */
  const handleEditTransactions = (transactionId, value) => {
    transactionService
      .editTranByTranIdAndBankId(transactionId, value)
      .then(function (response) {
        handleTransactions(setTransactions, value.bankAccountId);
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
   * @author Xiaobing Hou
   */
  const handleEditClick = () => {
    setTransactionModalShow(true);
  };

  /**
   * The purpose of this method is to handle the add transaction feature
   *
   * @author Xiaobing Hou
   */
  const handleAddTransactions = (value) => {
    transactionService
      .addTransaction(value)
      .then(function (response) {
        handleTransactions(setTransactions, value.bankAccountId);
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
    setTransactionModalShow(true);
  };

  return (
    <Dashboard
      transactions={transactions}
      bankAccountId={bankAccountId}
      bankAccounts={bankAccounts}
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
      handleBankAccounts={handleBankAccounts}
    />
  );
};

export default DashboardContainer;
