/**
 * Logout container to manage state and logic for the logout link.
 *
 * @author Jean Dorancy
 */

import React from "react";
import Logout from "./logout";
import TrackrUserService from "../user/trackr-user-service";
import { useNavigate } from "react-router";

const LogoutContainer = (props) => {
  const service = new TrackrUserService();
  const navigate = useNavigate();

  /**
   * Handle user logout action by setting the state to trigger components rendering.
   */
  const handleLogoutClick = () => {
    service
      .logout()
      .then(function (response) {
        props.setAlert({
          show: true,
          variant: "success",
          message: "Successfully logged out!",
        });
        setTimeout(() => props.setAlert({ show: false }), 2000);
        navigate("/login", { replace: true });
      })
      .catch(function (error) {
        props.setAlert({
          show: true,
          variant: "danger",
          message: "Logout attempt failed!",
        });
        setTimeout(() => props.setAlert({ show: false }), 2000);
      });
  };

  return <Logout handleLogoutClick={handleLogoutClick} />;
};

export default LogoutContainer;
