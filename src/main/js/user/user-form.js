/**
 * The signup form built using Formik for form element and Yup for validation.
 *
 * @author Jean Dorancy
 */

import React from "react";
import { ErrorMessage, Field, Form, Formik } from "formik";
import { Button, FormGroup, FormLabel } from "react-bootstrap";

const UserForm = (props) => {
  /**
   * Utility method to check if the user is logged in.
   *
   * @returns {boolean} Boolean
   */
  const isGuest = () => {
    return !props.hasOwnProperty("user");
  };

  return (
    <Formik
      enableReinitialize={true}
      initialValues={{
        firstName: isGuest() ? "" : props.user.firstName,
        lastName: isGuest() ? "" : props.user.lastName,
        email: isGuest() ? "" : props.user.email,
        username: "",
        password: "",
      }}
      validationSchema={props.validationSchema}
      onSubmit={(values, actions) => {
        props.handleUserFormSubmit(values);
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
            type="text"
            name="lastName"
            className="form-control col-md-5"
          />
          <ErrorMessage name="lastName" component="div" />
        </FormGroup>
        {isGuest() && (
          <FormGroup className="mt-md-2">
            <FormLabel htmlFor="username" className="form-label col-md-5">
              Username
            </FormLabel>
            <Field
              type="text"
              name="username"
              className="form-control col-md-5"
            />
            <ErrorMessage name="username" component="div" />
          </FormGroup>
        )}
        <FormGroup className="mt-md-2">
          <FormLabel htmlFor="email" className="form-label col-md-5">
            Email address
          </FormLabel>
          <Field type="email" name="email" className="form-control col-md-5" />
          <ErrorMessage name="email" component="div" />
        </FormGroup>
        {isGuest() && (
          <FormGroup className="mt-md-2">
            <FormLabel htmlFor="password" className="form-label col-md-5">
              Password
            </FormLabel>
            <Field
              type="password"
              name="password"
              className="form-control col-md-5"
            />
            <ErrorMessage name="password" component="div" />
          </FormGroup>
        )}
        {isGuest() && (
          <Button type="submit" className="form-control btn-primary mt-md-2">
            <h5>Sign Up!</h5>
          </Button>
        )}

        {!isGuest() && (
          <Button type="submit" className="form-control btn-primary mt-md-2">
            <h5>Save</h5>
          </Button>
        )}
      </Form>
    </Formik>
  );
};

export default UserForm;
