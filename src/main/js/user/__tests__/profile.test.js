import React from "react";
import { render } from "@testing-library/react";
import Profile from "../profile";

describe("Profile page component", () => {
  test("it renders the page", () => {
    render(
      <Profile
        user={{}}
        setIsChangePwd={jest.fn()}
        isChangePwd={false}
        handleUserFormSubmit={jest.fn()}
      />
    );
  });
});
