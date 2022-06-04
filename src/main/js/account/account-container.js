/**
 * All accounts page container which handle managing bank accounts.
 *
 * @author Jean Dorancy
 */
import React, { useEffect, useState } from "react";
import Account from "./account";
import AccountService from "./account-service";

const AccountContainer = (props) => {
  const service = new AccountService();
  const [bankAccounts, setBankAccounts] = useState([]);
  const [selectedBankAccount, setSelectedBankAccount] = useState({});
  const [bankAccountModalShow, setBankAccountModalShow] = useState(false);
  const [confirmationModalShow, setConfirmationModalShow] = useState(false);

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
  }, [bankAccountModalShow, confirmationModalShow]);

  /**
   * Handle click on bank account.
   *
   * @param id Bank account id
   */
  const handleBankAccountEditClick = (id) => {
    service
      .findBankAccountById(id)
      .then(function (response) {
        setBankAccountModalShow(true);
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

  /**
   * Handle bank account delete click.
   *
   * @param id Bank account id
   */
  const handleBankAccountDeleteClick = (id) => {
    service
      .findBankAccountById(id)
      .then(function (response) {
        setConfirmationModalShow(true);
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

  /**
   * Handle when bank account form is submitted in the modal.
   * - If new account then selected account is empty then no id
   * - if existing account then selected account has the id to update it
   *
   * @param values Form values
   */
  const handleBankAccountFormSubmit = (values) => {
    if (selectedBankAccount.id) {
      service
        .updateBankAccount(selectedBankAccount.id, values)
        .then(function (response) {
          setBankAccountModalShow(false);
          setSelectedBankAccount({});
          props.setAlert({
            show: true,
            variant: "success",
            message: "Successfully updated!",
          });
          setTimeout(() => props.setAlert({ show: false }), 2000);
        })
        .catch(function (error) {
          props.setAlert({
            show: true,
            variant: "danger",
            message: `${error.response.data.message}`,
          });
          setTimeout(() => props.setAlert({ show: false }), 2000);
        });
    } else {
      service
        .createBankAccount(values)
        .then(function (response) {
          setBankAccountModalShow(false);
          setSelectedBankAccount({});
          props.setAlert({
            show: true,
            variant: "success",
            message: "Successfully added!",
          });
          setTimeout(() => props.setAlert({ show: false }), 2000);
        })
        .catch(function (error) {
          props.setAlert({
            show: true,
            variant: "danger",
            message: `${error.response.data.message}`,
          });
          setTimeout(() => props.setAlert({ show: false }), 2000);
        });
    }
  };

  /**
   * Handle deleting a bank account by id.
   *
   * @param id Bank account id
   */
  const handleBankAccountDeleteConfirmation = (id) => {
    service
      .deleteBankAccountById(id)
      .then(function (response) {
        setConfirmationModalShow(false);
        setSelectedBankAccount({});
        props.setAlert({
          show: true,
          variant: "success",
          message: "Successfully deleted!",
        });
        setTimeout(() => props.setAlert({ show: false }), 2000);
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
      bankAccountModalShow={bankAccountModalShow}
      confirmationModalShow={confirmationModalShow}
      selectedBankAccount={selectedBankAccount}
      bankAccounts={bankAccounts}
      setBankAccountModalShow={setBankAccountModalShow}
      setConfirmationModalShow={setConfirmationModalShow}
      handleBankAccountEditClick={handleBankAccountEditClick}
      handleBankAccountDeleteClick={handleBankAccountDeleteClick}
      handleBankAccountFormSubmit={handleBankAccountFormSubmit}
      handleBankAccountDeleteConfirmation={handleBankAccountDeleteConfirmation}
    />
  );
};

export default AccountContainer;
