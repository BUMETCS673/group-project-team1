/**
 * Logout component.
 *
 * @author Jean Dorancy
 */

import React from "react";
import { Nav } from "react-bootstrap";

const Logout = (props) => {
  return (
    <Nav.Link href="#" onClick={props.handleLogoutClick}>
      Logout
    </Nav.Link>
  );
};

export default Logout;
