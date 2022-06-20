import React, { useEffect, useState } from "react";
import Profile from "./profile";
import TrackrUserService from "./trackr-user-service";
import { useNavigate } from "react-router";

const ProfileContainer = (props) => {
  const navigate = useNavigate();
  const service = new TrackrUserService();
  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    username: "",
    password: "",
  });
  const [isChangePwd, setIsChangePwd] = useState(false);

  /**
   * The purpose of this method is to let user login again after change the username or password.
   *
   * @author Xiaobing Hou
   */
  const handleLogout = () => {
    service
      .logout()
      .then(function (response) {
        props.setAlert({
          show: true,
          variant: "success",
          message: "Please use new username and password to login again!",
        });
        setTimeout(() => props.setAlert({ show: false }), 2000);
        navigate("/login", { replace: true });
      })
      .catch(function (error) {
        props.setAlert({
          show: true,
          variant: "danger",
          message: "Fail to modify!",
        });
        setTimeout(() => props.setAlert({ show: false }), 2000);
      });
  };

  /**
   * Update user profile.
   *
   * @param values Form values.
   */
  const handleUserFormSubmit = (values) => {
    service
      .updateProfile(values)
      .then(function (response) {
        if (values.newPassword !== "") {
          handleLogout();
        }
        setUser(response.data.additionalData || {});
        props.setAlert({
          show: true,
          variant: "success",
          message: "Successfully updated!",
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
  /**
   * Get current user profile.
   */
  const getUserProfile = () => {
    service
      .getProfile()
      .then(function (response) {
        setUser(response.data.additionalData || {});
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

  useEffect(() => getUserProfile(), []);

  return (
    <Profile
      user={user}
      setIsChangePwd={setIsChangePwd}
      isChangePwd={isChangePwd}
      handleUserFormSubmit={handleUserFormSubmit}
    />
  );
};

export default ProfileContainer;
