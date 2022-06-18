import React from "react";
import { render } from "@testing-library/react";
import TransactionPreview from "../transaction-preview";

describe("Transaction preview component", () => {
  test("it renders", () => {
    render(
      <TransactionPreview
        transactions={[
          {
            money: "100",
            counterparty: "BU",
            transactionDescription: "Tuition",
            time: "6/16/2022",
            bankAccount: { id: 0, accountDescription: "test" },
          },
        ]}
        bankAccounts={[{ id: 0, accountDescription: "test" }]}
        selectedTransaction={{
          money: "100",
          counterparty: "BU",
          transactionDescription: "Tuition",
          time: "6/16/2022",
          bankAccount: { id: 0, accountDescription: "test" },
        }}
        isAddTransaction={false}
        setTransactionConfModalShow={jest.fn()}
        transactionConfModalShow={false}
        handleDelClick={jest.fn()}
        handleEditClick={jest.fn()}
        handleAddTransactions={jest.fn()}
        handleAddClick={jest.fn()}
        transactionModalShow={jest.fn()}
        setTransactionModalShow={jest.fn()}
        handleEditTransactions={jest.fn()}
        handleDelTransactions={jest.fn()}
      />
    );
  });
});
