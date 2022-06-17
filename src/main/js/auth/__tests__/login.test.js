import React from "react";
import { render } from "@testing-library/react";
import Login from "../login";

describe("Login page component", () => {
  test("it renders the page", () => {
    render(<Login handleLoginSubmit={jest.fn()} />);
  });
});
