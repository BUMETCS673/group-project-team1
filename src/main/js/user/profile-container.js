import React from "react";
import Profile from "./profile";

const ProfileContainer = (props) => {

  const handleUserFormSubmit = (values) => {
    console.log(values);
  };

  return (
      <Profile
          user={{firstName: "Jean", lastName: "Dorancy", username: "jdorancy", email: "jdorancy@bu.edu"}}
          handleUserFormSubmit={handleUserFormSubmit}
      />
  );
};

export default ProfileContainer;
