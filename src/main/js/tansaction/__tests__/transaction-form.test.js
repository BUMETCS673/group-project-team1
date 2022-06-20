import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import TransactionForm from "../transaction-form";

// Accounts test data source
const DATA = [
  {
    id: 100,
    money: "1000",
    counterparty: "BU",
    transactionDescription: "Deposit",
    time: "06/16/2022",
    bankAccount: { id: 0, accountDescription: "test" },
  },
  {
    id: 9988333,
    money: "50",
    counterparty: "T-Mobile",
    transactionDescription: "Cellphone service",
    time: "05/30/2022",
    bankAccount: { id: 4003293, accountDescription: "Bills" },
  },
];

const INVALID_DATA = [
  {
    id: 100,
    money: "1000",
    counterparty: "",
    transactionDescription: "Deposit",
    time: "06/16/2022",
    bankAccount: { id: 0, accountDescription: "test" },
  },
  {
    id: 9988333,
    money: "",
    counterparty: "T-Mobile",
    transactionDescription: "",
    time: "05/30/2022",
    bankAccount: { id: 4003293, accountDescription: "Bills" },
  },
];

describe("Transaction form component", () => {
  test("it renders", () => {
    render(
      <TransactionForm
        selectedTransaction={{}}
        handleEditTransactions={jest.fn()}
        handleAddTransactions={jest.fn()}
        bankAccounts={[{ id: 0, accountDescription: "Test" }]}
        isAddTransaction={false}
      />
    );
  });

  test.each(DATA)(
    "it should allow the form to be filled and submit",
    async ({
      id,
      money,
      counterparty,
      transactionDescription,
      time,
      bankAccount,
    }) => {
      const handleEditTransactions = jest.fn();
      const handleAddTransactions = jest.fn();
      const user = userEvent.setup();
      render(
        <TransactionForm
          selectedTransaction={{
            id: id,
            money: money,
            counterparty: counterparty,
            transactionDescription: transactionDescription,
            time: time,
            bankAccount: bankAccount,
          }}
          bankAccounts={[bankAccount]}
          isAddTransaction={false}
          handleEditTransactions={handleEditTransactions}
          handleAddTransactions={handleAddTransactions}
        />
      );

      await user.type(screen.getByLabelText(/amount/i), money);
      await user.type(screen.getByLabelText(/counterparty/i), counterparty);
      await user.type(
        screen.getByLabelText(/description/i),
        transactionDescription
      );
      await user.click(screen.getByRole("button", { name: /save changes/i }));

      await waitFor(() => {
        expect(handleEditTransactions).toHaveBeenCalledTimes(1);
        expect(handleAddTransactions).toHaveBeenCalledTimes(0);
      });
    }
  );

  test.each(INVALID_DATA)(
    "it should require fields to have been filed",
    async ({
      id,
      money,
      counterparty,
      transactionDescription,
      time,
      bankAccount,
    }) => {
      const handleEditTransactions = jest.fn();
      const handleAddTransactions = jest.fn();
      const user = userEvent.setup();
      render(
        <TransactionForm
          selectedTransaction={{
            id: id,
            money: money,
            counterparty: counterparty,
            transactionDescription: transactionDescription,
            time: time,
            bankAccount: bankAccount,
          }}
          bankAccounts={[bankAccount]}
          isAddTransaction={false}
          handleEditTransactions={handleEditTransactions}
          handleAddTransactions={handleAddTransactions}
        />
      );

      await (money && user.type(screen.getByLabelText(/amount/i), money));
      await (counterparty &&
        user.type(screen.getByLabelText(/counterparty/i), counterparty));
      await (transactionDescription &&
        user.type(
          screen.getByLabelText(/description/i),
          transactionDescription
        ));
      await user.click(screen.getByRole("button", { name: /save changes/i }));

      await waitFor(() => {
        expect(handleEditTransactions).toHaveBeenCalledTimes(0);
        expect(handleAddTransactions).toHaveBeenCalledTimes(0);
      });
    }
  );
});
