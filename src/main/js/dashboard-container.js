/**
 * Dashboard container to manage state and logic for the user dashboard page.
 *
 * @author Jean Dorancy
 */

import React, { useEffect, useState } from "react";
import Dashboard from "./dashboard";
import AccountService from "./account/account-service";

const DashboardContainer = (props) => {
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

  return <Dashboard bankAccounts={bankAccounts} />;
};

export default DashboardContainer;
