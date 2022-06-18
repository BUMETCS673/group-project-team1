import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import SignUpForm from "../sign-up-form";

// Profile test data source
const DATA = [
  {
    firstName: "Foo",
    lastName: "Bar",
    email: "foo@bar.baz",
    username: "foo",
    password: "mypassword",
  },
  {
    firstName: "Jean",
    lastName: "Dorancy",
    email: "jdorancy@bu.edu",
    username: "jdorancy",
    password: "password123",
  },
  {
    firstName: "Mr. Wonderful",
    lastName: "Shark",
    email: "mr.wonderful@shark.tank",
    username: "wonderful",
    password: "shark.thank12",
  },
  {
    firstName: "Test 123",
    lastName: "Alphanumeric 103",
    email: "test1223@alphanum231.co.uk",
    username: "alphanumeric",
    password: "@##$jladkjs**(0",
  },
];

const INVALID_DATA = [
  {
    firstName: null,
    lastName: "Bar",
    email: "foo@bar.baz",
    username: "alphanumeric",
    password: "@##$jladkjs**(0",
  },
  {
    firstName: "Jean",
    lastName: null,
    email: "jdorancy@bu.edu",
    username: "alphanumeric",
    password: "@##$jladkjs**(0",
  },
  {
    firstName: "Mr. Wonderful",
    lastName: "",
    email: "mr.wonderful@shark.tank",
    username: "alphanumeric",
    password: "@##$jladkjs**(0",
  },
  {
    firstName: "Hello",
    lastName: "Alphanumeric 103",
    email: null,
    username: "alphanumeric",
    password: "@##$jladkjs**(0",
  },
  {
    firstName: "Test 123",
    lastName: "Alphanumeric 103",
    email: "test1223",
    username: "alphanumeric",
    password: "@##$jladkjs**(0",
  },
  {
    firstName: "Test 123",
    lastName: "Alphanumeric 103",
    email: "test1223@alpha.com",
    username: null,
    password: "@##$jladkjs**(0",
  },
  {
    firstName: "Test 123",
    lastName: "Alphanumeric 103",
    email: "foo@bar.baz",
    username: "alphanumeric",
    password: "123",
  },
];

describe("Profile form component", () => {
  test("it renders", () => {
    render(<SignUpForm handleUserFormSubmit={jest.fn()} />);
  });

  test.each(DATA)(
    "it should allow the form to be filled and submit",
    async ({ firstName, lastName, email, username, password }) => {
      const handleUserFormSubmit = jest.fn();
      const user = userEvent.setup();
      render(<SignUpForm handleUserFormSubmit={handleUserFormSubmit} />);

      await user.type(screen.getByLabelText(/first name/i), firstName);
      await user.type(screen.getByLabelText(/last name/i), lastName);
      await user.type(screen.getByLabelText(/email address/i), email);
      await user.type(screen.getByLabelText(/username/i), username);
      await user.type(screen.getByLabelText(/password/i), password);
      await user.click(screen.getByRole("button", { name: /sign up/i }));

      await waitFor(() =>
        expect(handleUserFormSubmit).toHaveBeenCalledWith({
          firstName: firstName,
          lastName: lastName,
          email: email,
          username: username,
          password: password,
        })
      );
    }
  );

  test.each(INVALID_DATA)(
    "it should require fields to have been filed",
    async ({ firstName, lastName, email, username, password }) => {
      const handleUserFormSubmit = jest.fn();
      const user = userEvent.setup();
      render(<SignUpForm handleUserFormSubmit={handleUserFormSubmit} />);

      await (firstName &&
        user.type(screen.getByLabelText(/first name/i), firstName));
      await (lastName &&
        user.type(screen.getByLabelText(/last name/i), lastName));
      await (email &&
        user.type(screen.getByLabelText(/email address/i), email));
      await (username &&
        user.type(screen.getByLabelText(/username/i), username));
      await (password &&
        user.type(screen.getByLabelText(/password/i), password));
      await user.click(screen.getByRole("button", { name: /sign up/i }));

      await waitFor(() =>
        expect(handleUserFormSubmit).toHaveBeenCalledTimes(0)
      );
    }
  );
});
