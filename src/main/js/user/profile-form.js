/**
 * The signup form built using Formik for form element and Yup for validation.
 *
 * @author Xiaobing Hou
 */

import React from "react";
import { ErrorMessage, Field, Form, Formik } from "formik";
import { Button, Col, FormGroup, FormLabel, Row } from "react-bootstrap";
import * as Yup from "yup";

const ProfileForm = (props) => {
  /**
   * Select validation schema.
   *
   * @returns Schema
   */
  const getValidationSchema = () => {
    if (props.isChangePwd) {
      return Yup.object({
        firstName: Yup.string()
          .max(100, "Must be 100 characters or less")
          .required("Required"),
        lastName: Yup.string()
          .max(100, "Must be 100 characters or less")
          .required("Required"),
        email: Yup.string()
          .max(50, "Must be 50 characters or less")
          .email("Invalid email address")
          .required("Required"),
        password: Yup.string()
          .min(6, "Must be at least 6 characters")
          .max(200, "Must be less than 200 characters")
          .required("Required"),
        newPassword: Yup.string()
          .min(6, "Must be at least 6 characters")
          .max(200, "Must be less than 200 characters")
          .required("Required"),
      });
    }

    // Not change password path
    return Yup.object({
      firstName: Yup.string()
        .max(100, "Must be 100 characters or less")
        .required("Required"),
      lastName: Yup.string()
        .max(100, "Must be 100 characters or less")
        .required("Required"),
      email: Yup.string()
        .max(50, "Must be 50 characters or less")
        .email("Invalid email address")
        .required("Required"),
      password: Yup.string(),
      newPassword: Yup.string(),
    });
  };

  return (
    <Formik
      enableReinitialize={true}
      initialValues={{
        firstName: props.user.firstName ? props.user.firstName : "",
        lastName: props.user.lastName ? props.user.lastName : "",
        email: props.user.email ? props.user.email : "",
        password: "",
        newPassword: "",
      }}
      validationSchema={getValidationSchema()}
      onSubmit={(values, actions) => {
        if (!props.isChangePwd) {
          values.password = "";
          values.newPassword = "";
        }
        props.handleUserFormSubmit(values);
        props.setIsChangePwd(false);
        actions.setSubmitting(false);
        actions.resetForm();
      }}
    >
      <Form className="form-control">
        <FormGroup className="mt-md-2">
          <FormLabel htmlFor="firstName" className="form-label col-md-5">
            First name
          </FormLabel>
          <Field
            id="firstName"
            type="text"
            name="firstName"
            className="form-control col-md-5"
          />
          <ErrorMessage name="firstName" component="div" />
        </FormGroup>
        <FormGroup className="mt-md-2">
          <FormLabel htmlFor="lastName" className="form-label col-md-5">
            Last name
          </FormLabel>
          <Field
            id="lastName"
            type="text"
            name="lastName"
            className="form-control col-md-5"
          />
          <ErrorMessage name="lastName" component="div" />
        </FormGroup>
        <FormGroup className="mt-md-2">
          <FormLabel htmlFor="email" className="form-label col-md-5">
            Email address
          </FormLabel>
          <Field
            id="email"
            type="email"
            name="email"
            className="form-control col-md-5"
          />
          <ErrorMessage name="email" component="div" />
        </FormGroup>

        {!props.isChangePwd && (
          <Button variant="link" onClick={() => props.setIsChangePwd(true)}>
            Change Password
          </Button>
        )}
        {props.isChangePwd && (
          <Row>
            <Col md={9}>
              <FormGroup className="mt-md-2">
                <FormLabel htmlFor="password" className="form-label col-md-9">
                  Old Password
                </FormLabel>
                <Field
                  id="password"
                  type="password"
                  name="password"
                  className="form-control col-md-5"
                />
                <ErrorMessage name="password" component="div" />

                <FormLabel
                  htmlFor="newPassword"
                  className="form-label col-md-9"
                >
                  New Password
                </FormLabel>
                <Field
                  id="newPassword"
                  type="password"
                  name="newPassword"
                  className="form-control col-md-5"
                />
                <ErrorMessage name="newPassword" component="div" />
              </FormGroup>
            </Col>
            <Col md={3}>
              <Button
                variant="link"
                onClick={() => props.setIsChangePwd(false)}
              >
                Cancel Change
              </Button>
            </Col>
          </Row>
        )}
        <Button type="submit" className="form-control btn-primary mt-md-2">
          <h5>Save</h5>
        </Button>
      </Form>
    </Formik>
  );
};

export default ProfileForm;
