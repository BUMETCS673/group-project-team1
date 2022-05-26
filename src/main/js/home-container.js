/**
 * Home container to manage state and logic for the home page.
 *
 * @author Jean Dorancy
 */

import React from "react";
import Home from "./home";
import { useNavigate } from "react-router";
import TrackrUserService from "./user/trackr-user-service";

const HomeContainer = (props) => {
  const service = new TrackrUserService();
  const navigate = useNavigate();

  /**
   * Handle submit of the signup form by sending it to the backend.
   *
   * @param user Submit form values
   */
  const handleSignUpFormSubmit = (user) => {
    service
      .create(user)
      .then(function (response) {
        navigate("/login", { replace: true });
        props.setAlert({
          show: true,
          variant: "success",
          message: "Account successfully created!",
        });
        setTimeout(() => props.setAlert({ show: false }), 2000);
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
        // Error message
      });
  };

  return <Home handleSignUpFormSubmit={handleSignUpFormSubmit} />;
};

export default HomeContainer;