import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import BankAccountForm from "../bank-account-form";

// Accounts test data source
const DATA = [
  {
    accountType: "CHECKING",
    accountDescription: "Bills",
    balance: "100.25",
  },
  {
    accountType: "SAVING",
    accountDescription: "Emergency Fund",
    balance: "180.01",
  },
  {
    accountType: "OTHER",
    accountDescription: "Money Market",
    balance: "10000",
  },
  {
    accountType: "RETIREMENT",
    accountDescription: "ROTH IRA",
    balance: "700.00",
  },
];

const INVALID_DATA = [
  { accountType: "CHECKING", accountDescription: null, balance: "100.25" },
  { accountType: "SAVING", accountDescription: "", balance: "100.25" },
];

describe("Bank account form component", () => {
  test("it renders", () => {
    render(
      <BankAccountForm
        bankAccount={{}}
        handleBankAccountFormSubmit={jest.fn()}
      />
    );
  });

  test.each(DATA)(
    "it should allow the form to be filled and submit",
    async ({ accountType, accountDescription, balance }) => {
      const handleBankAccountFormSubmit = jest.fn();
      const user = userEvent.setup();
      render(
        <BankAccountForm
          bankAccount={{}}
          handleBankAccountFormSubmit={handleBankAccountFormSubmit}
        />
      );

      await user.selectOptions(
        screen.getByLabelText(/select type/i),
        accountType
      );
      await user.type(
        screen.getByLabelText(/description/i),
        accountDescription
      );
      await user.type(screen.getByLabelText(/balance/i), balance);
      await user.click(screen.getByRole("button", { name: /save changes/i }));

      await waitFor(() =>
        expect(handleBankAccountFormSubmit).toHaveBeenCalledWith({
          accountType: accountType,
          accountDescription: accountDescription,
          balance: `0${balance}`,
        })
      );
    }
  );

  test.each(INVALID_DATA)(
    "it should require fields to have been filed",
    async ({ accountType, accountDescription, balance }) => {
      const handleBankAccountFormSubmit = jest.fn();
      const user = userEvent.setup();
      render(
        <BankAccountForm
          bankAccount={{}}
          handleBankAccountFormSubmit={handleBankAccountFormSubmit}
        />
      );

      await (accountType &&
        user.selectOptions(screen.getByLabelText(/select type/i), accountType));
      await (accountDescription &&
        user.type(screen.getByLabelText(/description/i), accountDescription));
      await (balance && user.type(screen.getByLabelText(/balance/i), balance));
      await user.click(screen.getByRole("button", { name: /save changes/i }));

      await waitFor(() =>
        expect(handleBankAccountFormSubmit).toHaveBeenCalledTimes(0)
      );
    }
  );
});
