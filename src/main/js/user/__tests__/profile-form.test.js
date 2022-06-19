import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import ProfileForm from "../profile-form";

// Profile test data source
const DATA = [
  { firstName: "Foo", lastName: "Bar", email: "foo@bar.baz" },
  { firstName: "Jean", lastName: "Dorancy", email: "jdorancy@bu.edu" },
  {
    firstName: "Mr. Wonderful",
    lastName: "Shark",
    email: "mr.wonderful@shark.tank",
  },
  {
    firstName: "Test 123",
    lastName: "Alphanumeric 103",
    email: "test1223@alphanum231.co.uk",
  },
];

const INVALID_DATA = [
  { firstName: null, lastName: "Bar", email: "foo@bar.baz" },
  { firstName: "Jean", lastName: null, email: "jdorancy@bu.edu" },
  {
    firstName: "Mr. Wonderful",
    lastName: "",
    email: "mr.wonderful@shark.tank",
  },
  { firstName: "", lastName: "Alphanumeric 103", email: "test1223" },
  { firstName: "Test 123", lastName: "Alphanumeric 103", email: "test1223" },
  { firstName: "Test 123", lastName: "Alphanumeric 103", email: "" },
  { firstName: "Test 123", lastName: "Alphanumeric 103", email: null },
];

describe("Profile form component", () => {
  test("it renders", () => {
    render(
      <ProfileForm
        setIsChangePwd={jest.fn()}
        isChangePwd={false}
        user={{}}
        handleUserFormSubmit={jest.fn()}
      />
    );
  });

  test.each(DATA)(
    "it should allow the form to be filled and submit",
    async ({ firstName, lastName, email }) => {
      const handleUserFormSubmit = jest.fn();
      const user = userEvent.setup();
      render(
        <ProfileForm
          setIsChangePwd={jest.fn()}
          isChangePwd={false}
          user={{}}
          handleUserFormSubmit={handleUserFormSubmit}
        />
      );

      await user.type(screen.getByLabelText(/first name/i), firstName);
      await user.type(screen.getByLabelText(/last name/i), lastName);
      await user.type(screen.getByLabelText(/email address/i), email);
      await user.click(screen.getByRole("button", { name: /save/i }));

      await waitFor(() =>
        expect(handleUserFormSubmit).toHaveBeenCalledWith({
          firstName: firstName,
          lastName: lastName,
          email: email,
          password: "",
          newPassword: "",
        })
      );
    }
  );

  test.each(INVALID_DATA)(
    "it should require fields to have been filed",
    async ({ firstName, lastName, email }) => {
      const handleUserFormSubmit = jest.fn();
      const user = userEvent.setup();
      render(
        <ProfileForm
          setIsChangePwd={jest.fn()}
          isChangePwd={false}
          user={{}}
          handleUserFormSubmit={handleUserFormSubmit}
        />
      );

      await (firstName &&
        user.type(screen.getByLabelText(/first name/i), firstName));
      await (lastName &&
        user.type(screen.getByLabelText(/last name/i), lastName));
      await (email &&
        user.type(screen.getByLabelText(/email address/i), email));
      await user.click(screen.getByRole("button", { name: /save/i }));

      await waitFor(() =>
        expect(handleUserFormSubmit).toHaveBeenCalledTimes(0)
      );
    }
  );
});
