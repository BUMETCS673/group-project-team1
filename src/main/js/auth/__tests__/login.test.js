import React from "react";
import { render } from "@testing-library/react";
import Login from "../login";

describe("Login page component tests", () => {
  test("it renders", () => {
    render(<Login handleLoginSubmit={jest.fn()} />);
  });
});
