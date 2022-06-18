import React from "react";
import { render } from "@testing-library/react";
import Account from "../account";
import AccountPreview from "../account-preview";

describe("Account page component", () => {
  test("it renders the page", () => {
    render(
      <Account
        bankAccountModalShow={true}
        confirmationModalShow={true}
        selectedBankAccount={{}}
        bankAccounts={[]}
        setBankAccountModalShow={jest.fn()}
        setConfirmationModalShow={jest.fn()}
        handleBankAccountEditClick={jest.fn()}
        handleBankAccountDeleteClick={jest.fn()}
        handleBankAccountFormSubmit={jest.fn()}
        handleBankAccountDeleteConfirmation={jest.fn()}
      />
    );
  });

  test("it renders the account preview", () => {
    render(<AccountPreview bankAccounts={[]} />);
  });
});
