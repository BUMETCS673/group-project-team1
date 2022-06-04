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

  return <Account bankAccounts={bankAccounts} />;
};

export default AccountContainer;
