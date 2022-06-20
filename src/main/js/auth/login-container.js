/**
 * Login container to manage state and logic for the login page.
 *
 * @author Jean Dorancy
 */

import React from "react";
import Login from "./login";
import { useNavigate } from "react-router";
import TrackrUserService from "../user/trackr-user-service";

const LoginContainer = (props) => {
  const service = new TrackrUserService();
  const navigate = useNavigate();

  /**
   * Handle submit of the login form by authenticating with the backend.
   *
   * @param credentials Login form values
   */
  const handleLoginSubmit = (credentials) => {
    service
      .login(credentials)
      .then(function (response) {
        navigate("/dashboard", { replace: true });
        props.setAlert({
          show: true,
          variant: "success",
          message: "Successfully authenticated!",
        });
        setTimeout(() => props.setAlert({ show: false }), 2000);
      })
      .catch(function (error) {
        props.setAlert({
          show: true,
          variant: "danger",
          message: `${error.response.data.message}`,
        });
        setTimeout(() => props.setAlert({ show: false }), 2000);
      });
  };

  return <Login handleLoginSubmit={handleLoginSubmit} />;
};

export default LoginContainer;
