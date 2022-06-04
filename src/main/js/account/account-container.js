/**
 * All accounts page container which handle managing bank accounts.
 *
 * @author Jean Dorancy
 */
import React from "react";
import Account from "./account";
import AccountService from "./account-service";
import { useEffect, useState } from "react";

const AccountContainer = (props) => {
  const service = new AccountService();
  const [bankAccounts, setBankAccounts] = useState([]);
  const [selectedBankAccount, setSelectedBankAccount] = useState({});
  const [modalShow, setModalShow] = useState(false);

  /**
   * Load all bank accounts on page load.
   */
  useEffect(() => {
    service
      .findAllBankAccount()
      .then(function (response) {
        setBankAccounts(response.data.additionalData || []);
      })
      .catch(function (error) {
        props.setAlert({
          show: true,
          variant: "danger",
          message: `${error.response.data.message}`,
        });
        setTimeout(() => props.setAlert({ show: false }), 2000);
      });
  }, []);

  /**
   * Handle click on bank account.
   *
   * @param id Bank account id
   */
  const handleBankAccountEditClick = (id) => {
    console.log("hello");
    service.findBankAccountById(id)
        .then(function (response) {
          console.log("set modal show");
          setModalShow(true);
          setSelectedBankAccount(response.data.additionalData || {});
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

  return (
      <Account
          modalShow={modalShow}
          setModalShow={setModalShow}
          handleBankAccountEditClick={handleBankAccountEditClick}
          selectedBankAccount={selectedBankAccount}
          bankAccounts={bankAccounts}
      />
  );
};

export default AccountContainer;
