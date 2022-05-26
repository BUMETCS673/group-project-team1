/**
 * Home container to manage state and logic for the home page.
 *
 * @author Jean Dorancy
 */

import React from "react";
import Home from "./home";

const HomeContainer = () => {
  /**
   * Handle submit of the signup form by sending it to the backend.
   *
   * @param values Submit form values
   */
  const handleSignUpFormSubmit = (values) => {
    setTimeout(() => {
      alert("Alert from contain component!");
      alert(JSON.stringify(values, null, 2));
    }, 400);
  };

  return <Home handleSignUpFormSubmit={handleSignUpFormSubmit} />;
};

export default HomeContainer;
