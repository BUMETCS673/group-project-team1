/**
 * User profile page which display a simple form for updating user info. Password change is not handled yet.
 *
 * @author Jean Dorancy
 */

import React from "react";
import Container from "react-bootstrap/Container";
import { Col, Row } from "react-bootstrap";
import { PersonBoundingBox } from "react-bootstrap-icons";
import ProfileForm from "./profile-form";

const Profile = (props) => {
  return (
    <Container className="mt-md-4">
      <Row className="me-lg-1">
        <Col md={12}>
          <h3>User Profile</h3>
        </Col>
      </Row>
      <Row className="mt-md-1 me-lg-1">
        <Col sm={2}>
          <PersonBoundingBox size="150" />
        </Col>
        <Col sm={4}>
          <ProfileForm
            setIsChangePwd={props.setIsChangePwd}
            isChangePwd={props.isChangePwd}
            user={props.user}
            handleUserFormSubmit={props.handleUserFormSubmit}
          />
        </Col>
      </Row>
    </Container>
  );
};

export default Profile;
