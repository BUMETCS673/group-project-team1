import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import Logout from "../logout";
import userEvent from "@testing-library/user-event";

describe("Logout component", () => {
  test("it renders", () => {
    render(<Logout handleLogoutClick={jest.fn()} />);
  });

  test("logout click handler is called on click", async () => {
    const handleLogoutClick = jest.fn();
    const user = userEvent.setup();
    render(<Logout handleLogoutClick={handleLogoutClick} />);

    await user.click(screen.getByRole("button", { name: /logout/i }));

    await waitFor(() => expect(handleLogoutClick).toHaveBeenCalledTimes(1));
  });
});
