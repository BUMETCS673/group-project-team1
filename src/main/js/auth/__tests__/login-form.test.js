import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import LoginForm from "../login-form";

// Logins test data source
const LOGINS = [
  { username: "user123", password: "password123" },
  { username: "mytestusername", password: "mytestpassword" },
  { username: "foo", password: "bar" },
];

describe("Login form component tests", () => {
  test("it renders", () => {
    render(<LoginForm handleLoginSubmit={jest.fn()} />);
  });

  test.each(LOGINS)(
    "it should allow the form to be filled and submit",
    async ({ username, password }) => {
      const handleSubmit = jest.fn();
      render(<LoginForm handleLoginSubmit={handleSubmit} />);
      const user = userEvent.setup();

      await user.type(screen.getByLabelText(/username/i), username);
      await user.type(screen.getByLabelText(/password/i), password);

      await user.click(screen.getByRole("button", { name: /login/i }));

      await waitFor(() =>
        expect(handleSubmit).toHaveBeenCalledWith({
          username: username,
          password: password,
        })
      );
    }
  );

  test("it should require both username and password to be typed", async () => {
    const handleSubmit = jest.fn();
    render(<LoginForm handleLoginSubmit={handleSubmit} />);
    const user = userEvent.setup();

    await user.click(screen.getByRole("button", { name: /login/i }));

    await waitFor(() => expect(handleSubmit).toHaveBeenCalledTimes(0));
  });
});
