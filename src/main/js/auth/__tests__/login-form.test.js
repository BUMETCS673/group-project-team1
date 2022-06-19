import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import LoginForm from "../login-form";

// Logins test data source
const DATA = [
  { username: "user123", password: "password123" },
  { username: "mytestusername", password: "mytestpassword" },
  { username: "foo", password: "bar" },
];

const INVALID_DATA = [
  { username: "", password: "password123" },
  { username: "mytestusername", password: "" },
  { username: null, password: "bar" },
  { username: "foo", password: null },
  { username: null, password: null },
];

describe("Login form component tests", () => {
  test("it renders", () => {
    render(<LoginForm handleLoginSubmit={jest.fn()} />);
  });

  test.each(DATA)(
    "it should allow the form to be filled and submit",
    async ({ username, password }) => {
      const handleLoginSubmit = jest.fn();
      const user = userEvent.setup();
      render(<LoginForm handleLoginSubmit={handleLoginSubmit} />);

      await user.type(screen.getByLabelText(/username/i), username);
      await user.type(screen.getByLabelText(/password/i), password);
      await user.click(screen.getByRole("button", { name: /login/i }));

      await waitFor(() =>
        expect(handleLoginSubmit).toHaveBeenCalledWith({
          username: username,
          password: password,
        })
      );
    }
  );

  test.each(INVALID_DATA)(
    "it should require fields to have been filed",
    async ({ username, password }) => {
      const handleLoginSubmit = jest.fn();
      const user = userEvent.setup();
      render(<LoginForm handleLoginSubmit={handleLoginSubmit} />);

      await (username &&
        user.type(screen.getByLabelText(/username/i), username));
      await (password &&
        user.type(screen.getByLabelText(/password/i), password));
      await user.click(screen.getByRole("button", { name: /login/i }));

      await waitFor(() => expect(handleLoginSubmit).toHaveBeenCalledTimes(0));
    }
  );
});
